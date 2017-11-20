package se.kth.id1020.tinysearchengine;
import java.util.ArrayList;

public class BinarySearch {

    
    public static int search(String key, ArrayList<Node> a) {
        return BinarySearch.search(key, a, 0, a.size()-1);
    }
    private static int search(String key, ArrayList<Node> a, int lo, int hi) {

        // possible key indices in [lo, hi)
        int mid = lo + (hi - lo)/2;
        if (hi <= lo) return -mid;
        int cmp = a.get(mid).compareTo(key);
        if      (cmp > 0) return BinarySearch.search(key, a, lo, mid);
        else if (cmp < 0) return BinarySearch.search(key, a, mid+1, hi);
        else              return mid;
    }
    
}
