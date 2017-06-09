package ecb.transformations.metadata;

import java.util.HashSet;
import java.util.Set;

import ecb.transformations.interfaces.nodes.TypeOfNode;
import ecb.transformations.operators.enums.InvisibleOps;
import ecb.transformations.operators.enums.OpsWithFollowingOperands;
import ecb.transformations.operators.enums.OpsWithTwoOperands;

public class NodeClassification {
    public static Set<String> typesToKeep;

    public static Set<String> nextSiblingToChildTypes;

    static {
	typesToKeep = getTypesToKeep();
    }

    @SuppressWarnings("unchecked")
    public static <V extends TypeOfNode, Build> Set<String> getTypesToKeep() {
	if (typesToKeep == null) {
	    typesToKeep = new HashSet<>();
	    Set<V> set = new HashSet<>();
	    for (InvisibleOps op : InvisibleOps.values()) {
		set.add((V) op);
	    }
	    for (OpsWithTwoOperands op : OpsWithTwoOperands.values()) {
		set.add((V) op);
	    }
	    for (OpsWithFollowingOperands op : OpsWithFollowingOperands.values()) {
		set.add((V) op);
	    }

	    // TODO: add other types of nodes
	    for (V element : set) {
		typesToKeep.add(element.getTypeOfNode());
	    }
	}
	return typesToKeep;
    }

    public static <V extends TypeOfNode, Build> Set<String> getNextSiblingToChildTypes() {
	if (nextSiblingToChildTypes == null) {
	    nextSiblingToChildTypes = new HashSet<>();
	    Set<V> set = new HashSet<>();
	    // TODO: add implementation

	}
	return nextSiblingToChildTypes;
    }

    public static void main(String[] args) {
	Set<String> set = NodeClassification.typesToKeep;
	for (String s : set) {
	    System.out.println(s);
	}
    }

}
