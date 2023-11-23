package com.example.marlonmania.model;

public class MatrixGame extends Game2 {

    private MatrixGraph<String> matrixGraph;

    public MatrixGame(String nickName) {
        super(nickName);

        matrixGraph = new MatrixGraph<>(false,64);

    }

    @Override
    public void init() {

    }

    @Override
    public void addPipe(String vertex, State newState) {

    }

    @Override
    public void initGrafo() {

    }

    @Override
    public void simulate() {

    }
}
