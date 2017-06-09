package ecb.transformations.operators.enums;

import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.enums.Bracket;
import ecb.transformations.interfaces.nodes.Build;
import ecb.transformations.interfaces.nodes.TypeOfNode;
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
    THEN("then"),
    WHEN("when"),
    NOT("not"),
    IF("if"),
    ELSEIF("elseif"),
    ELSE("else"),
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

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    OpsWithFollowingOperands(String typeOfNode) {
	this.typeOfNode = typeOfNode;
    }

    OpsWithFollowingOperands(String typeOfNode, Bracket bracket) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
    }

    OpsWithFollowingOperands(String typeOfNode, Bracket bracket, String separator) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
	this.separator = separator;
    }

    OpsWithFollowingOperands(String typeOfNode, Bracket bracket, String separator, String EOL) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
	this.separator = separator;
	this.EOL = EOL;
    }

    // ----------------------------------------------------------
    // get methods
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
