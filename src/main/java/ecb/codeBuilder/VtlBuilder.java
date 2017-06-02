package ecb.codeBuilder;

import ecb.codeBuilder.interfaces.GenerateCode;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.enums.Bracket;
import ecb.transformations.interfaces.Code;
import ecb.transformations.interfaces.Similar;
import ecb.transformations.interfaces.TypeOfNode;
import ecb.transformations.interfaces.WebComponent;
import ecb.transformations.operators.enums.InvisibleOps;
import ecb.transformations.operators.enums.OpsWithTwoOperands;
import ecb.transformations.treeStructure.TNode;

/**
 * Vtl builder class taking care of the building process for Vtl syntax of
 * transformation nodes.
 * 
 * @author Dominik Lin
 *
 */
public class VtlBuilder implements GenerateCode {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    /**
     * The {@link TypeOfNode} controlling the behaviour of the generation /
     * build process (e.g. operators with two operands are handled differently
     * than operators with multiple operands, etc.).
     */
    TypeOfNode typeOfNode;

    // ----------------------------------------------------------
    // get / set methods
    // ----------------------------------------------------------

    public TypeOfNode getTypeOfNode() {
	return typeOfNode;
    }

    @Override
    public void setTypeOfNode(TypeOfNode typeOfNode) {
	this.typeOfNode = typeOfNode;
    }

    // ----------------------------------------------------------
    // methods
    // ----------------------------------------------------------

    @Override
    public <T extends TNode<T, S>, S extends Similar & WebComponent> String generateCode(T node,
	    Representation representation) {
	String rString = new String();
	if (typeOfNode != null) {
	    String EOL = typeOfNode.getEOL();
	    String separator = typeOfNode.getSeparator();
	    String alias = typeOfNode.getAlias();
	    Bracket bracket = typeOfNode.getBracket();
	    String separatorOperatorChildren = typeOfNode.getOperatorChildrenSeparator();

	    if (typeOfNode instanceof OpsWithTwoOperands) {
		/*
		 * structure: code(child(0)) type(operator) code(child(1))
		 */
		try {
		    T child0 = (T) node.getChildAt(0);
		    T child1 = (T) node.getChildAt(1);
		    // construction of {child0 operator child1}
		    rString = ((Code) child0).getCode(representation) + separator + node.getData().getType() + separator
			    + ((Code) child1).getCode(representation) + EOL;
		} catch (Exception e) {
		    e.printStackTrace();
		}

	    } else if (typeOfNode instanceof InvisibleOps) {
		/*
		 * structure: code(child(0))
		 */

	    } else {
		// TODO: implement other types of operators
	    }
	}
	return rString;
    }

}
