package ecb.transformations.interfaces;

import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;

public interface Code {
    public String getCode(Syntax syntax, Representation representation);

    default public String getCode() {
	return getCode(Syntax.VTL, Representation.STANDARD);
    };

    default public String getCode(Syntax syntax) {
	return getCode(syntax, Representation.STANDARD);
    };

    default public String getCode(Representation representation) {
	return getCode(Syntax.VTL, representation);
    };
}
