package ecb.transformations.operators.enums;

import ecb.codeBuilder.VtlBuilder;
import ecb.codeBuilder.interfaces.GenerateCode;
import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.enums.Bracket;
import ecb.transformations.interfaces.nodes.Build;
import ecb.transformations.interfaces.nodes.TypeOfNode;
import ecb.transformations.treeStructure.TComponent;
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
    DEF(":=", " ", ";"),
    DOT(".", ""),
    AS("as"),
    ROLE("role"),
    CONCAT("||", Bracket.ROUND);

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private String typeOfNode;

    private Bracket bracket = Bracket.NONE;

    private String separator = " ";

    private String EOL = "";

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    OpsWithTwoOperands(String typeOfNode) {
	this.typeOfNode = typeOfNode;
    }

    OpsWithTwoOperands(String typeOfNode, Bracket bracket) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
    }

    OpsWithTwoOperands(String typeOfNode, String separator) {
	this.typeOfNode = typeOfNode;
	this.separator = separator;
    }

    OpsWithTwoOperands(String typeOfNode, Bracket bracket, String separator) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
	this.separator = separator;
    }

    OpsWithTwoOperands(String typeOfNode, String separator, String EOL) {
	this.typeOfNode = typeOfNode;
	this.separator = separator;
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
    public String getSeparator() {
	return separator;
    }

    @Override
    public String getEOL() {
	return EOL;
    }

    // ----------------------------------------------------------
    // build
    // ----------------------------------------------------------

    @Override
    public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node, Syntax syntax,
	    Representation representation) {
	// TODO: extend this method when adding additional syntax or
	// representation
	String rString = new String();
	GenerateCode builder = null;
	if (syntax.equals(Syntax.VTL)) {
	    builder = new VtlBuilder();
	} else if (syntax.equals(Syntax.SQL)) {
	    // set builder to SqlBuilder
	} else {
	    // TODO: implement SQL syntax generation
	}
	builder.setTypeOfNode(this);
	rString = builder.generateCode(node, representation);
	return rString;
    }

}
