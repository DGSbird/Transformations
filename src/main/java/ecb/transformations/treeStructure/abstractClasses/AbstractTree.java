package ecb.transformations.treeStructure.abstractClasses;

import java.util.Map.Entry;

import ecb.technical.interfaces.treeStructure.Node;
import ecb.technical.interfaces.treeStructure.Tree;

/**
 * Abstract tree class implementing the {@link Tree} interface.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            type implementing the {@link Node} interface
 * @param <S>
 *            type of data of this tree's nodes
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractTree<T extends Node, S> implements Tree<T, S> {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final String tab = "\t";

    protected T root;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public AbstractTree() {
	super();
    }

    // ----------------------------------------------------------
    // inherited methods (from interface)
    // ----------------------------------------------------------

    @Override
    public T getRoot() {
	return root;
    }

    @Override
    public void setRoot(T root) {
	this.root = root;
    }

    // ----------------------------------------------------------
    // additional methods
    // ----------------------------------------------------------

    @Override
    public String toString() {
	String rString = new String();
	if (!isEmpty()) {
	    rString = build(true).toString();
	}
	return rString;
    }

    public String toStringWithDepth() {
	return toStringWithDepth(true);
    }

    public String toStringWithDepth(boolean order) {
	String rString = new String();
	if (!isEmpty()) {
	    for (Entry<T, Integer> entry : buildWithDepth(order).entrySet()) {
		for (int i = 0; i < entry.getValue(); i++) {
		    System.out.print(tab);
		}
		System.out.println(entry.getKey().toString().replace("\n", "").replace("\t", ""));
	    }
	}
	return rString;
    }

}
