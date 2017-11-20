package se.kth.id1020.lab5;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import se.kth.id1020.Edge;
import se.kth.id1020.Graph;
import se.kth.id1020.Vertex;

public class ShortestPathDijkstra {
    public Edge[] edgeToVertex;
    // distance to the index-vertex from the source-node
    public double[] distToVertex;
    public IndexMinPQ<Double> vertexQ;
    
    // weightedEdges controls whether to account for edge weights or not
    public ShortestPathDijkstra(Graph g, int startVertex, boolean weightedEdges){
        
        // initialize the data structures
        edgeToVertex = new Edge[g.numberOfVertices()];
        distToVertex = new double[g.numberOfVertices()];
        vertexQ = new IndexMinPQ(g.numberOfVertices());
        
        // populate the distance array with infinite values
        for(int v = 0; v < g.numberOfVertices(); v++){
            distToVertex[v] = Double.POSITIVE_INFINITY;
        }
        // set the value of the starting node to 0
        distToVertex[startVertex] = 0.0;
        
        // determine the shortest path
        vertexQ.insert(startVertex, 0.0);
        while(!vertexQ.isEmpty()){
            relax(g, vertexQ.delMin(), weightedEdges);
        }
        
    }
    
    // weightedEdges controls whether to account for edge weights or not
    private void relax(Graph g, int from, boolean weightedEdges){
        for(Edge edge : g.adj(from)){
            
            int to = edge.to;
            // use the edge's weight or just 1 (constant for all edges
            double weight = (weightedEdges) ? edge.weight : 1.0;
            
            // if the current shortest is greater than the currently compared
            if(distToVertex[to] > distToVertex[from] + weight){
                
                // change the current shortest
                distToVertex[to] = distToVertex[from] + weight;
                edgeToVertex[to] = edge;
                if(vertexQ.contains(to))
                    vertexQ.decreaseKey(to, distToVertex[to]);
                else
                    vertexQ.insert(to, distToVertex[to]);
            }
        }
    }
    
    // returns the finished shortest path in the form of a stack of vertices
    public Stack<Vertex> pathToStack(Graph g, int dest){
        Stack<Vertex> comps = new Stack();
        comps.push(g.vertex(dest));
        
        Edge edge = edgeToVertex[dest];
        while(edge!=null){
            comps.push(g.vertex(edge.from));
            edge = edgeToVertex[edge.from];
        }
        return comps;
    }
    
    
}
