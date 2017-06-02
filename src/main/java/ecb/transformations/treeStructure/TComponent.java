package ecb.transformations.treeStructure;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import ecb.transformations.interfaces.components.Similar;
import ecb.transformations.interfaces.components.WebComponent;

/**
 * Stand alone transformation component without consideration of the tree / node
 * structure of the transformation. This class acts as a data field for
 * {@link Node} respectively {@link TNode} objects.<br>
 * Please note that the identifier for this class is the {@link #componentId}
 * which also acts as the relevant input for {@link #equals(Object)} and
 * {@link #hashCode()}. Similar {@link TComponent}s (i.e. components having the
 * same expression & type) are identified using {@link #isSimilar(Object)}.
 * 
 * @author Dominik Lin
 *
 */
@Entity
@SuppressWarnings("rawtypes")
public class TComponent implements Similar, WebComponent, Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = -943562954113189135L;

    /**
     * The (technical) identifier of this {@link TComponent}
     */
    @Id
    @Column(name = "componentId", nullable = false)
    private int componentId;

    /**
     * The expression of this {@link TComponent} (e.g. result)
     */
    @Column(name = "expression", length = 100000)
    private String expression;

    /**
     * The type of this {@link TComponent} (e.g. CUBE_ID)
     */
    @Column(name = "type")
    private String type;

    /**
     * The comment related to this {@link TComponent}
     */
    @Column(name = "comment", length = 100000)
    private String comment = "";

    /**
     * The {@link URL} related to this {@link TComponent}
     */
    @Column(name = "uniformResourceLocation")
    private URL uniformResourceLocation;

    /**
     * The tool tip related to this {@link TComponent}
     */
    @Column(name = "tooltip")
    private String tooltip = "";

    /**
     * The description related to this {@link TComponent}
     */
    @Column(name = "description", length = 100000)
    private String description;

    /**
     * The {@link TNode} related to this {@link TComponent}
     */
    @OneToOne
    private TNode node;

    // ----------------------------------------------------------
    // equals, hashCode, isSimilar
    // ----------------------------------------------------------

    @Override
    public boolean equals(Object obj) {
	boolean equals = false;
	if (obj instanceof TComponent) {
	    TComponent component = (TComponent) obj;
	    equals = this.componentId == component.componentId;
	}
	return equals;
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(componentId).append(getClass()).hashCode();
    }

    @Override
    public boolean isSimilar(Object obj) {
	boolean isSimilar = false;
	if (obj instanceof TComponent) {
	    TComponent component = (TComponent) obj;
	    isSimilar = this.expression.equals(component.expression) && this.type.equals(component.type);
	}
	return isSimilar;
    }

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public TComponent() {
	super();
    }

    public TComponent(String expression) {
	setExpression(expression);
    }

    public TComponent(String expression, String type) {
	setExpression(expression);
	setType(type);
    }

    public TComponent(String expression, String type, String comment) {
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
    public String getType() {
	return type;
    }

    @Override
    public String getComment() {
	return comment;
    }

    @Override
    public URL getUniformResourceLocation() {
	return uniformResourceLocation;
    }

    @Override
    public String getTooltip() {
	return tooltip;
    }

    @Override
    public String getDescription() {
	return description;
    }

    // ----------------------------------------------------------
    // additional methods
    // ----------------------------------------------------------

    public int getComponentId() {
	return componentId;
    }

    public void setComponentId(int componentId) {
	this.componentId = componentId;
    }

    public void setTooltip(String tooltip) {
	this.tooltip = tooltip;
    }

    public void setExpression(String expression) {
	this.expression = expression;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public void setType(String type) {
	this.type = type;
    }

    public void setUniformResourceLocation(URL uniformResourceLocation) {
	this.uniformResourceLocation = uniformResourceLocation;
    }

    /**
     * Allows to set the {@link URL} of this {@link TComponent} using the string
     * representation of the URL. This method throws a
     * <tt>MalformedURLException</tt>.
     * 
     * @param uniformResourceLocator
     *            the {@link String} representation of the Uniform Resource
     *            Locator
     * @exception MalformedURLException
     *                if the given input does not comply with the Uniform
     *                Resource Locator syntax
     */
    public void setUniformResourceLocation(String uniformResourceLocation) {
	try {
	    this.uniformResourceLocation = new URL(uniformResourceLocation);
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	}
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public TNode getNode() {
	return node;
    }

    public void setNode(TNode node) {
	setNode(node, true);
    }

    public void setNode(TNode node, boolean set) {
	this.node = node;
	if (node != null && set) {
	    node.setComponent(this, false);
	}
    }

    // ----------------------------------------------------------
    // toString()
    // ----------------------------------------------------------

    @Override
    public String toString() {
	return getExpression() + " [" + getType() + "]";
    }

}
