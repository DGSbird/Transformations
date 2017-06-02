package ecb.transformations.interfaces;

public interface VtlCode {
    default public String getVtlCode() {
	return getVtlCode(false);
    };

    public String getVtlCode(boolean htmlConfig);
}
