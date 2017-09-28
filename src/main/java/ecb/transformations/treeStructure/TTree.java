package ecb.transformations.treeStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ecb.codeBuilder.VtlBuilder;
import ecb.exceptions.NodeManipulationException;
import ecb.generalObjects.representation.enums.Representation;
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
import ecb.transformations.operators.enums.Leafs;
import ecb.transformations.operators.enums.MethodNode;
import ecb.transformations.operators.enums.OpsWithFollowingOperands;
import ecb.transformations.operators.enums.OpsWithTwoOperands;
import ecb.transformations.operators.enums.SpecialNode;

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
@SuppressWarnings({ "unchecked", "unused" })
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

    private T auxiliaryFindByType(T currentNode, List<String> list) {
	T result = null;
	if (list.contains(currentNode.getData().getType())) {
	    result = currentNode;
	} else {
	    if (currentNode.hasChildren()) {
		int i = 0;
		while (i < currentNode.getNumberOfChildren() && result == null) {
		    result = auxiliaryFindByType((T) currentNode.getChildAt(i), list);
		    i++;
		}
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
    private void makeNodeChild(T futureChild, T node) throws NodeManipulationException {
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
    private void omitNode(T node) {
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

    private T getNextSibling(T node) throws NodeManipulationException {
	return getSibling(node, true);
    }

    private T getPreviousSibling(T node) throws NodeManipulationException {
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

    private void makeNextSiblingChild(T node) throws NodeManipulationException {
	makeSiblingChild(node, true);
    }

    private void makePreviousSiblingChild(T node) throws NodeManipulationException {
	makeSiblingChild(node, false);
    }

    private void makeNextSiblingChild(T node, int position) throws NodeManipulationException {
	makeSiblingChild(node, true, position);
    }

    private void makePreviousSiblingChild(T node, int position) throws NodeManipulationException {
	makeSiblingChild(node, false, position);
    }

    private void makeSiblingChild(T node, boolean next) throws NodeManipulationException {
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

    private void makeAllSiblingsChildren(T node) {
	node.letSiblingsBecomeChildren();
    }

    private void becomeChild(T parent, T child) {
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
    private void becomeChild(T parent, T child, int position) {
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
    private void siblingsToChildren(T node) {
	node.letSiblingsBecomeChildren();
    }

    /**
     * Searches for specific types of nodes in this tree structure (i.e.
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
    private void findAndBecomeChildOf(S toFind, S futureParent, boolean backwards) {
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
			if (currentNode.getData().isSimilar(futureParent)) {
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

    private static boolean isAlmostEmpty(String s) {
	return Functions.isAlmostEmpty(s);
    }

    private static boolean isEOF(String s) {
	return Functions.isEOF(s);
    }

    /**
     * Applies the method {@link #reduceLeaf(TNode)} to all leafs of this tree.
     */
    private void reduceLeafs() throws NodeManipulationException {
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
    private void reduceLeaf(T node) throws NodeManipulationException {
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
    private void reduceBranches() {
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
    private void reduceBranch(T node) {
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
    private void removeSymbols() {
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
    private void removeSymbols(T node) {
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
    private void reorderAllChildren() {
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
    private void reorderChild(T node) {
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
    private void nextSiblingToChild() throws NodeManipulationException {
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
    private void nextSiblingToChild(List<T> list) throws NodeManipulationException {
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
    private void previousAndNextSiblingToChild() throws NodeManipulationException {
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
    private void previousAndNextSiblingToChild(List<T> list) throws NodeManipulationException {
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
    private void restructureDatasetOperators() throws NodeManipulationException {
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
    private void restructureDatasetOperators(List<T> list) throws NodeManipulationException {
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
    private void restructureDatasetOperator(T node) throws NodeManipulationException {
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
    private void restructureAsNodes() throws NodeManipulationException {
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
    private void restructureAsNodes(List<T> list) throws NodeManipulationException {
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
    private void restructureAsNode(T node) throws NodeManipulationException {
	String rename = DatasetOps.RENAME.getTypeOfNode();
	T previous = (T) getPreviousSibling(node);
	String type = previous.getType();
	if (type.equals(rename)) {
	    T child = (T) previous.getChildAt(0);
	    previous.removeChild(child);
	    node.addChild(child, 0);
	} else {
	    makePreviousSiblingChild(node, 0);
	    makeNextSiblingChild(node);
	}

    }

    /**
     * Searches for all nodes of type {@link OpsWithTwoOperands#ROLE}, stores
     * them in a list and applies the method
     * {@link #previousAndNextSiblingToChild(List)} to this list.
     * 
     * @throws NodeManipulationException
     */
    private void restructureRoleNodes() throws NodeManipulationException {
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
    private void restructureIfElseIfElseOperators() throws NodeManipulationException {
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
    private void restructureComments() throws NodeManipulationException {
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
    private void transferComments(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		transferComment(node);
	    }
	}
    }

    /**
     * Transfers the comment (in the form of the expression of the input
     * <tt>node</tt>) either:<br>
     * (1) to the following node (i.e. the next sibling) in case the type of
     * node of this sibling is comprised in
     * {@link NodeClassification#nextSiblingComment}, or<br>
     * (2) to the following node's first child in case the child's type is a
     * definition operator<br>
     * (3) to the following node's second child in case this child's type is
     * comprised in {@link NodeClassification#commentQualifyingIndicators}<br>
     * Please not that "transfer" refers to setting the comment of
     * {@link S#setComment(String)}.
     * 
     * @param node
     *            a <code>commentNode</code> node
     * @throws NodeManipulationException
     */
    private void transferComment(T node) throws NodeManipulationException {
	String comment = node.getExpression();

	boolean isCommentTransfered = false;
	String statement = IdentificationNode.STATEMENT.getTypeOfNode();
	String def = OpsWithTwoOperands.DEF.getTypeOfNode();

	T nextSibling = getNextSibling(node);
	String typeOfSibling = nextSibling.getType();

	if (NodeClassification.getNextSiblingComment().contains(typeOfSibling)) {
	    // comments for next sibling (e.g. if, elseif, else, ruleID nodes)
	    nextSibling.getData().setComment(comment);
	    isCommentTransfered = true;
	} else if (NodeClassification.getCommentQualifyingIndicators().contains(typeOfSibling)) {
	    // comments for functions, procedures and rule sets
	    T methodNode = (T) nextSibling.getChildAt(1);
	    String method = methodNode.getType();
	    if (NodeClassification.getCommentQualifyingMethodsTarget().contains(method)) {
		methodNode.getData().setComment(comment);
		isCommentTransfered = true;
	    }
	} else if (statement.equals(typeOfSibling)) {
	    // comments for statement nodes
	    T defNode = (T) nextSibling.getChildAt(0);
	    String defType = defNode.getType();
	    if (def.equals(defType)) {
		T var = (T) defNode.getChildAt(0);
		var.getData().setComment(comment);
		isCommentTransfered = true;
	    }
	}
	if (!isCommentTransfered) {
	    throw new NodeManipulationException("Could not transfer comment");
	}
    }

    /**
     * Applies the method {@link #restructureMethod(String)} for function,
     * procedur and datapoint definition nodes.
     */
    private void restructureMethods() throws NodeManipulationException {
	// restructure function definition
	String functionDef = IdentificationNode.CREATE_FUNCTION.getTypeOfNode();
	restructureMethod(functionDef);

	// restructure procedure definition
	String procedureDef = IdentificationNode.DEFINE_PROCEDURE.getTypeOfNode();
	restructureMethod(procedureDef);

	// restructure rule set definition
	String rulesetDef = IdentificationNode.DEFINE_DATAPOINT_RULESET.getTypeOfNode();
	restructureMethod(rulesetDef);
    }

    /**
     * Identifies <code>create function</code>, <code>define procedure</code>
     * and <code>DefineDatapointRuleset</code> nodes and applies the methods
     * {@link #restructureFunctionDef(BirdVtlNode, BirdVtlNode, BirdVtlNode, BirdVtlNode, BirdVtlNode)},
     * {@link #restructureProcedureDef(BirdVtlNode, BirdVtlNode, BirdVtlNode, BirdVtlNode, BirdVtlNode)},
     * {@link #restructureRulesetDef(BirdVtlNode, BirdVtlNode, BirdVtlNode, BirdVtlNode, BirdVtlNode)}
     * respectively.
     * 
     * @param methodDef
     *            <code>define procedure</code> for procedures,
     *            <code>create function</code> for functions and
     *            <code>DefineDatapointRuleset</code> for rule sets
     */
    private void restructureMethod(String methodDef) throws NodeManipulationException {
	String functionDef = IdentificationNode.CREATE_FUNCTION.getTypeOfNode();
	String procedureDef = IdentificationNode.DEFINE_PROCEDURE.getTypeOfNode();
	String rulesetDef = IdentificationNode.DEFINE_DATAPOINT_RULESET.getTypeOfNode();

	List<T> methodDefs = findAllByType(methodDef);
	if (methodDefs != null && !methodDefs.isEmpty()) {
	    Iterator<T> it = methodDefs.iterator();
	    while (it.hasNext()) {
		T methodDefNode = it.next();
		it.remove();
		T parent = (T) methodDefNode.getParent();
		if (parent != null) {
		    int index = parent.getChildren().indexOf(methodDefNode);
		    T methodID = (T) parent.getChildAt(index + 1);
		    T parameters = (T) parent.getChildAt(index + 2);
		    T body = (T) parent.getChildAt(index + 3);

		    if (methodDef.equals(functionDef)) {
			restructureFunctionDef(parent, methodDefNode, methodID, parameters, body);
		    } else if (methodDef.equals(procedureDef)) {
			restructureProcedureDef(parent, methodDefNode, methodID, parameters, body);
		    } else if (methodDef.equals(rulesetDef)) {
			restructureRulesetDef(parent, methodDefNode, methodID, parameters, body);
		    }
		} else {
		    System.err.println("[restructureMethod('" + methodDef + "')] WARNING: no parent!");
		}
	    }
	}
    }

    /**
     * Restructures nodes representing the definition of a function (including
     * the manipulation of <code>TerminalNodeImpl</code>) in the sense that this
     * tree structure (still reflecting the parse tree structure) is transformed
     * into the Bird interpretation of the sdmx information model for functions.
     * 
     * @param parent
     *            the parent of all (input) parameters
     * @param fDef
     *            the <code>create function</code> node
     * @param fID
     *            the <code>FunctionID</code> node
     * @param parameters
     *            the node containing the parameters of the procedure. Depending
     *            on the number of input parameters its one of the following
     *            type of nodes: <code>ArgList</code>, <code>Arg</code>
     * @param body
     *            the <code>returns</code> node
     */
    private void restructureFunctionDef(T parent, T fDef, T fID, T parameters, T body)
	    throws NodeManipulationException {
	String functionDef = InvisibleOps.FUNCTION_DEFINITION.getTypeOfNode();
	String functionInfo = MethodNode.FUNCTION_INFO.getTypeOfNode();
	String functionBody = InvisibleOps.FUNCTION_BODY.getTypeOfNode();
	String params = InvisibleOps.PARAMETERS.getTypeOfNode();

	String as = OpsWithTwoOperands.AS.getTypeOfNode();
	String dataset = Leafs.INPUT_DATASET.getTypeOfNode();

	String virtualDataset = Leafs.DATASET_ID.getTypeOfNode();
	String virtualVariable = Leafs.VARIABLE_ID.getTypeOfNode();

	fDef.getData().setExpression(functionDef);
	fDef.getData().setType(functionDef);

	T param = null;
	if (!parameters.getData().getType().equals(IdentificationNode.ARGUMENT.toString())) {
	    parameters.getData().setExpression(params);
	    parameters.getData().setType(params);
	} else {
	    param = (T) new TNode<>();
	    param.setData((S) new TComponent(params, params));
	    int index = parent.getChildren().indexOf(parameters);
	    parent.removeChild(parameters);
	    parent.addChild(param, index);
	    param.addChild(parameters);
	}

	List<String> relevantTypes = new ArrayList<>();
	relevantTypes.add(TContext.CONST_TERMINAL_NODE_IMPL);
	relevantTypes.add(IdentificationNode.ARGUMENT.getTypeOfNode());

	List<T> nodes = auxiliaryFindAllByType(parameters, relevantTypes);
	if (nodes != null && !nodes.isEmpty()) {
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T varNode = it.next();
		it.remove();
		T asNode = (T) varNode.getParent();
		if (asNode.getData().equals(new TComponent(as, as))) {
		    T type = (T) asNode.getChildAt(1);
		    String inputType = (type.getData().getType().equals(dataset)) ? virtualDataset : virtualVariable;
		    varNode.getData().setType(inputType);
		} else if (varNode.getData().getType().equals(IdentificationNode.ARGUMENT.toString())) {
		    varNode.getData().setType(virtualVariable);
		} else {
		    throw new NodeManipulationException(
			    "TerminalNodeImpl's parent is not an 'as' node and varNode is not an 'Arg' node!");
		}
	    }
	}

	T infoNode = (T) new TNode<>();
	infoNode.setData((S) new TComponent(functionInfo, functionInfo));

	parent.removeChild(fID);
	infoNode.addChild(fID);
	if (param != null) {
	    parent.removeChild(param);
	    infoNode.addChild(param);
	} else {
	    parent.removeChild(parameters);
	    infoNode.addChild(parameters);
	}

	fDef.addChild(infoNode, 0);

	// add return value to return node
	makeNextSiblingChild(body);

	T fBody = (T) new TNode<>();
	fBody.setData((S) new TComponent(functionBody, functionBody));

	parent.removeChild(body);
	fBody.addChild(body);

	fDef.addChild(fBody, 1);
    }

    /**
     * Restructures nodes representing the definition of a rule set in the sense
     * that the given structure (reflecting the parse tree) is transformed into
     * the Bird interpretation of the sdmx information model for rule sets.
     * 
     * @param parent
     *            the parent of all (input) nodes
     * @param rDef
     *            the <code>DefineDatapointRuleset</code> node
     * @param rID
     *            the <code>RulesetID</code> node
     * @param parameters
     *            the node containing the parameters of the ruleset. Depending
     *            on the number of input parameters its one of the following
     *            type of nodes: <code>RulesetArgList</code>,
     *            <code>RulesetArg</Code>, <code>VarID</code>
     * @param body
     *            the <code>RulesetBody</code> node
     */
    private void restructureRulesetDef(T parent, T rDef, T rID, T parameters, T body) {
	String rulesetDef = InvisibleOps.RULESET_DEFINITION.getTypeOfNode();
	String rulesetInfo = MethodNode.RULESET_INFO.getTypeOfNode();
	String rulesetBody = InvisibleOps.RULESET_BODY.getTypeOfNode();
	String params = InvisibleOps.PARAMETERS.getTypeOfNode();

	String varID = Leafs.VARIABLE_ID.getTypeOfNode();
	String ruleID = SpecialNode.RULE_ID.getTypeOfNode();

	rDef.getData().setExpression(rulesetDef);
	rDef.getData().setType(rulesetDef);

	body.getData().setExpression(rulesetBody);

	T infoNode = (T) new TNode<>();
	infoNode.setData((S) new TComponent(rulesetInfo, rulesetInfo));

	parent.removeChild(rID);
	infoNode.addChild(rID, 0);
	parent.removeChild(parameters);

	String typeOfParam = parameters.getData().getType();
	if (typeOfParam.equals(varID)) {
	    // create parameters node
	    T param = (T) new TNode<>();
	    param.setData((S) new TComponent(params, params));
	    parent.removeChild(parameters);
	    // add to info node
	    param.addChild(parameters);
	    infoNode.addChild(param, 1);
	} else {
	    parameters.getData().setType(params);
	    parameters.getData().setExpression(params);
	    infoNode.addChild(parameters, 1);
	}
	rDef.addChild(infoNode, 0);
	parent.removeChild(body);
	rDef.addChild(body);

	// restructure ruleID content
	List<T> rules = auxiliaryFindAllByType(body, ruleID);
	if (rules != null && !rules.isEmpty()) {
	    Iterator<T> it = rules.iterator();
	    while (it.hasNext()) {
		T rule = it.next();
		it.remove();
		makeAllSiblingsChildren(rule);
	    }
	}
    }

    /**
     * 
     * Restructures nodes representing the definition of a procedure (including
     * the manipulation of <code>input</code> / <code>output</code> nodes). This
     * method transforms the structure of this tree structure reflecting the
     * parse tree structure into the Bird interpretation of the sdmx information
     * model for procedures.
     * 
     * @param parent
     *            the parent of all other (input) nodes (i.e. the
     *            <code>DefProcedure</code> node)
     * @param pDef
     *            the <code>define procedure</code> node
     * @param pID
     *            the <code>ProcedureID</code> node
     * @param parameters
     *            the node containing the parameters of the procedure. Depending
     *            on the number of input parameters its one of the following
     *            type of nodes: <code>ProcedureArgList</code>,
     *            <code>ProcedureArg</code>
     * @param body
     *            the <code>ProcedureBody</code> node
     */
    private void restructureProcedureDef(T parent, T pDef, T pID, T parameters, T body)
	    throws NodeManipulationException {
	String procedureDef = InvisibleOps.PROCEDURE_DEFINITION.getTypeOfNode();
	String procedureInfo = MethodNode.PROCEDURE_INFO.getTypeOfNode();
	String procedureBody = InvisibleOps.PROCEDURE_BODY.getTypeOfNode();
	String input = OpsWithFollowingOperands.INPUT.getTypeOfNode();
	String output = OpsWithFollowingOperands.OUTPUT.getTypeOfNode();
	String as = OpsWithTwoOperands.AS.getTypeOfNode();
	String dataset = Leafs.INPUT_DATASET.getTypeOfNode();
	String params = InvisibleOps.PARAMETERS.getTypeOfNode();

	String virtualDataset = Leafs.DATASET_ID.getTypeOfNode();
	String virtualVariable = Leafs.VARIABLE_ID.getTypeOfNode();

	pDef.getData().setExpression(procedureDef);
	pDef.getData().setType(procedureDef);

	T infoNode = (T) new TNode<>();
	infoNode.setData((S) new TComponent(procedureInfo, procedureInfo));

	pDef.addChild(infoNode, 0);

	// transport from parent to info
	parent.removeChild(pID);
	infoNode.addChild((T) pID, 0);
	parameters.getData().setType(params);
	parameters.getData().setExpression(params);
	parent.removeChild(parameters);
	infoNode.addChild((T) parameters, 1);

	// handle input parameters of procedure definition
	List<T> inputNodes = auxiliaryFindAllByType(parameters, input);
	List<T> outputNodes = auxiliaryFindAllByType(parameters, output);
	List<T> nodes = new ArrayList<>();
	if (inputNodes != null) {
	    nodes.addAll(inputNodes);
	}
	if (outputNodes != null) {
	    nodes.addAll(outputNodes);
	}
	if (nodes != null && !nodes.isEmpty()) {
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T inputNode = it.next();
		it.remove();
		makeNextSiblingChild(inputNode);
	    }
	}

	// handle TerminalNodeImpl
	nodes = auxiliaryFindAllByType(parameters, TContext.CONST_TERMINAL_NODE_IMPL);
	if (nodes != null && !nodes.isEmpty()) {
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T varNode = it.next();
		it.remove();
		T asNode = (T) varNode.getParent();
		if (asNode.getData().equals(new TComponent(as, as))) {
		    T type = (T) asNode.getChildAt(1);
		    String inputType = (type.getData().getType().equals(dataset)) ? virtualDataset : virtualVariable;
		    varNode.getData().setType(inputType);
		} else {
		    new NodeManipulationException("WARNING: TerminalNodeImpl's parent is not an 'as' node!");
		}
	    }
	}

	body.getData().setExpression(procedureBody);
	parent.removeChild(body);
	pDef.addChild(body);
    }

    /**
     * Searches this tree structure for nodes where the 'right' child is a
     * container for one or many other children (e.g. the second child of an
     * <code>in</code> node). The following nodes are considered:<br>
     * <code>in</code>, <code>not in</code>, <code>keep</code>.
     */
    private void checkForParentsWithContainerChild() {
	List<String> typesToFind = new ArrayList<>();
	typesToFind.addAll(NodeClassification.getNodesWhereSecondChildIsContainer());
	List<T> list = findAllByType(typesToFind);
	checkIfChildIsContainer(list, 1);

	typesToFind = new ArrayList<>();
	typesToFind.addAll(NodeClassification.getNodesWhereFirstChildIsContainer());
	list = findAllByType(typesToFind);
	checkIfChildIsContainer(list, 0);
    }

    /**
     * Applies the method {@link #checkIfChildIsContainer(BirdVtlNode)} to all
     * members of the input list (i.e. <code>nodes</code>).
     * 
     * @param nodes
     *            a list of nodes
     */
    private void checkIfChildIsContainer(List<T> nodes, int position) {
	if (nodes != null && !nodes.isEmpty()) {
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		it.remove();
		checkIfChildIsContainer(node, position);
	    }
	}
    }

    /**
     * Checks if the child of a node (i.e. <code>node</code>) in a specific
     * position (i.e. <code>position</code>) is of type
     * {@link OtherNode#SET_MEMBER_LIST#getNodeType()}. If this is the case the
     * expression of this child is set to
     * {@link BirdObjectNode#PARAMETERS#getNodeType()}. Otherwise such a
     * 'parameter' node is created and included between the (input) node and the
     * child (at the specific position).
     * 
     * @param node
     *            the parent node where the child at position is to be checked
     * @param position
     *            the position of the child which should be a container
     */
    private void checkIfChildIsContainer(T node, int position) {
	String setMemberList = IdentificationNode.SET_MEMBER_LIST.getTypeOfNode();
	String procedureInputList = IdentificationNode.PROCEDURE_INPUT_LIST.getTypeOfNode();
	String strExprParam = IdentificationNode.STR_EXPR_PARAM.getTypeOfNode();
	String parameters = InvisibleOps.PARAMETERS.getTypeOfNode();

	T secondChild = (T) node.getChildAt(position);

	if (secondChild.getData().getType().equals(setMemberList)
		|| secondChild.getData().getType().equals(procedureInputList)
		|| secondChild.getData().getType().equals(strExprParam)) {
	    secondChild.getData().setExpression(parameters);
	    secondChild.getData().setType(parameters);
	} else {
	    T memberList = (T) new TNode<>();
	    memberList.setData((S) new TComponent(parameters, parameters));
	    int index = node.getChildren().indexOf(secondChild);
	    int numberOfChildren = node.getNumberOfChildren();
	    List<T> children = new ArrayList<>();
	    for (int i = index; i < numberOfChildren; i++) {
		children.add((T) node.getChildAt(i));
	    }
	    Iterator<T> it = children.iterator();
	    while (it.hasNext()) {
		T child = it.next();
		it.remove();
		node.removeChild(child);
		memberList.addChild(child);
	    }
	    // node.removeChild(secondChild);
	    // memberList.addChild(secondChild);
	    node.addChild(memberList, index);
	}
    }

    /**
     * Searches for clause nodes (e.g. <code>keep</code>, <code>filter</code>,
     * etc.) and applies the method {@link #restructureClauseOperators(List)}.
     */
    private void restructureClauseOperators() throws NodeManipulationException {
	List<String> clauseOperators = new ArrayList<>();
	clauseOperators.addAll(NodeClassification.getClauseOperators());
	List<T> clauseNodes = findAllByType(clauseOperators);
	restructureClauseOperators(clauseNodes);
    }

    /**
     * Applies the method {@link #restructureClauseOperator(BirdVtlNode)} to
     * each member of the input list.
     * 
     * @param clauseNodes
     *            a list of clause nodes
     */
    private void restructureClauseOperators(List<T> clauseNodes) throws NodeManipulationException {
	if (clauseNodes != null && !clauseNodes.isEmpty()) {
	    Iterator<T> it = clauseNodes.iterator();
	    while (it.hasNext()) {
		T clauseNode = it.next();
		it.remove();
		restructureClauseOperator(clauseNode);
	    }
	}
    }

    /**
     * Restructures a given clause node in the sense that finally the first
     * child of a clause node is the data set the operation is applied to.
     * 
     * @param clauseNode
     *            a clause node (e.g. <code>keep</code>, <code>filter</code>,
     *            etc.)
     */
    private void restructureClauseOperator(T clauseNode) throws NodeManipulationException {
	String joinBody = InvisibleOps.JOIN_BODY.getTypeOfNode();
	String def = OpsWithTwoOperands.DEF.getTypeOfNode();
	List<String> mathOps = new ArrayList<>();
	mathOps.addAll(NodeClassification.getMathOperators());

	T parent = (T) clauseNode.getParent();
	String type = parent.getData().getType();
	if (type.equals(joinBody)) {
	    parent.removeChild(clauseNode);
	    T joinExpression = (T) parent.getParent();
	    T joinExpressionParent = (T) joinExpression.getParent();
	    int index = joinExpressionParent.getChildren().indexOf(joinExpression);
	    joinExpressionParent.removeChild(joinExpression);
	    clauseNode.addChild(joinExpression, 0);
	    joinExpressionParent.addChild(clauseNode, index);
	} else if (type.equals(def) || mathOps.contains(type)) {
	    makePreviousSiblingChild(clauseNode, 0);
	} else {
	    throw new NodeManipulationException("WARNING: type of parent node is not covered in method!");
	}
    }

    /**
     * Searches for <code>JoinExpr</code> nodes and applies the method
     * {@link #restructureJoins(List)}.
     */
    private void restructureJoins() throws NodeManipulationException {
	String joinExpr = InvisibleOps.JOIN_EXPRESSION.getTypeOfNode();
	List<T> nodes = findAllByType(joinExpr);
	restructureJoins(nodes);
    }

    /**
     * Applies the method {@link #restructureJoinNode(BirdVtlNode)} to all
     * members of the input list.
     * 
     * @param joins
     *            a list of <code>JoinExpr</code> nodes
     */
    private void restructureJoins(List<T> joins) throws NodeManipulationException {
	String joinExpression = InvisibleOps.JOIN_EXPRESSION.getTypeOfNode();
	if (joins != null && !joins.isEmpty()) {
	    Iterator<T> it = joins.iterator();
	    while (it.hasNext()) {
		T join = it.next();
		it.remove();
		join.setData((S) new TComponent(joinExpression, joinExpression));
		restructureJoinNode(join);
	    }
	}
    }

    /**
     * 
     * @param joinNode
     *            a <code>JoinExpr</code> node
     */
    private void restructureJoinNode(T joinNode) throws NodeManipulationException {
	String joinClause = InvisibleOps.JOIN_CLAUSE.getTypeOfNode();
	String joinBody = InvisibleOps.JOIN_BODY.getTypeOfNode();
	String onString = OpsWithFollowingOperands.ON.getTypeOfNode();

	List<String> joinTypes = new ArrayList<>();
	joinTypes.addAll(NodeClassification.getTypesOfJoin());

	int numberOfChildren = joinNode.getNumberOfChildren();
	T clause = (T) joinNode.getChildAt(0);
	clause.getData().setExpression(joinClause);

	T joinType = auxiliaryFindByType(clause, joinTypes);
	int index = 0;
	if (joinType == null) {
	    joinType = getDefaultJoinTypeNode();
	}
	clause.removeChild(joinType);
	T typeOfJoin = getTypeOfJoinNode();
	typeOfJoin.addChild(joinType);

	T dSetNode1 = null;
	T dSetNode2 = null;
	dSetNode1 = (T) clause.getChildAt(index);
	dSetNode2 = (T) clause.getChildAt(index + 1);
	String dSetName1 = getDatasetName(dSetNode1);
	String dSetName2 = getDatasetName(dSetNode2);

	List<T> onList = auxiliaryFindAllByType(clause, onString);

	T on = onList.get(0);
	int position = clause.getChildren().indexOf(on);
	List<T> followingNodes = new ArrayList<>();
	for (int i = position + 1; i < clause.getNumberOfChildren(); i++) {
	    followingNodes.add((T) clause.getChildAt(i));
	}
	restructureOnNodeInJoin(on, followingNodes, dSetName1, dSetName2);
	clause.removeChild(on);

	T body = null;
	if (numberOfChildren == 1) {
	    body = (T) new TNode<>();
	    body.setData((S) new TComponent(joinBody, joinBody));
	} else if (numberOfChildren == 2) {
	    body = (T) joinNode.getChildAt(1);
	    body.getData().setExpression(joinBody);
	} else {
	    System.err.println("[restructureJoinNode] WARNING: joinExpr with #(children) != 1 or 2");
	}

	T datasetsToBeJoined = getDatasetsToBeJoinedNode();
	clause.removeChild(dSetNode1);
	clause.removeChild(dSetNode2);
	datasetsToBeJoined.addChild(dSetNode1);
	datasetsToBeJoined.addChild(dSetNode2);

	clause.addChild(typeOfJoin);
	clause.addChild(datasetsToBeJoined);
	clause.addChild(on);

	joinNode.removeChild(clause);
	joinNode.removeChild(body);

	joinNode.addChild(clause);
	joinNode.addChild(body);
    }

    /**
     * Extracts the name of the data set for the following cases:<br>
     * (1) setA --> setA<br>
     * (2) setA as "A" --> A
     * 
     * @param node
     *            either a <code>VarID</code> node or a
     *            <code>DatasetAlias</code> node
     * @return the name of the data set
     */
    private String getDatasetName(T node) {
	String rString = new String();
	String datasetAlias = IdentificationNode.DATASET_ALIAS.getTypeOfNode();
	String varID = Leafs.VARIABLE_ID.getTypeOfNode();
	String stringC = Leafs.STRING_CONSTANT.getTypeOfNode();

	String type = node.getData().getType();
	if (type.equals(varID)) {
	    rString = node.getData().getExpression();
	} else if (type.equals(datasetAlias)) {
	    List<T> nameNode = auxiliaryFindAllByType(node, stringC);
	    rString = nameNode.get(0).getData().getExpression().replace("\"", "");
	} else {
	    rString = null;
	}
	return rString;
    }

    /**
     * Manipulates the (node) structure of an <code>on</code> node in the sense
     * that an expression like "on x, y" gets transformed into "on
     * dSet1.x=dSet2.x and dSet1.y=dSet2.y".
     * 
     * @param onNode
     *            an <code>on</code> node
     * @param onFollowingNodes
     *            a list of all nodes following the <code>on</code> node having
     *            the same parent
     * @param dSet1
     *            the (string) name of the first data set
     * @param dSet2
     *            the (string) name of the second data set
     */
    private void restructureOnNodeInJoin(T onNode, List<T> onFollowingNodes, String dSet1, String dSet2)
	    throws NodeManipulationException {
	String terminalNodeImp = TContext.CONST_TERMINAL_NODE_IMPL;
	String componentID = Leafs.COMPONENT_ID.getTypeOfNode();
	String and = OpsWithTwoOperands.AND.getTypeOfNode();
	String varID = Leafs.VARIABLE_ID.getTypeOfNode();

	T firstFollowingNode = (T) onFollowingNodes.get(0);
	String type = firstFollowingNode.getData().getType();

	if (type.equals(terminalNodeImp) || type.equals(varID)) {
	    T parent = (T) onNode.getParent();
	    List<T> conditions = new ArrayList<>();
	    for (T node : onFollowingNodes) {
		node.getData().setType(componentID);
		parent.removeChild(node);
		T equalNode = generateEqualsNode(node, dSet1, dSet2);
		conditions.add((T) equalNode);
	    }
	    if (conditions.size() > 1) {
		Iterator<T> it = conditions.iterator();
		T currentAndNode = null;
		T andNode = (T) new TNode<>();
		andNode.setData((S) new TComponent(and, and));
		while (it.hasNext()) {
		    T node = it.next();
		    it.remove();
		    if (andNode.getNumberOfChildren() == 0) {
			if (currentAndNode == null) {
			    andNode.addChild(node);
			} else {
			    andNode.addChild(currentAndNode);
			    andNode.addChild(node);
			    currentAndNode = andNode;
			    andNode = (T) new TNode<>();
			    andNode.setData((S) new TComponent(and, and));
			}
		    } else {
			if (currentAndNode == null) {
			    andNode.addChild(node);
			    currentAndNode = andNode;
			    andNode = (T) new TNode<>();
			    andNode.setData((S) new TComponent(and, and));
			} else {
			    andNode.addChild(node);
			}
		    }
		}
		onNode.addChild(currentAndNode);
	    } else {
		onNode.addChild(conditions.get(0));
	    }
	} else {
	    makeNextSiblingChild(onNode);
	}
    }

    /**
     * map: "on x" --> (dSet1.x = dSet2.x)
     * 
     * @param node
     *            a node
     * @param dSet1
     *            the (string) name of the first data set
     * @param dSet2
     *            the (string) name of the second data set
     * @return the harmonised node representation of the input node
     */
    private T generateEqualsNode(T node, String dSet1, String dSet2) {
	String varID = Leafs.VARIABLE_ID.toString();
	String dot = OpsWithTwoOperands.DOT.toString();
	String equals = OpsWithTwoOperands.EQUALS.toString();

	T copy = (T) new TNode<>();
	copy.setData((S) new TComponent(node.getData().getType(), node.getData().getExpression()));
	T equalNode = (T) new TNode<>();
	equalNode.setData((S) new TComponent(equals, equals));
	T dotNode1 = (T) new TNode<>();
	dotNode1.setData((S) new TComponent(dot, dot));
	T dotNode2 = (T) new TNode<>();
	dotNode2.setData((S) new TComponent(dot, dot));
	T set1 = (T) new TNode<>();
	set1.setData((S) new TComponent(varID, dSet1));
	T set2 = (T) new TNode<>();
	set2.setData((S) new TComponent(varID, dSet2));
	dotNode1.addChild(set1);
	dotNode1.addChild(node);
	dotNode2.addChild(set2);
	dotNode2.addChild(copy);
	equalNode.addChild(dotNode1);
	equalNode.addChild(dotNode2);
	return (T) equalNode;
    }

    /**
     * Removes all nodes from this tree structure that are not matching an
     * element in the list {@link #typesToKeep}.
     */
    private void removeNodes() {
	Set<String> typesToKeep = NodeClassification.getTypesToKeep();
	Set<T> nodes = getAllNodes();
	if (nodes != null && !nodes.isEmpty()) {
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		it.remove();
		if (!typesToKeep.contains(node.getData().getType())) {
		    omitNode(node);
		}
	    }
	}
    }

    /**
     * Searches for all <code>group by</code> nodes and calls the method
     * {@link #restructureGroupByOperators(List)}.
     */
    private void restructureGroupByOperators() throws NodeManipulationException {
	List<T> groupByOps = findAllByType(OpsWithFollowingOperands.GROUP_BY.getTypeOfNode());
	restructureGroupByOperators(groupByOps);
    }

    /**
     * Applies the method {@link #restructureGroupByOperator(BirdVtlNode)} for
     * each member of the input list.
     * 
     * @param list
     *            a list of <code>group by</code> nodes
     */
    private void restructureGroupByOperators(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T groupByOp = it.next();
		it.remove();
		restructureGroupByOperator(groupByOp);
	    }
	}
    }

    /**
     * Places an <code>Aggregate Function</code> node in between a
     * <code>group by</code> node and it's parent. Additionally the previous
     * node (with respect to the <code>group by</code> node) is also placed as a
     * child to the <code>Aggregate Function</code> node.
     * 
     * @param node
     *            a <code>group by</code> node
     */
    private void restructureGroupByOperator(T node) throws NodeManipulationException {
	T parent = (T) node.getParent();
	T previousSibling = getPreviousSibling(node);
	int index = parent.getChildren().indexOf(previousSibling);
	parent.removeChild(previousSibling);
	parent.removeChild(node);
	T aggregateNode = getAggregateFunctionNode();
	aggregateNode.addChild(previousSibling);
	aggregateNode.addChild(node);
	parent.addChild(aggregateNode, index);
    }

    /**
     * Renames the expression of specific nodes (e.g. <code>FunctionCall</code>)
     */
    private void renameSpecificNodes() {
	List<T> functionCallNodes = findAllByType(InvisibleOps.FUNCTION_CALL.getTypeOfNode());
	renameNodeExpression(functionCallNodes, InvisibleOps.FUNCTION_CALL.getTypeOfNode());

	List<T> procedureInputNodes = findAllByType(IdentificationNode.PROCEDURE_INPUT.getTypeOfNode());
	renameNodeType(procedureInputNodes, Leafs.VARIABLE_ID.getTypeOfNode());
    }

    private void renameNodeType(List<T> list, String type) {
	renameNode(list, type, true);
    }

    private void renameNodeExpression(List<T> list, String expression) {
	renameNode(list, expression, false);
    }

    /**
     * Renames the expression or type of a node
     * 
     * @param list
     *            a list of nodes
     * @param newExpression
     *            the type or expression
     * @param type
     *            <code>true</code> if the type should be renamed,
     *            <code>false</code> otherwise
     */
    private void renameNode(List<T> list, String newExpression, boolean type) {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = (T) it.next();
		it.remove();
		if (type) {
		    node.getData().setType(newExpression);
		} else {
		    node.getData().setExpression(newExpression);
		}
	    }
	}
    }

    /**
     * Searches for specific constellations in this tree structure and assigns
     * specific types of nodes in case such constellations are found.<br>
     * The following constellations are considered:<br>
     * (i) the first child of a <code>:=</code> node is always a data set (i.e.
     * of type <code>DatasetID</code>)<br>
     * (ii) the first child of a <code>.</code> node is always a data set while
     * the second child is always a variable. Therefore the types of the
     * children will be set to <code>DatasetID</code> and <code>VarID</code>
     * respectively.<br>
     * (iii) the children of a <code>Datasets to be joined</code> node are
     * always data sets. In such a case we also need to consider that a data set
     * can be renamed inside a join operation (e.g. 'setA as "A"')<br>
     * (iv) in case the first child of a clause operator (e.g. keep, filter,
     * etc.) is a <code>VarID</code> node this method changes the type into
     * <code>DatasetID</code><br>
     * (v) in case of a <code>call</code> node where the first child's
     * expression of such a node equals "PRCDR_CHCK_RLST", the children of the
     * second child of this <code>call</code> node's <code>Parameters</code>
     * node are set to <code>DatasetID</code> and <code>RulesetID</code>
     * respectively.
     * 
     */
    private void setDefaultTypes() throws NodeManipulationException {
	List<T> defNodes = findAllByType(OpsWithTwoOperands.DEF.getTypeOfNode());
	setTypeOfChildTo(defNodes, 0, Leafs.DATASET_ID.getTypeOfNode());

	List<T> dotNodes = findAllByType(OpsWithTwoOperands.DOT.getTypeOfNode());
	setTypeOfChildTo(dotNodes, 0, Leafs.DATASET_ID.getTypeOfNode());

	dotNodes = findAllByType(OpsWithTwoOperands.DOT.getTypeOfNode());
	setTypeOfChildTo(dotNodes, 1, Leafs.VARIABLE_ID.getTypeOfNode());

	List<T> joinedDatasetNodes = findAllByType(InvisibleOps.DATASETS_TO_BE_JOINED.getTypeOfNode());
	setJoinedDatasetChildsTo(joinedDatasetNodes);

	List<String> clauseOperators = new ArrayList<>();
	clauseOperators.addAll(NodeClassification.getClauseOperators());
	List<T> clauseOperatorNodes = findAllByType(clauseOperators);
	setClauseOperatorChildTo(clauseOperatorNodes);

	List<String> setOperators = new ArrayList<>();
	setOperators.addAll(NodeClassification.getOperatorsWithFollowingOps());
	List<T> setOperatorNodes = findAllByType(setOperators);
	setSetOperatorParameterChildrenToDatasetID(setOperatorNodes);

	List<T> equalsNodes = findAllByType(OpsWithTwoOperands.DEF.toString());
	setDatasetEqualsDatasetType(equalsNodes);

	List<T> joinExpressionNodes = findAllByType(InvisibleOps.JOIN_EXPRESSION.getTypeOfNode());
	handleJoinAliasDatasets(joinExpressionNodes);

    }

    /**
     * Calls the method {@link #handleJoinAliasDataset(BirdVtlNode)} for each
     * member of the input list.
     * 
     * @param list
     *            a list of <code>JoinExpr</code> nodes
     */
    private void handleJoinAliasDatasets(List<T> list) {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		handleJoinAliasDataset(node);
	    }
	}
    }

    /**
     * Searches for <code>on</code> nodes being children of the given
     * <code>joinExpressionNode</code> and identifies dataset alias. In case
     * such an alias is found (e.g. ds1 as "A" where A is the alias) the
     * <code>JoinBody</code> node's children are checked if the alias is used.
     * If the alias is used the type of such an alias is changed to
     * <code>DatasetAliasID</code> (from <code>DatasetID</code>).
     * 
     * @param joinExpressionNode
     *            a <code>JoinExpr</code> node
     */
    private void handleJoinAliasDataset(T joinExpressionNode) {
	try {
	    T parent = (T) joinExpressionNode.getParent();
	    T joinClause = (T) joinExpressionNode.getChildAt(0);
	    T datasetsToBeJoined = (T) joinClause.getChildAt(1);
	    T joinBody = (T) joinExpressionNode.getChildAt(1);
	    // TODO: make the selection of as nodes more specific (they have to
	    // be the children of the Datasets to be joined node), consider
	    // selects of selects
	    List<T> asNodes = auxiliaryFindAllByType(datasetsToBeJoined, OpsWithTwoOperands.AS.getTypeOfNode());
	    if (asNodes != null && !asNodes.isEmpty()) {
		Iterator<T> it = asNodes.iterator();
		while (it.hasNext()) {
		    T node = it.next();
		    T aliasNode = (T) node.getChildAt(1);
		    String aliasName = aliasNode.getData().getExpression().replace("\"", "");
		    List<T> aliasNodes = new ArrayList<>();
		    if (NodeClassification.getClauseOperators().contains(parent.getData().getType())) {
			List<T> usedAliasNodes = auxiliaryFindAll(parent,
				(S) new TComponent(Leafs.DATASET_ID.getTypeOfNode(), aliasName));
			if (usedAliasNodes != null && !usedAliasNodes.isEmpty()) {
			    aliasNodes.addAll(usedAliasNodes);
			}
		    } else {
			List<T> usedAliasNodes = auxiliaryFindAll(joinBody,
				(S) new TComponent(Leafs.DATASET_ID.getTypeOfNode(), aliasName));
			if (usedAliasNodes != null && !usedAliasNodes.isEmpty()) {
			    aliasNodes.addAll(usedAliasNodes);
			}
			usedAliasNodes = auxiliaryFindAll(joinClause,
				(S) new TComponent(Leafs.DATASET_ID.getTypeOfNode(), aliasName));
			if (usedAliasNodes != null && !usedAliasNodes.isEmpty()) {
			    aliasNodes.addAll(usedAliasNodes);
			}
		    }
		    if (aliasNodes != null && !aliasNodes.isEmpty()) {
			Iterator<T> iterator = aliasNodes.iterator();
			while (iterator.hasNext()) {
			    T usedAliasNode = iterator.next();
			    usedAliasNode.getData().setType(Leafs.DATASET_ALIAS_ID.getTypeOfNode());
			}
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Applies the method {@link #setTypeOfChildTo(List, Integer, String)} to
     * each member of the input list.
     * 
     * @param list
     *            a list of nodes
     * @param childIndex
     *            the index of the child who's type should be changed;
     *            <code>null</code> in case that all children should be changed
     * @param type
     *            the type of node that the child will be set to
     */
    private void setTypeOfChildTo(List<T> list, Integer childIndex, String type) {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		it.remove();
		setTypeOfChildTo(node, childIndex, type);
	    }
	}
    }

    /**
     * 
     * @param node
     *            a node who's child's type of node should be changed / set
     * @param childIndex
     *            the index of the child; <code>null</code> in case that all
     *            children should be changed / set
     * @param type
     *            the type of node which will be set
     */
    private void setTypeOfChildTo(T node, Integer childIndex, String type) {
	int min = 0;
	if (childIndex == null) {
	    childIndex = node.getNumberOfChildren() - 1;
	} else {
	    min = childIndex;
	}
	for (int i = min; i <= childIndex; i++) {
	    T child = (T) node.getChildAt(i);
	    child.getData().setType(type);
	}
    }

    /**
     * Identifies those members of the input list where the first child is of
     * type {@link TreeLeaf#VARIABLE_ID#getTypeOfNode()} and applies the method
     * {@link #setTypeOfChildTo(List, Integer, String)}.
     * 
     * @param list
     *            a list of clause nodes
     */
    private void setClauseOperatorChildTo(List<T> list) {
	if (list != null && !list.isEmpty()) {
	    List<T> firstChildIsVarID = new ArrayList<>();
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T clauseNode = it.next();
		it.remove();
		T child = (T) clauseNode.getChildAt(0);
		if (child.getData().getType().equals(Leafs.VARIABLE_ID.getTypeOfNode())) {
		    firstChildIsVarID.add(clauseNode);
		}
	    }
	    setTypeOfChildTo(firstChildIsVarID, 0, Leafs.DATASET_ID.getTypeOfNode());
	}
    }

    /**
     * Applies the method {@link #setJoinedDatasetChildsTo(BirdVtlNode)} to each
     * member of the input list.
     * 
     * @param list
     *            a list of <code>Datasets to be joined</code> nodes
     */
    private void setJoinedDatasetChildsTo(List<T> list) {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		it.remove();
		setJoinedDatasetChildsTo(node);
	    }
	}
    }

    /**
     * Identifies the <code>VarID</code> node in the children of the given
     * <code>Datasets to be joined</code> node and replaces the type to
     * <code>DatasetID</code>.
     * 
     * @param node
     *            a <code>Datasets to be joined</code> node
     */
    private void setJoinedDatasetChildsTo(T node) {
	for (int i = 0; i < node.getNumberOfChildren(); i++) {
	    T child = (T) node.getChildAt(i);
	    T varIdNode = getVarIDNode(child);
	    if (varIdNode != null) {
		varIdNode.getData().setType(Leafs.DATASET_ID.getTypeOfNode());
	    }
	}
    }

    /**
     * 
     * @param node
     *            either a <code>VarID</code> node or a <code>as</code> node
     *            having a <code>VarID</code> node as child
     * @return the <code>VarID</code> node; if no <code>VarID</code> node is
     *         present this method returns <code>null</code>
     */
    private T getVarIDNode(T node) {
	String varID = Leafs.VARIABLE_ID.getTypeOfNode();
	String as = OpsWithTwoOperands.AS.getTypeOfNode();
	T variable = null;
	if (node.getData().getType().equals(varID)) {
	    variable = node;
	} else if (node.getData().getType().equals(as)) {
	    List<String> list = new ArrayList<>();
	    list.add(varID);
	    variable = auxiliaryFindByType(node, list);
	} else {
	}
	return variable;
    }

    /**
     * This methods sets the type of node of the right hand side of an equation
     * in case of equations like result := x (where the type of x is set to
     * DatasetID). Additionally this methods takes into account if-then-else
     * structures on the right hand side of equations (in the sense that the
     * child of each <code>then</code> node is a dataset, except the value is
     * null).
     * 
     * @param list
     *            a list of <code>:=</code> nodes
     */
    private void setDatasetEqualsDatasetType(List<T> list) {
	if (list != null && !list.isEmpty()) {
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		if (node.getChildAt(1) != null) {
		    T rightSide = (T) node.getChildAt(1);
		    if (!rightSide.hasChildren()) {
			rightSide.getData().setType(Leafs.DATASET_ID.getTypeOfNode());
		    } else if (rightSide.getData().getType().equals(InvisibleOps.IF_THEN_ELSE.getTypeOfNode())) {
			List<T> thenNodes = auxiliaryFindAllByType(rightSide,
				OpsWithFollowingOperands.THEN.getTypeOfNode());
			List<T> elseNodes = auxiliaryFindAllByType(rightSide,
				OpsWithFollowingOperands.ELSE.getTypeOfNode());
			List<T> nodes = new ArrayList<>();
			if (thenNodes != null) {
			    nodes.addAll(thenNodes);
			}
			if (elseNodes != null) {
			    nodes.addAll(elseNodes);
			}
			Iterator<T> iterator = nodes.iterator();
			while (iterator.hasNext()) {
			    T thenNode = iterator.next();
			    T conditionResult = (T) thenNode.getChildAt(0);
			    if (!conditionResult.getData().getExpression().equals(Leafs.NULL.getTypeOfNode())) {
				conditionResult.getData().setType(Leafs.DATASET_ID.getTypeOfNode());
			    }
			}
		    }
		}
	    }
	}
    }

    /**
     * Identifies all children that are <code>Parameters</code> nodes of each
     * member of the input list and calls
     * {@link #setTypeOfChildTo(List, Integer, String)} on those nodes.
     * 
     * @param list
     *            a list of set operator nodes
     */
    private void setSetOperatorParameterChildrenToDatasetID(List<T> list) throws NodeManipulationException {
	if (list != null && !list.isEmpty()) {
	    List<T> paramNodes = new ArrayList<>();
	    Iterator<T> it = list.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		if (node.getNumberOfChildren() == 1) {
		    T child = (T) node.getChildAt(0);
		    if (child.getData().getType().equals(InvisibleOps.PARAMETERS.getTypeOfNode())) {
			paramNodes.add(child);
		    }

		} else {
		    throw new NodeManipulationException(
			    "found set operator with getNumberOfChildren() != 1 (the only child should be the Parameters node!)");
		}
	    }
	    if (paramNodes != null && !paramNodes.isEmpty()) {
		setTypeOfChildTo(paramNodes, null, Leafs.DATASET_ID.getTypeOfNode());
	    }
	}
    }

    /**
     * In case the <code>origin</code> node (i.e. the root) has only one child
     * (i.e. the given expression results in only one transformation scheme)
     */
    private void removeOrigin() {
	if (getRoot() != null) {
	    if (getRoot().getData().equals(new TComponent(TContext.CONST_ORIGIN, TContext.CONST_ORIGIN))) {
		T root = getRoot();
		if (root.hasChildren() && root.getNumberOfChildren() == 1) {
		    T child = (T) root.getChildAt(0);
		    root.removeChild(child);
		    setRoot(child);
		}
	    }
	}
    }

    // ----------------------------------------------------------
    // default nodes (related to other manipulation methods)
    // ----------------------------------------------------------

    /**
     * 
     * @return a <code>AggregateFunction</code> node
     */
    private T getAggregateFunctionNode() {
	String aggregate = InvisibleOps.AGGREGATE_FUNCTION.getTypeOfNode();
	T rNode = (T) new TNode<>();
	rNode.setData((S) new TComponent(aggregate, aggregate));
	return (T) rNode;
    }

    /**
     * 
     * @return a <code>Datasets to be joined</code> node
     */
    private T getDatasetsToBeJoinedNode() {
	String text = InvisibleOps.DATASETS_TO_BE_JOINED.getTypeOfNode();
	T rNode = (T) new TNode<>();
	rNode.setData((S) new TComponent(text, text));
	return (T) rNode;
    }

    /**
     * 
     * @return a <code>on</code> node
     */
    private T getOnNode() {
	String onString = OpsWithFollowingOperands.ON.getTypeOfNode();
	T onNode = (T) new TNode<>();
	onNode.setData((S) new TComponent(onString, onString));
	return (T) onNode;
    }

    /**
     * 
     * @return the default type of join in case it is not explicitly specified
     */
    private T getDefaultJoinTypeNode() {
	String inner = Leafs.INNER.getTypeOfNode();
	T joinType = (T) new TNode<>();
	joinType.setData((S) new TComponent(inner, inner));
	return (T) joinType;
    }

    /**
     * 
     * @return the <code>Type of join</code> node
     */
    private T getTypeOfJoinNode() {
	String text = InvisibleOps.TYPE_OF_JOIN.getTypeOfNode();
	T typeOfJoin = (T) new TNode<>();
	typeOfJoin.setData((S) new TComponent(text, text));
	return (T) typeOfJoin;
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

	    try {
		restructureComments(); // throws
				       // NodeManipulationException("Could
		// not transfer comment")
	    } catch (NodeManipulationException nme) {
		// TODO adjust method with respect to different types of comment
		// targets
	    }

	    restructureMethods();

	    checkForParentsWithContainerChild();

	    renameSpecificNodes();

	    restructureJoins();

	    removeNodes();

	    restructureGroupByOperators();

	    restructureClauseOperators();

	    setDefaultTypes();

	    removeOrigin();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Sets the code of each node of this tree using the {@link VtlBuilder}
     * class.
     * 
     * @param representation
     *            the representation that should be generated
     */
    private void generateCodes(Representation representation) {
	VtlBuilder builder = new VtlBuilder();
	int maxDistance = getMaximumDistance();
	for (int distance = maxDistance; distance >= 0; distance--) {
	    Set<T> nodes = getAllNodesWithDistance(distance);
	    Iterator<T> it = nodes.iterator();
	    while (it.hasNext()) {
		T node = it.next();
		builder.setTypeOfNode(node.getTypeOfNOde());
		node.setVtlCode(builder.generateCode(node, representation));
	    }
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

	generateCodes(Representation.STANDARD);
	
    }

}