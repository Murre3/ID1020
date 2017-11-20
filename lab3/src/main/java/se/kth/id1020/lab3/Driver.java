package se.kth.id1020.lab3;


public class Driver {
    
    public static void main(String[] args){
        
        /* Integer test */
        Integer[] arr = {7, 5, 2, 3, 2, 4, 6, 5, 3};
        /**/
        
        /* String test
        String[] arr = {"Zebra", "Elephant", "Piranha", "Ape", "Electric Eel", "Dolphin"};
        /* */
        LinkedList list = new LinkedList(arr);
        System.out.println("FULL UNSORTED LIST: " + list.toString());
        
        // constructor automatically sorts but
        // sorted list is in instance variable 'MergeSort.sortedList'
        MergeSort mS = new MergeSort(list); 
        
        list.bubbleSort();
        
        
        // output to control sorting works and inversions are counted correctly
        System.out.println(
                "Merge Sorted list: " + mS.sortedList.toString());
        System.out.println(
                "Counted inversions with Merge Sort: " + mS.inversions);
        System.out.println(
                "Bubble Sorted list: " + list.toString());
        System.out.println(
                "Counted Bubble Sort swaps: " + list.getBubbleSwaps());
    }        
        
}
