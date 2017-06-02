package ecb.transformations.enums.transformationProcess;

/**
 * Phases in the BIRD transformation process. Can be assigned to a
 * {@link TScheme}.
 * 
 * @author Dominik Lin
 *
 */
public enum Phase {
    PREPARATION("Preparation"),
    ENRICHMENT("Enrichment"),
    GENERATION("Generation"),
    NO_PHASE("");

    String text;

    Phase(String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }

    public static Phase getPhaseByText(String text) {
	Phase result = null;
	int i = 0;
	do {
	    if (Subphase.values()[i].getText().equals(text)) {
		result = Phase.values()[i];
	    } else {
		i++;
	    }
	} while (i < Phase.values().length && result == null);
	return result;
    }

}
