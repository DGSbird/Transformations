package ecb.transformations.metadata;

import java.util.HashSet;
import java.util.Set;

import ecb.technical.interfaces.nodes.TypeOfNode;
import ecb.transformations.enums.operators.DatasetOps;
import ecb.transformations.enums.operators.IdentificationNode;
import ecb.transformations.enums.operators.InvisibleOps;
import ecb.transformations.enums.operators.Leafs;
import ecb.transformations.enums.operators.MethodNode;
import ecb.transformations.enums.operators.OpsWithFollowingOperands;
import ecb.transformations.enums.operators.OpsWithTwoOperands;
import ecb.transformations.enums.operators.SpecialNode;

/**
 * Node classification allows to "classify" nodes related to the procedures that
 * are applied on these nodes when transforming the (copy of the)
 * {@link ParseTree} into the {@link TTree} that is used to store
 * transformations in the BIRD transformation model.
 * 
 * @author Dominik Lin
 *
 */
@SuppressWarnings("unchecked")
public class NodeClassification {

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------


    /**
     * A set of type of nodes that are valid in the BIRD transformation model
     */
    public static Set<String> typesToKeep;

    /**
     * A set of type of nodes where the next sibling should become a child of
     * this node
     */
    public static Set<String> nextSiblingToChildTypes;

    /**
     * A set of type of nodes where the previous and the next sibling should
     * become children of this node
     */
    public static Set<String> previousAndNextSiblingToChildTypes;

    /**
     * A set of type of nodes where the first child is a container node
     */
    public static Set<String> nodesWhereFirstChildIsContainer;

    /**
     * A set of type of nodes where the second child is a container node
     */
    public static Set<String> nodesWhereSecondChildIsContainer;

    /**
     * A set of type of nodes where the next sibling could be a comment
     */
    public static Set<String> nextSiblingComment;

    /**
     * A set of type of nodes which qualify as indicators for comments (TODO
     * check)
     */
    public static Set<String> commentQualifyingIndicators;

    /**
     * A set of type of nodes which represent the target of possible comments
     * (TODO check)
     */
    public static Set<String> commentQualifyingMethodsTarget;

    /**
     * A set of type of nodes representing different type of joins
     */
    public static Set<String> typesOfJoin;

    /**
     * A set of type of nodes containing clause operators (i.e. data set
     * operators)
     */
    public static Set<String> clauseOperators;

    /**
     * A set of type of nodes containing operators where the operands are
     * following the operator (e.g. union, setdiff, symdiff, intersect)
     */
    public static Set<String> operatorsWithFollowingOps;

    /**
     * A set of type of nodes containing simple mathematical methods (e.g. +, -,
     * *, /)
     */
    public static Set<String> mathOperators;

    // ----------------------------------------------------------
    // static
    // ----------------------------------------------------------

    static {
	typesToKeep = getTypesToKeep();
	nextSiblingToChildTypes = getNextSiblingToChildTypes();
	previousAndNextSiblingToChildTypes = getPreviousAndNextSiblingToChildTypes();
	nodesWhereFirstChildIsContainer = getNodesWhereFirstChildIsContainer();
	nodesWhereSecondChildIsContainer = getNodesWhereSecondChildIsContainer();
	nextSiblingComment = getNextSiblingComment();
	commentQualifyingIndicators = getCommentQualifyingIndicators();
	commentQualifyingMethodsTarget = getCommentQualifyingMethodsTarget();
	typesOfJoin = getTypesOfJoin();
	clauseOperators = getClauseOperators();
	operatorsWithFollowingOps = getOperatorsWithFollowingOps();
	mathOperators = getMathOperators();
    }

    // ----------------------------------------------------------
    // additional methods
    // ----------------------------------------------------------

    /**
     * 
     * @return a complete set of valid types of nodes (string representation);
     *         valid in the sense that they are part of the BIRD transformation
     *         model
     */
    public static <V extends TypeOfNode, Build> Set<String> getTypesToKeep() {
	if (typesToKeep == null) {
	    typesToKeep = new HashSet<>();
	    Set<V> set = new HashSet<>();
	    for (InvisibleOps op : InvisibleOps.values()) {
		set.add((V) op);
	    }
	    for (OpsWithTwoOperands op : OpsWithTwoOperands.values()) {
		set.add((V) op);
	    }
	    for (OpsWithFollowingOperands op : OpsWithFollowingOperands.values()) {
		set.add((V) op);
	    }
	    for (DatasetOps op : DatasetOps.values()) {
		set.add((V) op);
	    }
	    for (MethodNode op : MethodNode.values()) {
		set.add((V) op);
	    }
	    for (Leafs op : Leafs.values()) {
		set.add((V) op);
	    }
	    for (SpecialNode op : SpecialNode.values()) {
		set.add((V) op);
	    }
	    for (V element : set) {
		typesToKeep.add(element.getTypeOfNode());
	    }
	}
	return typesToKeep;
    }

