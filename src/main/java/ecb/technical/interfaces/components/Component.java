package ecb.technical.interfaces.components;

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
    public String getType();

    /**
     * 
     * @return the comment related to this component
     */
    public String getComment();

    /**
     * 
     * @return the description related to this component
     */
    public String getDescription();
}
