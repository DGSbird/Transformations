package ecb.transformations.operators.enums;

import ecb.generalObjects.languages.enums.Syntax;
import ecb.generalObjects.representation.enums.Representation;
import ecb.transformations.interfaces.nodes.Build;
import ecb.transformations.interfaces.nodes.TypeOfNode;
import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;

public enum Leafs implements TypeOfNode, Build {
    // ----------------------------------------------------------
    // components
    // ----------------------------------------------------------

    CUBE("CUBE_ID"),
    VARIABLE("VARIABLE_ID"),
    MEMBER("MEMBER_ID"),

    PERSISTENT_DATASET_ID("PersistentDatasetID"),
    COMPONENT_ID("ComponentID"),
    VARIABLE_ID("VarID"),
    DATASET_ID("DatasetID"),

    MAPPING_ID("MappingID"),

    CONSTANT("Constant"),
    STRING_CONSTANT("StringC"),
    INTEGER_CONSTANT("IntegerC"),
    FLOAT_CONSTANT("FloatC"),
    BOOLEAN_CONSTANT("BooleanC"),
    NULL("null"),

    IDENTIFIER("Identifier"),
    MEASURE("Measure"),
    ATTRIBUTE("Attribute"),

    FUNCTION("FUNCTION_ID"),
    PROCEDURE("PROCEDURE_ID"),
    RULESET("RULESET_ID"),

    FUNCTION_ID("FunctionID"),
    PROCEDURE_ID("ProcedureID"),
    RULESET_ID("RulesetID"),

    NUMBER("number"),
    INTEGER("integer"),
    STRING("string"),
    FLOAT("float"),
    DATE("date"),
    INPUT_DATASET("dataset"),

    INNER("inner"),
    OUTER("outer"),
    LEFT("left"),
    RIGHT("right"),
    CROSS("cross"),

    DATASET_ALIAS_ID("DatasetAliasID");

    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    String typeOfNode;

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    Leafs(String typeOfNode) {
	this.typeOfNode = typeOfNode;
    }

    // ----------------------------------------------------------
    // get methods
    // ----------------------------------------------------------

    @Override
    public String getTypeOfNode() {
	return typeOfNode;
    }

    // ----------------------------------------------------------
    // build
    // ----------------------------------------------------------

    @Override
    public <T extends TNode<T, S>, S extends TComponent> String buildCode(T node, Syntax syntax,
	    Representation representation) {
	// TODO Auto-generated method stub
	return null;
    }

}
