package ecb.transformations.interfaces.nodes;

import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.interfaces.components.Similar;
import ecb.transformations.interfaces.components.WebComponent;
import ecb.transformations.treeStructure.TNode;

public interface Build {
    public <T extends TNode<T, S>, S extends Similar & WebComponent> String buildCode(T node, Syntax syntax,
	    Representation representation);

    default public <T extends TNode<T, S>, S extends Similar & WebComponent> String buildCode(T node, Syntax syntax) {
	return buildCode(node, syntax, Representation.STANDARD);
    };

    default public <T extends TNode<T, S>, S extends Similar & WebComponent> String buildCode(T node,
	    Representation representation) {
	return buildCode(node, Syntax.VTL, representation);
    };

    default public <T extends TNode<T, S>, S extends Similar & WebComponent> String buildCode(T node) {
	return buildCode(node, Syntax.VTL, Representation.STANDARD);
    };

}
