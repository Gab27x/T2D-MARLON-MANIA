package com.example.marlonmania.model;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.exceptions.LoopsNotAllowedException;
import com.example.marlonmania.exceptions.MultipleEdgesNotAllowedException;
import com.example.marlonmania.exceptions.VertexAlreadyAddedException;
import com.example.marlonmania.exceptions.VertexNotFoundException;

import java.util.ArrayList;

public class ListGame extends Game2 {

    private ArrayList<String> path;
    private ListGraph<String> listGraph;

    public ListGame(String nickName) {
        super(nickName);
        listGraph = new ListGraph<>(false,false,false);
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
        int dy = ThreadLocalRandom.current().nextInt(5, 8);


        this.d = dx + "," + dy;
        this.f = fx + "," + fy;*/
        setD("3,5");
        setF("7,5");

        listGraph.obtainVertex(listGraph.searchVertexIndex(getF())).setState(State.START);
        listGraph.obtainVertex(listGraph.searchVertexIndex(getD())).setState(State.END);

        path.add(getF());
        path.add(getD());




    }

    @Override
    public void addPipe(String vertex, State newState) {
        if(!vertex.equals(getF()) && !vertex.equals(getD())){


            listGraph.obtainVertex(listGraph.searchVertexIndex(vertex)).setState(newState);

            System.out.println("Funciona: " + vertex);
            setNumOfPipes(getNumOfPipes() + 1);

            if(!path.contains(vertex)){
                path.add(vertex);
            }

            path.removeIf( v-> v.equals(vertex) && listGraph.obtainVertex(listGraph.searchVertexIndex(vertex)).getState().equals(State.EMPTY));

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
                    try {
                        String edgeId = String.format("%s -> %s", x + "," + (y - 1), x + "," + y);
                        listGraph.addEdge(x + "," + (y - 1), x + "," + y, edgeId, 1);
                    } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                        System.err.println("Error al agregar arista: " + e.getMessage());
                    }
                }

                if (x > 0) {
                    try {
                        String edgeId = String.format("%s -> %s", (x - 1) + "," + y, x + "," + y);
                        listGraph.addEdge((x - 1) + "," + y, x + "," + y, edgeId, 1);
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

    @Override
    public void simulate() {
        path.removeIf( v-> v.equals(getD()));
        path.add(getD());


        System.out.println("path " + path.size());
        for (String v : path) {
            System.out.print( v + " ");

        }



        try{

            if(this.listGraph.DFSVALIDATOR(path)){
                MainApplication.openWindow("menu.fxml");
                System.out.println("-> true");

            }else {
                System.out.println("-> false");
            }

        }catch (Exception e){
            System.err.println("TOCA PEGARLE A LUIS");
        }


    }
}
