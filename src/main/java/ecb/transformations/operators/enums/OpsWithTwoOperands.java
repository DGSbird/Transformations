package ecb.transformations.operators.enums;

import ecb.transformations.BirdVtl.TNode;
import ecb.transformations.enums.Bracket;
import ecb.transformations.interfaces.TypeOfNode;
import ecb.transformations.interfaces.VtlBuild;
import ecb.transformations.interfaces.WebComponent;

public enum OpsWithTwoOperands implements TypeOfNode, VtlBuild {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    MINUS("-", Bracket.ROUND),
    PLUS("+", Bracket.ROUND),
    MULTIPLY("*", Bracket.ROUND),
    DIVIDE("/", Bracket.ROUND),
    EQUALS("="),
    NOT_EQUALS("<>"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    AND("and"),
    OR("or", Bracket.ROUND),
    IN("in"),
    NOT_IN("not in"),
    DEF(":=", Bracket.NONE, ";"),
    DOT(".", Bracket.NONE, ""),
    AS("as"),
    ROLE("role"),
    CONCAT("||", Bracket.ROUND);

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private String typeOfNode;

    private Bracket bracket;

    private String EOL;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    OpsWithTwoOperands(String typeOfNode) {
	this.typeOfNode = typeOfNode;
	this.bracket = Bracket.NONE;
	this.EOL = "";
    }

    OpsWithTwoOperands(String typeOfNode, Bracket bracket) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
	this.EOL = "";
    }

    OpsWithTwoOperands(String typeOfNode, Bracket bracket, String EOL) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
	this.EOL = EOL;
    }

    // ----------------------------------------------------------
    // inherited methods (from interface)
    // ----------------------------------------------------------

    @Override
    public String getTypeOfNode() {
	return typeOfNode;
    }

    @Override
    public Bracket getBracket() {
	return bracket;
    }

    @Override
    public String getEOL() {
	return EOL;
    }

    @Override
    public <T extends TNode<T, S>, S extends WebComponent> String buildVtlCode(T node, boolean htmlConfig) {
	String rString = new String();
	try {
	    T child0 = (T) node.getChildAt(0);
	    T child1 = (T) node.getChildAt(1);

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return rString;
    }

}
