package se.kth.id1020.lab4;

public class Node {
        public Node parent;
        public int value; // number of occurences of this node's key
        public Node[] child = new Node[Trie.N_CHILDREN];
        public int skips; // used in iterator to remember how many children to skip
        
        public Node(Node p){ parent = p; }
    }
