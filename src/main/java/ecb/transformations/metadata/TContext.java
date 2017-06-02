package ecb.transformations.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import ecb.transformations.informationModel.TScheme;
import ecb.transformations.informationModel.Transformation;
import ecb.transformations.interfaces.nodes.TypeOfNode;
import ecb.transformations.operators.enums.InvisibleOps;
import ecb.transformations.operators.enums.OpsWithTwoOperands;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

/**
 * Transformation context.
 * 
 * @author Dominik Lin
 *
 */
@SuppressWarnings({ "unchecked" })
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

    /**
     * The name of the persistence unit
     */
    public static final String persistenceUnitName = "entityRelationshipModel";

    /**
     * The {@link EntityManagerFactory} of this {@link Context}
     */
    public EntityManagerFactory factory;

    /**
     * The {@link EntityManager}
     */
    public EntityManager entityManager;

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
    // constructor
    // ----------------------------------------------------------

    public TContext() {
	super();
	// scale down the information displayed on the console
	java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
    }

    public TContext(String persistenceUnitName) {
	super();
	setEntityManager(persistenceUnitName);
	// scale down the information displayed on the console
	java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
    }

    // ----------------------------------------------------------
    // get / set methods
    // ----------------------------------------------------------

    /**
     * Returns the {@link #entityManager}. In case the {@link #entityManager} is
     * null, a new {@link EntityManager} object is created and returned.
     * 
     * @return the {@link EntityManager}
     */
    public EntityManager getEntityManager() {
	return getEntityManager(null);
    }

    public EntityManager getEntityManager(String persistenceUnitName) {
	if (entityManager == null) {
	    setEntityManager(persistenceUnitName);
	}
	return entityManager;
    }

    private void setEntityManager(String persistenceUnitName) {
	String name = (persistenceUnitName != null) ? persistenceUnitName : TContext.persistenceUnitName;
	try {
	    factory = Persistence.createEntityManagerFactory(name);
	    entityManager = factory.createEntityManager();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private EntityManagerFactory getFactory() {
	return factory;
    }

    // ----------------------------------------------------------
    // closing the context
    // ----------------------------------------------------------

    public void close() {
	getEntityManager().close();
	getFactory().close();
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

    // ----------------------------------------------------------
    // methods for extracting objects from the persistence context
    // ----------------------------------------------------------

    /**
     * 
     * @return a list of all transformation nodes present in the persistence
     *         context
     */
    public <T extends TNode<T, S>, S extends TComponent> List<T> getTNodes() {
	return getEntityManager().createNamedQuery(TNode.QUERY_GET_ALL).getResultList();
    }

    /**
     * 
     * @return a list of all transformations present in the persistence context
     */
    public <T extends TNode<T, S>, S extends TComponent> List<Transformation<T, S>> getTransformations() {
	return getEntityManager().createNamedQuery(Transformation.QUERY_GET_ALL).getResultList();
    }

    /**
     * 
     * @return a list of all transformation schemes present in the persistence
     *         context
     */
    public <T extends TNode<T, S>, S extends TComponent> List<TScheme<T, S>> getTSchemes() {
	return getEntityManager().createNamedQuery(TScheme.QUERY_GET_ALL).getResultList();
    }

    /**
     * Retrieves a list of transformation nodes matching the given (input)
     * expression from the persistence context.
     * 
     * @param expression
     *            an expression that should be matched
     * @return a list of all transformation nodes matching the given (input)
     *         expression
     */

    public <T extends TNode<T, S>, S extends TComponent> List<T> getTNodesByExpression(String expression) {
	return getEntityManager().createNamedQuery(TNode.QUERY_FIND_BY_EXPRESSION)
		.setParameter(TNode.PARAM_EXPRESSION, expression).getResultList();
    }

    /**
     * Retrieves a list of transformation nodes matching the given (input) type
     * from the persistence context.
     * 
     * @param type
     *            a type that should be matched
     * @return a list of all transformation nodes matching the given (input)
     *         type
     */
    public <T extends TNode<T, S>, S extends TComponent> List<T> getTNodesByType(String type) {
	return getEntityManager().createNamedQuery(TNode.QUERY_FIND_BY_TYPE).setParameter(TNode.PARAM_TYPE, type)
		.getResultList();
    }

    /**
     * Retrieves a list of transformation nodes matching the given (input) code
     * from the persistence context.
     * 
     * @param code
     *            a code that should be matched
     * @return a list of all transformation nodes matching the given (input)
     *         code
     */
    public <T extends TNode<T, S>, S extends TComponent> TNode<T, S> getTNodeByCode(String code) {
	TNode<T, S> node = null;
	try {
	    node = (TNode<T, S>) getEntityManager().createNamedQuery(TNode.QUERY_FIND_BY_CODE)
		    .setParameter(TNode.PARAM_CODE, code).getSingleResult();
	} catch (NoResultException NRe) {
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return node;
    }

}
