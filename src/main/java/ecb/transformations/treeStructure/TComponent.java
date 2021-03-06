package ecb.transformations.treeStructure;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import ecb.transformations.abstractClasses.Component;

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
public class TComponent extends Component implements Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = -943562954113189135L;

    /**
     * The (technical) identifier of this {@link TComponent}
     */
    @Id
    @GeneratedValue
    @Column(name = "componentId", nullable = false)
    private int componentId;

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
	super(expression);
    }

    public TComponent(String expression, String type) {
	super(expression, type);
    }

    public TComponent(String expression, String type, String comment) {
	setExpression(expression);
	setType(type);
	setComment(comment);
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
