package ecb.transformations.interfaces.components;

/**
 * Interface providing {@link #isSimilar(Object)} method which can be used
 * instead of {@link #equals(Object)} in specific cases (e.g. when overriding
 * the {@link #equals(Object)} in an implementation of the {@link Tree}
 * interface).
 * 
 * @author Dominik Lin
 *
 */
public interface Similar {
    /**
     * 
     * @param obj
     *            an {@link Object}
     * @return true iff the input is "similar" to this object
     */
    public boolean isSimilar(Object obj);
}
