package com.example.marlonmania.model;


import  com.example.marlonmania.exceptions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;


public class MatrixGraph<T> /*implements IGraph<T>*/ {

    private final MatrixVertex<T>[] vertices;
    private final int[][] matrix;
    private final boolean isDirected;

    public MatrixGraph(boolean isDirected, int vertices) {
        this.isDirected = isDirected;
        this.vertices = new MatrixVertex [vertices];
        this.matrix = new int[vertices][vertices];
    }

    public int getNumOfVertices(){
        return vertices.length;
    }

    public void addVertex(T value, int posX , int posY) throws VertexAlreadyAddedException {
        boolean stop = false;
        if (searchVertexIndex(value) != -1)
            throw new VertexAlreadyAddedException("There is a vertex with the same value");
        for (int i = 0; i < vertices.length && !stop; i++) {
            if (vertices[i] == null) {
                vertices[i] = new MatrixVertex<>(value,  posX,  posY);
                vertices[i].setState(State.EMPTY);
                stop = true;
            }
        }
    }


/*    @Override*/
    public void addEdge(
            T start, T end, String id, int weight
    ) throws VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        int vertex1 = searchVertexIndex(start);
        int vertex2 = searchVertexIndex(end);

        if (vertex1 == vertex2) {
            System.out.println(start + " -> " + end);
            throw new LoopsNotAllowedException("Loops are not allowed");

        }

        if (vertex1 != -1 && vertex2 != -1) {
            if (matrix[vertex1][vertex2] != 0)
                throw new MultipleEdgesNotAllowedException("Multiples edges are not allowed");
            if (isDirected) {
                matrix[vertex1][vertex2] = weight;
            } else {
                matrix[vertex1][vertex2] = weight;
                matrix[vertex2][vertex1] = weight;
            }
        } else {
            String vError = vertex1 == -1 ? "vertex1" : "vertex2";
            throw new VertexNotFoundException("Cannot find the vertex " + vError);
        }
    }


/*    @Override*/
    public void deleteVertex(T value) throws VertexNotFoundException {
        int oldVerPos = searchVertexIndex(value);
        if (oldVerPos == -1) throw new VertexNotFoundException("There's no such vertex in the graph");
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].getValue().equals(value)) {
                vertices[i] = null;
                break;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            matrix[oldVerPos][i] = 0;
            matrix[i][oldVerPos] = 0;
        }
    }


