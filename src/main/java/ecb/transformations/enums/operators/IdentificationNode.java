package ecb.transformations.enums.operators;

import ecb.technical.interfaces.nodes.Build;
import ecb.technical.interfaces.nodes.TypeOfNode;
import ecb.transformations.enums.Representation;
import ecb.transformations.enums.Syntax;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public enum IdentificationNode implements TypeOfNode, Build {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    DEFINE_DATAPOINT_RULESET("DefineDatapointRuleset"),
    CREATE_FUNCTION("create function"),
    DEFINE_PROCEDURE("define procedure"),
    COMMENT("Comment"),
    SET_MEMBER_LIST("SetMemberList"),
    PROCEDURE_ARGUMENT_LIST("ProcedureArgList"),
    PROCEDURE_ARGUMENT("ProcedureArg"),
    RULESET_ARGUMENT_LIST("RulesetArgList"),
    RULESET_ARGUMENT("RulesetArg"),
    ARGUMENT_LIST("ArgList"),
    STATEMENT("Statement"),
    DEF_FUNCTION("DefFunction"),
    DEF_PROCEDURE("DefProcedure"),
    DEF_RULESET("DefDatapoint"),
    EXPRESSION_EQUATION("ExprEq"),
    DATASET_ALIAS("DatasetAlias"),
    ARGUMENT("Arg"),
    PROCEDURE_INPUT_LIST("ProcedureInputList"),
    PROCEDURE_CALL_BODY("ProcedureCallBody"),
    PROCEDURE_INPUT("ProcedureInput"),
    STR_EXPR_PARAM("strExprParam");

    // ----------------------------------------------------------
    // field
    // ----------------------------------------------------------

    String typeOfNode;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    IdentificationNode(String typeOfNode) {
	this.typeOfNode = typeOfNode;
    }

    // ----------------------------------------------------------
    // get methods
    // ----------------------------------------------------------

    @Override
    public String getTypeOfNode() {
	return typeOfNode;
    }

    // ----------------------------------------------------------
    // build
    // ----------------------------------------------------------

    @Override
    public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node, Syntax syntax,
	    Representation representation) {
	return null;
    }

}
