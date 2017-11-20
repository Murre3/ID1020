package se.kth.id1020.lab3;

public class Node<Item extends Comparable<Item>> implements Comparable<Node<Item>>{
        
        public Item data;
        public Node<Item> next;
        
        public Node(Item i){
            data = i;
            next = null;
        }
        
        
        public void swapNext(){
            Item temp = data;
            data = next.data;
            next.data = temp;
        }
        
        @Override
        public int compareTo(Node<Item> n){
            return data.compareTo(n.data);
            
        }
        
        @Override
        public String toString(){
            return "(data: " + data.toString() + ")";
        }
    }