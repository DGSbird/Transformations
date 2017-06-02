package ecb.transformations.enums;

public enum Bracket {
    NONE("", ""),
    ROUND("(", ")"),
    SQUARE("[", "]"),
    CURLED("{", "}");

    String left;

    String right;

    Bracket(String left, String right) {
	this.left = left;
	this.right = right;
    }

}
