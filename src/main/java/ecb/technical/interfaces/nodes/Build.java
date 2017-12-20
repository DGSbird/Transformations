package ecb.technical.interfaces.nodes;

import ecb.transformations.enums.Representation;
import ecb.transformations.enums.Syntax;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public interface Build {
    public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node, Syntax syntax,
	    Representation representation);

    default public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node, Syntax syntax) {
	return buildCode(node, syntax, Representation.STANDARD);
    };

    default public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node,
	    Representation representation) {
	return buildCode(node, Syntax.VTL, representation);
    };

    default public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node) {
	return buildCode(node, Syntax.VTL, Representation.STANDARD);
    };

}
