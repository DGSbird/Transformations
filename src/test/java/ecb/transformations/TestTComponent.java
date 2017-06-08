package ecb.transformations;

import org.junit.Test;

import ecb.transformations.treeStructure.TComponent;
import junit.framework.TestCase;

/**
 * Test case for {@link TComponent} class. Please note that this test case does
 * not cover connections to other objects (e.g. transformation node). Please
 * note that this test case also covers methods inherited from the
 * {@link Component} class.
 * 
 * @author Dominik Lin
 *
 */
public class TestTComponent extends TestCase {

    TComponent component;

    TComponent equalRef;

    TComponent inEqualRef;

    TComponent similar;

    int componentId;

    String expression;

    String type;

    String comment;

    String location;

    String tooltip;

    String description;

    public TestTComponent() {
	super("TestTComponent");
    }

    protected void setUp() throws Exception {
	super.setUp();

	componentId = 0;
	expression = "expression";
	type = "type";
	comment = "comment";
	location = "location";
	description = "description";
	tooltip = "tooltip";
	component = new TComponent(expression, type);
	component.setComponentId(componentId);
	component.setLocation(location);
	component.setDescription(description);
	component.setTooltip(tooltip);
	component.setComment(comment);
	equalRef = new TComponent(expression, type);
	equalRef.setComponentId(componentId);
	inEqualRef = new TComponent(expression + " other", type + " other");
	inEqualRef.setComponentId(componentId + 1);
	similar = new TComponent(expression, type);
	similar.setComponentId(componentId + 1);
    }

    @Test
    public void testEquals() {
	assertTrue(component.equals(equalRef));
	assertTrue(equalRef.equals(component));
	assertFalse(component.equals(inEqualRef));
	assertTrue(similar.equals(inEqualRef));
	assertFalse(component.equals(similar));
    }

    @Test
    public void testHashCode() {
	assertTrue(component.hashCode() == equalRef.hashCode());
	assertFalse(component.hashCode() == inEqualRef.hashCode());
	assertTrue(similar.hashCode() == inEqualRef.hashCode());
    }

    @Test
    public void testIsSimilar() {
	assertTrue(component.isSimilar(equalRef));
	assertTrue(equalRef.isSimilar(component));
	assertTrue(component.isSimilar(similar));
	assertTrue(similar.isSimilar(component));
	assertFalse(component.isSimilar(inEqualRef));
    }

    @Test
    public void testGetId() {
	assertTrue(component.getComponentId() == componentId);
    }

    @Test
    public void testGetExpression() {
	assertTrue(component.getExpression().equals(expression));
    }

    @Test
    public void testGetType() {
	assertTrue(component.getType().equals(type));
    }

    @Test
    public void testGetComment() {
	assertTrue(component.getComment().equals(comment));
    }

    @Test
    public void testGetLocation() {
	assertTrue(component.getLocation().equals(location));
    }

    @Test
    public void testGetTooltip() {
	assertTrue(component.getTooltip().equals(tooltip));
    }

    @Test
    public void testGetDescription() {
	assertTrue(component.getDescription().equals(description));
    }

}
