package com.example.marlonmania.model;

import com.example.marlonmania.Controllers.ControllerPlayers;
import com.example.marlonmania.MainApplication;
import com.example.marlonmania.exceptions.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ListGame extends Game2 {


    private ArrayList<String> path;
    private ListGraph<String> listGraph;

    public ListGame(String nickName) {
        super(nickName);
        listGraph = new ListGraph<>(false,false,false);

        this.path = new ArrayList<>(); //camino de pipes

        init();



    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public void setListGraph(ListGraph<String> listGraph) {
        this.listGraph = listGraph;
    }

    @Override
    public void init() {
        initGrafo();
        int fx = ThreadLocalRandom.current().nextInt(8);
        int fy = ThreadLocalRandom.current().nextInt(4);


        int dx = ThreadLocalRandom.current().nextInt(8);
        int dy = ThreadLocalRandom.current().nextInt(5, 8);


        setD(dx + "," + dy);
        setF(fx + "," + fy);

        listGraph.obtainVertex(listGraph.searchVertexIndex(getF())).setState(State.START);
        listGraph.obtainVertex(listGraph.searchVertexIndex(getD())).setState(State.END);

        path.add(getF());


    }

    @Override
    public void addPipe(String vertex, State newState) {

        if(!vertex.equals(getF()) && !vertex.equals(getD())){

            listGraph.obtainVertex(listGraph.searchVertexIndex(vertex)).setState(newState);
            System.out.println("Funciona: " + vertex);

            ListVertex<String> temporalVertex =  listGraph.obtainVertex(listGraph.searchVertexIndex(vertex));
            ListVertex<String> D =  listGraph.obtainVertex(listGraph.searchVertexIndex(getD()));
            ListVertex<String> F =  listGraph.obtainVertex(listGraph.searchVertexIndex(getF()));

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

            path.removeIf( v-> v.equals(vertex) && listGraph.obtainVertex(listGraph.searchVertexIndex(vertex)).getState().equals(State.EMPTY));
            printPath();
        }

    }


    void printPath(){
        for(String vertex : path){
            System.out.println(vertex + " ");
        }
    }

    @Override
    public void initGrafo(){


        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                try {
                    listGraph.addVertex(x + "," + y, x, y);



                } catch (VertexAlreadyAddedException e) {
                    System.err.println("Error al agregar vértice: " + e.getMessage());
                }

                if (y > 0) {
                    int weight = ThreadLocalRandom.current().nextInt(2,10);
                    try {
                        String edgeId = String.format("%s -> %s", x + "," + (y - 1), x + "," + y);
                        listGraph.addEdge(x + "," + (y - 1), x + "," + y, edgeId, weight);
                    } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                        System.err.println("Error al agregar arista: " + e.getMessage());
                    }
                }

                if (x > 0) {

                    int weight = ThreadLocalRandom.current().nextInt(2,6);
                    try {
                        String edgeId = String.format("%s -> %s", (x - 1) + "," + y, x + "," + y);
                        listGraph.addEdge((x - 1) + "," + y, x + "," + y, edgeId, weight);
                    } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                        System.err.println("Error al agregar arista: " + e.getMessage());
                    }
                }
            }
        }
        System.out.println(listGraph.toString());

    }

    public ArrayList<ListVertex<String>> getListGraph() {

        return listGraph.getList();
    }

    public void simulate()  {

        if (!path.get(path.size()-1).equals(getD())){
            System.out.println("FALSE");
            path= new ArrayList<>();
            path.add(getF());
            setNumOfPipes(0);
        }else{
            System.out.println("path " + path.size() );
            for (String v : path) {
                System.out.print( v + " ");

            }

            try {
                if(this.listGraph.DFSVALIDATOR(path)){
                    System.out.println("TRUE");
                    int score = 1000 - ((50)* ( getNumOfPipes() - (path.size() -2) ));
                    ControllerPlayers.getInstance().addPlayer(getNickName(),score);
                    MainApplication.openWindow("menu.fxml");



                }
            } catch (VertexNotFoundException | VertexNotAchievableException e) {
                throw new RuntimeException(e);
            }


        }
    }



}
