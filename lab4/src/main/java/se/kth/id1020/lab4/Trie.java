package se.kth.id1020.lab4;

import java.util.Iterator;
import java.util.*;

public class Trie implements Iterable<Map.Entry<String, Integer>>{
    
    public static final int N_CHILDREN = 26;   // number of branches, per branch
    public static final int ASCII_OFFSET = 97; // 97 is 'a', being index 0
    private Node top;                          // top of the trie, the root
    private Node currentParent;
    private Iterator<Map.Entry<String,Integer>> iter;
    
    /*
        FINISHED METHODS:
    public get    
    private get
    public put
    private put
    public count
    private countChildren
    public distinct
    private distinctChildren
    iterator()
    
    */
    
    
    
    public void put(String key)
    {   top = put(top, key, 0);    }
    // helper recursive function
    private Node put(Node n, String key, int i){
        // if this part of the trie doesn't exist yet, we create it
        if(n == null) n = new Node(currentParent);
        
        if(i == key.length()){ // base case, returns the node        
            n.value++;         // where the value was put
            return n;
        }
        // used to traverse back up the tree
        currentParent = n;
        
        // identifier for which of the node's children is the next node
        char childIdx = (char)(key.charAt(i) - ASCII_OFFSET);
        // traverses down the trie
        n.child[childIdx] =  put(n.child[childIdx], key, i+1);
        return n;
    }
    
    
    public int get(String key){
        Node n = get(top, key, 0);
        if(n == null) return 0; // the key doesn't exist in the trie
        
        return n.value;
    }
    // helper recursive function
    public static Node get(Node n, String key, int i){
        if(n == null) return null;
        if(i == key.length()) return n; // base case, returns the node
                                        // associated with the key

        // identifier for which of the node's children is the next node
        char childIdx = (char)(key.charAt(i) - ASCII_OFFSET);
        // traverses down the trie
        return get(n.child[childIdx], key, i+1);
    }
    
    // initializes the counting
    public int count(String prefix){
        Node startNode = get(top, prefix, 0); // top node with given prefix
        return countChildren(startNode);
    }
    // recursively counts all the children
    private int countChildren(Node n){
        if(n == null) return 0;
        int sum = n.value;
        for(int i = 0; i < Trie.N_CHILDREN; i++){
            sum += countChildren(n.child[i]);
        }
        return sum;
    }
    
    // initializes the counting and discounts the top if it has a value
    // since it will be counted in the recursive function
    public int distinct(String prefix){
        Node startNode = get(top, prefix, 0);
        int topDistinct = (startNode.value > 0) ? 1 : 0;
        
        return distinctChildren(startNode) - topDistinct;
    }
    // recursively adds all the distinct nodes with a positive value
    private int distinctChildren(Node n){
        if(n == null) return 0;
        int valSum = (n.value > 0) ? 1 : 0;
        
        for(int i = 0; i < Trie.N_CHILDREN; i++){
            valSum += distinctChildren(n.child[i]);
        }
        return valSum;
    }
    
    public Entry maxTwoPrefix(){
        Entry max = new Entry("", -1);
        String prefix;
        int freqCount;
        
        for(int i = 0; i < Trie.N_CHILDREN; i++){
            for(int j = 0; j < Trie.N_CHILDREN; j++){
                // constructs all the possible 2-letter combinations
                prefix = "" + (char)(i+97) + (char)(j+97);
                Node n = get(top, prefix, 0);
                if(n != null){
                    freqCount = count(prefix);
                    max = (freqCount > max.getValue())
                            ? new Entry(prefix, freqCount) : max;
                }
                
            }
        }
        
        return max;
    }
    
    public Entry maxStartingLetter(){
        
        Entry max = new Entry("", -1);
        int distinctCount;
        String letter;
        
        for(int i = 0; i < Trie.N_CHILDREN; i++){
            letter = "" +(char)(i+ASCII_OFFSET);
            Node n = get(top, letter, 0);
            if(n != null){
                distinctCount = distinct(letter);
                max = (distinctCount > max.getValue())
                        ? new Entry(letter, distinctCount) : max;
            }
        }
        
        return max;
    }
    
    
    
    
    
    public void createIterator(String prefix){
        Node startNode = get(top, prefix, 0);
        iter = new TrieIterator(startNode, prefix);
    }
    @Override
    public Iterator<Map.Entry<String, Integer>> iterator() {
        if(iter==null) throw new IllegalStateException(
                "Need to initialize the iterator with "
                        + "createIterator(String prefix) first");
        return iter;
        
    }
}
