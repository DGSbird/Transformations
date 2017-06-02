package ecb.transformations.metadata;

import java.util.ArrayList;
import java.util.List;

import ecb.transformations.interfaces.nodes.TypeOfNode;
import ecb.transformations.operators.enums.InvisibleOps;
import ecb.transformations.operators.enums.OpsWithTwoOperands;

/**
 * Transformation context.
 * 
 * @author Dominik Lin
 *
 */
public class TContext {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------
    final public static String origin = "origin";

    final public static String eof = "<EOF>";

    /**
     * A complete list containing all allowed types of nodes (e.g. definition
     * operator, equals operator, data set identifier, cube identifier, etc.)
     */
    public static List<TypeOfNode> TypesOfNodes;

    // ----------------------------------------------------------
    // static initialisation
    // ----------------------------------------------------------

    static {
	TypesOfNodes = new ArrayList<>();
	for (TypeOfNode typeOfNode : InvisibleOps.values()) {
	    TypesOfNodes.add(typeOfNode);
	}
	for (TypeOfNode typeOfNode : OpsWithTwoOperands.values()) {
	    TypesOfNodes.add(typeOfNode);
	}

    }

    // ----------------------------------------------------------
    // additional methods
    // ----------------------------------------------------------

    public static TypeOfNode getTypeOfNode(String type) {
	TypeOfNode typeOfNode = null;
	int i = 0;
	do {
	    if (TypesOfNodes.get(i).getTypeOfNode().equals(type)) {
		typeOfNode = TypesOfNodes.get(i);
	    } else {
		i++;
	    }
	} while (i < TypesOfNodes.size() && typeOfNode == null);

	return typeOfNode;
    }

}
