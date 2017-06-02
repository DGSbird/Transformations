package ecb.transformations.enums.transformationProcess;

/**
 * Types of {@link TScheme} (e.g. technical, validation, derivation,
 * generation).
 * 
 * @author Dominik Lin
 *
 */
public enum SchemeType {
    DERIVATION("Derivation"),
    GENERATION("Generation"),
    TECHNICAL("Technical"),
    VALIDATION("Validation");

    String text;

    SchemeType(String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }

    public static SchemeType getSchemeTypeByText(String text) {
	SchemeType result = null;
	int i = 0;
	do {
	    if (SchemeType.values()[i].getText().equals(text)) {
		result = SchemeType.values()[i];
	    } else {
		i++;
	    }
	} while (i < SchemeType.values().length && result == null);

	return result;
    }

}
