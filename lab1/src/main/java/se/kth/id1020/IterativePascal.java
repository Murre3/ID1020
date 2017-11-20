package se.kth.id1020;

public class IterativePascal extends ErrorPascal implements Pascal{
    
    private final boolean UPSIDE_DOWN;
    private int[][] pTri;
    
    
    public IterativePascal(boolean upsideDown){
        this.UPSIDE_DOWN = upsideDown;
    }
    
    @Override
    public void printPascal(int n){
        super.printPascal(n); // sanity check to prevent negative numbers
        
        pTri = new int[n+1][n/2 +1];
        
        if(!UPSIDE_DOWN){ // smallest level at the top
            for(int i = 0; i < n; i++){
                for(int j = 0; j < i+1; j++)
                    System.out.print(binom(i, j) + " ");
                System.out.print("\n");
            }
        }
        else{ // upside down version (biggest level at the top)
            for(int i = n-1; i >= 0; i--){
                 for(int j = 0; j <= i; j++)
                     System.out.print(binom(i, j) + " ");
                System.out.print("\n");
            }
            
            
            
            
        }
    }
    
    @Override
    public int binom(int n, int k){
        super.binom(n, k); // sanity check to prevent negative numbers
        
        if(k > n/2)
            k = n-k;
        if(pTri[n][k] != 0)
            return pTri[n][k];
    
        
        if(n == 0 || k == 0 || k == n){
            pTri[n][k] = 1;
            return 1;
        }
        /*
        if(k == 1){
            pTri[n][k] = n;
            return n;
        }
        */
        
        if(pTri[n-1][k] != 0 && pTri[n-1][k-1] != 0){
            pTri[n][k] = pTri[n-1][k] + pTri[n-1][k-1];
            return pTri[n][k];
        }

        
        for(int i = 0; i <= n; i++)
            for(int j = 0; j <= k && j <= i; j++){
                if(pTri[i][j] != 0)
                    continue;
                if(j == 0 || j == i)
                    pTri[i][j] = 1;
                else
                    pTri[i][j] = pTri[i-1][j] + pTri[i-1][j-1];
            }
        
        return pTri[n][k];
    }
}
    
