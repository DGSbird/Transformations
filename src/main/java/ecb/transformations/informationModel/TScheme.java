package ecb.transformations.informationModel;

import ecb.transformations.enums.transformationProcess.Phase;
import ecb.transformations.enums.transformationProcess.SchemeSubtype;
import ecb.transformations.enums.transformationProcess.SchemeType;
import ecb.transformations.enums.transformationProcess.Subphase;
import ecb.transformations.interfaces.components.Similar;
import ecb.transformations.interfaces.components.WebComponent;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;
import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TScheme<br>
 * BIRD interpretation of the SDMX information model for transformations for
 * Transformation scheme object.
 * 
 * @param <T>
 *            type extending {@link TNode}
 * @param <S>
 *            type implementing {@link Similar} & {@link WebComponent} interface
 * @author Dominik Lin
 */
@Entity
@NamedQueries({ @NamedQuery(name = TScheme.QUERY_GET_ALL, query = "SELECT s FROM TScheme s"),
	@NamedQuery(name = TScheme.QUERY_FIND_BY_CODE, query = "SELECT s FROM TScheme s WHERE s.code=:"
		+ TScheme.PARAM_CODE) })
public class TScheme<T extends TNode<T, S>, S extends TComponent> extends TNode<T, S> implements Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = 5125128435436851791L;

    public static final String QUERY_GET_ALL = "TScheme.getAll";

    public static final String QUERY_FIND_BY_CODE = "TScheme.findByCode";

    public static final String PARAM_CODE = "code";

    /**
     * This scheme's phase
     */
    private Phase phase = Phase.NO_PHASE;

    /**
     * This scheme's sub phase
     */
    private Subphase subphase;

    /**
     * This scheme's type
     */
    private SchemeType schemeType;

    /**
     * This scheme's sub type
     */
    private SchemeSubtype schemeSubtype;

    /**
     * This scheme's related entity
     */
    private String relatedEntity;

    /**
     * This scheme's phase number
     */
    private int phaseNumber = 0;

    /**
     * This scheme's sub phase number
     */
    private int subphaseNumber = 0;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public TScheme() {
	super();
    }

    public TScheme(TComponent component) {
	super(component);
    }

    // ----------------------------------------------------------
    // get / set methods
    // ----------------------------------------------------------

    public Phase getPhase() {
	return this.phase;
    }

    public void setPhase(Phase phase) {
	this.phase = phase;
    }

    public Subphase getSubphase() {
	return this.subphase;
    }

    public void setSubphase(Subphase subphase) {
	this.subphase = subphase;
    }

    public SchemeType getSchemeType() {
	return this.schemeType;
    }

    public void setSchemeType(SchemeType schemeType) {
	this.schemeType = schemeType;
    }

    public SchemeSubtype getSchemeSubtype() {
	return this.schemeSubtype;
    }

    public void setSchemeSubtype(SchemeSubtype schemeSubtype) {
	this.schemeSubtype = schemeSubtype;
    }

    public String getRelatedEntity() {
	return this.relatedEntity;
    }

    public void setRelatedEntity(String relatedEntity) {
	this.relatedEntity = relatedEntity;
    }

    public int getPhaseNumber() {
	return this.phaseNumber;
    }

    public void setPhaseNumber(int phaseNumber) {
	this.phaseNumber = phaseNumber;
    }

    public int getSubphaseNumber() {
	return this.subphaseNumber;
    }

    public void setSubphaseNumber(int subphaseNumber) {
	this.subphaseNumber = subphaseNumber;
    }

}
