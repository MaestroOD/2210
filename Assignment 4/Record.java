/** @author Adam Gale
 * 251326327
 * CS2210 Assignment 4
 * Record.java
 * 
 * The Record class is a container for a Key and associated data.
 * To be used in a binary search tree.
 */

public class Record {
    
    private Key theKey;  // Key storing the label and type associated with the data
    private String data;  // data associated with the key

    /**
     * Constructus a new Record with the given key and data.
     *
     * @param k the key of the new record
     * @param theData the data associated with the key
     */
    public Record(Key k, String theData) {

        this.theKey = k;
        this.data = theData;
    }

    /**
     * This method returns the key associated with this record.
     *
     * @return the key of this record
     */
    public Key getKey() {
        return this.theKey;
    }

    /**
     * This method returns the data associated with this record.
     *
     * @return the data of this record
     */
    public String getDataItem() {
        return this.data;
    }

}
