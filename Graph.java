package com.company.Dijkstra;

import java.util.*;

public class Graph {

    public Map<String, List<Node>> adjacencyList;

    public Graph(){
        this.adjacencyList = new LinkedHashMap<String, List<Node>>();
    }

    public void addVertex(String vertex){
        if(!this.adjacencyList.containsKey(vertex)) {

            List<Node> connectionsList = new ArrayList<Node>();
            this.adjacencyList.put(vertex, connectionsList);
        }
    }

    public void addEdge(String v1, String v2, Integer weight){
        Node vertex2 = new Node(weight, v2);
        Node vertex1 = new Node(weight, v1);

        this.adjacencyList.get(v1).add(vertex2);
        this.adjacencyList.get(v2).add(vertex1);
    }

    public void removeEdge(String vertex1, String vertex2){

        // Go through each node in the list for the first key
        for (int i = 0; i < this.adjacencyList.get(vertex1).size(); i++) {
            // If a vertex with the given name is found, remove it from the list
            if (this.adjacencyList.get(vertex1).get(i).getName().equals(vertex2)) {
                this.adjacencyList.get(vertex1).remove(i);
                break;
            }
        }

        // Go through each node in the list for the second key
        for (int j = 0; j < this.adjacencyList.get(vertex2).size(); j++) {
            // If a vertex with the given name is found, remove it from the list
            if (this.adjacencyList.get(vertex2).get(j).getName().equals(vertex1)) {
                this.adjacencyList.get(vertex2).remove(j);
                break;
            }
        }
    }

    public void removeVertex(String removedVertex){
        for (Map.Entry<String, List<Node>> vertexKey : this.adjacencyList.entrySet()) {
            for (int i = 0; i < vertexKey.getValue().size(); i++) {
                removeEdge(vertexKey.getKey(), removedVertex);
            }
        }
        this.adjacencyList.remove(removedVertex);
    }

    public List<String> DFS_recursive(String vertex){
        List<String> result = new ArrayList<>();

        DFS_recursive_helper(vertex, result);

        return result;
    }

    private void DFS_recursive_helper(String start, List<String> result){
        System.out.println("Visit adjacent nodes to vertex: " + start + " - " + this.adjacencyList.get(start));
        if(!this.adjacencyList.containsKey(start)) return;

        if(!result.contains(start)){
            result.add(start);
            System.out.println("Result - " + result);
            this.adjacencyList.get(start).forEach(vertex -> this.DFS_recursive_helper(vertex.getName(), result));
        }else{
            System.out.println("Vertex: " + start + " already visited!");
        }
    }

    public List<String> DFS_iterative(String start) {
        List<String> result  = new ArrayList<>();
        List<String> stack   = new ArrayList<>();
        List<String> visited = new ArrayList<>();

        stack.add(start);
        visited.add(start);

        while(stack.size() > 0) {
            //Get last node from stack
            String current = stack.get(stack.size() - 1);
            stack.remove(stack.size() - 1);

            result.add(current);

            System.out.println(current);
            System.out.println(stack);

            //Visit each neighbor for the current vertex
            this.adjacencyList.get(current).forEach(vertex -> {
                if(!visited.contains(vertex.getName())){
                    stack.add(vertex.getName());
                    visited.add(vertex.getName());
                }
            });
        }
        return result;
    }

    public List<String> BFS(String start) {
        List<String> result  = new ArrayList<>();
        List<String> queue   = new ArrayList<>();

        queue.add(start);
        result.add(start);

        while(queue.size() > 0) {

            String current = queue.get(0);
            queue.remove(0);

            this.adjacencyList.get(current).forEach(neighbor -> {
                if(!result.contains(neighbor.getName())){
                    result.add(neighbor.getName());
                    queue.add(neighbor.getName());
                }
            });
        }
        return result;
    }

    //--------------Dijkstra-------------------------------
    public List<String> Dijkstra(String start, String end){
        System.out.println("\n--------Dijkstra-------- \n");
        // Update each vertex with the shortest distance to start
        PriorityQueue queue = new PriorityQueue();

        // Keeps shortest distance to the starting vertex
        Map<String, Integer> distance = new LinkedHashMap<>();

        // For the shortest distance to start, which vertex should I go to first?
        Map<String, String> previous = new LinkedHashMap<>();


        List<String> result = new ArrayList<>();

        // Build initial state for the algorithm
        for (Map.Entry<String, List<Node>> vertex : this.adjacencyList.entrySet()) {
            if(vertex.getKey().equals(start)) {
                distance.put(vertex.getKey(), 0);
                previous.put(vertex.getKey(), null);
                queue.enqueue(0, vertex.getKey());
            } else {
                distance.put(vertex.getKey(), Integer.MAX_VALUE);
                previous.put(vertex.getKey(), null);
                queue.enqueue(Integer.MAX_VALUE, vertex.getKey());
            }
        }
        printStateVariables(distance, previous, queue);

        // Visit each  node in the priority queue
        while(queue.values.size() > 0){
            String currentVertex = queue.dequeue().getName();

            this.adjacencyList.get(currentVertex).forEach(vertex -> {
                String vertexName = vertex.getName();
                Integer vertexValue = vertex.getValue();

                //If the calculated distance is smaller then update distance
                Integer candidateDist = distance.get(currentVertex) + vertexValue;
                if(distance.get(vertexName) > candidateDist){
                    distance.put(vertexName, candidateDist);
                    previous.put(vertexName, currentVertex);
                    queue.enqueue(candidateDist, vertexName);
                }
            });
        }
        // Build path
        result.add(end);
        String iter = end;
        for (Map.Entry<String, String> vertex : previous.entrySet()) {
            if(iter.equals(start)) break;
            result.add(previous.get(iter));
            iter = previous.get(iter);
        }

        Collections.reverse(result);
        return result;
    }
    private void printStateVariables(Map<String, Integer> distance, Map<String, String> previous, PriorityQueue queue){
        System.out.println("This is the initial state of the algorithm:");
        System.out.println("\nDISTANCE:");
        for (Map.Entry<String, Integer> vertex : distance.entrySet()) {
            String current = vertex.getKey() + " " + Integer.toString(vertex.getValue());
            System.out.println(current);
        }
        System.out.println("\nPREVIOUS:");
        for (Map.Entry<String, String> vertex : previous.entrySet()) {
            String current = vertex.getKey() + " " + "null";
            System.out.println(current);
        }
        System.out.println("\nQUEUE:");
        System.out.println(queue.toString());
    }

    public void printGraph(){
        System.out.println("\n--------Graph--------: \n");
        for (Map.Entry<String, List<Node>> vertexKey : this.adjacencyList.entrySet()) {
            System.out.printf("%s = [", vertexKey.getKey());
            for (int i = 0; i < vertexKey.getValue().size(); i++) {
                String nodeName = vertexKey.getValue().get(i).getName();
                Integer nodeWeight = vertexKey.getValue().get(i).getValue();
                System.out.printf("{ %s %d }", nodeName, nodeWeight);
            }
            System.out.print("]");
            System.out.println();
        }
        System.out.println("\n---------------------");
    }
}
