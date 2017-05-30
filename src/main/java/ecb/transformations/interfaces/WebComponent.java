package ecb.transformations.interfaces;

/**
 * Extends the {@link Component} interface with methods related to the
 * illustration on the web site (i.e. get / set methods for link and tooltip).
 * 
 * @author Dominik Lin
 *
 */
public interface WebComponent extends Component {
    public String getLink();

    public String getTooltip();
}
