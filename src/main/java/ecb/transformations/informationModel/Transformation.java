package ecb.transformations.informationModel;

import ecb.transformations.treeStructure.TComponent;
import ecb.transformations.treeStructure.TNode;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Transformation
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = Transformation.QUERY_GET_ALL, query = "SELECT s FROM Transformation s"),
	@NamedQuery(name = Transformation.QUERY_FIND_BY_CODE, query = "SELECT s FROM Transformation s WHERE s.code=:"
		+ Transformation.PARAM_CODE) })
public class Transformation<T extends TNode<T, S>, S extends TComponent> extends TNode<T, S> implements Serializable {
    // ----------------------------------------------------------
    // fields
    // ----------------------------------------------------------

    private static final long serialVersionUID = -6209054479941503315L;

    public static final String QUERY_GET_ALL = "Transformation.getAll";

    public static final String QUERY_FIND_BY_CODE = "Transformation.findByCode";

    public static final String PARAM_CODE = "code";

    // ----------------------------------------------------------
    // constructor
    // ----------------------------------------------------------

    public Transformation() {
	super();
    }

    public Transformation(TComponent component) {
	super(component);
    }

}
