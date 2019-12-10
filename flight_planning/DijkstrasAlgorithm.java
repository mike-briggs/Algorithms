import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class DijkstrasAlgorithm {
	
	private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> doneNodes;
    private Set<Vertex> incompleteNodes;
    private Map<Vertex, Vertex> pre;
    private Map<Vertex, Integer> distance;

    public DijkstrasAlgorithm(Graph graph) {
        
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Vertex src) {
        doneNodes = new HashSet<Vertex>();
        incompleteNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        pre = new HashMap<Vertex, Vertex>();
        distance.put(src, 0);
        incompleteNodes.add(src);
        while (incompleteNodes.size() > 0) {
            Vertex node = getMinimum(incompleteNodes);
            doneNodes.add(node);
            incompleteNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                pre.put(target, node);
                incompleteNodes.add(target);
            }
        }

    }

    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getArrival();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return doneNodes.contains(vertex);
    }

    private int getShortestDistance(Vertex dest) {
        Integer d = distance.get(dest);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * returns the path from src to selected target and
     * NULL if no path exists
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        // check if a path exists
        if (pre.get(step) == null) {
            return null;
        }
        path.add(step);
        while (pre.get(step) != null) {
            step = pre.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}

class Edge  {
    private final String id;
    private final Vertex src;
    private final Vertex dest;
    //private final int departure;
    private final int arrival;
    

    public Edge(String id, Vertex src, Vertex dest, int arrival) {
        this.id = id;
        this.src = src;
        this.dest = dest;
        //this.departure = departure;
        this.arrival = arrival;
    }

    public String getId() {
        return id;
    }
    public Vertex getDestination() {
        return dest;
    }

    public Vertex getSource() {
        return src;
    }
    public int getArrival() {
        return arrival;
    }
   

    public String toString() {
        return src + " " + dest;
    }


}
class Vertex {
    final private String id;
    final private String name;


    public Vertex(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public String toString() {
        return name;
    }

}

class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }



}
