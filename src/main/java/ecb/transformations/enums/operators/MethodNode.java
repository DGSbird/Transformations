package ecb.transformations.enums.operators;

import ecb.technical.interfaces.nodes.Build;
import ecb.technical.interfaces.nodes.TypeOfNode;
import ecb.transformations.enums.Representation;
import ecb.transformations.enums.Syntax;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public enum MethodNode implements TypeOfNode, Build {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    PROCEDURE_INFO("Procedure information", "define procedure "),
    FUNCTION_INFO("Function information", "create function "),
    RULESET_INFO("Ruleset information", "define datapoint ruleset ");

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    String typeOfNode;

    String alias;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    MethodNode(String typeOfNode, String alias) {
	this.typeOfNode = typeOfNode;
	this.alias = alias;
    }

    // ----------------------------------------------------------
    // get methods
    // ----------------------------------------------------------

    @Override
    public String getTypeOfNode() {
	return typeOfNode;
    }

    @Override
    public String getAlias() {
	return alias;
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
