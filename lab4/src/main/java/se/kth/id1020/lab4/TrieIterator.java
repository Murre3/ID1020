package se.kth.id1020.lab4;

import java.util.Iterator;
import java.util.Map;

public class TrieIterator implements Iterator<Map.Entry<String, Integer>>{

    private final String prefix;
    private final Node top;
    private int count;
    private Node currentNode;
    private String currentKey;
    
    public TrieIterator(Node n, String p){
        top = n;
        prefix = p;
        init();
    }
    
    
    @Override
    public boolean hasNext() {
        return count > 0;
    }

    @Override
    public Map.Entry<String, Integer> next() {
        count--;
        Entry ent = new Entry(currentKey, currentNode.value);
        
        // instantly returns the Entry if there are no nodes left to find
        if(count == 0) return (Map.Entry<String, Integer>)ent;
                
        int i = nextChild(currentNode);
        while(i == Trie.N_CHILDREN)
        {
            recede();
            i = nextChild(currentNode);
        }

        currentNode.skips = i+1;
        traverse(i);
            
        return (Map.Entry<String, Integer>)ent;
    }

    // gets the index of the next available child in the node
    private int nextChild(Node n){
        for(int j = n.skips; j < Trie.N_CHILDREN; j++)
            if(n.child[j] != null) return j;
        return Trie.N_CHILDREN;
    }
    // traverses down the trie
    private void traverse(int i){
        currentNode = currentNode.child[i];
        currentKey += (char)(i+97);
    }
    // recedes back up the trie, up to the parent
    private void recede(){
        currentNode = currentNode.parent;
        currentKey = currentKey.substring(0, currentKey.length()-1);
    }
    
    
    
    
    /*=================================//
            INTIALIZING
    
    //=================================*/
    
    // can be used to reset the iterator if one want to use it to iterate again
    public void reset(){ init();}
    private void init(){
            // sets the currentNode to the prefix-node
            currentNode = Trie.get(top, prefix, prefix.length());
            // 
            currentKey = prefix;
            // top node's children
            count();
            System.out.println(count);
    }
    
    // counts the top's children
    private void count(){
        Node n = currentNode; // prefix node
        for(int j = 0; j < Trie.N_CHILDREN; j++){
            if(n.child[j] != null) countChildren(n.child[j]);
        }
        if(currentNode == top){
            count++;
            next();
        }
    }
    // recursively counts all the children
    private void countChildren(Node n){
        count++;
        for(int j = 0; j < Trie.N_CHILDREN; j++){
            if(n.child[j] != null) countChildren(n.child[j]);
        }
    }
    
    
    
} // end of class
