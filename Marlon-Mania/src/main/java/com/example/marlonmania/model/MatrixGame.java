package com.example.marlonmania.model;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.exceptions.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixGame extends Game2 {

    private MatrixGraph<String> matrixGraph;
    private ArrayList<String> path;


    public MatrixGame(String nickName) {
        super(nickName);

        matrixGraph = new MatrixGraph<>(false,64);
        init();
    }

    @Override
    public void init() {
        initGrafo();



        /*
        int fx = ThreadLocalRandom.current().nextInt(8);
        int fy = ThreadLocalRandom.current().nextInt(4);


        int dx = ThreadLocalRandom.current().nextInt(8);
        int dy = ThreadLocalRandom.current().nextInt(6, 8);


        this.d = dx + "," + dy;
        this.f = fx + "," + fy;*/
        setD("3,5");
        setF("7,5");

        matrixGraph.obtainVertex(matrixGraph.searchVertexIndex(getF())).setState(State.START);
        matrixGraph.obtainVertex(matrixGraph.searchVertexIndex(getD())).setState(State.END);

        path.add(getF());




    }

    @Override
    public void addPipe(String vertex, State newState) {

    }

    @Override
    public void initGrafo() {


        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                try {
                    System.out.println(x +","+ y);
                    matrixGraph.addVertex(x + "," + y, x, y);



                } catch (VertexAlreadyAddedException e) {
                    System.err.println("Error al agregar vértice: " + e.getMessage());
                }

                if (y > 0) {
                    int weight = 1/*ThreadLocalRandom.current().nextInt(2,10)*/;
                    try {
                        String edgeId = String.format("%s -> %s", x + "," + (y - 1), x + "," + y);
                        matrixGraph.addEdge(x + "," + (y - 1), x + "," + y, edgeId, weight);
                    } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                        System.err.println("Error al agregar arista: " + e.getMessage());
                    }
                }

                if (x > 0) {

                    int weight = 1/* ThreadLocalRandom.current().nextInt(2,6)*/;
                    try {
                        String edgeId = String.format("%s -> %s", (x - 1) + "," + y, x + "," + y);
                        matrixGraph.addEdge((x - 1) + "," + y, x + "," + y, edgeId, weight);
                    } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                        System.err.println("Error al agregar arista: " + e.getMessage());
                    }
                }
            }
        }
        matrixGraph.print();

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
