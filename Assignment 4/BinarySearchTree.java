/** @author Adam Gale
 * 251326327
 * CS2210 Assignment 4
 * BinarySearchTree.java
 * 
 * The BinarySearchTree class represents a binary search tree data structure
 * Contains BSTNode objects and has a variety of methods to traverse or manipulate the tree
 */

public class BinarySearchTree {
    
    private BSTNode root = null; // node set as the root of the tree

    /**
     * Constructs a new BinarySearchTree with a null node as the root
     */
    public BinarySearchTree() {
        root = new BSTNode(null);
    }

    /**
     * This method returns root node of the binary tree 
     * 
     * @return the root node of the tree
     */
    public BSTNode getRoot() {
        return this.root;
    }

    /**
     * This method returns the node containing Key k in the binary tree with root r 
     * If there is no node containing Key k, returns null
     * NOTE: This is the "bad" version of get as given in the assignment - the "good" version is included as a private helper method "find"
     * 
     * @return the node containing Key k, or null if no node is found
     * @param r the root node of the BST to search
     * @param k the Key to search for
     */    
    public BSTNode get(BSTNode r, Key k) {

        if (r == null) {
            return null;
        }

        BSTNode targetNode;
        targetNode = find(r,k);
        return targetNode;

    }

    /**
     * This method inserts the node containing Record d in the binary tree with root r 
     * Throws a DictionaryException if the record is already stored in the tree
     * 
     * @param r the root node of the BST to search
     * @param d the record to store in the tree
     */        
    public void insert(BSTNode r, Record d) throws DictionaryException{
        
        BSTNode targetNode;
        targetNode = find(r,d.getKey());  // using our "proper" get method to see if d is already in the tree, returns the location d should be in

        if (targetNode.isLeaf() == false) {
            throw new DictionaryException("Key already stored in tree.");  // if we find d already in tree, throw error
        }

        else {  // if we find the leaf node where d should b, we insert it and add children
            targetNode.setRecord(d);
            targetNode.setLeftChild(new BSTNode(null));
            targetNode.getLeftChild().setParent(targetNode);
            targetNode.setRightChild(new BSTNode(null));
            targetNode.getRightChild().setParent(targetNode);
        }
    }

    /**
     * This method removes the node containing Key k from the binary tree with root r 
     * Throws a DictionaryException if the record is not stored in the tree
     * Forgive me, for I think this is much longer than it needed to be
     * 
     * @param r the root node of the BST to search
     * @param k the Key k to remove from the tree
     */      
    public void remove(BSTNode r, Key k) throws DictionaryException{

        BSTNode targetNode;
        targetNode = find(r,k);  // using our "proper" get method to see if d is already in the tree, returns the location d should be in

        if (targetNode.isLeaf()) {
            throw new DictionaryException("Key is not in the tree.");  // if we can't find d in tree, throw error
        }

        else {

            if (targetNode.getLeftChild().isLeaf()) {  // dealing with the case where removed node's left child is a leaf

                BSTNode otherChild = targetNode.getRightChild();
                BSTNode targetParent = targetNode.getParent();

                if (targetParent != null) {  // if we're not removing the root node

                    if (targetParent.getLeftChild() == targetNode) {  // if removed node was the left child of it's parent, we set the removed node's non-leaf child as parent's new left child
                        targetParent.setLeftChild(otherChild);
                        otherChild.setParent(targetParent);
                    }

                    else if (targetParent.getRightChild() == targetNode) {  // if removed node was the right child of it's parent, we set the removed node's non-leaf child as parent's new right child
                        targetParent.setRightChild(otherChild);
                        otherChild.setParent(targetParent);
                    }

                }

                else {
                    otherChild.setParent(null);  // dealing with edge case of the removed node being the root
                    root = otherChild;
                }    
            }

            else if (targetNode.getRightChild().isLeaf()) { // dealing with the case where removed node's right child is a leaf

                BSTNode otherChild = targetNode.getLeftChild();
                BSTNode targetParent = targetNode.getParent();

                if (targetParent != null) { // if we're not removing the root node

                    if (targetParent.getLeftChild() == targetNode) { // if removed node was the left child of it's parent, we set the removed node's non-leaf child as parent's new left child
                        targetParent.setLeftChild(otherChild);
                        otherChild.setParent(targetParent);
                    }

                    else if (targetParent.getRightChild() == targetNode) { // if removed node was the right child of it's parent, we set the removed node's non-leaf child as parent's new right child
                        targetParent.setRightChild(otherChild);
                        otherChild.setParent(targetParent);
                    }

                }

                else {
                    otherChild.setParent(null);  // dealing with edge case of the removed node being the root
                    root = otherChild;
                }    
            }

            else { // the case in which removed node has two leaf children or two non-leaf children
                BSTNode smallest = smallest(targetNode.getRightChild());  
                targetNode.setRecord(smallest.getRecord());  // steals the smallest record in the right subtree
                remove(smallest,smallest.getRecord().getKey());  // deletes the node from which the record was taken
            }
            
        }
    }

