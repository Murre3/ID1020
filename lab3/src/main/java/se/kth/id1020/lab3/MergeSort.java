package se.kth.id1020.lab3;

public class MergeSort extends Sort {

    public int inversions;
    public LinkedList sortedList;
    private int tempCount;
    
    public MergeSort(LinkedList list){
        inversions = 0;
        tempCount = 0;
        sortedList = sort(list);
    }
    
    //recursive function
    private LinkedList sort(LinkedList list){
        assert(list.size() > 0);
        
        // recursive base case
        if(list.size() == 1) 
            return list;

        // sets midN to the middle node
        Node midN = list.peekFirst();
        int half = list.size()/2;
        for(int i = 0; i < half-1; i++)
            midN = midN.next;
        
        // sorts the left and right half respectively (recursively)
        LinkedList leftHalf = new LinkedList(list.peekFirst(), half);
        LinkedList rightHalf = new LinkedList(midN.next, list.size() - half);
        leftHalf = sort(leftHalf);
        rightHalf = sort(rightHalf);
        
        assert (isSorted(leftHalf) && isSorted(rightHalf));
        // merges the two halves
        return merge(leftHalf, rightHalf, half);
    }


    private LinkedList merge(LinkedList leftHalf, LinkedList rightHalf, int half){
        
        // sets up temp variables
        LinkedList aux = new LinkedList();
        Node currRight = rightHalf.peekFirst();
        Node currLeft = leftHalf.peekFirst();
        tempCount = 0;
        
        // adds both halves to auxiliary list
        while(currLeft != null || currRight != null){
            
            if(currLeft == null){ // left half is empty
                aux.appendNode(currRight);
                break; // adds the entire chain of the remaining right half
            }
            
            if(currRight == null){ // right half is empty
                aux.appendNode(currLeft);
                break; // adds the entire chain of the remaining left half
            }
            
            
            if(less(currRight, currLeft)){ //the right node is appended
                aux.appendNode(currRight);
                currRight = currRight.next;
                inversions += (half - tempCount);
            }else{
                aux.appendNode(currLeft); // the left node is appended
                currLeft = currLeft.next;
                tempCount++;
            }
                
        }
        // done merging
        return aux;
    }



}
