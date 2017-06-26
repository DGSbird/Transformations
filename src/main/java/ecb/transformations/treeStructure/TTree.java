package ecb.transformations.treeStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ecb.exceptions.NodeManipulationException;
import ecb.generalObjects.treeStructure.abstractClasses.AbstractTree;
import ecb.generalObjects.treeStructure.interfaces.Node;
import ecb.generalObjects.treeStructure.interfaces.Tree;
import ecb.transformations.enums.Bracket;
import ecb.transformations.functions.Functions;
import ecb.transformations.interfaces.components.Similar;
import ecb.transformations.interfaces.components.WebComponent;
import ecb.transformations.metadata.NodeClassification;
import ecb.transformations.metadata.TContext;
import ecb.transformations.operators.enums.DatasetOps;
import ecb.transformations.operators.enums.IdentificationNode;
import ecb.transformations.operators.enums.InvisibleOps;
import ecb.transformations.operators.enums.OpsWithFollowingOperands;
import ecb.transformations.operators.enums.OpsWithTwoOperands;

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
public class TTree<T extends TNode<T, S> & Node<T, S>, S extends TComponent> extends AbstractTree<T, S>
	implements Tree<T, S> {
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
	super();
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
		i++;
	    }
	}
	return result;
    }

    public List<T> findAllByType(String type) {
	return findAllByTypeOrExpression(type, true);
    }

    public List<T> findAllByType(List<String> types) {
	return findAllByTypeOrExpression(types, true);
    }

    public List<T> findAllByExpression(String expression) {
	return findAllByTypeOrExpression(expression, false);
    }

    public List<T> findAllByExpression(List<String> expressions) {
	return findAllByTypeOrExpression(expressions, false);
    }

    public List<T> auxiliaryFindAllByType(T currentNode, String type) {
	return auxiliaryFindAllByTypeOrExpression(currentNode, type, true);
    }

    public List<T> auxiliaryFindAllByType(T currentNode, List<String> types) {
	return auxiliaryFindAllByTypeOrExpression(currentNode, types, true);
    }

    public List<T> auxilaryFindAllByExpression(T currentNode, String expression) {
	return auxiliaryFindAllByTypeOrExpression(currentNode, expression, false);
    }

    public List<T> auxiliaryFindAllByExpression(T currentNode, List<String> expressions) {
	return auxiliaryFindAllByTypeOrExpression(currentNode, expressions, false);
    }

    private List<T> findAllByTypeOrExpression(String typeOrExpression, boolean type) {
	List<T> result = null;
	if (!isEmpty()) {
	    List<T> rList = null;
	    rList = auxiliaryFindAllByTypeOrExpression(getRoot(), typeOrExpression, type);
	    if (rList != null && !rList.isEmpty()) {
		result = new ArrayList<>();
		result.addAll(rList);
	    }
	}
	return result;
    }

    private List<T> findAllByTypeOrExpression(List<String> typeOrExpression, boolean type) {
	List<T> result = null;
	if (!isEmpty()) {
	    List<T> rList = null;
	    rList = auxiliaryFindAllByTypeOrExpression(getRoot(), typeOrExpression, type);
	    if (rList != null && !rList.isEmpty()) {
		result = new ArrayList<>();
		result.addAll(rList);
	    }
	}
	return result;
    }

    private List<T> auxiliaryFindAllByTypeOrExpression(T currentNode, String typeOrExpression, boolean type) {
	List<T> result = null;
	String comp = (type) ? currentNode.getType() : currentNode.getExpression();
	if (typeOrExpression.equals(comp)) {
	    result = new ArrayList<>();
	    result.add(currentNode);
	}
	if (currentNode.hasChildren()) {
	    int i = 0;
	    while (i < currentNode.getNumberOfChildren()) {
		List<T> rList = null;
		rList = auxiliaryFindAllByTypeOrExpression((T) currentNode.getChildAt(i), typeOrExpression, type);
		if (rList != null && !rList.isEmpty()) {
		    if (result != null) {
			result.addAll(rList);
		    } else {
			result = rList;
		    }
		}
		i++;
	    }
	}
	return result;
    }

    private List<T> auxiliaryFindAllByTypeOrExpression(T currentNode, List<String> typeOrExpression, boolean type) {
	List<T> result = null;
	String comp = (type) ? currentNode.getType() : currentNode.getExpression();
	if (typeOrExpression.contains(comp)) {
	    result = new ArrayList<>();
	    result.add(currentNode);
	}
	if (currentNode.hasChildren()) {
	    int i = 0;
	    while (i < currentNode.getNumberOfChildren()) {
		List<T> rList = null;
		rList = auxiliaryFindAllByTypeOrExpression((T) currentNode.getChildAt(i), typeOrExpression, type);
		if (rList != null && !rList.isEmpty()) {
		    if (result != null) {
			result.addAll(rList);
		    } else {
			result = rList;
		    }
		}
		i++;
	    }
	}
	return result;
    }

    // ----------------------------------------------------------
    // additional methods for tree manipulation
    // ----------------------------------------------------------

    /**
     * Replaces a node (i.e. <tt>futureChild</tt>) by another node (i.e.
     * <tt>node</tt>) in this tree. The replaced node becomes a child of the
     * other node (placed in the first position) while this other node takes the
     * (former) position of the futureChild (node).
     * 
     * @param futureChild
     *            the node becoming the child node
     * @param node
     *            the node taking the place of the (input parameter) futureChild
     */
    public void makeNodeChild(T futureChild, T node) throws NodeManipulationException {
	if (futureChild.getParent() != null) {
	    T parent = (T) futureChild.getParent();
	    int position = parent.getChildren().indexOf(futureChild);
	    parent.removeChild(futureChild);
	    node.getParent().removeChild(node);
	    parent.addChild(node, position);
	    node.addChild(futureChild, 0);
	} else {
	    throw new NodeManipulationException("Future child has no parent");
	}
    }

    /**
     * Replaces a node in the sense that this node's children remain in the tree
     * structure and become children of this node's parent. The (new) children
     * are placed in the position where the node was removed.
     * 
     * @param node
     *            the node to be replaced
     */
    public void omitNode(T node) {
	if (node.getParent() != null) {
	    T parent = (T) node.getParent();
	    int index = parent.getChildren().indexOf(node);
	    if (node.hasChildren()) {
		List<T> children = node.getChildren();
		Iterator<T> it = children.iterator();
		while (it.hasNext()) {
		    T child = (T) it.next();
		    it.remove();
		    node.removeChild(child);
		    parent.addChild(child, index);
		    index++;
		}
	    }
	    parent.removeChild(node);
	}
    }

    public T getNextSibling(T node) throws NodeManipulationException {
	return getSibling(node, true);
    }

    public T getPreviousSibling(T node) throws NodeManipulationException {
	return getSibling(node, false);
    }

    /**
     * Extracts the next or previous sibling of the (input parameter) node.
     * 
     * @param node
     *            the node who's sibling should be returned
     * @param nextOrPrevious
     *            <code>true</code> for next
     * @return
     */
    private T getSibling(T node, boolean nextOrPrevious) throws NodeManipulationException {
	T sibling = null;
	int nOp = (-1);
	if (nextOrPrevious) {
	    nOp = 1;
	}
	if (node != null) {
	    if (node.getParent() != null) {
		T parent = (T) node.getParent();
		int index = parent.getChildren().indexOf(node);
		if (parent.getChildAt(index + nOp) != null) {
		    sibling = (T) parent.getChildAt(index + nOp);
		} else {
		    System.out.println("WARNING [getSibling]: node has no next / previous sibling!");
		}
	    } else {
		throw new NodeManipulationException("Trying to manipulate root of this tree");
	    }
	}
	return sibling;
    }

    public void makeNextSiblingChild(T node) throws NodeManipulationException {
	makeSiblingChild(node, true);
    }

    public void makePreviousSiblingChild(T node) throws NodeManipulationException {
	makeSiblingChild(node, false);
    }

    public void makeNextSiblingChild(T node, int position) throws NodeManipulationException {
	makeSiblingChild(node, true, position);
    }

    public void makePreviousSiblingChild(T node, int position) throws NodeManipulationException {
	makeSiblingChild(node, false, position);
    }

    public void makeSiblingChild(T node, boolean next) throws NodeManipulationException {
	makeSiblingChild(node, next, node.getNumberOfChildren());
    }

    /**
     * Rearranges this tree structure such that the next sibling of the (input
     * parameter) node becomes this (input parameter) node's child after this
     * method is called
     * 
     * @param node
     *            the node getting a child
     * @param next
     *            boolean variable specifying if the next or the previous
     *            sibling should become the child of the (input parameter) node.
     *            <b>true</b> if next.
     * @param position
     *            the prefered position where the sibling should be placed when
     *            becoming a child
     */
    private void makeSiblingChild(T node, boolean next, int position) throws NodeManipulationException {
	int nOp = (-1);
	if (next) {
	    nOp = 1;
	}
	if (node != null) {
	    if (node.getParent() != null) {
		T parent = (T) node.getParent();
		int index = parent.getChildren().indexOf(node);
		if (parent.getChildAt(index + nOp) != null) {
		    T sibling = (T) parent.getChildAt(index + nOp);
		    becomeChild(node, sibling, position);
		} else {
		    System.out.println("WARNING [makeSiblingChild]: node has no next / previous sibling!");
		}
	    } else {
		throw new NodeManipulationException("Trying to manipulate the root of this tree");
	    }
	}
    }

    public void makeAllSiblingsChildren(T node) {
	node.letSiblingsBecomeChildren();
    }

    public void becomeChild(T parent, T child) {
	becomeChild(parent, child, parent.getNumberOfChildren());
    }

    /**
     * Rearranges this tree such that the (input parameter) child becomes the
     * (input parameter) parent's child.
     * 
     * @param parent
     *            the future parent
     * @param child
     *            the future child
     */
    public void becomeChild(T parent, T child, int position) {
	if (parent != null && child != null) {
	    if (child.getParent() != null) {
		T currentParent = (T) child.getParent();
		currentParent.removeChild(child);
		parent.addChild(child, position);
	    } else {
		System.out.println(
			"WARNING [becomeChild]: tried to make a node having no parent (i.e. the root of this tree) a child!");
	    }
	}
    }

    /**
     * rearranges the siblings of the given node such that they become children
     * of this node after this method is called
     * 
     * @param node
     *            the node who's siblings will be rearranged to children
     */
    public void siblingsToChildren(T node) {
	node.letSiblingsBecomeChildren();
    }

    /**
     * Searches for specific types of nodes in the tree structure (i.e.
     * <code>toFind</code>). If such a node is found this methods searches for
     * another specific node (<code>futureParent</code>) having the same parent
     * (i.e. a sibling) and performs the operations necessary to make the toFind
     * node the child of the futureParent node.
     * 
     * @param toFind
     *            the type of node that should be found
     * @param futureParent
     *            the other type of node that should be found
     * @param backwards
     *            <code>true</code> if the position of toFind is smaller then
     *            the position of the futureParent
     */
    public void findAndBecomeChildOf(S toFind, S futureParent, boolean backwards) {
	List<T> nodes = findAll(toFind);
	if (nodes != null && !nodes.isEmpty()) {
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		if (node.getParent() != null) {
		    T parent = (T) node.getParent();
		    int index = parent.getChildren().indexOf(node);
		    T futureParentNode = null;
		    boolean found = false;
		    boolean condition = false;
		    int i = index;
		    i = (backwards) ? i - 1 : i + 1;

		    int iMin = 0;
		    int iMax = parent.getNumberOfChildren();
		    do {
			T currentNode = (T) parent.getChildAt(i);
			if (currentNode.getData().equals(futureParent)) {
			    found = true;
			    futureParentNode = currentNode;
			} else {
			    i = (backwards) ? i - 1 : i + 1;
			}
			condition = (backwards) ? i >= iMin : i < iMax;
		    } while (condition && !found);
		    if (found) {
			it.remove();
			parent.removeChild(node);
			futureParentNode.addChild(node);
		    }
		}
	    }
	}
    }

    // ----------------------------------------------------------
    // specific methods used in the transformation process
    // ----------------------------------------------------------

    public static boolean isAlmostEmpty(String s) {
	return Functions.isAlmostEmpty(s);
    }

    public static boolean isEOF(String s) {
	return Functions.isEOF(s);
    }

    /**
     * Applies the method {@link #reduceLeaf(TNode)} to all leafs of this tree.
     */
    public void reduceLeafs() throws NodeManipulationException {
	List<T> leafs = getLeafs();
	Iterator<T> it = leafs.iterator();
	while (it.hasNext()) {
	    T leaf = it.next();
	    reduceLeaf(leaf);
	}
    }

    /**
     * Reduces a leaf (i.e. a node without children) of this tree. The leaf is
     * reduced iff the expression of this leaf's parent is equal to the content
     * of this leaf (except for round brackets covering the expression) and the
     * parent does only have one child (i.e. this leaf). <br>
     * Additionally (in case this branch of the tree is reduced) if the type of
     * this node equals the default type (i.e.
     * {@link TContext#CONST_TERMINAL_NODE_IMPL} and the parent's node is
     * different to the default type the resulting node's type is set to the
     * non-default type.<br>
     * Finally, in case this node's expression is almost empty (see
     * {@link #isAlmostEmpty(String)}) or is the end of file token (see
     * {@link #isEOF(String)}) this node is removed from the tree.
     * 
     * @param node
     *            a leaf of this tree
     */
    public void reduceLeaf(T node) throws NodeManipulationException {
	if (node != null && node.getData() != null) {
	    String nodeExpression = node.getExpression();
	    T parent = node.getParent();
	    boolean process = true;
	    while (process) {
		parent = (T) node.getParent();
		if (parent != null && parent.getData() != null) {
		    String parentExpression = parent.getExpression();
		    if (parentExpression.equals(nodeExpression) || (parentExpression.startsWith(Bracket.ROUND.getLeft())
			    && parentExpression.endsWith(Bracket.ROUND.getRight())
			    && parentExpression.substring(1, parentExpression.length() - 1).equals(nodeExpression))) {
			T pNode = (T) parent.getParent();
			pNode.removeChild(parent);
			pNode.addChild(node);
			String nodeType = node.getType();
			String parentType = parent.getType();
			if (nodeType.equals(TContext.CONST_TERMINAL_NODE_IMPL)
				&& !parentType.equals(TContext.CONST_TERMINAL_NODE_IMPL)) {
			    node.getData().setType(parentType);
			}
		    } else {
			process = false;
		    }

		} else {
		    process = false;
		}
	    }
	    if (isAlmostEmpty(nodeExpression) || isEOF(nodeExpression)) {
		parent = (T) node.getParent();
		if (parent != null) {
		    parent.removeChild(node);
		}
	    }
	}
    }

    /**
     * Applies the method {@link #reduceBranche(TNode)} to all branches (i.e.
     * nodes having a parent and at least one child) of this tree.
     */
    public void reduceBranches() {
	List<T> leafs = getLeafs();
	Iterator<T> it = leafs.iterator();
	while (it.hasNext()) {
	    T leaf = it.next();
	    T parent = (T) leaf.getParent();
	    while (parent != null) {
		reduceBranch(parent);
		parent = (T) parent.getParent();
	    }
	}
    }

    /**
     * Reduces the given branch (i.e. <tt>node</tt> object having a parent and
     * at least one child) of this tree. The branch is reduced in the sense that
     * if the expression of this branch is equal (not taking into consideration
     * brackets or a semicolon at the end of the expression) to the parent's
     * expression the parent is removed from the tree structure (and the given
     * branch takes the parent's former place).
     * 
     * @param node
     *            a node of this tree
     */
    public void reduceBranch(T node) {
	if (node != null && node.getData() != null) {
	    String nodeExpression = node.getExpression().replace("\n", "");
	    T parent = null;
	    boolean process = true;
	    while (process) {
		parent = (T) node.getParent();
		if (parent != null && parent.getData() != null) {
		    String parentExpression = parent.getExpression().replace("\n", "");
		    if (parentExpression.equals(nodeExpression)
			    || (parentExpression.startsWith(Bracket.ROUND.getLeft())
				    && parentExpression.endsWith(Bracket.ROUND.getRight())
				    && parentExpression.substring(1, parentExpression.length() - 1)
					    .equals(nodeExpression))
			    || (parentExpression + ";").equals(nodeExpression)) {
			T pNode = (T) parent.getParent();
			int index = pNode.getChildren().indexOf(parent);
			pNode.removeChild(parent);
			pNode.addChild(node, index);
		    } else {
			process = false;
		    }
		} else {
		    process = false;
		}
	    }
	}
    }

    /**
     * Applies the method {@link #removeSymbols(TNode)} to all nodes (except the
     * root) of this tree.
     */
    public void removeSymbols() {
	List<T> leafs = getLeafs();
	Iterator<T> it = leafs.iterator();
	while (it.hasNext()) {
	    T leaf = it.next();
	    removeSymbols(leaf);
	    T parent = (T) leaf.getParent();
	    while (parent != null) {
		removeSymbols(parent);
		parent = parent.getParent();
	    }
	}
    }

    /**
     * Checks if the expression of the given <tt>node</tt> is contained in
     * {@link TContext#remains}. If so the node is removed.
     * 
     * @param node
     *            a node of this tree
     */
    public void removeSymbols(T node) {
	String expression = node.getExpression();
	if (TContext.remains.contains(expression)) {
	    T parent = (T) node.getParent();
	    parent.removeChild(node);
	}
    }

    /**
     * Applies the method {@link #reorderChild(TNode)} to all children of the
     * root.
     */
    public void reorderAllChildren() {
	if (!isEmpty()) {
	    List<T> children = getRoot().getChildren();
	    Iterator<T> it = children.iterator();
	    while (it.hasNext()) {
		T child = it.next();
		reorderChild(child);
	    }
	}
    }

    /**
     * Reorders the children of the given <tt>node</tt> and applies this method
     * to all of the node's children.
     * 
     * @param node
     *            a node of this tree
     */
    public void reorderChild(T node) {
	node.reorderChildren();
	List<T> children = node.getChildren();
	Iterator<T> it = children.iterator();
	while (it.hasNext()) {
	    T child = it.next();
	    reorderChild(child);
	}
    }

    /**
     * Searches for all nodes of specific type, stores them in a list and
     * applies the method {@link #nextSiblingToChild(List)} to this list. The
     * types qualifying are stored in
     * {@link NodeClassification#nextSiblingToChildTypes}.
     * 
     * @throws NodeManipulationException
     */
    public void nextSiblingToChild() throws NodeManipulationException {
	for (String type : NodeClassification.nextSiblingToChildTypes) {
	    List<T> nodes = findAllByType(type);
	    nextSiblingToChild(nodes);
	}
    }

    /**
     * Applies the method {@link #makeNextSiblingChild(TNode)} to each member of
     * the input <tt>list</tt>.
     * 
     * @param list
     *            a list comprising nodes of this tree
     * @throws NodeManipulationException
     */
    public void nextSiblingToChild(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		makeNextSiblingChild(node);
	    }
	}
    }

    /**
     * Searches for all nodes of specific type, stores them in a list and
     * applies the method {@link #previousAndNextSiblingToChild(List)} to this
     * list. The types qualifying are stored in
     * {@link NodeClassification#previousAndNextSiblingToChildTypes}.
     * 
     * @throws NodeManipulationException
     */
    public void previousAndNextSiblingToChild() throws NodeManipulationException {
	for (String type : NodeClassification.previousAndNextSiblingToChildTypes) {
	    List<T> nodes = findAllByType(type);
	    previousAndNextSiblingToChild(nodes);
	}
    }

    /**
     * Applies the methods {@link #makePreviousSiblingChild(TNode)} and
     * {@link #makeNextSiblingChild(TNode)} to each member of the input
     * <tt>list</tt>.
     * 
     * @param list
     *            a list of nodes
     * @throws NodeManipulationException
     */
    public void previousAndNextSiblingToChild(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		makePreviousSiblingChild(node);
		makeNextSiblingChild(node);
	    }
	}
    }

    /**
     * Searches for all nodes of specific type, stores them in a list and
     * applies the method {@link #restructureDatasetOperators(List)} to this
     * list. The types qualifying are stored in
     * {@link NodeClassification#operatorsWithFollowingOps}.
     * 
     * @throws NodeManipulationException
     */
    public void restructureDatasetOperators() throws NodeManipulationException {
	List<String> operators = new ArrayList<>();
	operators.addAll(NodeClassification.operatorsWithFollowingOps);
	List<T> operatorNodes = findAllByType(operators);
	restructureDatasetOperators(operatorNodes);
    }

    /**
     * Applies the method {@link #restructureDatasetOperator(TNode)} to each
     * member of the input <tt>list</tt>.
     * 
     * @param list
     *            a list of nodes
     * @throws NodeManipulationException
     */
    public void restructureDatasetOperators(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		restructureDatasetOperator(node);
	    }
	}
    }

    /**
     * Applies the method {@link #makeNextSiblingChild(TNode)} to each following
     * sibling of the given <tt>node</tt>.
     * 
     * @param node
     *            a node representing a data set operator
     * @throws NodeManipulationException
     */
    public void restructureDatasetOperator(T node) throws NodeManipulationException {
	T parent = (T) node.getParent();
	int index = parent.getChildren().indexOf(node);
	int numberOfSiblings = parent.getNumberOfChildren();
	for (int i = 0; i < (numberOfSiblings - index) - 1; i++) {
	    makeNextSiblingChild(node);
	}
    }

    /**
     * Searches for all nodes of specific type, stores them in a list and
     * applies the method {@link #restructureAsNodes(List)} to this list. The
     * types qualifying are equal to {@link OpsWithTwoOperands#AS}.
     * 
     * @throws NodeManipulationException
     */
    public void restructureAsNodes() throws NodeManipulationException {
	String asString = OpsWithTwoOperands.AS.getTypeOfNode();
	List<T> asNodes = findAllByType(asString);
	restructureAsNodes(asNodes);
    }

    /**
     * Applies the method {@link #restructureAsNode(TNode)} to each member of
     * the input <tt>list</tt>.
     * 
     * @param list
     *            a list of nodes
     * @throws NodeManipulationException
     */
    public void restructureAsNodes(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		restructureAsNode(node);
	    }
	}
    }

    /**
     * Checks if the node previous to the input <tt>node</tt> is of type
     * {@link DatasetOps#RENAME}. If this is the case the first child node of
     * this rename node is removed from the rename structure and added to the
     * input <tt>node</tt> (on the first position). Otherwise (in case it's not
     * a rename node) the previous node becomes the child of the input
     * <tt>node</tt>.
     * 
     * @param node
     *            a node having the type {@link OpsWithTwoOperands#AS}
     * @throws NodeManipulationException
     */
    public void restructureAsNode(T node) throws NodeManipulationException {
	String rename = DatasetOps.RENAME.getTypeOfNode();
	T previous = (T) getPreviousSibling(node);
	String type = previous.getType();
	if (type.equals(rename)) {
	    T child = (T) previous.getChildAt(0);
	    previous.removeChild(child);
	    node.addChild(child, 0);
	} else {
	    makePreviousSiblingChild(node, 0);
	}

    }

    /**
     * Searches for all nodes of type {@link OpsWithTwoOperands#ROLE}, stores
     * them in a list and applies the method
     * {@link #previousAndNextSiblingToChild(List)} to this list.
     * 
     * @throws NodeManipulationException
     */
    public void restructureRoleNodes() throws NodeManipulationException {
	String role = OpsWithTwoOperands.ROLE.getTypeOfNode();
	List<T> nodes = findAllByType(role);
	previousAndNextSiblingToChild(nodes);
    }

    /**
     * Searches for all nodes of the following types:<br>
     * <li>{@link OpsWithFollowingOperands#IF}<br>
     * <li>{@link OpsWithFollowingOperands#ELSEIF}<br>
     * <li>{@link OpsWithFollowingOperands#THEN}<br>
     * and applies the method
     * {@link #findAndBecomeChildOf(TComponent, TComponent, boolean)}.
     * Additionally this method searches for all
     * {@link InvisibleOps#IF_THEN_ELSE} nodes and sets the type and expression
     * to {@link InvisibleOps#IF_THEN_ELSE#getTypeOfNode()}.
     * 
     * @throws NodeManipulationException
     */
    @SuppressWarnings("unchecked")
    public void restructureIfElseIfElseOperators() throws NodeManipulationException {
	// handle if, elseif, then nodes
	String ifString = OpsWithFollowingOperands.IF.getTypeOfNode();
	String elseIfString = OpsWithFollowingOperands.ELSEIF.getTypeOfNode();
	String thenString = OpsWithFollowingOperands.THEN.getTypeOfNode();
	S ifType = (S) new TComponent(ifString, ifString);
	S elseIfType = (S) new TComponent(elseIfString, elseIfString);
	S thenType = (S) new TComponent(thenString, thenString);
	findAndBecomeChildOf((S) thenType, (S) ifType, true);
	findAndBecomeChildOf((S) thenType, (S) elseIfType, true);

	// set expression and type of (invisible) if-then-else node
	String ifThenElseString = InvisibleOps.IF_THEN_ELSE.getTypeOfNode();
	List<T> nodes = findAllByType(ifThenElseString);
	if (nodes != null && !nodes.isEmpty()) {
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		node.getData().setExpression(ifThenElseString);
		node.getData().setType(ifThenElseString);
	    }
	}
    }

    /**
     * Searches for all nodes of type {@link IdentificationNode#COMMENT}, stores
     * them in a list and applies the method {@link #transferComments(List)} to
     * this list.
     * 
     * @throws NodeManipulationException
     */
    public void restructureComments() throws NodeManipulationException {
	String comment = IdentificationNode.COMMENT.getTypeOfNode();
	List<T> nodes = findAllByType(comment);
	transferComments(nodes);
    }

    /**
     * Applies the method {@link #transferComment(TNode)} to each member of the
     * input <tt>list</tt>.
     * 
     * @param list
     *            a list of nodes
     * @throws NodeManipulationException
     */
    public void transferComments(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		transferComment(node);
	    }
	}
    }

    public void transferComment(T node) {

    }

    // ----------------------------------------------------------
    // tree transformation methods
    // ----------------------------------------------------------

    /**
     * Removes redundant information from this tree structure by applying the
     * following methods (on this tree structure):<br>
     * <li>{@link #reduceLeafs()}<br>
     * <li>{@link #reduceBranches()}<br>
     * <li>{@link #removeSymbols()}<br>
     * Additionally this method applies:<br>
     * <li>{@link #reorderAllChildren()}<br>
     * in order to set the order of children of each node with respect to it's
     * expression.
     */
    private void prepareTree() {
	try {
	    // reduce leafs of this tree structure
	    reduceLeafs();
	    // reduce branches of this tree structure
	    reduceBranches();
	    // remove symbols that do not contain semantic meaning
	    removeSymbols();
	    // reorder children of each node of this tree structure
	    reorderAllChildren();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Manipulates this tree structure in order to comply with the BIRD
     * interpretation of the SDMX information model for transformations applying
     * the following methods:<br>
     * <li>{@link #nextSiblingToChild()}<br>
     * <li>{@link #previousAndNextSiblingToChild()}<br>
     * <li>{@link #restructureDatasetOperators()}<br>
     * <li>...<br>
     */
    private void reorganiseTreeStructure() {
	try {
	    nextSiblingToChild();

	    previousAndNextSiblingToChild();

	    restructureDatasetOperators();

	    restructureAsNodes();

	    restructureRoleNodes();

	    restructureIfElseIfElseOperators();

	    restructureComments();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Transforms this tree structure from the parse tree structure into the
     * corresponding valid tree structure reflecting the BIRD transformation
     * model
     */
    public void transform() {
	prepareTree();

	reorganiseTreeStructure();

	try {

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}