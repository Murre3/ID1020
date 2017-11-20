package se.kth.id1020;

public abstract class ErrorPascal implements Pascal{
    
    @Override
    public void printPascal(int n){
        if(n < 0)
            throw new IllegalArgumentException();
        
    }
    
    @Override
    public int binom(int n, int k){
        if(n < 0 || k < 0 || n < k)
            throw new IllegalArgumentException();
        return -1;
    }
}