    /**
     * 
     * @return a set of type of nodes where the next sibling should become this
     *         node's child
     */
    public static <V extends TypeOfNode, Build> Set<String> getNextSiblingToChildTypes() {
	if (nextSiblingToChildTypes == null) {
	    nextSiblingToChildTypes = new HashSet<>();
	    Set<V> set = new HashSet<>();
	    for (DatasetOps op : DatasetOps.values()) {
		set.add((V) op);
	    }
	    set.add((V) OpsWithFollowingOperands.CALL);
	    set.add((V) OpsWithFollowingOperands.COUNT);
	    set.add((V) OpsWithFollowingOperands.ELSE);
	    set.add((V) OpsWithFollowingOperands.ERRORCODE);
	    set.add((V) OpsWithFollowingOperands.GET);
	    set.add((V) OpsWithFollowingOperands.GROUP_BY);
	    set.add((V) OpsWithFollowingOperands.ISNULL);
	    set.add((V) OpsWithFollowingOperands.NOT_ISNULL);
	    set.add((V) OpsWithFollowingOperands.MIN);
	    set.add((V) OpsWithFollowingOperands.MAX);
	    set.add((V) OpsWithFollowingOperands.SUM);
	    set.add((V) OpsWithFollowingOperands.SQRT);
	    set.add((V) OpsWithFollowingOperands.AVG);
	    set.add((V) OpsWithFollowingOperands.THEN);
	    set.add((V) OpsWithFollowingOperands.WHEN);
	    set.add((V) OpsWithFollowingOperands.NOT);
	    set.add((V) OpsWithFollowingOperands.IF);
	    set.add((V) OpsWithFollowingOperands.ELSEIF);
	    set.add((V) OpsWithFollowingOperands.CHECK);
	    set.add((V) OpsWithFollowingOperands.HIERARCHY);
	    set.add((V) OpsWithFollowingOperands.TRANSCODE);
	    set.add((V) OpsWithFollowingOperands.PUT);
	    set.add((V) OpsWithFollowingOperands.REPLACE);
	    set.add((V) OpsWithFollowingOperands.SUBSTR);

	    set.add((V) OpsWithFollowingOperands.SET_DIFF);
	    set.add((V) OpsWithFollowingOperands.SYM_DIFF);
	    set.add((V) OpsWithFollowingOperands.UNION);
	    for (V v : set) {
		nextSiblingToChildTypes.add(v.getTypeOfNode());
	    }
	}
	return nextSiblingToChildTypes;
    }

