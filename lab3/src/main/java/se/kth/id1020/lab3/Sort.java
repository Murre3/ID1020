package se.kth.id1020.lab3;


public class Sort {
	
	public static boolean less(Comparable c1, Comparable c2){
		return c1.compareTo(c2) < 0;
	}
	
        
	public static boolean isSorted(LinkedList list){
            Node n = list.peekFirst();
            while(n.next != null){
                if(less(n.next, n)) return false;
                n = n.next;
            }
            return true;
	}
        
        
}
