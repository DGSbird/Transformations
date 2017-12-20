package ecb.technical.interfaces.nodes;

import ecb.transformations.enums.Representation;
import ecb.transformations.enums.Syntax;

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
