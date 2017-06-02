package ecb.transformations.enums.transformationProcess;

public enum Subphase {
    LOAD("Load"),
    SETUP("Setup");

    String text;

    Subphase(String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }

    public static Subphase getSubphaseByText(String text) {
	Subphase result = null;
	int i = 0;
	do {
	    if (Subphase.values()[i].getText().equals(text)) {
		result = Subphase.values()[i];
	    } else {
		i++;
	    }
	} while (i < Subphase.values().length && result == null);
	return result;
    }
}
