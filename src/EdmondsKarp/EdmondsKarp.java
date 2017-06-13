package EdmondsKarp;
import java.util.*;
 
//Class to implement the Edmonds Karp algorithm for finding max flow
public class EdmondsKarp
{
    private int[] parent;
    private Queue<Integer> queue;
    private int numberOfVertices;
    private boolean[] visited;
 
    public EdmondsKarp(int numberOfVertices)
    {
        this.numberOfVertices = numberOfVertices;
        this.queue = new LinkedList<Integer>();
        parent = new int[numberOfVertices + 1];
        visited = new boolean[numberOfVertices + 1];		
    }
 
    //The Edmonds Karp algorithm used BFS to find augementing paths in the graph
    public boolean breadthFirstSearch(int source, int goal, int graph[][])
    {
        boolean pathFound = false;
        int destination;
        int element;
 
        for(int vertex = 1; vertex <= numberOfVertices; vertex++)
        {
            parent[vertex] = -1;
            visited[vertex] = false;
        }
 
        queue.add(source);
        parent[source] = -1;
        visited[source] = true;
 
        while (!queue.isEmpty())
        { 
            element = queue.remove();
            destination = 1;
 
            while (destination <= numberOfVertices)
            {
                if (graph[element][destination] > 0 &&  !visited[destination])
                {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
        }
        if(visited[goal])
        {
            pathFound = true;
        }
        return pathFound;
    }
    
    //Since Edmonds Karp is just a modified version of Edmonds Karp, part of its code is used here
    public int edmonds_Karp(int graph[][], int source, int destination)
    {
        int u, v;
        int maxFlow = 0;
        int pathFlow;
 
        int[][] residualGraph = new int[numberOfVertices + 1][numberOfVertices + 1];
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++)
            {
                residualGraph[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
            }
        }
 
        //Run BFS
        while (breadthFirstSearch(source ,destination, residualGraph))
        {
            pathFlow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = parent[v])
            {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            for (v = destination; v != source; v = parent[v])
            {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;	
        }
 
        return maxFlow;
    }
 
    public static void main(String...arg)
    {
        int[][] graph;
        int numberOfNodes;
        int source;
        int sink;
        int maxFlow;
 
        //Get user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of nodes in the graph:");
        numberOfNodes = scanner.nextInt();
        graph = new int[numberOfNodes + 1][numberOfNodes + 1];
 
        System.out.println("Enter the matrix of the flow values in the graph:");
        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++)
        {
           for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++)
           {
               graph[sourceVertex][destinationVertex] = scanner.nextInt();
           }
        }
 
        System.out.println("Enter the source vertex of the graph:");
        source= scanner.nextInt();
 
        System.out.println("Enter the sink vertex of the graph:");
        sink = scanner.nextInt();
 
        //Run algorithm and output result
        EdmondsKarp edmonds_Karp = new EdmondsKarp(numberOfNodes);
        maxFlow = edmonds_Karp.edmonds_Karp(graph, source, sink);
        System.out.println("The Max Flow of the graph is " + maxFlow);
        scanner.close();
    }
}
