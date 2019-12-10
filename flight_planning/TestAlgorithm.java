import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


/*“I certify that this submission contains my own work, except as noted"
 * Michael Briggs
 * 20013906
 * 
 * Note - Dijkstra Implemented and Flights put into algorithm but can't calculate best path
 */
public class TestAlgorithm {
	private List<Vertex> nodes;
    private List<Edge> edges;
    
    public static void main(String[]args) {
    	TestAlgorithm test = new TestAlgorithm();
    	test.run();
    	
    }

   
    public void run() {
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        
     
        
        Path filePath = Paths.get("src/flights.txt");
        Scanner scanner;
        int curr =0;
        int x =0;
        int[]flights = new int[4];
        
		try {
			scanner = new Scanner(filePath);
			List<Integer> integers = new ArrayList<>();
			int numNodes = scanner.nextInt();
            for (int i = 0; i < numNodes; i++) {
                Vertex location = new Vertex("Node_" + i, "Node_" + i);
                nodes.add(location);
            }
	        while (scanner.hasNext()) {
	            if (scanner.hasNextInt()) {
	            	
	            	flights[curr] = scanner.nextInt();
	                integers.add(scanner.nextInt());
	                curr++;
	                x++;
	                if(curr == 4) {
	                	addLane("Edge_"+x, flights[0], flights[1], flights[3]);
	                	curr=0;
	                }
	            } else {
	                scanner.next();
	            }
	        }
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		//shortest path
        Graph graph = new Graph(nodes, edges);
        //algorithm learnt from online
        DijkstrasAlgorithm dijkstra = new DijkstrasAlgorithm(graph);
        dijkstra.execute(nodes.get(0));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(2));

  

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }

    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }
}
