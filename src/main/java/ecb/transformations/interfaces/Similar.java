package ecb.transformations.interfaces;

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
    public boolean isSimilar(Object obj);
}
