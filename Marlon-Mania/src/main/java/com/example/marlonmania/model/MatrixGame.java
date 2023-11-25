package com.example.marlonmania.model;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.exceptions.VertexNotAchievableException;
import com.example.marlonmania.exceptions.VertexNotFoundException;

import java.util.ArrayList;

public class MatrixGame extends Game2 {

    private MatrixGraph<String> matrixGraph;
    private ArrayList<String> path;


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

        ArrayList<MatrixVertex<String>> userPathVertex= new ArrayList<>();
        for (String temp: path){
            int tempInd=matrixGraph.searchVertexIndex(temp);
            userPathVertex.add( matrixGraph.obtainVertex(tempInd));
        }
        try {
            int shortPath= matrixGraph.dijkstra(getF(), getD());
            ArrayList<Integer> userPathVertexEdges= matrixGraph.getEdgeWeightsList(userPathVertex);
            int userShortPath= matrixGraph.subGraphDistance(userPathVertexEdges);

            if (shortPath!=userShortPath){
                System.out.println("NO GANASTE");
                System.out.println("DIFERENCIA"+Math.abs(shortPath-userShortPath));
            }else  {
                System.out.println("GANASTE");
                MainApplication.openWindow("menu.fxml");
            }
        } catch (VertexNotFoundException | VertexNotAchievableException e) {
            throw new RuntimeException(e);
        }
        path= new ArrayList<>();
        path.add(getF());
        setNumOfPipes(0);
    }

    public MatrixGraph<String> getMatrixGraph() {
        return matrixGraph;
    }

    public void setMatrixGraph(MatrixGraph<String> matrixGraph) {
        this.matrixGraph = matrixGraph;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }
}
