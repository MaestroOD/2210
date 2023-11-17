/** @author Adam Gale
 * 251326327
 * CS2210 Assignment 4
 * BSTDictionary.java
 * 
 * The BSTDictionary class implements BSTDictionaryADT
 * This is in essence a direct duplicate of the BinarySearchTree class, only I have mashed in a bunch of (possibly redundant?) null checks to ensure nothing goes wrong
 * Uses a BinarySearchTree object and has a variety of methods to traverse or manipulate the tree (generally called straight from the BinarySearchTree clas)
 */

public class BSTDictionary implements BSTDictionaryADT {
    
    private BinarySearchTree BST;  // The binary search tree structure that our dictionary is stored in

    /**
     * Constructor method that creates a new BST object to store the dictionary 
     */    
    public BSTDictionary() {
        BST = new BinarySearchTree();
    }

    /**
     * This method returns the record matching Key k in the dictinoary
     * if the Recrord containing Key k is not in the dictionary, returns null
     * 
     * @return the Record containing Key k
     * @param k the Key to search for
     */    
    public Record get(Key k) {
        if (BST.getRoot().getRecord() == null) {
            return null;
        }
        return BST.get(BST.getRoot(),k).getRecord();
    }

    /**
     * This method adds the Record d to the dictionary
     * throws a DictionaryException if the Record is already in the dictionary
     * 
     * @param d the Record to add to the dictionary
     */       
    public void put(Record d) throws DictionaryException {
        BST.insert(BST.getRoot(),d);
    }

    /**
     * This method removes the Key k from the dictionary
     * throws a DictionaryException if the Key k is not in the dictionary
     * 
     * @param k the Key to remove from the dictionary
     */      
    public void remove(Key k) throws DictionaryException {
        BST.remove(BST.getRoot(),k);
    }

    /**
     * This method finds the next entry in the dictionary after Key k
     * returns null if there is no successor
     * (i am fairly sure these null checks are redundant with the null checks in BinarySearchTree.java but uhhh better safe than sorry)
     * 
     * @param k the Key find the successor to
     * @return the Record immediately following Key k in the dictionary
     */  
    public Record successor(Key k) {
        if (BST.getRoot().getRecord() == null) {
            return null;
        }
        if (BST.successor(BST.getRoot(),k) == null) {
            return null;
        }
        return BST.successor(BST.getRoot(),k).getRecord();
    }

    /**
     * This method finds the previous entry in the dictionary after Key k
     * returns null if there is no predecessor
     * (i am fairly sure these null checks are redundant with the null checks in BinarySearchTree.java but uhhh better safe than sorry)
     * 
     * @param k the Key find the predecessor to
     * @return the Record immediately before Key k in the dictionary
     */       
    public Record predecessor(Key k) {
        if (BST.getRoot().getRecord() == null) {
            return null;
        }    
        if (BST.predecessor(BST.getRoot(),k) == null) {
            return null;
        }    
        return BST.predecessor(BST.getRoot(),k).getRecord();
    }

     /**
     * This method finds the first entry in the dictionary
     * returns null if the dictionary is empty
     * (i am fairly sure these null checks are redundant with the null checks in BinarySearchTree.java but uhhh better safe than sorry)
     * 
     * @return the first Record in the dictionary
     */  
    public Record smallest() {
        if (BST.getRoot().getRecord() == null) {
            return null;
        }        
        return BST.smallest(BST.getRoot()).getRecord();
    }

     /**
     * This method finds the last entry in the dictionary
     * returns null if the dictionary is empty
     * (i am fairly sure these null checks are redundant with the null checks in BinarySearchTree.java but uhhh better safe than sorry)
     * 
     * @return the last Record in the dictionary
     */      
    public Record largest() {
        if (BST.getRoot().getRecord() == null) {
            return null;
        }        
        return BST.largest(BST.getRoot()).getRecord();
    }

}
