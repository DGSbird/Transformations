package ecb.transformations.interfaces;

import ecb.transformations.BirdVtl.TNode;

public interface VtlBuild {
    public <T extends TNode<T, S>, S extends WebComponent> String buildVtlCode(T node, boolean htmlConfig);
}
