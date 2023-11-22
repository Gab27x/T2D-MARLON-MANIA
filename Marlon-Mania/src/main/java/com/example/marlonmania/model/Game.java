package com.example.marlonmania.model;

import com.example.marlonmania.exceptions.*;

import java.util.ArrayList;

public class Game {

    private String nickName;

    private int numOfPipes;

    private String f;

    private String d;

    private ListGraph<String> listGraph;
/*    private MatrixGraph<String> matrixGraph;*/


    public Game(){
        this.nickName = "Hola";
        listGraph = new ListGraph<>(false,false,false);
        this.numOfPipes = 0;


    }

    public void init(){
        this.f = "1,1";
        this.d = "7,1";
        listGraph.obtainVertex(listGraph.searchVertexIndex(f)).setState(State.START);
        listGraph.obtainVertex(listGraph.searchVertexIndex(d)).setState(State.END);
        for(int x = 2; x < 7; x++){
            addPipe(x +","+ 1 ,State.HORIZONTAL);
        }


    }






    public void addPipe(String vertex, State newSate){
        if(!vertex.equals(f) && !vertex.equals(d)){
            listGraph.obtainVertex(listGraph.searchVertexIndex(vertex)).setState(newSate);
            System.out.println("Funciona");
            this.numOfPipes ++;

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

    public void initGrafoDescendente(){
        for (int x = 7; x >= 0; x--) {
            for (int y = 7; y >= 0; y--) {
                try {
                    listGraph.addVertex(x + "," + y, x, y);

                } catch (VertexAlreadyAddedException e) {
                    System.err.println("Error al agregar vértice: " + e.getMessage());
                }

                if (y < 7) {
                    try {
                        String edgeId = String.format("%s -> %s", x + "," + (y + 1), x + "," + y);
                        listGraph.addEdge(x + "," + (y + 1), x + "," + y, edgeId, 1);
                    } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                        System.err.println("Error al agregar arista: " + e.getMessage());
                    }
                }

                if (x < 7) {
                    try {
                        String edgeId = String.format("%s -> %s", (x + 1) + "," + y, x + "," + y);
                        listGraph.addEdge((x + 1) + "," + y, x + "," + y, edgeId, 1);
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

        String[] simulate = new String[8];

        simulate[0] = "2,1";
        simulate[1] = "3,1";
        simulate[2] = "4,1";
        simulate[3] = "5,1";
        simulate[4] = "6,1";
        simulate[5] = "1,1";
        simulate[6] = "7,1";
        simulate[7] = "7,7";
        try{
            System.out.println(this.listGraph.DFSVALIDATOR(simulate));

        }catch (Exception e){
            System.err.println("TOCA PEGARLE A LUIS");
        }




    }
}














