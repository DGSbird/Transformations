package ecb.transformations.enums.transformationProcess;

/**
 * Types of {@link TScheme} (e.g. AnaCredit, SHS, Business, Completeness, etc.).
 * 
 * @author Dominik Lin
 *
 */
public enum SchemeSubtype {
    ANACREDIT("AnaCredit"),
    BUSINESS("Business"),
    COMPLETENESS("Completeness"),
    CONSISTENCY("Consistency"),
    GET("Get"),
    IMPLICIT("Implicit"),
    INTEGRITY("Integrity"),
    OTHER("Other"),
    SHS("SHS"),
    UNION("Union"),
    UNIQUENESS("Uniqueness");

    String text;

    SchemeSubtype(String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }

    public static SchemeSubtype getSchemeSubtypeByText(String text) {
	SchemeSubtype result = null;
	int i = 0;
	do {
	    if (SchemeSubtype.values()[i].getText().equals(text)) {
		result = SchemeSubtype.values()[i];
	    } else {
		i++;
	    }
	} while (i < SchemeSubtype.values().length && result == null);

	return result;
    }
}
