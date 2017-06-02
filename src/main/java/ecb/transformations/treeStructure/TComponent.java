package ecb.transformations.BirdVtl;

import java.io.Serializable;

import ecb.transformations.interfaces.TypeOfNode;
import ecb.transformations.interfaces.WebComponent;


/**
 * Stand alone transformation component without consideration of the tree / node
 * structure of the transformation. This class acts as a data field for
 * {@link Node} respectively {@link TNode} objects.
 * 
 * @author Dominik Lin
 *
 */
public class TComponent implements WebComponent, Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = -943562954113189135L;

    protected String expression;

    protected TypeOfNode type;

    protected String comment = "";

    protected String link = "";

    protected String tooltip = "";

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public TComponent() {
	super();
    }

    public TComponent(String expression) {
	setExpression(expression);
    }

    public TComponent(String expression, TypeOfNode type) {
	setExpression(expression);
	setType(type);
    }

    public TComponent(String expression, TypeOfNode type, String comment) {
	setExpression(expression);
	setType(type);
	setComment(comment);
    }

    // ----------------------------------------------------------
    // inherited methods (from interface)
    // ----------------------------------------------------------

    @Override
    public String getExpression() {
	return expression;
    }

    @Override
    public TypeOfNode getType() {
	return type;
    }

    @Override
    public String getComment() {
	return comment;
    }

    @Override
    public String getLink() {
	return link;
    }

    @Override
    public String getTooltip() {
	return tooltip;
    }

    // ----------------------------------------------------------
    // additional methods
    // ----------------------------------------------------------

    public void setTooltip(String tooltip) {
	this.tooltip = tooltip;
    }

    public void setExpression(String expression) {
	this.expression = expression;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public void setType(TypeOfNode type) {
	this.type = type;
    }

    public void setLink(String link) {
	this.link = link;
    }

    // ----------------------------------------------------------
    // toString()
    // ----------------------------------------------------------

    @Override
    public String toString() {
	return getExpression() + " [" + getType() + "]";
    }

}
