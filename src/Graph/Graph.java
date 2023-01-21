package Graph;

import java.util.*;

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

    public boolean depthFirstSearch(String s, String t) {
        HashSet<Vertex> explored = new HashSet<>();
        Deque<Vertex> stack = new LinkedList<>();
        stack.push(vertices.get(s));

        while(!stack.isEmpty()){
            Vertex n = stack.pop();
            if(!explored.contains(n)){
                explored.add(n);
                System.out.println(n.label);
                for(Vertex v: n.neighbors){
                    if(v==vertices.get(t)){
                        return true;
                    }
                    stack.push(v);
                }
            }
        }

        return false;
    }

    public boolean breadthFirstSearch(String s, String t) {
        HashSet<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        visited.add(vertices.get(s));
        queue.offer(vertices.get(s));

        while(!queue.isEmpty()){
            Vertex n = queue.poll();
            System.out.println(n.label);
            for(Vertex v: n.neighbors){
                if(v==vertices.get(t)){
                    return true;
                }
                if(!visited.contains(v)){
                    visited.add(v);
                    queue.offer(v);
                }
            }
        }

        return false;
    }

    public String breadthFirstSearchWithPath(String s, String t) {
        HashMap<String, String> path = new HashMap<>();
        boolean found = false;

        HashSet<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        visited.add(vertices.get(s));
        queue.offer(vertices.get(s));

        while(!queue.isEmpty()){
            if(found){
                break;
            }
            Vertex n = queue.poll();
            for(Vertex v: n.neighbors){
                if(!visited.contains(v)){
                    visited.add(v);
                    queue.offer(v);
                    path.put(v.label, n.label);
                }
                if(v==vertices.get(t)){
                    found = true;
                    break;
                }
            }
        }

        if(!found){
            return "no path";
        }

        Vertex current = vertices.get(t);
        String pathStringRev = t;
        while (current.label != s){
            pathStringRev += path.get(current.label);
            current = vertices.get(path.get(current.label));
        }

        String pathString = "";
        for(int i = pathStringRev.length()-1;i>=0;i--){
            pathString += pathStringRev.charAt(i);
        }
        return pathString;
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

        // --------------------------
        // Test 6: DFS True
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 6: DFS True");
        System.out.println("Expected:");
        System.out.println("A\n" +
                "B\n" +
                "E\n" +
                "F\n" +
                "C\n" +
                "true");

        System.out.println("\nGot:");

        Graph graph2 = new Graph();
        graph2.addVertex("A");
        graph2.addVertex("B");
        graph2.addVertex("C");
        graph2.addVertex("D");
        graph2.addVertex("E");
        graph2.addVertex("F");
        graph2.addVertex("G");
        graph2.addVertex("H");
        graph2.addVertex("I");
        graph2.addEdge("A", "D");
        graph2.addEdge("A", "C");
        graph2.addEdge("A", "B");
        graph2.addEdge("D", "I");
        graph2.addEdge("D", "H");
        graph2.addEdge("C", "G");
        graph2.addEdge("B", "F");
        graph2.addEdge("B", "E");

        System.out.println(graph2.depthFirstSearch("A", "G"));
        System.out.println();

        // --------------------------
        // Test 7: DFS False
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 7: DFS False");
        System.out.println("Expected:");
        System.out.println("A\n" +
                "B\n" +
                "E\n" +
                "F\n" +
                "C\n" +
                "G\n" +
                "D\n" +
                "H\n" +
                "I\n" +
                "false");

        System.out.println("\nGot:");

        System.out.println(graph2.depthFirstSearch("A", "Z"));
        System.out.println();

        // --------------------------
        // Test 8: BFS True
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 8: BFS True");
        System.out.println("Expected:");
        System.out.println("A\n" +
                "B\n" +
                "C\n" +
                "true");

        System.out.println("\nGot:");

        // graph3 is the same as graph3, but the
        // neighbor lists are reversed

        Graph graph3 = new Graph();
        graph3.addVertex("A");
        graph3.addVertex("B");
        graph3.addVertex("C");
        graph3.addVertex("D");
        graph3.addVertex("E");
        graph3.addVertex("F");
        graph3.addVertex("G");
        graph3.addVertex("H");
        graph3.addVertex("I");
        graph3.addEdge("A", "B");
        graph3.addEdge("A", "C");
        graph3.addEdge("A", "D");
        graph3.addEdge("D", "H");
        graph3.addEdge("D", "I");
        graph3.addEdge("C", "G");
        graph3.addEdge("B", "E");
        graph3.addEdge("B", "F");

        System.out.println(graph3.breadthFirstSearch("A", "G"));
        System.out.println();

        // --------------------------
        // Test 9: BFS False
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 9: BFS False");
        System.out.println("Expected:");
        System.out.println("A\n" +
                "B\n" +
                "C\n" +
                "D\n" +
                "E\n" +
                "F\n" +
                "G\n" +
                "H\n" +
                "I\n" +
                "false");

        System.out.println("\nGot:");

        System.out.println(graph3.breadthFirstSearch("A", "Z"));
        System.out.println();

        // --------------------------
        // Test 10: BFS Pathfinding
        // --------------------------
        System.out.println("-------------------");
        System.out.println("Test 10: BFS Pathfinding");
        System.out.println("Expected:");
        System.out.println("ACB\n" +
                "AC\n" +
                "AD\n" +
                "AE\n" +
                "AEF\n" +
                "AIG\n" +
                "AIH\n" +
                "AI\n" +
                "AJ\n" +
                "no path");

        System.out.println("\nGot:");

        Graph graph4 = new Graph();
        graph4.addVertex("A");
        graph4.addVertex("B");
        graph4.addVertex("C");
        graph4.addVertex("D");
        graph4.addVertex("E");
        graph4.addVertex("F");
        graph4.addVertex("G");
        graph4.addVertex("H");
        graph4.addVertex("I");
        graph4.addVertex("J");


        graph4.addEdge("A", "C");
        graph4.addEdge("A", "D");
        graph4.addEdge("A", "E");
        graph4.addEdge("A", "I");
        graph4.addEdge("A", "J");
        graph4.addEdge("B", "C");
        graph4.addEdge("B", "H");
        graph4.addEdge("B", "I");
        graph4.addEdge("D", "I");
        graph4.addEdge("D", "E");
        graph4.addEdge("E", "F");
        graph4.addEdge("F", "G");
        graph4.addEdge("F", "J");
        graph4.addEdge("G", "H");
        graph4.addEdge("G", "I");
        graph4.addEdge("G", "J");
        graph4.addEdge("H", "I");
        graph4.addEdge("H", "J");
        graph4.addEdge("I", "J");

        System.out.println(graph4.breadthFirstSearchWithPath("A", "B"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "C"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "D"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "E"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "F"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "G"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "H"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "I"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "J"));
        System.out.println(graph4.breadthFirstSearchWithPath("A", "Z"));
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


