package ecb.transformations;

import org.junit.Before;
import org.junit.Test;

import ecb.transformations.enums.transformationProcess.Phase;
import ecb.transformations.enums.transformationProcess.SchemeSubtype;
import ecb.transformations.enums.transformationProcess.SchemeType;
import ecb.transformations.enums.transformationProcess.Subphase;
import ecb.transformations.informationModel.TScheme;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;
import junit.framework.TestCase;

/**
 * Test case for {@link TScheme} class. Please note that this test case does not
 * cover the interaction with the persistence context.
 * 
 * @author Dominik Lin
 *
 * @param <T>
 *            a type extending the {@link TNode} class
 * @param <S>
 *            a type extending the {@link TComponent} class
 */
public class TestTScheme<T extends TNode<T, S>, S extends TComponent> extends TestCase {

    TScheme<T, S> scheme;

    Phase phase = Phase.GENERATION;

    Subphase subphase = Subphase.SETUP;

    SchemeType schemeType = SchemeType.GENERATION;

    SchemeSubtype schemeSubtype = SchemeSubtype.ANACREDIT;

    String relatedEntity = "relatedEntity";

    int phaseNumber = 13;

    int subphaseNumber = 17;

    public TestTScheme() {
	super("TestTScheme");
    }

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
	scheme = new TScheme<>((S) new TComponent("origin", "origin"));
	scheme.setPhase(phase);
	scheme.setSubphase(subphase);
	scheme.setSchemeType(schemeType);
	scheme.setSchemeSubtype(schemeSubtype);
	scheme.setRelatedEntity(relatedEntity);
	scheme.setPhaseNumber(phaseNumber);
	scheme.setSubphaseNumber(subphaseNumber);
    }

    @Test
    public void testGetPhase() {
	assertTrue(scheme.getPhase().equals(phase));
    }

    @Test
    public void testGetSubphase() {
	assertTrue(scheme.getSubphase().equals(subphase));
    }

    @Test
    public void testGetSchemeType() {
	assertTrue(scheme.getSchemeType().equals(schemeType));
    }

    @Test
    public void testGetSchemeSubtype() {
	assertTrue(scheme.getSchemeSubtype().equals(schemeSubtype));
    }

    @Test
    public void testGetRelatedEntity() {
	assertTrue(scheme.getRelatedEntity().equals(relatedEntity));
    }

    @Test
    public void testGetPhaseNumber() {
	assertTrue(scheme.getPhaseNumber() == phaseNumber);
    }

    @Test
    public void testGetSubphaseNumber() {
	assertTrue(scheme.getSubphaseNumber() == subphaseNumber);
    }

}
