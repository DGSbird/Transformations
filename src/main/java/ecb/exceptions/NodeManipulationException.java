package ecb.exceptions;

/**
 * Exception thrown in case a manipulation of a node in a tree fails.
 * 
 * @author Dominik Lin
 *
 */
public class NodeManipulationException extends Exception {

    private static final long serialVersionUID = -3376169874309659959L;

    public NodeManipulationException() {
    }

    public NodeManipulationException(String message) {
	super(message);
    }

    public NodeManipulationException(Throwable cause) {
	super(cause);
    }

    public NodeManipulationException(String message, Throwable cause) {
	super(message, cause);
    }

    public NodeManipulationException(String message, Throwable cause, boolean enableSuppression,
	    boolean writeableStackTrace) {
	super(message, cause, enableSuppression, writeableStackTrace);
    }
}
