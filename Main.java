package com.company.Dijkstra;

import com.company.Dijkstra.Graph;


public class Main {
    public static void main(String[] args) {

// ===================== Min Priority Queue =====================

//        System.out.println("Start priority queue test");
//
//        PriorityQueue ER = new PriorityQueue();
//
//        ER.enqueue(5, "common cold");
//        ER.enqueue(1, "gunshot wound");
//        ER.enqueue(4, "high fever");
//        ER.enqueue(2, "broken arm");
//        ER.enqueue(3, "glass in foot");
//
//        System.out.println(ER.dequeue().getName());
//
//        System.out.println(ER.toString());


// ===================== Graph =====================

        Graph graph = new Graph();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addEdge("A","B", 4);
        graph.addEdge("A","C", 2);
        graph.addEdge("B","E", 3);
        graph.addEdge("C","D", 2);
        graph.addEdge("C","F", 4);
        graph.addEdge("D","E", 3);
        graph.addEdge("D","F", 1);
        graph.addEdge("E","F", 1);

        graph.printGraph();

        // Dijkstra's algorithm
        //System.out.println(graph.Dijkstra("A", "E"));

// ===================== Graph Traversal =====================

//        System.out.println("DFS graph traversal: ");
//        System.out.println(graph.DFS_iterative("A"));

//        System.out.println("BFS graph traversal: ");
//        System.out.println(graph.BFS("A"));
    }
}