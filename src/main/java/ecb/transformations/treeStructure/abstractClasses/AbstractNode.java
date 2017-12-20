package ecb.transformations.treeStructure.abstractClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ecb.technical.interfaces.treeStructure.Node;

/**
 * Abstract node class implementing the {@link Node} interface.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            type implementing the {@link Node} interface
 * @param <S>
 *            type of data of this node
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractNode<T extends Node, S> implements Node<T, S>, Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = 575964929609153046L;

    protected S data;

    protected AbstractNode<T, S> parent;

    protected List<AbstractNode<T, S>> children;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public AbstractNode() {
	super();
	children = new ArrayList<>();
    }

    // ----------------------------------------------------------
    // methods
    // ----------------------------------------------------------

    public S getData() {
	return data;
    }

    public void setData(S data) {
	this.data = data;
    }

    @Override
    public T getParent() {
	return (T) parent;
    }

    @Override
    public void setParent(T parent, boolean set) {
	this.parent = (AbstractNode) parent;
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
	this.children = (List<AbstractNode<T, S>>) children;
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
	    } catch (IndexOutOfBoundsException indexException) {
		getChildren().add(child);
		System.out.println(indexException.getMessage());
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	if (add) {
	    child.setParent(this, false);
	}
    }

    @Override
    public void removeChild(T child) {
	if (getChildren().remove(child)) {
	    child.setParent(null);
	}
    }

}
