package ecb.codeBuilder;

import java.util.ArrayList;
import java.util.List;

import ecb.codeBuilder.interfaces.GenerateCode;
import ecb.technical.interfaces.nodes.Code;
import ecb.technical.interfaces.nodes.TypeOfNode;
import ecb.transformations.enums.Bracket;
import ecb.transformations.enums.Representation;
import ecb.transformations.enums.operators.DatasetOps;
import ecb.transformations.enums.operators.InvisibleOps;
import ecb.transformations.enums.operators.Leafs;
import ecb.transformations.enums.operators.MethodNode;
import ecb.transformations.enums.operators.OpsWithFollowingOperands;
import ecb.transformations.enums.operators.OpsWithTwoOperands;
import ecb.transformations.enums.operators.SpecialNode;
import ecb.transformations.treeStructure.TComponent;
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

    public static <T> int getPosition(String term, T[] list) {
	List<T> tList = new ArrayList<>();
	for (T t : list) {
	    tList.add(t);
	}
	return getPosition(term, tList);
    }

    public static <T> int getPosition(String term, List<T> list) {
	int rValue = (-1);
	if (contains(term, list)) {
	    rValue = term.length();
	    int i = 0;
	    do {
		int pos = term.indexOf(list.get(i).toString());
		if (pos > 0 && pos < rValue) {
		    rValue = pos;
		}
		i++;
	    } while (i < list.size());
	}
	return rValue;
    }

    public static <T> boolean contains(String term, T[] list) {
	List<T> tList = new ArrayList<>();
	for (T t : list) {
	    tList.add(t);
	}
	return contains(term, tList);
    }

    public static <T> boolean contains(String term, List<T> list) {
	boolean found = false;
	int i = 0;
	do {
	    if (term.contains(list.get(i).toString())) {
		found = true;
	    } else {
		i++;
	    }
	} while (i < list.size() && !found);
	return found;
    }

    public String cover(Bracket bracket, String content) {
	return bracket.getLeft() + content + bracket.getRight();
    }

    @Override
    public <T extends TNode<T, S>, S extends TComponent> String generateCode(T node, Representation representation) {
	String rString = new String();
	if (typeOfNode != null) {
	    String EOL = typeOfNode.getEOL();
	    String separator = typeOfNode.getSeparator();
	    String alias = typeOfNode.getAlias();
	    Bracket bracket = typeOfNode.getBracket();
	    String separatorOperatorChildren = typeOfNode.getOperatorChildrenSeparator();

	    if (typeOfNode instanceof OpsWithTwoOperands) {
		/*
		 * structure: code(child(0)) separator type(operator) separator
		 * code(child(1)) EOL
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

	    } else if (typeOfNode instanceof OpsWithFollowingOperands) {
		/*
		 * structure: typeOfNode(node)
		 */
		for (int i = 0; i < node.getNumberOfChildren(); i++) {
		    if (i == 0) {
			rString += ((Code) node.getChildAt(i)).getCode(representation);
		    } else {
			rString += separator + ((Code) node.getChildAt(i)).getCode(representation);
		    }
		}
		rString = typeOfNode.getTypeOfNode() + separatorOperatorChildren + cover(bracket, rString) + EOL;
	    } else if (typeOfNode instanceof InvisibleOps) {
		/*
		 * structure: code(child(0)) separator code(child(1)) separator
		 * code(child(2))...
		 */
		try {
		    for (int i = 0; i < node.getNumberOfChildren(); i++) {
			T child = (T) node.getChildAt(i);
			if (i == 0) {
			    rString += ((Code) child).getCode(representation);
			} else {
			    rString += separator + ((Code) child).getCode(representation);
			}
		    }
		    rString = cover(bracket, rString);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    } else if (typeOfNode instanceof DatasetOps) {
		/*
		 * TODO: statements containing get and multiple clause operators
		 * structure:
		 */

		try {
		    rString = "";
		    T child0 = (T) node.getChildAt(0);
		    T child1 = (T) node.getChildAt(1);

		    String possibleDataset = ((Code) child0).getCode(representation);
		    boolean isJoinedSet = (possibleDataset.startsWith(Bracket.SQUARE.getLeft())) ? true : false;
		    boolean containsClauseOp = contains(possibleDataset, DatasetOps.values());
		    boolean isGetSet = (possibleDataset.startsWith("get")) ? true : false;

		    String expression0 = ((Code) child0).getCode(representation);
		    String expression1 = ((Code) child1).getCode(representation);

		    rString = generateCode(isJoinedSet, isGetSet, containsClauseOp, expression0, expression1,
			    representation, typeOfNode, separator, bracket);

		} catch (Exception e) {
		    e.printStackTrace();
		}

	    } else if (typeOfNode instanceof Leafs) {
		/*
		 * structure: comment(node) blank expression(node)
		 */
		if (representation.equals(Representation.STANDARD)) {
		    rString = (node.getComment() != null && !node.getComment().isEmpty())
			    ? node.getComment() + "" + node.getExpression() : node.getExpression();
		} else if (representation.equals(Representation.HTML)) {
		    /*
		     * to be considered: (1) dictionary objects need to be
		     * linked (e.g. cubes, variables) (2) members need to be
		     * implemented as tool tips
		     */

		    if (node.getComment() != null && !node.getComment().isEmpty()) {
			// TODO add comment as tool tip
		    }

		    rString = (node.getComment() != null && !node.getComment().isEmpty())
			    ? node.getComment() + "" + node.getExpression() : node.getExpression();
		} else {
		    // implement other types of represnetations
		}
	    } else if (typeOfNode instanceof MethodNode) {
		/*
		 * structure: alias code(child(0)) separator [ code(child(1)) ]
		 */
		T child0 = (T) node.getChildAt(0);
		T child1 = (T) node.getChildAt(1);
		// TODO check if comment is relevant in this case
		String comment = (node.getComment() != null && !node.getComment().isEmpty()) ? node.getComment() : "";
		rString = alias + separator + ((Code) child0).getCode(representation) + separator
			+ cover(bracket, ((Code) child1).getCode(representation));
	    } else if (typeOfNode instanceof SpecialNode) {
		/*
		 * structure: expression(node) + ":" + separatorOperatorChildren
		 * + code(child(0)) + "\n" + code(child(1) + ... + EOL
		 */
		rString = node.getExpression() + ":" + separatorOperatorChildren;
		for (int i = 0; i < node.getNumberOfChildren(); i++) {
		    if (i == 0) {
			rString += ((Code) node.getChildAt(i)).getCode(representation);
		    } else if (i == node.getNumberOfChildren() - 1) {
			rString += separator + ((Code) node.getChildAt(i)).getCode(representation);
		    } else {
			rString += separator + ((Code) node.getChildAt(i)).getCode(representation) + "\n";
		    }
		}
		rString += EOL;
	    } else {
		// TODO: implement other types of operators
	    }
	}
	return rString;
    }

    private String generateCode(boolean isJoinedSet, boolean isGetSet, boolean containsClauseOp, String term1,
	    String term2, Representation representation, TypeOfNode operator, String separator, Bracket bracket) {
	String rString = "";

	String symbolToFind = "";

	if (containsClauseOp) {
	    if (isJoinedSet && isGetSet) {
		int pos = getPosition(term1, DatasetOps.values());
		if (pos > 0) {
		    String transformation = term1;
		    rString = transformation.substring(0, pos) + operator.getTypeOfNode() + separator
			    + cover(bracket, term2) + ", " + transformation.substring(pos, transformation.length());
		}
	    } else if (!isJoinedSet && isGetSet) {
		symbolToFind = Bracket.ROUND.getRight();
		int pos = term1.lastIndexOf(symbolToFind);
		if (pos > 0) {
		    String transformation = term1;
		    rString = transformation.substring(0, pos) + ", " + operator.getTypeOfNode() + separator
			    + cover(bracket, term2) + symbolToFind;
		}
	    } else if (!isJoinedSet && !isGetSet) {
		symbolToFind = Bracket.SQUARE.getRight();
		int pos = term1.lastIndexOf(symbolToFind);
		if (pos > 0) {
		    String transformation = term1;
		    rString = transformation.substring(0, pos) + ", " + operator.getTypeOfNode() + separator
			    + cover(bracket, term2) + symbolToFind;
		}
	    } else {
		// throw exception: expression cannot be a joined set and a get
		// at the same time!
	    }
	} else {
	    if (isJoinedSet && isGetSet) {
		symbolToFind = Bracket.CURLED.getRight();
		int pos = term1.lastIndexOf(symbolToFind);
		if (pos > 0) {
		    String transformation = term1;
		    if (transformation.subSequence(pos - 1, pos).equals("{")) {
			rString = transformation.substring(0, pos) + operator.getTypeOfNode() + separator
				+ cover(bracket, term2) + symbolToFind;
		    } else {
			rString = transformation.substring(0, pos) + ", " + toString() + separator
				+ cover(bracket, term2) + symbolToFind;
		    }
		}
	    } else if (!isJoinedSet && isGetSet) {
		symbolToFind = Bracket.ROUND.getRight();
		int pos = term1.lastIndexOf(symbolToFind);
		if (pos > 0) {
		    String transformation = term1;
		    rString = transformation.substring(0, pos) + ", " + toString() + separator + cover(bracket, term2)
			    + symbolToFind;
		}
	    } else if (!isJoinedSet && !isGetSet) {
		rString = term1 + separator + Bracket.SQUARE.getLeft() + toString() + separator + cover(bracket, term2)
			+ Bracket.SQUARE.getRight();
	    } else {
		// throw exception: expression cannot be a joined set and a get
		// at the same time!
	    }
	}
	return rString;
    }

}
