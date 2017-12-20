package ecb.technical.interfaces.nodes;

import java.io.Serializable;

import ecb.transformations.enums.Bracket;

/**
 * Type of node interface comprising methods that need to be implemented by all
 * {@link Enum}s representing nodes.
 * 
 * @author Dominik Lin
 *
 */
public interface TypeOfNode extends Serializable {
    /**
     * 
     * @return the {@link String} representation of this type of node (e.g.
     *         ":=")
     */
    public String getTypeOfNode();

    /**
     * 
     * @return the {@link Bracket} related to this type of node (e.g.
     *         {@link Bracket#ROUND}
     */
    default public Bracket getBracket() {
	return Bracket.NONE;
    };

    /**
     * 
     * @return the separator between this node's children
     */
    default public String getSeparator() {
	return " ";
    };

    /**
     * 
     * @return the separator between this node's expression (i.e. the operator)
     *         and it's children (i.e. the operands)
     */
    default public String getOperatorChildrenSeparator() {
	return ", ";
    };

    /**
     * 
     * @return the end of line symbol (e.g. ";")
     */
    default public String getEOL() {
	return "";
    };

    /**
     * 
     * @return the alias of this node's expression (e.g. "define procedure " for
     *         "Procedure information" <code>node</code>)
     */
    default public String getAlias() {
	return "";
    };

}
