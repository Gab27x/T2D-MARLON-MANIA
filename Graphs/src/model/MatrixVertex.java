package model;

public class MatrixVertex<T> implements Cloneable {
    private final T Value;
    private int distance;
    private boolean visited;
    private State state;
    private int posX;

    private int posY;

    private MatrixVertex<T> father;

    public MatrixVertex(T Value) {
        this.Value = Value;
        this.state = State.CONNECTOR;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public MatrixVertex<T> clone() {
        try {
            MatrixVertex<T> clone = (MatrixVertex<T>) super.clone();
            if (father != null) {
                clone.father = father.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
