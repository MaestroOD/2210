/** @author Adam Gale
 * 251326327
 * CS2210 Assignment 4
 * BSTNode.java
 * 
 * The BSTNode class represents a node in a binary search tree.
 * Contains a record (which contains a key and data) and has pointers to the parent node, left child node, and right child node
 */

public class BSTNode {

    private Record record; // record contained within the node - contains a key and data
    private BSTNode parent, leftChild, rightChild;  // other nodes associated with this node

    
    /**
     * Constructs a new BSTNode with the given record. The parent, leftchild, rightchild are initialized to null.
     *
     * @param item the record to be stored in the node
     */
    public BSTNode(Record item) {
        this.record = item;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * This method returns the record object contained in this node 
     * 
     * @return the record contained in this node
     */
    public Record getRecord() {
        return this.record;
    }

    /**
     * This method sets the record object contained in this node 
     * 
     * @param d record to be contained in the node
     */
    public void setRecord(Record d) {
        this.record = d;
    }

    /**
     * This method returns the left child of the node 
     * 
     * @return the left child of this node
     */    
    public BSTNode getLeftChild() {
        return this.leftChild;
    }

    /**
     * This method returns the right child of the node 
     * 
     * @return the right child of this node
     */   
    public BSTNode getRightChild() {
        return this.rightChild;
    }

    /**
     * This method returns the parent of the node 
     * 
     * @return the parent of this node
     */      
    public BSTNode getParent() {
        return this.parent;
    }

    /**
     * This method sets the left child of the node 
     * 
     * @param u the node to be set as the left child
     */     
    public void setLeftChild(BSTNode u) {
        this.leftChild = u;
    }

    /**
     * This method sets the right child of the node 
     * 
     * @param u the node to be set as the right child
     */ 
    public void setRightChild(BSTNode u) {
        this.rightChild = u;
    }

    /**
     * This method sets the parents of the node 
     * 
     * @param u the node to be set as the parent
     */ 
    public void setParent(BSTNode u) {
        this.parent = u;
    }

    /**
     * This method tests to see if the node is a leaf 
     * 
     * @return true if the node is a leaf, false otherwise
     */ 
    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }
}
