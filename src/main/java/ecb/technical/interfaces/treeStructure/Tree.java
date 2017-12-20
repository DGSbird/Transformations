package ecb.technical.interfaces.treeStructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Tree interface comprising methods for tree structure.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            type implementing the {@link Node} interface (i.e. a node)
 * @param <S>
 *            type of data stored in the node
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public interface Tree<T extends Node, S> {

    public T getRoot();

    public void setRoot(T root);

    default public boolean isEmpty() {
	return (getRoot() == null);
    };

    default public boolean exists(S dataToFind) {
	return (find(dataToFind) != null);
    };

    default public T find(S dataToFind) {
	T returnNode = null;
	if (getRoot() != null) {
	    returnNode = auxiliaryFind(getRoot(), dataToFind);
	}
	return returnNode;
    };

    default public T auxiliaryFind(T currentNode, S dataToFind) {
	T returnNode = null;
	int i = 0;
	if (currentNode.getData().equals(dataToFind)) {
	    returnNode = currentNode;
	} else if (currentNode.hasChildren()) {
	    while (returnNode == null && i < currentNode.getNumberOfChildren()) {
		returnNode = auxiliaryFind((T) currentNode.getChildAt(i), dataToFind);
		i++;
	    }
	}
	return returnNode;
    };

    default public int getNumberOfNodes() {
	int numberOfNodes = 0;
	if (!isEmpty()) {
	    numberOfNodes = auxiliaryGetNumberOfNodes(getRoot()) + 1;
	}
	return numberOfNodes;
    };

    default public int auxiliaryGetNumberOfNodes(T node) {
	int numberOfNodes = node.getNumberOfChildren();
	Iterator<Node> it = node.getChildren().iterator();
	while (it.hasNext()) {
	    T child = (T) it.next();
	    numberOfNodes += auxiliaryGetNumberOfNodes(child);
	}
	return numberOfNodes;
    };

    default List<T> findAll(S dataToFind) {
	List<T> result = null;
	if (!isEmpty()) {
	    List<T> rList = null;
	    rList = auxiliaryFindAll(getRoot(), dataToFind);
	    if (rList != null && !rList.isEmpty()) {
		result = new ArrayList<>();
		result.addAll(rList);
	    }
	}
	return result;
    };

    default List<T> auxiliaryFindAll(T currentNode, S dataToFind) {
	List<T> result = null;
	if (currentNode.getData().equals(dataToFind)) {
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
    };

    default public List<T> build(boolean order) {
	List<T> result = null;
	if (!isEmpty()) {
	    result = build(getRoot(), order);
	}
	return result;
    };

    default public List<T> build(T node, boolean order) {
	List<T> result = new ArrayList<>();
	if (order) {
	    buildPreOrder(node, result);
	} else {
	    buildPostOrder(node, result);
	}
	return result;
    };

    default public void buildPreOrder(T node, List<T> result) {
	result.add(node);
	Iterator<Node> it = node.getChildren().iterator();
	while (it.hasNext()) {
	    T child = (T) it.next();
	    buildPreOrder(child, result);
	}
    };

    default public void buildPostOrder(T node, List<T> result) {
	int iMax = node.getNumberOfChildren();
	for (int i = iMax; i >= 0; i--) {
	    T child = (T) node.getChildAt(i);
	    result.add(child);
	}
	result.add(node);
    };

    default public Map<T, Integer> buildWithDepth(boolean order) {
	Map<T, Integer> rMap = null;
	if (getRoot() != null) {
	    rMap = buildWithDepth(getRoot(), order);
	}
	return rMap;
    };

    default Map<T, Integer> buildWithDepth(T node, boolean order) {
	Map<T, Integer> result = new LinkedHashMap<T, Integer>();
	if (order) {
	    buildPreOrderWithDepth(node, result, 0);
	} else {
	    buildPostOrderWithDepth(node, result, 0);
	}
	return result;
    };

    default public void buildPreOrderWithDepth(T node, Map<T, Integer> result, int depth) {
	result.put(node, depth);
	Iterator<Node> it = node.getChildren().iterator();
	while (it.hasNext()) {
	    T child = (T) it.next();
	    buildPreOrderWithDepth(child, result, depth + 1);
	}
    };

    default public void buildPostOrderWithDepth(T node, Map<T, Integer> result, int depth) {
	int iMax = node.getNumberOfChildren();
	for (int i = iMax; i >= 0; i--) {
	    T child = (T) node.getChildAt(i);
	    buildPostOrderWithDepth(child, result, depth + 1);
	}
	result.put(node, depth);
    };

    default public int getDistanceToRoot(T node) {
	int distance = 0;
	T parent = (T) node.getParent();
	while (parent != null) {
	    distance++;
	    parent = (T) parent.getParent();
	}
	return distance;
    };

    default public List<T> getLeafs() {
	List<T> result = null;
	if (!isEmpty()) {
	    result = new ArrayList<>();
	    result.addAll(auxiliaryGetLeafs(getRoot()));
	}
	return result;
    };

    default public List<T> auxiliaryGetLeafs(T node) {
	List<T> result = new ArrayList<>();
	if (node.hasChildren()) {
	    Iterator<Node> it = node.getChildren().iterator();
	    while (it.hasNext()) {
		Node child = it.next();
		if (child.hasChildren()) {
		    result.addAll(auxiliaryGetLeafs((T) child));
		} else {
		    result.add((T) child);
		}
	    }
	}
	return result;
    };

    default Set<T> getAllNodes() {
	return getAllNodes(false);
    };

    default Set<T> getAllNodes(boolean includeRoot) {
	Set<T> result = new HashSet<>();
	List<T> leafs = getLeafs();
	Iterator<T> it = leafs.iterator();
	while (it.hasNext()) {
	    T leaf = it.next();
	    result.add(leaf);
	    T parent = (T) leaf.getParent();
	    while (parent != null) {
		result.add(parent);
		parent = (T) parent.getParent();
	    }
	}
	if (includeRoot) {
	    result.add(getRoot());
	}
	return result;
    };

    default public int getMaximumDistance() {
	int distance = 0;
	List<T> leafs = getLeafs();
	if (leafs != null && !leafs.isEmpty()) {
	    Iterator<T> it = leafs.iterator();
	    while (it.hasNext()) {
		T leaf = it.next();
		if (getDistanceToRoot(leaf) > distance) {
		    distance = getDistanceToRoot(leaf);
		}
	    }
	}

	return distance;
    };

    default public Set<T> getAllNodesWithDistance(int distance) {
	Set<T> result = new HashSet<>();
	Set<T> nodes = getAllNodes();
	Iterator<T> it = nodes.iterator();
	while (it.hasNext()) {
	    T node = it.next();
	    if (getDistanceToRoot(node) == distance) {
		result.add(node);
	    }
	}
	return result;
    };

}
