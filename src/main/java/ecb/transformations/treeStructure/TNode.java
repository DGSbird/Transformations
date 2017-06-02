package ecb.transformations.treeStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.generalObjects.treeStructure.interfaces.Node;
import ecb.transformations.functions.Functions;
import ecb.transformations.interfaces.Code;
import ecb.transformations.interfaces.Similar;
import ecb.transformations.interfaces.TypeOfNode;
import ecb.transformations.interfaces.WebComponent;
import ecb.transformations.metadata.TContext;

/**
 * Transformation node class representing nodes of a transformation. The data
 * field of such a node must implement the {@link WebComponent} interface.<br>
 * TODO: implementation of getCode() method via related interface.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            type extending this class
 * @param <S>
 *            type extending {@link TComponent}
 */
@Entity
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TNode<T extends TNode, S extends Similar & WebComponent> implements Node<T, S>, Code, Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = 6776798141844716368L;

    /**
     * The (technical) identifier of this {@link TNode}
     */
    @Id
    @Column(name = "nodeId", nullable = false)
    private int nodeId;

    /**
     * The {@link TComponent} of this {@link TNode}
     */
    @OneToOne
    private TComponent component;

    /**
     * The parent of this {@link TNode}
     */
    @ManyToOne
    private TNode parent;

    /**
     * The children of this {@link TNode}
     */
    @OneToMany
    private List<TNode> children;

    /**
     * The VLT code of this {@link TNode} (already taken into consideration the
     * children's contribution)
     */
    @Column(name = "vtlCode", length = 100000)
    private String vtlCode;

    /**
     * The VTL code of this {@link TNode} in Html format as used for website
     * representation (already taken into consideration the children's
     * contribution)
     */
    @Column(name = "vtlHtmlCode", length = 100000)
    private String vtlHtmlCode;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public TNode() {
	super();
    }

    /**
     * Please note that this constructor does not establish a bidirectional
     * relation to the {@link #component}. The application must ensure that such
     * a bidirectional connection is established.
     * 
     * @param component
     *            the component related to this node
     */
    public TNode(TComponent component) {
	super();
	setComponent(component, false);
    }

    // ----------------------------------------------------------
    // methods passing objects from data field
    // ----------------------------------------------------------

    public String getExpression() {
	return getData().getExpression();
    }

    public String getType() {
	return getData().getType();
    }

    public String getComment() {
	return getData().getComment();
    }

    // ----------------------------------------------------------
    // get / set methods
    // ----------------------------------------------------------

    public int getNodeId() {
	return nodeId;
    }

    public void setNodeId(int nodeId) {
	this.nodeId = nodeId;
    }

    public String getVtlCode() {
	return vtlCode;
    }

    public void setVtlCode(String vtlCode) {
	this.vtlCode = vtlCode;
    }

    public String getVtlHtmlCode() {
	return vtlHtmlCode;
    }

    public void setVtlHtmlCode(String vtlHtmlCode) {
	this.vtlHtmlCode = vtlHtmlCode;
    }

    // ----------------------------------------------------------
    // additional get methods
    // ----------------------------------------------------------

    public TypeOfNode getTypeOfNOde() {
	return TContext.getTypeOfNode(getType());
    }

    // ----------------------------------------------------------
    // additional methods
    // ----------------------------------------------------------

    /**
     * Reorders the children of this {@link TNode} such that the order reflects
     * the order in which the terms of the children (i.e. the expressions) are
     * present in the expression of this node.
     */
    public void reorderChildren() {
	if (hasChildren()) {
	    String expression = getData().getExpression();
	    if (expression != null && !expression.isEmpty() && !expression.equals(TContext.origin)
		    && !expression.equals(TContext.eof)) {
		Map<Integer, Boolean> isOccupied = new HashMap<Integer, Boolean>();
		for (int i = 0; i < expression.length(); i++) {
		    isOccupied.put(i, false);
		}
		SortedMap<Integer, TNode<T, S>> map = new TreeMap<>();
		List<TNode<T, S>> children = (List<TNode<T, S>>) getChildren();
		Iterator<TNode<T, S>> it = children.iterator();
		while (it.hasNext()) {
		    TNode<T, S> child = it.next();
		    String s = child.getData().getExpression();

		    boolean found = false;
		    int position = 0;
		    do {
			position = expression.indexOf(s, position);
			if (position >= 0) {
			    if (!isOccupied.get(position)) {
				found = true;
				// put node into sorted map
				map.put(position, (TNode<T, S>) child);
				for (int i = 0; i < s.length(); i++) {
				    isOccupied.put(position + i, true);
				}
			    } else {
				// identify next non occupied character of the
				// expression following the position
				position++;
			    }
			} else {
			    System.err.println(
				    "WARNING: could not find string '" + s + "' in expression '" + expression + "'");
			    found = true;
			}
		    } while (!found && position >= 0);
		}

		List<TNode<T, S>> orderedChildren = null;
		if (map != null && !map.isEmpty()) {
		    orderedChildren = new ArrayList<>();
		    for (Entry<Integer, TNode<T, S>> entry : map.entrySet()) {
			orderedChildren.add(entry.getValue());
		    }
		    Iterator<TNode<T, S>> childIterator = orderedChildren.iterator();
		    while (childIterator.hasNext()) {
			TNode<T, S> child = childIterator.next();
			removeChild((T) child);
			addChild((T) child);
		    }
		}
	    }
	}
    }

    /**
     * Reverses the order of this node's children.
     */
    public void reverseOrderOfChildren() {
	List<T> refChildren = Functions.getReverseOrderedList(getChildren());
	Iterator<T> it = refChildren.iterator();
	while (it.hasNext()) {
	    T child = it.next();
	    removeChild(child);
	    addChild(child);
	    it.remove();
	}
    }

    public void letSiblingsBecomeChildren() {
	letSiblingsBecomeChildren(true);
    }

    /**
     * Reorders the structure of this node such that the siblings of this node
     * will be this node's children after this method is called
     * 
     * @param insertFirst
     *            a boolean variable specifying if the siblings should be
     *            inserted before any already existing children
     */
    public void letSiblingsBecomeChildren(boolean insertFirst) {
	if (getParent() != null) {
	    T parent = getParent();
	    List<T> children = parent.getChildren();
	    Iterator<T> it = children.iterator();
	    int index = 0;
	    while (it.hasNext()) {
		T child = it.next();
		if (!child.equals(this)) {
		    it.remove();
		    parent.removeChild(child);
		    if (insertFirst) {
			addChild(child, index);
			index++;
		    } else {
			addChild(child);
		    }
		}
	    }
	    parent.addChild(this);
	}
    }

    public TComponent getComponent() {
	return component;
    }

    public void setComponent(TComponent component) {
	setComponent(component, true);
    }

    public void setComponent(TComponent component, boolean set) {
	this.component = component;
	if (component != null && set) {
	    component.setNode(this, false);
	}
    }

    // ----------------------------------------------------------
    // methods inherited from node interface
    // ----------------------------------------------------------

    @Override
    public S getData() {
	return (S) getComponent();
    }

    @Override
    public void setData(S data) {
	setComponent((TComponent) data);
    }

    @Override
    public T getParent() {
	return (T) parent;
    }

    @Override
    public void setParent(T parent, boolean set) {
	this.parent = (TNode) parent;
	if (parent != null && set) {
	    parent.addChild(this, false);
	}
    }

    @Override
    public List<T> getChildren() {
	return (List<T>) children;
    }

    @Override
    public void setChildren(List<T> children, boolean set) {
	this.children = (List<TNode>) children;
	if (children != null && set) {
	    Iterator<T> it = children.iterator();
	    while (it.hasNext()) {
		T child = it.next();
		child.setParent(this, false);
	    }
	}
    }

    @Override
    public void addChild(T child, boolean add, int position) {
	if (getChildren().contains(child)) {
	    getChildren().set(getChildren().indexOf(child), child);
	} else {
	    try {
		getChildren().add(position, child);
	    } catch (Exception e) {
		System.out.println("WARNING [TNode.addChild()]: was not able to insert child at the given position ('"
			+ position + "', '" + getNumberOfChildren() + "')!");
		getChildren().add(child);
	    }
	}
	if (add) {
	    child.setParent(this, false);
	}
    }

    @Override
    public void removeChild(T child, boolean remove) {
	if (getChildren().remove(child)) {
	    if (remove) {
		child.setParent(null);
	    }
	}
    }

    // ----------------------------------------------------------
    // additional overrides
    // ----------------------------------------------------------

    @Override
    public String getCode(Syntax syntax, Representation representation) {
	String rString = new String();
	if (syntax.equals(Syntax.VTL)) {
	    if (representation.equals(Representation.STANDARD)) {
		rString = getVtlCode();
	    } else if (representation.equals(Representation.HTML)) {
		rString = getVtlHtmlCode();
	    } else {
		// TODO: implement other types of representation
	    }
	} else {
	    // TODO: implement other types of syntax
	}
	return rString;
    }

    // ----------------------------------------------------------
    // toString()
    // ----------------------------------------------------------

    @Override
    public String toString() {
	return getData().toString();
    }

}
