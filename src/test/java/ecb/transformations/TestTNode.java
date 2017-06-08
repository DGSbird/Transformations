package ecb.transformations;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;
import junit.framework.TestCase;

/**
 * Test case for the {@link TNode} class. Please note that this test case does
 * not cover the interaction with the persistence context.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            a type extending the {@link TNode} class
 * @param <S>
 *            a type extending the {@link TComponent} class
 */
public class TestTNode<T extends TNode<T, S>, S extends TComponent> extends TestCase {

    // objects related to test case one

    private T node;

    private T parent;

    private T child1;

    private T child2;

    private S nodeComp;

    private S parentComp;

    private S child1Comp;

    private S child2Comp;

    private int nodeId = 0;

    private String code = "code";

    private String vtlCode = "vtlCode";

    private String htmlVtlCode = "htmlVtlCode";

    // objects related to test case one

    private T y;

    private T x;

    private T multiply;

    private T k;

    private T d;

    private T equals;

    private T redundant;

    private T plus;

    private T equation;

    public TestTNode() {
	super("TestTNode");
    }

    @SuppressWarnings("unchecked")
    public void setupObjects() {
	nodeComp = (S) new TComponent("expression", "type");
	node = (T) new TNode<T, S>(nodeComp);
	node.setNodeId(nodeId);
	node.setCode(code);
	node.setVtlCode(vtlCode);
	node.setVtlHtmlCode(htmlVtlCode);

	parentComp = (S) new TComponent("parentExpression", "parentType");
	parent = (T) new TNode<T, S>(parentComp);

	child1Comp = (S) new TComponent("childExpression", "childType");
	child1 = (T) new TNode<T, S>(child1Comp);

	child2Comp = (S) new TComponent("childExpression", "chilType");
	child2 = (T) new TNode<T, S>(child2Comp);

	y = (T) new TNode<T, S>((S) new TComponent("y", "variable"));

	k = (T) new TNode<T, S>((S) new TComponent("k", "constant"));

	x = (T) new TNode<T, S>((S) new TComponent("x", "variable"));

	multiply = (T) new TNode<T, S>((S) new TComponent("*", "*"));

	equals = (T) new TNode<T, S>((S) new TComponent("=", "="));

	plus = (T) new TNode<T, S>((S) new TComponent("+", "+"));

	d = (T) new TNode<T, S>((S) new TComponent("d", "constant"));

	equation = (T) new TNode<T, S>((S) new TComponent("y = k * x + d", "equation"));

	redundant = (T) new TNode<T, S>((S) new TComponent("k * x", "redundantNode"));
    }

    public void setupObjectConnections() {
	// test case one
	parent.addChild(node);
	node.addChild(child1);
	node.addChild(child2);

	// test case two
	equation.addChild(redundant);
	List<T> children = new ArrayList<>();
	children.add(x);
	children.add(multiply);
	children.add(k);
	redundant.setChildren(children);
	equation.addChild(y);
	equation.addChild(equals);
	equation.addChild(plus);
	equation.addChild(d);

    }

    @Before
    protected void setUp() throws Exception {
	super.setUp();

	setupObjects();

	setupObjectConnections();
    }

    @Test
    public void testEquals() {
	T equal = node;
	assertTrue(node.equals(equal));
	assertFalse(node.equals(parent));
	assertFalse(child1.equals(child2));
	assertFalse(child2.equals(child1));
    }

    @Test
    public void testHashCode() {
	T equal = node;
	assertTrue(node.hashCode() == equal.hashCode());
	assertFalse(node.hashCode() == parent.hashCode());
	assertFalse(child1.hashCode() == child2.hashCode());
    }

    @Test
    public void testGetComponent() {
	assertTrue(node.getComponent().equals(nodeComp));
	assertTrue(nodeComp.getNode().equals(node));
    }

    @Test
    public void testGetId() {
	assertTrue(node.getNodeId() == nodeId);
	assertTrue(node.getIdentifier() == node.getNodeId());
    }

    @Test
    public void testGetCode() {
	assertTrue(node.getCode().equals(code));
    }

    @Test
    public void testGetVtlCode() {
	assertTrue(node.getVtlCode().equals(vtlCode));
    }

    @Test
    public void testGetHtmlVtlCode() {
	assertTrue(node.getVtlHtmlCode().equals(htmlVtlCode));
    }

    @Test
    public void testParentChildRelationship() {
	assertTrue(node.getParent().equals(parent));
	assertTrue(parent.getChildren().size() == 1);
	assertTrue(parent.getChildren().contains(node));
	assertTrue(parent.getChildAt(0).equals(node));
	assertTrue(node.getChildren().size() == 2);
	assertTrue(node.getChildren().contains(child1));
	assertTrue(node.getChildren().contains(child2));
	assertTrue(node.getChildAt(0).equals(child1));
	assertTrue(node.getChildAt(1).equals(child2));
	assertTrue(child1.getParent().equals(node));
	assertTrue(child2.getParent().equals(node));
    }

    @Test
    public void testParentChildTransactions() {
	node.removeChild(child1);
	assertTrue(node.getParent().equals(parent));
	assertTrue(parent.getChildren().size() == 1);
	assertTrue(parent.getChildren().contains(node));
	assertTrue(parent.getChildAt(0).equals(node));
	assertTrue(node.getChildren().size() == 1);
	assertFalse(node.getChildren().contains(child1));
	assertTrue(node.getChildren().contains(child2));
	assertTrue(node.getChildAt(0).equals(child2));
	assertTrue(child1.getParent() == null);
	assertTrue(child2.getParent().equals(node));
	node.addChild(child1);
    }

    @Test
    public void testReorderChildren() {
	redundant.reorderChildren();
	assertTrue(redundant.getChildAt(0).equals(k));
	assertTrue(redundant.getChildAt(1).equals(multiply));
	assertTrue(redundant.getChildAt(2).equals(x));
    }

    @Test
    public void testReverseOrderOfChildren() {
	redundant.reverseOrderOfChildren();
	assertTrue(redundant.getChildAt(0).equals(k));
	assertTrue(redundant.getChildAt(1).equals(multiply));
	assertTrue(redundant.getChildAt(2).equals(x));
    }

    @Test
    public void testLetSiblingsBecomeChildren() {
	multiply.letSiblingsBecomeChildren();
	assertTrue(redundant.getNumberOfChildren() == 1);
	assertTrue(redundant.getChildAt(0).equals(multiply));
	assertTrue(multiply.getChildren().size() == 2);
	assertTrue(multiply.getChildAt(0).equals(x));
	assertTrue(multiply.getChildAt(1).equals(k));
    }

}
