package model;

import java.util.ArrayList;

public class ListVertex<T> implements Cloneable {
    private final T value;
    private ArrayList<ListEdge<T>> edges;
    private boolean visited;
    private int distance;
    private ListVertex<T> father;
    private State state;

    public ListVertex(T value) {
        this.value = value;
        this.state = State.CONNECTOR;
        edges = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public ArrayList<ListEdge<T>> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<ListEdge<T>> newEdges) {
        this.edges = newEdges;
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public ListVertex<T> clone() {
        try {
            ListVertex<T> clone = (ListVertex<T>) super.clone();

            clone.edges = new ArrayList<>();

            // Clonar el campo mutable father si no es nulo
            if (father != null) {
                clone.father = father.clone();
            }

            // Aquí puedes realizar la clonación de otros campos mutables si los tienes
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
