package se.kth.id1020;

import java.awt.Point;
import java.util.HashMap;

public class RecursivePascal extends ErrorPascal implements Pascal{

    private final boolean UPSIDE_DOWN; // used to determine ascending/descending levels
    private final HashMap<Point, Integer> MAP; // data structure to reduce calculations

    public RecursivePascal(boolean upsideDown){
            this.UPSIDE_DOWN = upsideDown;
            MAP = new HashMap<>();
    }
    
    @Override
    public void printPascal(int n) {
        
        super.printPascal(n); // sanity checking for negative n's
        
        if(n-1 == 0){ // smallest level (just "1"), the base case
            System.out.print("1");
            return;
        }
            
        if(UPSIDE_DOWN){ // upside down version, bigger levels first
                for (int k = n-1; k >= 0; k--) {
                    System.out.print(binom(n-1, k) + " ");
                    // printing of the elements
                }
                System.out.println();
                // prints the smaller levels last when upside down
                printPascal(n-1);
        
        }else{ // regular version, smaller levels first 
            
            printPascal(n-1);
            System.out.println();
            for(int k = 0; k < n; k++)
                System.out.print(binom(n-1, k) + " ");
                
        }
        
    }
    
    
    @Override
    public int binom (int n, int k) {
        
        super.binom(n, k); // sanity checking for negative parameters
        
        if(k > n/2)
            k = n-k;
        
        if(k == 0 || n == k)
            return 1;
        if(k == 1)
            return n;
        
        Point binomP = new Point(n, k);
        
        if(MAP.containsKey(binomP) )
            return MAP.get(binomP);
        
        Integer binomC = binom(n-1,k) + binom(n-1, k-1);
        MAP.put(binomP, binomC);
        return binomC;
    }
    
    /* Recursive version with no memory structure for task 2.2
    public int binom(int n, int k) {
        if(k > n/2)
            k = n-k;
        if (n == 0 || k == 0 || n == k) {
            return 1;
        }
        if (k == 1) {
            return n;
        }
        
        return binom(n - 1, k - 1) + binom(n - 1, k);
    }
    */
}
