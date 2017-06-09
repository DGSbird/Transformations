package ecb.transformations.operators.enums;

import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.interfaces.nodes.Build;
import ecb.transformations.interfaces.nodes.TypeOfNode;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public enum SpecialNode implements TypeOfNode, Build {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    RULE_ID("RuleID");

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    String typeOfNode;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    SpecialNode(String typeOfNode) {
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
	// TODO Auto-generated method stub
	return null;
    }

}
