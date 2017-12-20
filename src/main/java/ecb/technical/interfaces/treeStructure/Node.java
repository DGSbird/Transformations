package ecb.technical.interfaces.treeStructure;

import java.util.List;

/**
 * Node interface comprising methods for node structures.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            type implementing this interface
 */
@SuppressWarnings({ "rawtypes" })
public interface Node<T extends Node, S> {

    public S getData();

    public void setData(S data);

    public T getParent();

    default public void setParent(T parent) {
	setParent(parent, true);
    };

    public void setParent(T parent, boolean set);

    public List<T> getChildren();

    default public void setChildren(List<T> children) {
	setChildren(children, true);
    };

    public void setChildren(List<T> children, boolean set);

    default public T getChildAt(int index) throws IndexOutOfBoundsException {
	T child = null;
	if (hasChildren()) {
	    child = getChildren().get(index);
	}
	return child;
    };

    default public boolean hasChildren() {
	return (getChildren() != null && !getChildren().isEmpty());
    };

    default public int getNumberOfChildren() {
	int rValue = 0;
	if (hasChildren()) {
	    rValue = getChildren().size();
	}
	return rValue;
    };

    default public void addChild(T child) {
	addChild(child, true);
    }

    default public void addChild(T child, boolean add) {
	addChild(child, add, getChildren().size());
    };

    default public void addChild(T child, int position) {
	addChild(child, true, position);
    };

    public void addChild(T child, boolean add, int position);

    default public void removeChild(T child) {
	removeChild(child, true);
    };

    public void removeChild(T child, boolean remove);
}
