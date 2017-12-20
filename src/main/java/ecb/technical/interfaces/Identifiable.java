package ecb.technical.interfaces;

/**
 * Interface allowing the identification of objects by (technical) identifier or
 * (semantic) code.
 * 
 * @author Dominik Lin
 *
 */
public interface Identifiable {
    /**
     * 
     * @return the identifier of this object (e.g. 1)
     */
    public Integer getIdentifier();

    /**
     * 
     * @return the code of this object (e.g. "INSTRMNT_UNQ_ID")
     */
    public String getCode();

}
