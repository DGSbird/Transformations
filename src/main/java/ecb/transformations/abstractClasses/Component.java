package ecb.transformations.abstractClasses;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import ecb.transformations.interfaces.components.Similar;
import ecb.transformations.interfaces.components.WebComponent;

/**
 * Entity implementation class for Entity: Component
 * 
 * @author Dominik Lin
 *
 */
@MappedSuperclass

public abstract class Component implements Similar, WebComponent, Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = 1L;

    /**
     * The expression of this {@link Component} (e.g. result)
     */
    @Column(name = "expression", length = 100000)
    protected String expression;

    /**
     * The type of this {@link Component} (e.g. CUBE_ID)
     */
    @Column(name = "type")
    protected String type;

    /**
     * The comment related to this {@link Component}
     */
    @Column(name = "comment", length = 100000)
    protected String comment;

    /**
     * The location related to this {@link Component}
     */
    @Column(name = "location")
    protected String location;

    /**
     * The tool tip related to this {@link Component}
     */
    @Column(name = "tooltip")
    protected String tooltip;

    /**
     * The description related to this {@link Component}
     */
    @Column(name = "description", length = 100000)
    protected String description;

    // ----------------------------------------------------------
    // isSimilar
    // ----------------------------------------------------------

    @Override
    public boolean isSimilar(Object obj) {
	boolean isSimilar = false;
	if (obj instanceof Component) {
	    Component component = (Component) obj;
	    isSimilar = this.expression.equals(component.expression) && this.type.equals(component.type);
	}
	return isSimilar;
    }

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public Component() {
	super();
    }

    /**
     * Please note that this constructor is used for components where the
     * expression equals the type.
     * 
     * @param expressionEqualsType
     *            the expression of this component
     */
    public Component(String expressionEqualsType) {
	super();
	setExpression(expressionEqualsType);
	setType(expressionEqualsType);
    }

    public Component(String expression, String type) {
	super();
	setExpression(expression);
	setType(type);
    }

    public Component(String expression, String type, String comment) {
	super();
	setExpression(expression);
	setType(type);
	setComment(comment);
    }

    // ----------------------------------------------------------
    // get / set methods
    // ----------------------------------------------------------

    public String getExpression() {
	return this.expression;
    }

    public void setExpression(String expression) {
	this.expression = expression;
    }

    public String getType() {
	return this.type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getComment() {
	return this.comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public String getLocation() {
	return this.location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public String getTooltip() {
	return this.tooltip;
    }

    public void setTooltip(String tooltip) {
	this.tooltip = tooltip;
    }

    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

}
