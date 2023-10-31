package model;

import java.util.ArrayList;

public class ListVertex<T> {

    private final T value;
    private final ArrayList<ListEdge<T>> edges;


    private int distance;
    private ListVertex<T> father;

    public ListVertex(T value) {
        this.value = value;
        edges = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public ArrayList<ListEdge<T>> getEdges() {
        return edges;
    }


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ListVertex<T> getFather() {
        return father;
    }

    public void setFather(ListVertex<T> father) {
        this.father = father;
    }
}
