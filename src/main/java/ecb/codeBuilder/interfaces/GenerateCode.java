package ecb.codeBuilder.interfaces;

import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.interfaces.Similar;
import ecb.transformations.interfaces.TypeOfNode;
import ecb.transformations.interfaces.WebComponent;
import ecb.transformations.treeStructure.TNode;

/**
 * 
 * @author Dominik Lin
 *
 */
public interface GenerateCode {
    /**
     * @param node
     *            the transformation node that's code should be generated
     * @param representation
     *            the {@link Representation} that should be generated (e.g.
     *            html)
     * @return a {@link String} object that comprises the code related to the
     *         given node
     */
    public <T extends TNode<T, S>, S extends Similar & WebComponent> String generateCode(T node, Representation representation);

    /**
     * Sets the type of node.
     * 
     * @param typeOfNode
     *            the type of node that's code should be generated
     */
    public void setTypeOfNode(TypeOfNode typeOfNode);
}
