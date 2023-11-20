package ui;
import exceptions.*;
import model.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class Main {

    public Main(){

    }


    public static void main(String[] args) throws VertexNotAchievableException, VertexNotFoundException {

        Main m = new Main();
        try {
            try {
                m.test1();
/*                m.test3();*/
            } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException e) {
                throw new RuntimeException(e);
            }
        } catch (VertexAlreadyAddedException e) {
            throw new RuntimeException(e);
        }


/*        try {
            try {
                m.test2();
            } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException e) {
                throw new RuntimeException(e);
            }
        } catch (VertexAlreadyAddedException e) {
            throw new RuntimeException(e);
        }*/

  /*      try{


            m.generateMethod();

        }catch (Exception e){
            System.err.println("MAL");
        }
        System.out.println("\n\n\n");*/
        m.generateMethod2();

    }
    // TESTING

    void test1() throws VertexAlreadyAddedException, VertexNotAchievableException, VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        ListGraph<String> graph = new ListGraph<>(false, false, false);

        graph.addVertex("A");
        graph.obtainVertex(graph.searchVertexIndex("A")).setState(State.START);
        graph.obtainVertex(graph.searchVertexIndex("A")).setPosX(0);

        graph.obtainVertex(graph.searchVertexIndex("A")).setPosY(0);

        graph.addVertex("C");
        graph.obtainVertex(graph.searchVertexIndex("C")).setState(State.VERTICAL);
        graph.obtainVertex(graph.searchVertexIndex("C")).setPosX(0);  // <-- Corregir aquí
        graph.obtainVertex(graph.searchVertexIndex("C")).setPosY(1);

        graph.addVertex("D");
        graph.obtainVertex(graph.searchVertexIndex("D")).setState(State.END);
        graph.obtainVertex(graph.searchVertexIndex("D")).setPosX(0);
        graph.obtainVertex(graph.searchVertexIndex("D")).setPosY(2);

        // Asegurarse de que las conexiones reflejen la disposición deseada
        graph.addEdge("A", "C", "AC", 1);
        graph.addEdge("C", "D", "CD", 1);

        System.out.println("PRUEBA" + graph.toString());


        String[] simulate = new String[3];
        simulate[0] = "A";
        simulate[1] = "C";
        simulate[2] = "D";

        System.out.println(  graph.obtainVertex(graph.searchVertexIndex(simulate[0])).getPosY());
        System.out.println(  graph.obtainVertex(graph.searchVertexIndex(simulate[1])).getPosY());
        System.out.println(  graph.obtainVertex(graph.searchVertexIndex(simulate[2])).getPosY());



        try {
            System.out.println("SIMULATE: " + graph.DFSVALIDATOR(simulate));
        } catch (VertexNotFoundException | VertexNotAchievableException e) {
            e.printStackTrace();
        }
    }







    void test2() throws VertexAlreadyAddedException, LoopsNotAllowedException, MultipleEdgesNotAllowedException, VertexNotFoundException, VertexNotAchievableException {
        ListGraph<String> graphList = new ListGraph<>(false,true,false);
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addEdge("A","B","A-B",1);
        graphList.addEdge("A","C","A-C",2);
        graphList.addEdge("C","D","C-D",2);
        graphList.addEdge("B","D","C-D",2);


        System.out.println("LIST"+graphList.dijkstra("A","D"));
       // System.out.println(graphList.toString());


    }

    void test3() throws VertexAlreadyAddedException, VertexNotAchievableException, VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        MatrixGraph<String> graph = new MatrixGraph<>(false,4);

        graph.addVertex("1,2");
        graph.addVertex("1,3");
        graph.addVertex("1,4");
        graph.addVertex("1,5");
        graph.addEdge("1,2","1,3","A-B",1);
        graph.addEdge("1,2","1,4","A-C",12);
        graph.addEdge("1,4","1,5","C-D",8);
        /*        graph.addEdge("B","D","C-D",2);*/


        //System.out.println(graph.dijkstra("1,2","1,5"));
        graph.print();



    }

    void generateMethod() {
        MatrixGraph<String> graph = new MatrixGraph<>(false, 64);
        System.out.println(graph.getNumOfVertices());

        for (int i = 0; i < 8; i++) {
            try {
                graph.addVertex((i + "," + 0));
            } catch (VertexAlreadyAddedException e) {
                System.err.println("Error al agregar vértice: " + e.getMessage());
            }

            for (int j = 1; j < 8; j++) {
                try {
                    graph.addVertex((i + "," + j));
                } catch (VertexAlreadyAddedException e) {
                    System.err.println("Error al agregar vértice: " + e.getMessage());
                }

                try {
                    graph.addEdge(i + "," + (j - 1), i + "," + j, "none" + i, 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista: " + e.getMessage());
                }
            }
        }

        // Agregar conexiones entre filas consecutivas
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    graph.addEdge(i + "," + j, (i + 1) + "," + j, "none_between_rows", 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista entre filas: " + e.getMessage());
                }
            }
        }

        graph.print();
    }

    void generateMethod2() {
        ListGraph<String> graph = new ListGraph<>(false, false, false);

        for (int i = 0; i < 8; i++) {
            try {
                graph.addVertex(i + "," + 0);
            } catch (VertexAlreadyAddedException e) {
                System.err.println("Error al agregar vértice: " + e.getMessage());
            }

            for (int j = 1; j < 8; j++) {
                try {
                    graph.addVertex((i) + "," + j);
                } catch (VertexAlreadyAddedException e) {
                    System.err.println("Error al agregar vértice: " + e.getMessage());
                }

                try {
                    String edgeId = String.format("%s -> %s", (i) + "," + (j - 1), (i) + "," + j);
                    graph.addEdge((i + "," + (j - 1)), (i + "," + j), edgeId, 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista: " + e.getMessage());
                }
            }
        }

        // Agregar conexiones entre filas consecutivas
        /*for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    String edgeId = String.format("%s -> %s", String.valueOf(i) + "," + j, String.valueOf(i + 1) + "," + j);
                    graph.addEdge(String.valueOf(i) + "," + j, String.valueOf(i + 1) + "," + j, edgeId, 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista entre filas: " + e.getMessage());
                }
            }
        }*/

        // Mostrar la representación del grafo
        System.out.println(graph);
    }

/*

    void generateMethod2() {
        ListGraph<String> graph = new ListGraph<>(false, false, false);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    graph.addVertex(String.format("%d,%d", i, j));
                } catch (VertexAlreadyAddedException e) {
                    System.err.println("Error al agregar vértice: " + e.getMessage());
                }
            }
        }

        // Agregar aristas horizontales
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                try {
                    String edgeId = String.format("%d -> %d", i, j);
                    graph.addEdge(String.format("%d,%d", i, j-1), String.format("%d,%d", i, j ), edgeId, 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista: " + e.getMessage());
                }
            }
        }

        // Agregar aristas verticales
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    String edgeId = String.format("%d -> %d", i, j);
                    graph.addEdge(String.format("%d,%d", i, j), String.format("%d,%d", i + 1, j), edgeId, 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista: " + e.getMessage());
                }
            }
        }

        // Mostrar la representación del grafo
        System.out.println(graph);
    }

*/
    /*
    void generateMethod2() {
        ListGraph<String> graph = new ListGraph<>(false, false, false);

        for (int i = 0; i < 8; i++) {
            try {
                graph.addVertex(String.valueOf(i) + "," + 0);
            } catch (VertexAlreadyAddedException e) {
                System.err.println("Error al agregar vértice: " + e.getMessage());
            }

            for (int j = 1; j < 8; j++) {
                try {
                    graph.addVertex(String.valueOf(i) + "," + j);
                } catch (VertexAlreadyAddedException e) {
                    System.err.println("Error al agregar vértice: " + e.getMessage());
                }

                try {
                    String edgeId = String.format("%d -> %d", i, j);
                    graph.addEdge(String.valueOf(i) + "," + (j - 1), String.valueOf(i) + "," + j, edgeId, 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista: " + e.getMessage());
                }
            }
        }

        // Agregar conexiones entre filas consecutivas
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    String edgeId = String.format("%d -> %d", i, j);
                    String nextEdgeId = String.format("%d -> %d", i + 1, j);
                    graph.addEdge(String.valueOf(i) + "," + j, String.valueOf(i + 1) + "," + j, nextEdgeId, 1);
                } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException | VertexNotFoundException e) {
                    System.err.println("Error al agregar arista entre filas: " + e.getMessage());
                }
            }
        }

        // Mostrar la representación del grafo
        System.out.println(graph);
    }
*/


}
