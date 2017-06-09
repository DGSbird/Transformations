package ecb.transformations.operators.enums;

import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.enums.Bracket;
import ecb.transformations.interfaces.nodes.Build;
import ecb.transformations.interfaces.nodes.TypeOfNode;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public enum InvisibleOps implements TypeOfNode, Build {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    PARAMETERS("Parameters", Bracket.ROUND, ", "),
    IF_THEN_ELSE("IfThenElseExpr", Bracket.ROUND, " </br>"),
    JOIN_CLAUSE("JoinClause", Bracket.SQUARE),
    TYPE_OF_JOIN("Type of join", Bracket.NONE),
    DATASETS_TO_BE_JOINED("Datasets to be joined", Bracket.NONE, ", "),
    JOIN_BODY("JoinBody", Bracket.CURLED),
    JOIN_EXPRESSION("JoinExpr", Bracket.NONE),
    FUNCTION_BODY("FunctionBody", Bracket.CURLED),
    PROCEDURE_BODY("ProcedureBody", Bracket.CURLED, " </br>"),
    RULESET_BODY("RulesetBody", Bracket.CURLED, " </br>"),
    FUNCTION_DEFINITION("Function definition", Bracket.NONE),
    RULESET_DEFINITION("Ruleset definition", Bracket.NONE),
    PROCEDURE_DEFINITION("Procedure definition", Bracket.NONE),
    FUNCTION_CALL("FunctionCall", Bracket.ROUND),
    AGGREGATE_FUNCTION("AggregateFunction", Bracket.NONE);

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    String typeOfNode;

    Bracket bracket = Bracket.NONE;

    String separator = "";

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    private InvisibleOps(String typeOfNode, Bracket bracket) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
    }

    private InvisibleOps(String typeOfNode, Bracket bracket, String separator) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
	this.separator = separator;

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
