package se.kth.id1020.lab3;

import java.util.Collections;
import java.util.Iterator;


public class LinkedList<Item extends Comparable<Item>> implements Iterable<Item> {
    
    private Node<Item> first;
    private Node<Item> last;
    private int size;
    private int bubbleSwaps;

    public LinkedList(){ // explicit default constructor
        first = null;
        last = null;
        size = 0;
        bubbleSwaps = 0;
    }
    
    public LinkedList(Node currentNode, int size){
        
        for(int i = 0; i < size; i++){
            appendNode(new Node(currentNode.data));
            currentNode = currentNode.next;
        }
            
    }
    
    public LinkedList(Item[] items){ // pass an array to transform
        Node<Item> n;                // it to a linked list
        for(Item i : items){
            n = new Node(i);
            appendNode(n);
        }
        
    }
    
    public boolean isEmpty(){
        return(first == null);
    }
    public int size(){
        return size;
    }
    public Node<Item> peekFirst(){
        return first;
    }
    public Node<Item> peekLast(){
        return last;
    }
    public void appendNode(Node<Item> n){
        size++; // increase size by 1
        
        if(last==null){
            first = n;
            last = n;
            return;
        }
        
        last.next = n;
        last = last.next;
    }
    
    @Override
    public String toString(){
        String s = "{";
        
        Node<Item> current = first;
        while(current != null){
            s += " " + current.toString();
            current = current.next;
        }
        
        return s+" }";
    }
    
    
    /*
            BUBBLE SORT METHOD
    */
    public void bubbleSort(){
        assert size > 0; // list must be length > 0
        
        int r  = size-1;
        boolean swapped = true;
        Node<Item> curr = first;
        
        while(r >= 0 && swapped){
            
            swapped = false;
            
            for(int i = 0; i < r; i++){
                if(Sort.less(curr.next, curr)){
                    curr.swapNext(); // swaps data of current with next
                    bubbleSwaps++; // increments counter for # of swaps
                    swapped = true;
                }
                curr = curr.next;
            }
            
            r--;
            curr = first;
        }
        
        assert Sort.isSorted(this); // ensures the array is sorted
    }
    
    public int getBubbleSwaps(){
        return bubbleSwaps;
    }
    
    
    /*
        Unused methods for this lab
    */
    public void prependNode(Node<Item> n){
        if(first==null){
            first = n;
            last = n;
            return;
        }
        n.next = first;
        first = n;
    }
    public void clear(){
        first = null;
        last = null;
    }
    // End of unused methods
    
    
    // Iterator of the LinkedList
    @Override
    public Iterator<Item> iterator() {
        
        if(isEmpty()) { // returns empty iterator if list is empty
            return Collections.<Item>emptyList().iterator();
        }
        
        return new Iterator<Item>(){
            private Node<Item> currentNode;
            
            @Override
            public boolean hasNext(){
                return currentNode != last;
            }
            
            @Override
            public Item next() {
                if(currentNode == null){
                    currentNode = first;
                    return currentNode.data;
                }
                    
                currentNode = currentNode.next;
                return currentNode.data;
                
            }
            
        };
    } // end of iterator
    
    
}
