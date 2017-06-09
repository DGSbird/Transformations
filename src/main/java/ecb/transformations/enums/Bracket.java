package ecb.transformations.enums;

public enum Bracket {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    NONE("", ""),
    ROUND("(", ")"),
    SQUARE("[", "]"),
    CURLED("{", "}");

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    String left;

    String right;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    Bracket(String left, String right) {
	this.left = left;
	this.right = right;
    }

    // ----------------------------------------------------------
    // get methods
    // ----------------------------------------------------------

    public String getLeft() {
	return left;
    }

    public String getRight() {
	return right;
    }
}
