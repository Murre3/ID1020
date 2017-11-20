package se.kth.id1020;
import edu.princeton.cs.algs4.Stopwatch;

public class Driver {
    
    public static void main(String[] args) {
        int pascalN = 100;
        boolean upsideDown = !true;
        
        Stopwatch s = new Stopwatch();
        RecursivePascal rp = new RecursivePascal(upsideDown);
        IterativePascal ip = new IterativePascal(upsideDown);
        
        rp.printPascal(pascalN);
        //ip.printPascal(pascalN);
        System.out.println("\nElapsed time: " + s.elapsedTime());
    }
    
    
}
