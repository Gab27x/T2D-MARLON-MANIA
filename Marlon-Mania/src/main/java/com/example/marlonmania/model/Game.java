package com.example.marlonmania.model;

import com.example.marlonmania.exceptions.*;

import java.util.ArrayList;

public class Game {

    private String nickName;
    private ListGraph<String> listGraph;
    private MatrixGraph<String> matrixGraph;


    public Game(){
        this.nickName = "Hola";
        listGraph = new ListGraph<>(false,false,false);
        /*matrixGraph = new MatrixGraph<>(false,64);*/
    }



    public void initGrafo(){
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                try {
                    listGraph.addVertex(x + "," + y);

                    

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
}














