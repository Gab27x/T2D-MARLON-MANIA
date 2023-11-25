package com.example.marlonmania.model;

import com.example.marlonmania.MainApplication;
import com.example.marlonmania.exceptions.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private String nickName;

    private ArrayList<String> path;

    private int numOfPipes;

    private String f;

    private String d;

    private ListGraph<String> listGraph;
/*    private MatrixGraph<String> matrixGraph;*/


    public Game(String nickName){
        this.nickName = nickName;
        listGraph = new ListGraph<>(false,false,false);
        this.numOfPipes = 0;
        this.path = new ArrayList<>(); //camino de pipes

        init();


    }




/*    public void init(){
        this.f = "1,1";
        this.d = "7,1";
        listGraph.obtainVertex(listGraph.searchVertexIndex(f)).setState(State.START);
        listGraph.obtainVertex(listGraph.searchVertexIndex(d)).setState(State.END);
        for(int x = 2; x < 7; x++){
            addPipe(x +","+ 1 ,State.HORIZONTAL);
        }


    }*/

    public void init(){
        initGrafo();
/*
        int fx = ThreadLocalRandom.current().nextInt(8);
        int fy = ThreadLocalRandom.current().nextInt(4);


        int dx = ThreadLocalRandom.current().nextInt(8);
        int dy = ThreadLocalRandom.current().nextInt(5, 8);


        this.d = dx + "," + dy;
        this.f = fx + "," + fy;*/
        this.d = "3,5";
        this.f = "7,5";

        listGraph.obtainVertex(listGraph.searchVertexIndex(f)).setState(State.START); //F
        listGraph.obtainVertex(listGraph.searchVertexIndex(d)).setState(State.END); //D

        path.add(this.f);





    }



    public void addPipe(String vertex, State newSate){
        if(!vertex.equals(f) && !vertex.equals(d)){



            listGraph.obtainVertex(listGraph.searchVertexIndex(vertex)).setState(newSate);

            System.out.println("Funciona: " + vertex);


            ListVertex<String> temporalVertex =  listGraph.obtainVertex(listGraph.searchVertexIndex(vertex));
            ListVertex<String> D =  listGraph.obtainVertex(listGraph.searchVertexIndex(this.d));
            ListVertex<String> F =  listGraph.obtainVertex(listGraph.searchVertexIndex(this.f));


            int posY= temporalVertex.getPosY();
            int posX= temporalVertex.getPosX();


            if ( !path.contains(vertex) && (posY+1==D.getPosY())  || (posY-1==D.getPosY()) || (posX+1==D.getPosX()) || (posX-1==D.getPosX())) {
                System.out.println("CONDICION FINAL"+path.size());
                path.add(vertex);
                this.numOfPipes ++;
                path.add(this.d);
                simulate();
            }else if(!path.contains(vertex)){
                System.out.println("CONDICION INICIAL"+path.size());
                path.add(vertex);
                this.numOfPipes ++;
            }
            path.removeIf( v-> v.equals(vertex) && listGraph.obtainVertex(listGraph.searchVertexIndex(vertex)).getState().equals(State.EMPTY));

        }


    }



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


    public void simulate()  {

        if (path.get(path.size()-1)!=this.d){
            System.out.println("FALSE");
            path= new ArrayList<>();
            path.add(this.f);
            this.numOfPipes=0;
        }else{
            System.out.println("path " + path.size() );
            for (String v : path) {
                System.out.print( v + " ");

            }

            try {
                if(this.listGraph.DFSVALIDATOR(path)){
                    System.out.println("TRUE");

                }
            } catch (VertexNotFoundException | VertexNotAchievableException e) {
                throw new RuntimeException(e);
            }


        }

    }
}