    /**
     * 
     * @return a set of type of nodes where the previous and the next sibling
     *         need to become this node's child
     */
    public static <V extends TypeOfNode, Build> Set<String> getPreviousAndNextSiblingToChildTypes() {
	if (previousAndNextSiblingToChildTypes == null) {
	    previousAndNextSiblingToChildTypes = new HashSet<>();
	    Set<V> set = new HashSet<>();
	    set.add((V) OpsWithTwoOperands.DEF);
	    set.add((V) OpsWithTwoOperands.DOT);
	    set.add((V) OpsWithTwoOperands.MINUS);
	    set.add((V) OpsWithTwoOperands.PLUS);
	    set.add((V) OpsWithTwoOperands.MULTIPLY);
	    set.add((V) OpsWithTwoOperands.DIVIDE);
	    set.add((V) OpsWithTwoOperands.EQUALS);
	    set.add((V) OpsWithTwoOperands.NOT_EQUALS);
	    set.add((V) OpsWithTwoOperands.GT);
	    set.add((V) OpsWithTwoOperands.GE);
	    set.add((V) OpsWithTwoOperands.LT);
	    set.add((V) OpsWithTwoOperands.LE);
	    set.add((V) OpsWithTwoOperands.AND);
	    set.add((V) OpsWithTwoOperands.OR);
	    set.add((V) OpsWithTwoOperands.IN);
	    set.add((V) OpsWithTwoOperands.NOT_IN);
	    set.add((V) OpsWithTwoOperands.CONCAT);
	    for (V v : set) {
		previousAndNextSiblingToChildTypes.add(v.getTypeOfNode());
	    }
	}
	return previousAndNextSiblingToChildTypes;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         all nodes where the first child should be a container for one or
     *         more members.
     */
    public static Set<String> getNodesWhereFirstChildIsContainer() {
	if (nodesWhereFirstChildIsContainer == null) {
	    nodesWhereFirstChildIsContainer = new HashSet<>();
	    nodesWhereFirstChildIsContainer.add(DatasetOps.KEEP.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(DatasetOps.RENAME.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(OpsWithFollowingOperands.GROUP_BY.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(OpsWithFollowingOperands.UNION.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(OpsWithFollowingOperands.SET_DIFF.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(OpsWithFollowingOperands.SYM_DIFF.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(OpsWithFollowingOperands.INTERSECT.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(OpsWithFollowingOperands.REPLACE.getTypeOfNode());
	    nodesWhereFirstChildIsContainer.add(OpsWithFollowingOperands.SUBSTR.getTypeOfNode());
	}
	return nodesWhereFirstChildIsContainer;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         all nodes where the second child should be a container for one or
     *         more members.
     */
    public static Set<String> getNodesWhereSecondChildIsContainer() {
	if (nodesWhereSecondChildIsContainer == null) {
	    nodesWhereSecondChildIsContainer = new HashSet<>();
	    nodesWhereSecondChildIsContainer.add(OpsWithTwoOperands.IN.getTypeOfNode());
	    nodesWhereSecondChildIsContainer.add(OpsWithTwoOperands.NOT_IN.getTypeOfNode());
	    nodesWhereSecondChildIsContainer.add(InvisibleOps.FUNCTION_CALL.getTypeOfNode());
	    nodesWhereSecondChildIsContainer.add(IdentificationNode.PROCEDURE_CALL_BODY.getTypeOfNode());
	}
	return nodesWhereSecondChildIsContainer;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         all nodes which qualify for a comment in case the previous
     *         sibling is a <code>Comment</code> node.
     */
    public static Set<String> getNextSiblingComment() {
	if (nextSiblingComment == null) {
	    nextSiblingComment = new HashSet<>();
	    nextSiblingComment.add(SpecialNode.RULE_ID.getTypeOfNode());
	    nextSiblingComment.add(OpsWithFollowingOperands.IF.getTypeOfNode());
	    nextSiblingComment.add(OpsWithFollowingOperands.ELSEIF.getTypeOfNode());
	    nextSiblingComment.add(OpsWithFollowingOperands.ELSE.getTypeOfNode());
	}
	return nextSiblingComment;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         all nodes which are used as indicators of methods for comment
     *         transfer.
     */
    public static Set<String> getCommentQualifyingIndicators() {
	if (commentQualifyingIndicators == null) {
	    commentQualifyingIndicators = new HashSet<>();
	    commentQualifyingIndicators.add(IdentificationNode.DEF_FUNCTION.getTypeOfNode());
	    commentQualifyingIndicators.add(IdentificationNode.DEF_PROCEDURE.getTypeOfNode());
	    commentQualifyingIndicators.add(IdentificationNode.DEF_RULESET.getTypeOfNode());
	}
	return commentQualifyingIndicators;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         nodes which qualify as target for method comments.
     */
    public static Set<String> getCommentQualifyingMethodsTarget() {
	if (commentQualifyingMethodsTarget == null) {
	    commentQualifyingMethodsTarget = new HashSet<>();
	    commentQualifyingMethodsTarget.add(Leafs.FUNCTION_ID.getTypeOfNode());
	    commentQualifyingMethodsTarget.add(Leafs.PROCEDURE_ID.getTypeOfNode());
	    commentQualifyingMethodsTarget.add(Leafs.RULESET_ID.getTypeOfNode());
	}
	return commentQualifyingMethodsTarget;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         all valid join types.
     */
    public static Set<String> getTypesOfJoin() {
	if (typesOfJoin == null) {
	    typesOfJoin = new HashSet<>();
	    typesOfJoin.add(Leafs.INNER.getTypeOfNode());
	    typesOfJoin.add(Leafs.OUTER.getTypeOfNode());
	    typesOfJoin.add(Leafs.LEFT.getTypeOfNode());
	    typesOfJoin.add(Leafs.RIGHT.getTypeOfNode());
	    typesOfJoin.add(Leafs.CROSS.getTypeOfNode());
	}
	return typesOfJoin;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         all clause operators
     */
    public static Set<String> getClauseOperators() {
	if (clauseOperators == null) {
	    clauseOperators = new HashSet<>();
	    for (DatasetOps op : DatasetOps.values()) {
		clauseOperators.add(op.getTypeOfNode());
	    }
	}
	return clauseOperators;
    }

    /**
     * 
     * @return a set of string objects containing the string representation of
     *         all set operators
     */
    public static Set<String> getOperatorsWithFollowingOps() {
	if (operatorsWithFollowingOps == null) {
	    operatorsWithFollowingOps = new HashSet<>();
	    operatorsWithFollowingOps.add(OpsWithFollowingOperands.UNION.toString());
	    operatorsWithFollowingOps.add(OpsWithFollowingOperands.SET_DIFF.toString());
	    operatorsWithFollowingOps.add(OpsWithFollowingOperands.SYM_DIFF.toString());
	    operatorsWithFollowingOps.add(OpsWithFollowingOperands.INTERSECT.toString());
	}
	return operatorsWithFollowingOps;
    }

    /**
     * 
     * @return a set of string object containing the string representation of
     *         math operators
     */
    public static Set<String> getMathOperators() {
	if (mathOperators == null) {
	    mathOperators = new HashSet<>();
	    mathOperators.add(OpsWithTwoOperands.PLUS.toString());
	    mathOperators.add(OpsWithTwoOperands.MINUS.toString());
	    mathOperators.add(OpsWithTwoOperands.MULTIPLY.toString());
	    mathOperators.add(OpsWithTwoOperands.DIVIDE.toString());
	}
	return mathOperators;
    }

}
