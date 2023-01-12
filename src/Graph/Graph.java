package Graph;

import java.util.HashMap;
import java.util.LinkedList;

public class Graph {
    HashMap<String, Vertex> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    // Creates a new vertex with the given label
    // and adds it to the graph
    public void addVertex(String label) {
        Vertex newVertex = new Vertex(label);
        vertices.put(label, newVertex);
    }

    // Adds an edge between the vertices with the given
    // labels to the graph
    public void addEdge(String label1, String label2) {
        Vertex vertex1 = vertices.get(label1);
        Vertex vertex2 = vertices.get(label2);
        vertex1.neighbors.add(vertex2);
        vertex2.neighbors.add(vertex1);
    }

    // Checks to see if there is an edges between the
    // vertices with the given labels
    public boolean hasEdge(String label1, String label2) {
        Vertex vertex1 = vertices.get(label1);
        Vertex vertex2 = vertices.get(label2);
        return vertex1.neighbors.contains(vertex2);
    }

    // Removes the given vertex and all of its edges from
    // the graph
    public void removeVertex(String label) {
        Vertex vertexToRemove = vertices.get(label);
        for(Vertex v: vertexToRemove.neighbors){
            removeEdge(label, v.label);
        }
        vertices.remove(label);
    }

    // Removes the edge between the given vertices
    public void removeEdge(String label1, String label2) {
        Vertex vertex1 = vertices.get(label1);
        Vertex vertex2 = vertices.get(label2);
        vertex1.neighbors.remove(vertex2);
        vertex2.neighbors.remove(vertex1);
    }

    public void printGraph() {
        int longest = 7;
        for (String str: vertices.keySet()) {
            longest = Math.max(longest, str.length() + 1);
        }

        String line = "Vertex ";
        for (int i = 7; i < longest; i++)
            line += " ";
        int leftLength = line.length();
        line += "| Adjacent Vertices";
        System.out.println(line);

        for (int i = 0; i < line.length(); i++)
        {
            System.out.print("-");
        }
        System.out.println();

        for (String str: vertices.keySet()) {
            Vertex v1 = vertices.get(str);

            for (int i = str.length(); i < leftLength; i++) {
                str += " ";
            }
            System.out.print(str + "| ");

            for (int i = 0; i < v1.neighbors.size()-1; i++) {
                System.out.print(v1.neighbors.get(i).label + ", ");
            }

            if (!v1.neighbors.isEmpty()) {
                System.out.print(v1.neighbors.get(v1.neighbors.size()-1).label);
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        // --------------------------
        // Test 1: Add Vertices
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 1: Add Vertices");
        System.out.println("Expected:");
        System.out.println("Vertex        | Adjacent Vertices\n" +
                "---------------------------------\n" +
                "San Francisco | \n" +
                "New York      | \n" +
                "Chicago       | \n" +
                "London        | ");

        System.out.println("\nGot:");

        Graph graph = new Graph();

        graph.addVertex("London");
        graph.addVertex("New York");
        graph.addVertex("San Francisco");
        graph.addVertex("Chicago");
        graph.printGraph();
        System.out.println();

        // --------------------------
        // Test 2: Add Edges
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 2: Add Edges");
        System.out.println("Expected:");
        System.out.println("Vertex        | Adjacent Vertices\n" +
                "---------------------------------\n" +
                "San Francisco | New York, Chicago\n" +
                "New York      | London, Chicago, San Francisco\n" +
                "Chicago       | New York, San Francisco\n" +
                "London        | New York");

        System.out.println("\nGot:");

        graph.addEdge("London", "New York");
        graph.addEdge("New York", "Chicago");
        graph.addEdge("San Francisco", "New York");
        graph.addEdge("San Francisco", "Chicago");
        graph.printGraph();
        System.out.println();

        // --------------------------
        // Test 3: Has Edges
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 3: Has Edges");
        System.out.println("Expected:");
        System.out.println("true");
        System.out.println("true");
        System.out.println("false");


        System.out.println("\nGot:");

        System.out.println(graph.hasEdge("London", "New York"));
        System.out.println(graph.hasEdge("New York", "Chicago"));
        System.out.println(graph.hasEdge("London", "San Francisco"));

        // --------------------------
        // Test 4: Remove edge
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 4: Remove edge");
        System.out.println("Expected:");
        System.out.println("Vertex        | Adjacent Vertices\n" +
                "---------------------------------\n" +
                "San Francisco | New York, Chicago\n" +
                "New York      | London, San Francisco\n" +
                "Chicago       | San Francisco\n" +
                "London        | New York");


        System.out.println("\nGot:");
        graph.removeEdge("New York", "Chicago");
        graph.printGraph();
        System.out.println();

        // --------------------------
        // Test 5: Remove vertex
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 5: Remove vertex");
        System.out.println("Expected:");
        System.out.println("Vertex        | Adjacent Vertices\n" +
                "---------------------------------\n" +
                "San Francisco | New York, Chicago\n" +
                "New York      | San Francisco\n" +
                "Chicago       | San Francisco");

        System.out.println("\nGot:");
        graph.removeVertex("London");
        graph.printGraph();
        System.out.println();
    }
}

class Vertex {
    String label;
    LinkedList<Vertex> neighbors;

    public Vertex(String label) {
        this.label = label;
        this.neighbors = new LinkedList<>();
    }
}


