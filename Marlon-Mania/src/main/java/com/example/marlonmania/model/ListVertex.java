package com.example.marlonmania.model;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ListVertex<T>  extends Circle implements Cloneable {
    private final T value;
    private ArrayList<ListEdge<T>> edges;
    private boolean visited;
    private int distance;
    private ListVertex<T> father;
    private State state;

    private int posX;

    private int posY;

    public ListVertex(T value, int posX, int posY) {
        this.value = value;
        this.state = State.EMPTY;
        edges = new ArrayList<>();
        this.posX = posX;
        this.posY = posY;

        this.setRadius(15.0); // Establece el radio predeterminado
        this.setFill(Color.LIGHTBLUE);


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
    public ListVertex<T> clone() {
        try {
            ListVertex<T> clone = (ListVertex<T>) super.clone();
            clone.posX = this.posX;
            clone.posY = this.posY;
            clone.edges = new ArrayList<>();
            if (father != null) {
                clone.father = father.clone();
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
