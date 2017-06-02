package ecb.transformations.operators.enums;

import ecb.codeBuilder.VtlBuilder;
import ecb.codeBuilder.interfaces.GenerateCode;
import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.enums.Bracket;
import ecb.transformations.interfaces.TypeOfNode;
import ecb.transformations.interfaces.Build;
import ecb.transformations.interfaces.Similar;
import ecb.transformations.interfaces.WebComponent;
import ecb.transformations.treeStructure.TNode;

public enum OpsWithTwoOperands implements TypeOfNode, Build {
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
    public <T extends TNode<T, S>, S extends Similar & WebComponent> String buildCode(T node, Syntax syntax,
	    Representation representation) {
	// TODO: extend this method when adding additional syntax or
	// representation
	String rString = new String();
	GenerateCode builder = null;
	if (syntax.equals(Syntax.VTL)) {
	    builder = new VtlBuilder();
	} else {
	    // TODO: implement SQL syntax generation
	}
	builder.setTypeOfNode(this);
	rString = builder.generateCode(node, representation);
	return rString;
    }

}
