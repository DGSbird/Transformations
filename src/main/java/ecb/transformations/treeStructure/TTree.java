package ecb.transformations.treeStructure;

import java.util.ArrayList;
import java.util.List;

import ecb.generalObjects.treeStructure.interfaces.Tree;
import ecb.transformations.interfaces.components.Similar;
import ecb.transformations.interfaces.components.WebComponent;

/**
 * Implementation of the {@link Tree} interface in order to provide a
 * transformation tree comprised of {@link TNode}s.<br>
 * Please note that the methods {@link #auxiliaryFind(TNode, Similar)} and
 * {@link #auxiliaryFindAll(TNode, Similar)} are overridden using
 * {@link Similar#isSimilar(Object)} instead of {@link #equals(Object)} in order
 * to reuse the tree structure.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            type extending {@link TNode}
 * @param <S>
 *            type implementing {@link Similar} and {@link WebComponent}
 *            interfaces
 */
public class TTree<T extends TNode<T, S>, S extends Similar & WebComponent> implements Tree<T, S> {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private T root;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public TTree() {
	super();
    }

    public TTree(T root) {
	setRoot(root);
    }

    // ----------------------------------------------------------
    // get / set methods (inherited from interface)
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
    // methods override (in order to use isSimilar(obj) instead of equals(obj)
    // ----------------------------------------------------------

    @Override
    public T auxiliaryFind(T currentNode, S dataToFind) {
	T returnNode = null;
	int i = 0;
	if (currentNode.getData().isSimilar(dataToFind)) {
	    returnNode = currentNode;
	} else if (currentNode.hasChildren()) {
	    while (returnNode == null && i < currentNode.getNumberOfChildren()) {
		returnNode = auxiliaryFind((T) currentNode.getChildAt(i), dataToFind);
		i++;
	    }
	}
	return returnNode;
    }

    @Override
    public List<T> auxiliaryFindAll(T currentNode, S dataToFind) {
	List<T> result = null;
	if (currentNode.getData().isSimilar(dataToFind)) {
	    result = new ArrayList<>();
	    result.add(currentNode);
	}
	if (currentNode.hasChildren()) {
	    int i = 0;
	    while (i < currentNode.getNumberOfChildren()) {
		List<T> rList = null;
		rList = auxiliaryFindAll((T) currentNode.getChildAt(i), dataToFind);
		if (rList != null && !rList.isEmpty()) {
		    if (result != null) {
			result.addAll(rList);
		    } else {
			result = rList;
		    }
		}
	    }
	}
	return result;
    }

}
