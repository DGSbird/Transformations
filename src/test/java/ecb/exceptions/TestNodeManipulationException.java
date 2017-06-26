package ecb.exceptions;

import org.junit.Test;

import junit.framework.TestCase;

public class TestNodeManipulationException extends TestCase {

    public TestNodeManipulationException() {
	super("TestChildWithoutParentException");
    }

    public static void testThrowException(String s) throws NodeManipulationException {
	if (s == null) {
	    throw new NodeManipulationException("Node without child");
	}
    }

    @Test
    public void testThrow() {
	try {
	    testThrowException(null);
	} catch (NodeManipulationException e) {
	    e.printStackTrace();
	}
    }

}
