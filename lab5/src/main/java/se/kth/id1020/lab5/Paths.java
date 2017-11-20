package se.kth.id1020.lab5;
import edu.princeton.cs.algs4.Stack;
import se.kth.id1020.Graph;
import se.kth.id1020.DataSource;
import se.kth.id1020.Vertex;

public class Paths {
    
    
    public static void main(String[] args) {
        Graph g = DataSource.load();
        // work on g
        
        // task 3.1
        System.out.println("\t\tTASK 3.1");
        ConnectedComponent cc = new ConnectedComponent(g);
        System.out.println("Connected components (subgraphs) count: " + cc.count());
        System.out.println("Number of vertices: " + g.numberOfEdges());
        if(cc.count() > 1) System.out.println("There are more than one "
                + "connected components (subgraphs), so the graph is not fully connected");
        
        
        // task 3.2
        System.out.println("\n\n\t\tTASK 3.2");
        
        // finding the id for the vertices "Renyn" and "Parses"
        int renynId = 0;
        int parsesId = 0;
        for(Vertex v : g.vertices()){
            if(v.label.equals("Renyn"))
                renynId = v.id;
            else if(v.label.equals("Parses"))
                parsesId = v.id;
        }
        
        // declaring reused variables
        int nComponents;
        ShortestPathDijkstra spd;
        Stack<Vertex> vertexStack;
        
        
        //
        //      SHORTEST PATH, IGNORING WEIGHTS
        //
        nComponents = 0;
        // false = account for weights
        spd = new ShortestPathDijkstra(g, renynId, false); 
        System.out.println("\nShortest path, weights NOT accounted for between"
                + " Renyn and Parses: ");
        
        // get the stack of vertices
        vertexStack = spd.pathToStack(g, parsesId);
        
        // count and print vertices
        while(!vertexStack.isEmpty()){
            Vertex v = vertexStack.pop();
            nComponents++;
            System.out.println("Component #" + nComponents+ ": " + v + " ");
        }
        System.out.println("\tNumber of components found: " +nComponents);
        
        
        
        //
        //      SHORTEST PATH, INCLUDING WEIGHTS
        //
        nComponents = 0;
        // true = account for weights
        spd = new ShortestPathDijkstra(g, renynId, true); 
        System.out.println("\nShortest path, weights accounted for between"
                + " Renyn and Parses: ");
        
        // get the stack of vertices
        vertexStack = spd.pathToStack(g, parsesId);
        
        // count and print vertices
        while(!vertexStack.isEmpty()){
            Vertex v = vertexStack.pop();
            nComponents++;
            System.out.println(nComponents+ ": " + v + " ");
        }
        System.out.println("\tNumber of components found: " +nComponents);
        
        
        
    }
}