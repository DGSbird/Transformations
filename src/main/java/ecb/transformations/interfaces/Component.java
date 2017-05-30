package ecb.transformations.interfaces;

import java.io.Serializable;

/**
 * Component interface comprising methods for transformation components (i.e.
 * get / set methods for expression, type and comment).
 * 
 * @author Dominik Lin
 *
 */
public interface Component extends Serializable {
    /**
     * 
     * @return the expression of this component (e.g. ":=")
     */
    public String getExpression();

    /**
     * 
     * @return the type of this component
     */
    public TypeOfNode getType();

    /**
     * 
     * @return the comment related to this component
     */
    public String getComment();
}
