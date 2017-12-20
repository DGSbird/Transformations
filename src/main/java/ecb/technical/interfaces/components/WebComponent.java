package ecb.technical.interfaces.components;

/**
 * Extends the {@link Component} interface with methods related to the
 * illustration on the web site (i.e. get / set methods for link and tool tip).
 * 
 * @author Dominik Lin
 *
 */
public interface WebComponent extends Component {
    /**
     * 
     * @return the (web) location of this object
     */
    public String getLocation();

    /**
     * 
     * @return the tool tip related to this object
     */
    public String getTooltip();
}
