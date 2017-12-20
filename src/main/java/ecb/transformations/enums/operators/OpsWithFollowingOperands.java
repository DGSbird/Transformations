package ecb.transformations.enums.operators;

import ecb.technical.interfaces.nodes.Build;
import ecb.technical.interfaces.nodes.TypeOfNode;
import ecb.transformations.enums.Bracket;
import ecb.transformations.enums.Representation;
import ecb.transformations.enums.Syntax;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public enum OpsWithFollowingOperands implements TypeOfNode, Build {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    SET_DIFF("setdiff", Bracket.NONE),
    SYM_DIFF("symdiff", Bracket.NONE),
    INTERSECT("intersect", Bracket.NONE),
    UNION("union", Bracket.NONE),
    COUNT("count", Bracket.ROUND),
    GET("get", Bracket.ROUND, " "),
    GROUP_BY("group by"),
    ISNULL("isnull", Bracket.ROUND),
    MIN("min", Bracket.ROUND),
    MAX("max", Bracket.ROUND),
    SUM("sum", Bracket.ROUND),
    SQRT("sqrt", Bracket.ROUND),
    AVG("avg", Bracket.ROUND),
    NOT_ISNULL("not isnull", Bracket.ROUND),
    ON("on"),
    RETURNS("returns"),
    THEN("then", Bracket.NONE, " ", "", " "),
    WHEN("when"),
    NOT("not"),
    IF("if", Bracket.NONE, " ", "", " "),
    ELSEIF("elseif", Bracket.NONE, " ", "", " "),
    ELSE("else", Bracket.NONE, " ", "", " "),
    ERRORCODE("errorcode", Bracket.ROUND),
    INPUT("input"),
    OUTPUT("output"),
    CALL("call", Bracket.NONE, " ", ";"),
    CHECK("check", Bracket.ROUND, ", ", " "),
    HIERARCHY("hierarchy", Bracket.ROUND, ", ", ""),
    TRANSCODE("transcode", Bracket.ROUND, ", ", ""),
    PUT("put", Bracket.ROUND, ", ", " "),
    SUBSTR("substr", Bracket.ROUND, ", ", ""),
    REPLACE("replace", Bracket.ROUND, ", ", "");

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    String typeOfNode;

    Bracket bracket = Bracket.NONE;

    String separator = "";

    String EOL = "";

    String operatorChildSeparator = ", ";

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    OpsWithFollowingOperands(String typeOfNode) {
	this.typeOfNode = typeOfNode;
    }

    OpsWithFollowingOperands(String typeOfNode, Bracket bracket) {
	this(typeOfNode);
	this.bracket = bracket;
    }

    OpsWithFollowingOperands(String typeOfNode, Bracket bracket, String separator) {
	this(typeOfNode, bracket);
	this.separator = separator;
    }

    OpsWithFollowingOperands(String typeOfNode, Bracket bracket, String separator, String EOL) {
	this(typeOfNode, bracket, separator);
	this.EOL = EOL;
    }

    OpsWithFollowingOperands(String typeOfNode, Bracket bracket, String separator, String EOL,
	    String operatorChildSeparator) {
	this(typeOfNode, bracket, separator, EOL);
	this.operatorChildSeparator = operatorChildSeparator;
    }

    // ----------------------------------------------------------
    // get methods
    // ----------------------------------------------------------

    @Override
    public String getOperatorChildrenSeparator() {
	return operatorChildSeparator;
    }

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
    public String getSeparator() {
	return separator;
    }

    // ----------------------------------------------------------
    // build
    // ----------------------------------------------------------

    @Override
    public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node, Syntax syntax,
	    Representation representation) {
	// TODO Auto-generated method stub
	return null;
    }

}