    /**
     * This method finds the node containing the next Key greater than Key k from the binary tree with root r 
     * Key k does not need to be stored in the tree
     * 
     * @param r the root node of the BST to search
     * @param k the Key k to find the successor to
     */        
    public BSTNode successor(BSTNode r, Key k) {

        if (r == null) {
            return null;  // if tree is empty return null
        }

        BSTNode targetNode = find(r,k);  // locate the location of the node (or the leaf node where the key would have been contained)
        
        
        if (targetNode.getRightChild() != null && targetNode.getRightChild().isLeaf() == false) {
            return smallest(targetNode.getRightChild()); // if location has a non-leaf right child, that subtree is where we will find the successor, recursively iterate through it
        }
        else {  // otherwise we iterate up the tree until we find either the root or a node that is the left child of it's parent
            BSTNode targetParent = targetNode.getParent();
            while (targetParent != null && targetNode == targetParent.getRightChild()) {
                targetNode = targetParent;
                targetParent = targetParent.getParent();
            }
            return targetParent;
        }

    }

    /**
     * This method finds the node containing the next Key less than Key k from the binary tree with root r 
     * Key k does not need to be stored in the tree
     * Essentially identical to successor but looking in left child subtrees
     * 
     * @param r the root node of the BST to search
     * @param k the Key k to find the predecessor to
     */       
    public BSTNode predecessor(BSTNode r, Key k) {

        if (r == null) {
            return null;  // if tree is empty return null
        }        

        BSTNode targetNode = find(r,k);  // locate the location of the node (or the leaf node where the key would have been contained)
        
        if (targetNode.getLeftChild() != null && targetNode.getLeftChild().isLeaf() == false) {
            return largest(targetNode.getLeftChild());  // if location has a non-leaf left child, that subtree is where we will find the successor, recursively iterate through it
        }
        else {
            BSTNode targetParent = targetNode.getParent();  // otherwise we iterate up the tree until we find either the root or a node that is the right child of it's parent
            while (targetParent != null && targetParent.getRecord().getKey().compareTo(k)==1) {
                targetNode = targetParent;
                targetParent = targetParent.getParent();
            }
            return targetParent;
        }

    }

    /**
     * This method finds the smallest record from the binary tree with root r 
     * 
     * @param r the root node of the BST to search
     * @return the node containing the smallest record
     */        
    public BSTNode smallest(BSTNode r) {
        
        BSTNode targetNode;
        
        if (r == null || r.isLeaf()) {
            return null;  // if tree is completely null or contains no records, return null
        }

        else {  // otherwise find and return the leftmost node
            targetNode = r;
            while (targetNode.isLeaf() == false) {
                targetNode = targetNode.getLeftChild();
            }
            return targetNode.getParent();
        }

    }

    /**
     * This method finds the largest record from the binary tree with root r 
     * 
     * @param r the root node of the BST to search
     * @return the node containing the largest record
     */       
    public BSTNode largest(BSTNode r) {
        
        BSTNode targetNode;
        
        if (r == null || r.isLeaf()) {
            return null; // if tree is completely null or contains no records, return null
        }

        else {  // otherwise find and return the rightmost node
            targetNode = r; 
            while (targetNode.isLeaf() == false) {
                targetNode = targetNode.getRightChild();
            }
            return targetNode.getParent();
        }

    }

    /**
     * This method returns the node containing Key k in the binary tree with root r 
     * If there is no node containing Key k, returns the leaf node where Key k should be located
     * NOTE: This is the "good" version of get as given in class - the "bad" version is included as a the public method "get"
     * We use this internally instead of "get" as the functionality is much greater
     * 
     * @return the node containing Key k, or the leaf node where Key k should have been located
     * @param r the root node of the BST to search
     * @param k the Key to search for
     */ 
    private BSTNode find(BSTNode r, Key k) {
        if (r == null) {  // return null if the tree is null
            return null;
        }

        if (r.isLeaf() || r.getRecord().getKey().compareTo(k) == 0) {  // if the node we're looking at matches, return it
            return r;
        }

        else {  // otherwise iterate through the tree while comparing keys to find location of k
            
            if (r.getRecord().getKey().compareTo(k) == -1) {
                return get(r.getRightChild(),k);
            }

            else {
                return get(r.getLeftChild(),k);
            } 
        }
    }

}
