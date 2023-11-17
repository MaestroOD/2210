/** @author Adam Gale
 * 251326327
 * CS2210 Assignment 4
 * Key.java
 * 
 * The Key class stores the label and type of a dictionary entry. Other classes can access this information with the accessor methods.
 * To be stored inside a Record object.
 */

public class Key {
    
    private String label;  // private String holding the label 
    private int type;  // private int holding the tpye

    /**
     * This constructor initializes a new Key object with a specified label and type.
     * The label is converted to lowercase before being assigned.
     *
     * @param theLabel the label of the key
     * @param theType the type of the key
     */
    public Key(String theLabel, int theType) {

        this.label = theLabel.toLowerCase();  // converting to lowercase as per assignment instructions
        this.type = theType;
    }

    /**
     * This method returns the label associated with the key. 
     *
     * @return the label of this key
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * This method returns the type associated with the key. 
     *
     * @return the label of this key
     */    
    public int getType() {
        return this.type;
    }


    /**
     * This method compares the key with another key object based on label and type.
     *
     * @param k the Key to be compared
     * @return -1 if this key is before k, 0 if this key is equal to k, 1 if this key is greater than k
     */
    public int compareTo(Key k) {

        if (this.label.equals(k.getLabel())) { // if labels match, compare types instead

            if (this.type == k.getType()) {
                return 0;  // return 0 for equal types
            }
            if (this.type < k.getType()) {
                return -1;  // return -1 for "less than"
            }
            return 1;  // return 1 for "greater than"
        }

        if (this.label.compareTo(k.getLabel()) < 0) {  // if labels don't match, compare them alphabetically
            return -1;  // return -1 for "less than"
        }
        return 1; // return 1 for "greater than"
    }

// Block used for compare testing  
/*    public static void main(String[] args) {

        Key A = new Key ("Car",2);
        Key B = new Key ("CAR", 2);
        Key C = new Key ("caR", 3);
        Key D = new Key ("house", 1);
        
        System.out.println(A.compareTo(B));  // should return 0
        System.out.println(A.compareTo(C));  // -1
        System.out.println(A.compareTo(D));  // -1
        System.out.println(D.compareTo(C));  // 1

    }
*/

}