/*    @Override*/
    public void deleteEdge(T start, T end, String id) throws EdgeNotFoundException, VertexNotFoundException {
        int posVal1 = searchVertexIndex(start);
        int posVal2 = searchVertexIndex(end);

        boolean hasEdge = searchEdge(start, end, id);

        if (hasEdge) {
            matrix[posVal1][posVal2] = 0;
            matrix[posVal2][posVal1] = 0;
        } else {
            throw new EdgeNotFoundException("The edge was not found");
        }
    }


    public int searchVertexIndex(T value) {
        for (int i = 0; i < matrix.length; i++) {
            if (vertices[i] != null && vertices[i].getValue().equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public MatrixVertex<T> obtainVertex(int index){
        return vertices[index];

    }

    public int getEdgeWeight(MatrixVertex<T> startVertex, MatrixVertex<T> endVertex) {
        int startIndex = searchVertexIndex(startVertex.getValue());
        int endIndex = searchVertexIndex(endVertex.getValue());

        if (startIndex != -1 && endIndex != -1) {
            return matrix[startIndex][endIndex];
        } else {
            // Manejar el caso donde uno de los vértices no se encuentra
            return 0; // O podrías lanzar una excepción, dependiendo de tus necesidades
        }
    }


    /*    @Override*/
    public boolean searchEdge(T start, T end, String id) throws VertexNotFoundException {
        int vertex1 = searchVertexIndex(start);
        int vertex2 = searchVertexIndex(end);
        boolean result = false;

        String error = vertex1 == -1 ? "start" : "end";
        if (vertex1 == -1 || vertex2 == -1) throw new VertexNotFoundException("The " + error + " vertex was not found");

        if (isDirected) {
            if (matrix[vertex1][vertex2] != 0) {
                result = true;
            }
        } else {
            if (matrix[vertex1][vertex2] != 0 && matrix[vertex2][vertex1] != 0) {
                result = true;
            }
        }

        return result;
    }

    public boolean checkShortPath(
            MatrixVertex<T>[] subgraph
    ) throws VertexNotAchievableException, VertexNotFoundException {
        var res = dijkstra(subgraph[0].getValue(), subgraph[subgraph.length - 1].getValue());

        int first = searchVertexIndex(subgraph[0].getValue());
        int second = searchVertexIndex(subgraph[1].getValue());
        int acu = matrix[first][second] + subgraph[0].getDistance();
        for (int i = 1; i < subgraph.length; i++) {
            int last = searchVertexIndex(subgraph[i - 1].getValue());
            int act = searchVertexIndex(subgraph[i].getValue());
            acu += matrix[act][last] + subgraph[i - 1].getDistance();
        }

        return res == acu;
    }

/*    @Override*/
    public int dijkstra(T startVertex, T endVertex) throws VertexNotFoundException, VertexNotAchievableException {
        int startVertexIndex = searchVertexIndex(startVertex);
        int endVertexIndex = searchVertexIndex(endVertex);

        if (startVertexIndex == -1 || endVertexIndex == -1) {
            throw new VertexNotFoundException("Vertex was not found");
        }

        PriorityQueue<MatrixVertex<T>> q = new PriorityQueue<>(Comparator.comparingInt(MatrixVertex::getDistance));

        for (MatrixVertex<T> vertex : vertices) {
            if (vertex != null) {
                vertex.setDistance(Integer.MAX_VALUE);
                vertex.setFather(null);
                vertex.setVisited(false);
            }
        }

        vertices[startVertexIndex].setDistance(0);
        q.add(vertices[startVertexIndex]);

        while (!q.isEmpty()) {
            MatrixVertex<T> u = q.poll();

            if (u.isVisited()) {
                continue;
            }

            int uIndex = searchVertexIndex(u.getValue());

            for (int i = 0; i < vertices.length; i++) {
                if (matrix[uIndex][i] != 0 && !vertices[i].isVisited()) {
                    int alt = u.getDistance() + matrix[uIndex][i];

                    if (alt < vertices[i].getDistance()) {
                        vertices[i].setDistance(alt);
                        vertices[i].setFather(u);

                        q.add(vertices[i]);
                    }
                }
            }

            u.setVisited(true);
        }

        if (vertices[endVertexIndex].getDistance() == Integer.MAX_VALUE) {
            throw new VertexNotAchievableException("The end vertex is not reachable from the start vertex.");
        }

        return vertices[endVertexIndex].getDistance();
    }




    //DFS normal para recorrer todo el arbol
/*    @Override*/
    public void DFS(T startVertex) throws VertexNotFoundException, VertexNotAchievableException {
        Stack<MatrixVertex<T>> stack = new Stack<>();

        int startIndex = searchVertexIndex(startVertex);

        if (startIndex == -1) {
            throw new VertexNotFoundException("Vertex was not found");
        }

        stack.push(vertices[startIndex]);
        vertices[startIndex].setVisited(true);

        while (!stack.isEmpty()) {
            MatrixVertex<T> vertex = stack.pop();
            for (int i = 0; i < vertices.length; i++) {
                int currentIndex = searchVertexIndex(vertex.getValue());
                if (!vertices[i].isVisited() && matrix[currentIndex][i] != 0) {
                    stack.push(vertices[i]);
                    vertices[i].setVisited(true);
                }
            }
        }
    }

    //DFS para asegurarme que el camino entre los grafos es correcto

/*    @Override*/
    public boolean DFSVALIDATOR(T[] vertexes) throws VertexNotFoundException, VertexNotAchievableException {
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<MatrixVertex<T>> temps = new ArrayList<>();
        ArrayList<MatrixVertex<T>> sub_graph = new ArrayList<>();
        for (T vertex : vertexes) {
            indexes.add(searchVertexIndex(vertex));
        }

        for (int i = 0; i < vertices.length-1; i++) {
            if (indexes.get(i) == -1) {
                throw new VertexNotFoundException("Start vertex not found.");
            }

            temps.add(vertices[indexes.get(i)]);
        }

        sub_graph.add(temps.get(0).clone());
        sub_graph.get(0).setVisited(false);

        for (int i = 1; i < temps.size(); i++) {
            sub_graph.add(temps.get(i).clone());
            sub_graph.get(i).setVisited(false);
        }
        return dfsSimplified(sub_graph);
    }


    private boolean dfsSimplified(ArrayList<MatrixVertex<T>> subGraph) {
        for (MatrixVertex<T> vertex : subGraph) {
            if (!vertex.isVisited()) {
                if (!depthFirstSearchRecursive(vertex, subGraph)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean depthFirstSearchRecursive(MatrixVertex<T> vertex, ArrayList<MatrixVertex<T>> subGraph) {
        vertex.setVisited(true);

        for (MatrixVertex<T> neighbor : subGraph) {
            if (!neighbor.isVisited() && vertex.getState().checkConnection(neighbor.getState(),vertex.getPosX(),vertex.getPosY(),neighbor.getPosX(),neighbor.getPosY())) {
                if (!depthFirstSearchRecursive(neighbor, subGraph)) {
                    return false;
                }
            }
        }

        return true;
    }

    public ArrayList<Integer> getEdgeWeightsList(ArrayList<MatrixVertex<T>> vertexList) {
        ArrayList<Integer> edgeWeights = new ArrayList<>();

        for (int i = 0; i < vertexList.size() - 1; i++) {
            MatrixVertex<T> currentVertex = vertexList.get(i);
            MatrixVertex<T> nextVertex = vertexList.get(i + 1);

            int currentVertexIndex = searchVertexIndex(currentVertex.getValue());
            int nextVertexIndex = searchVertexIndex(nextVertex.getValue());

            if (currentVertexIndex != -1 && nextVertexIndex != -1) {
                int weight = matrix[currentVertexIndex][nextVertexIndex];
                edgeWeights.add(weight);
            } else {
                System.out.println("One of the vertices is not found in the graph.");
            }
        }

        return edgeWeights;
    }

    public int subGraphDistance(ArrayList<Integer> subEdges){
        return subEdges.stream().mapToInt(Integer::valueOf).sum();// no borrar, es un santo grial; SI LO BORRAS TODO SE CAE; POSDATA, EL QUE LO BORRE MERECE EL PURGATORIO
    }
/*
    public void print() {
        String msg = "      ";
        for (int i = 0; i < vertices.length; i++){
            msg += vertices[i].getValue() + "  ";
        }
        System.out.println(msg);

        for (int i = 0; i < vertices.length; i++) {
            System.out.print(vertices[i].getValue() + " [ ");

            for (int j = 0; j < vertices.length; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.print(" ]");
            System.out.println();

        }
    }
*/


    public void print(){

        StringBuilder msg = new StringBuilder("      ");
        for (MatrixVertex<T> vertex : vertices) {
            msg.append(vertex.getValue()).append("  ");
        }
        System.out.println(msg.toString());

        for (int i = 0; i < vertices.length; i++) {
            System.out.printf("%-4s [ ", vertices[i].getValue());

            for (int j = 0; j < vertices.length; j++) {
                System.out.printf("%-4d ", matrix[i][j]);
            }

            System.out.print("]");
            System.out.println();
        }

    }
    public MatrixVertex<T>[] getVertices() {
        return vertices;
    }
}

