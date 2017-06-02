package ecb.transformations;

import org.junit.Test;

import ecb.transformations.abstractClasses.Component;
import junit.framework.TestCase;

/**
 * Test case for {@link Component} class.
 * 
 * @author Dominik Lin
 *
 */
public class TestComponent extends TestCase {

    Component component;

    String expression;

    String type;

    String comment;

    String location;

    String tooltip;

    String description;

    public TestComponent() {
	super("TestComponent");
    }

    protected void setUp() throws Exception {
	super.setUp();

	expression = "expression";
	type = "type";
	comment = "comment";
	location = "location";
	tooltip = "tooltip";
	description = "description";

	component = new Component(expression);
	component.setType(type);
	component.setComment(comment);
	component.setLocation(location);
	component.setTooltip(tooltip);
	component.setDescription(description);
    }

    @Test
    public void testGetExpression() {
	assertTrue(component.getExpression().equals(expression));
	assertFalse(component.getExpression().equals(type));
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
