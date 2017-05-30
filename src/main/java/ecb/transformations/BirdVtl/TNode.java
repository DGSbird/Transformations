package ecb.transformations.BirdVtl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import ecb.generalObjects.treeStructure.abstractClasses.AbstractNode;
import ecb.transformations.functions.Functions;
import ecb.transformations.interfaces.TypeOfNode;
import ecb.transformations.interfaces.WebComponent;

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
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TNode<T extends TNode, S extends WebComponent> extends AbstractNode<T, S> implements Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = 6776798141844716368L;

    // ----------------------------------------------------------
    // methods passing objects from data field
    // ----------------------------------------------------------

    public String getExpression() {
	return getData().getExpression();
    }

    public TypeOfNode getType() {
	return getData().getType();
    }

    public String getComment() {
	return getData().getComment();
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

    // ----------------------------------------------------------
    // toString()
    // ----------------------------------------------------------

    @Override
    public String toString() {
	return getData().toString();
    }

}
