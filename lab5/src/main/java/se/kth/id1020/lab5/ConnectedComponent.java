package se.kth.id1020.lab5;
import se.kth.id1020.Graph;
import se.kth.id1020.Edge;

public class ConnectedComponent {
    
    private boolean[] visitedVertices;
    private int count;
    
    public ConnectedComponent(Graph g){
        visitedVertices = new boolean[g.numberOfVertices()];
        
        for(int i = 0; i < g.numberOfVertices(); i++){
            if(!visitedVertices[i]){
                depthFirstSearch(g, i);
                count++;
            }
            
        }
    }
    private void depthFirstSearch(Graph g, int v){
        visitedVertices[v] = true;
        for(Edge e : g.adj(v)){
            if(!visitedVertices[e.to])
                depthFirstSearch(g, e.to);
        }
        
    }
    
    public int count(){return count;}

}
