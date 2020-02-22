package com.company.Dijkstra;



import java.util.ArrayList;
import java.util.List;

public class PriorityQueue {

    public List<Node> values;

    public PriorityQueue(){
        this.values = new ArrayList<Node>();
    }

    public void enqueue(int value, String name){
        Node node = new Node(value, name);
        this.values.add(node);

        if(this.values.size() > 1){
            this.bubbleUp();
        }
    }
    private void bubbleUp(){
        int idx = this.values.size() - 1;
        Node child = this.values.get(idx);

        while(idx > 0){
            int parentIdx = (int) Math.floor(((idx - 1 )/ 2));
            Node parent = this.values.get(parentIdx);
            if(parent.getValue() <= child.getValue()) break;
            this.values.set(parentIdx, child);
            this.values.set(idx, parent);
            idx = parentIdx;
        }
    }

    public Node dequeue(){
        Node min = this.values.get(0);
        Node end = this.values.get(this.values.size() - 1);
        this.values.remove(this.values.size() -1);
        if(this.values.size() > 0){
            this.values.set(0, end);
            this.sinkDown();
        }
        return min;
    }
    private void sinkDown(){
        int idx = 0;
        int length = this.values.size()-1;
        Node rightChild, leftChild;
        Node element = this.values.get(0);

        while(true){
            //get children indexes
            int leftChildIdx  = 2 * idx + 1;
            int rightChildIdx = 2 * idx + 2;
            int swap = Integer.MAX_VALUE;

            //get childrent values and compare
            if(leftChildIdx <= length){
                leftChild = this.values.get(leftChildIdx);
                if(leftChild.getValue() < element.getValue()){
                    swap = leftChildIdx;
                }
            }

            if(rightChildIdx <= length){
                rightChild = this.values.get(rightChildIdx);
                if(swap == Integer.MAX_VALUE && rightChild.getValue() < element.getValue()) {
                    swap = rightChildIdx;
                } else if(swap == Integer.MAX_VALUE && leftChildIdx <= length) {
                    leftChild = this.values.get(leftChildIdx);
                    if (rightChild.getValue() < leftChild.getValue()) {
                        swap = rightChildIdx;
                    }

                }
            }
            //swap
            if(swap == Integer.MAX_VALUE) return;
            this.values.set(idx, this.values.get(swap));
            this.values.set(swap, element);
            idx = swap;
        }
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        this.values.forEach(node -> {
            String name = node.getName();
            int value = node.getValue();
            String current = "Node: " +  name + " " + value;
            result.append(current + "\n");
        });
        return result.toString();
    }
}