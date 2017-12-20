package ecb.transformations.enums.operators;

import ecb.technical.interfaces.nodes.Build;
import ecb.technical.interfaces.nodes.TypeOfNode;
import ecb.transformations.enums.Bracket;
import ecb.transformations.enums.Representation;
import ecb.transformations.enums.Syntax;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public enum DatasetOps implements TypeOfNode, Build {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    FILTER("filter", Bracket.NONE),
    DROP("drop", Bracket.NONE),
    KEEP("keep", Bracket.NONE),
    RENAME("rename", Bracket.NONE),
    CALC("calc", Bracket.NONE);

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    String typeOfNode;

    Bracket bracket;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    DatasetOps(String typeOfNode, Bracket bracket) {
	this.typeOfNode = typeOfNode;
	this.bracket = bracket;
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

    // ----------------------------------------------------------
    // build
    // ----------------------------------------------------------

    @Override
    public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node, Syntax syntax,
	    Representation representation) {
	return null;
    }

}
