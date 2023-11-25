package com.example.marlonmania.model;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.exceptions.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;



// FIXME CUANDO EL JUGADOR GANE TOCA AGREGAR SU PUNTAJE CON CONTROLLERPLAYERS.GETINSTANCE Y DEVOLVERLO AL MENU PARA QUE PUEDA ACCEDER AL RANKING

public class MatrixGame extends Game2 {

    private MatrixGraph<String> matrixGraph;
    private ArrayList<String> path;


    public MatrixGame(String nickName) {
        super(nickName);

        matrixGraph = new MatrixGraph<>(false,64);
        this.path = new ArrayList<>();
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
        // FIXME LUIS DEBES TERMINAR ESTO YA QUE TUS METODOS DEPENDE DE ESTE
        // FIXME CUANDO EL JUGADOR GANE TOCA AGREGAR SU PUNTAJE CON CONTROLLERPLAYERS.GETINSTANCE Y DEVOLVERLO AL MENU PARA QUE PUEDA ACCEDER AL RANKING
        if(!vertex.equals(getF()) && !vertex.equals(getD())){

            matrixGraph.obtainVertex(matrixGraph.searchVertexIndex(vertex)).setState(newState);
            System.out.println("Funciona: " + vertex);

            MatrixVertex<String> temporalVertex =  matrixGraph.obtainVertex(matrixGraph.searchVertexIndex(vertex));
            MatrixVertex<String> D =  matrixGraph.obtainVertex(matrixGraph.searchVertexIndex(getD()));
            MatrixVertex<String> F =  matrixGraph.obtainVertex(matrixGraph.searchVertexIndex(getF()));

            int posY= temporalVertex.getPosY();
            int posX= temporalVertex.getPosX();

            if( !path.contains(vertex) && (posY+1==D.getPosY())  || (posY-1==D.getPosY()) || (posX+1==D.getPosX()) || (posX-1==D.getPosX())){
                path.add(vertex);
                setNumOfPipes(getNumOfPipes() + 1);
                path.add(getD());
                simulate();
            } else if (!path.contains(vertex)) {
                System.out.println("CONDICION INICIAL"+path.size());
                path.add(vertex);
                setNumOfPipes(getNumOfPipes() + 1);
            }

            path.removeIf( v-> v.equals(vertex) && matrixGraph.obtainVertex(matrixGraph.searchVertexIndex(vertex)).getState().equals(State.EMPTY));

        }




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
                    int weight = ThreadLocalRandom.current().nextInt(2,10);
                    try {
                        String edgeId = String.format("%s -> %s", x + "," + (y - 1), x + "," + y);
                        matrixGraph.addEdge(x + "," + (y - 1), x + "," + y, edgeId, weight);
                    } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                        System.err.println("Error al agregar arista: " + e.getMessage());
                    }
                }

                if (x > 0) {

                    int weight =  ThreadLocalRandom.current().nextInt(2,6);
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
        // FIXME LEVEL 0 ES FACIL Y VA CON DFS - LEVEL 1 ES HARD Y VA CON EL OTRO

        //el simulate esta o no completo
        if (!path.get(path.size()-1).equals(getD())){
            //resetear el simulate si esta mal
            System.out.println("FALSE");
            path= new ArrayList<>();
            this.path.add(getF());
            setNumOfPipes(0);
            System.out.println("new path");

        }else{
            if(getLevel()==1){
                ArrayList<MatrixVertex<String>> userPathVertex= new ArrayList<>();
                for (String temp: path){
                    int tempInd=matrixGraph.searchVertexIndex(temp);
                    userPathVertex.add( matrixGraph.obtainVertex(tempInd));
                }
                try {
                    int shortPath= matrixGraph.dijkstra(getF(), getD());
                    System.out.println("DIKSTRAJ "+shortPath);
                    ArrayList<Integer> userPathVertexEdges= matrixGraph.getEdgeWeightsList(userPathVertex);
                    int userShortPath= matrixGraph.subGraphDistance(userPathVertexEdges);
                    System.out.println("USER PATH"+ userShortPath);

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
            }else{

                try {
                    if(this.matrixGraph.DFSVALIDATOR(path)){
                        System.out.println("VALIDADO GANASTE");
                        MainApplication.openWindow("menu.fxml");
                    }else {
                        System.out.println("PERDISTE");
                    }
                } catch (VertexNotFoundException | VertexNotAchievableException e) {
                    throw new RuntimeException(e);
                }


            }

        }


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
