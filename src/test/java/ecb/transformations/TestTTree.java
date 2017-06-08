package ecb.transformations;

import org.junit.Before;
import org.junit.Test;

import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;
import ecb.transformations.treeStructure.TTree;
import junit.framework.TestCase;

/**
 * Test case for {@link TTree} class. Please note that this class does not cover
 * tests related to the interaction with the persistence context (e.g. import /
 * export to and from the persistence context).
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            a type extending the {@link TNode} class
 * @param <S>
 *            a type extending the {@link TComponent} class
 */
@SuppressWarnings("unchecked")
public class TestTTree<T extends TNode<T, S>, S extends TComponent> extends TestCase {

    private TTree<T, S> tree;

    private T y;

    private T x;

    private T multiply;

    private T k;

    private T d;

    private T equals;

    private T redundant;

    private T plus;

    private T equation;

    public void setupObjects() {
	y = (T) new TNode<T, S>((S) new TComponent("y", "variable"));

	k = (T) new TNode<T, S>((S) new TComponent("k", "constant"));

	x = (T) new TNode<T, S>((S) new TComponent("x", "variable"));

	multiply = (T) new TNode<T, S>((S) new TComponent("*", "*"));

	equals = (T) new TNode<T, S>((S) new TComponent("=", "="));

	plus = (T) new TNode<T, S>((S) new TComponent("+", "+"));

	d = (T) new TNode<T, S>((S) new TComponent("d", "constant"));

	equation = (T) new TNode<T, S>((S) new TComponent("y = k * x + d", "equation"));

	redundant = (T) new TNode<T, S>((S) new TComponent("k * x", "redundantNode"));

	tree = new TTree<>(equation);

    }

    public void setupObjectConnections() {
	equation.addChild(equals);
	equals.addChild(y);
	equals.addChild(plus);
	plus.addChild(redundant);
	plus.addChild(d);
	redundant.addChild(multiply);
	multiply.addChild(k);
	multiply.addChild(x);
    }

    @Before
    protected void setUp() throws Exception {
	super.setUp();

	setupObjects();

	setupObjectConnections();
    }

    @Test
    public void testGetRoot() {
	tree.getRoot().equals(equation);
    }

    @Test
    public void testTreeStructure() {
	assertTrue(equation.getNumberOfChildren() == 1);
	assertTrue(equation.getChildAt(0).equals(equals));
	assertTrue(equals.getNumberOfChildren() == 2);
	assertTrue(equals.getChildAt(0).equals(y));
	assertTrue(equals.getChildAt(1).equals(plus));
	assertTrue(plus.getNumberOfChildren() == 2);
	assertTrue(plus.getChildAt(0).equals(redundant));
	assertTrue(plus.getChildAt(1).equals(d));
	assertTrue(redundant.getNumberOfChildren() == 1);
	assertTrue(redundant.getChildAt(0).equals(multiply));
	assertTrue(multiply.getNumberOfChildren() == 2);
	assertTrue(multiply.getChildAt(0).equals(k));
	assertTrue(multiply.getChildAt(1).equals(x));
    }

    @Test
    public void testFind() {
	T node = tree.find((S) new TComponent("y", "variable"));
	assertTrue(node.equals(y));
    }

    @Test
    public void testAuxiliaryFind() {
	assertTrue(tree.auxiliaryFind(redundant, (S) new TComponent("k", "constant")).equals(k));
	assertTrue(tree.auxiliaryFind(redundant, (S) new TComponent("y", "constant")) == null);
    }

    @Test
    public void testAuxiliaryFindAll() {
	T addOn1 = (T) new TNode<T, S>(new TComponent("+", "+"));
	T addOn2 = (T) new TNode<T, S>(new TComponent("+", "+"));
	k.addChild(addOn1);
	equation.addChild(addOn2);
	assertTrue(tree.auxiliaryFindAll(equals, (S) new TComponent("+", "+")).size() == 2);
	assertTrue(tree.auxiliaryFindAll(equals, (S) new TComponent("+", "+")).contains(addOn1));
	assertTrue(tree.auxiliaryFindAll(equals, (S) new TComponent("+", "+")).contains(plus));
    }

    @Test
    public void testFindAllByType() {
	assertTrue(tree.findAllByType("constant").size() == 2);
	assertTrue(tree.findAllByType("constant").contains(d));
	assertTrue(tree.findAllByType("constant").contains(k));
    }

    @Test
    public void testFindAllByExpression() {
	T addOn1 = (T) new TNode<T, S>(new TComponent("+", "+"));
	T addOn2 = (T) new TNode<T, S>(new TComponent("+", "+"));
	k.addChild(addOn1);
	equation.addChild(addOn2);
	assertTrue(tree.findAllByExpression("+").size() == 3);
	assertTrue(tree.findAllByExpression("+").contains(addOn1));
	assertTrue(tree.findAllByExpression("+").contains(addOn2));
	assertTrue(tree.findAllByExpression("+").contains(plus));
    }

}
