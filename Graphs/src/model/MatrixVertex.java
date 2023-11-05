package model;

public class MatrixVertex<T> {
    private final T Value;
    private int distance;
    private boolean visited;

    private MatrixVertex<T> father;

    public MatrixVertex(T Value) {
        this.Value = Value;
    }

    public T getValue() {
        return Value;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }


    public MatrixVertex<T> getFather() {
        return father;
    }

    public void setFather(MatrixVertex<T> father) {
        this.father = father;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
