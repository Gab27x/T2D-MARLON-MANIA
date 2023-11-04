package model;

import java.util.*;

import exceptions.*;


public class MatrixGraph<T> implements IGraph<T> {
    private final MatrixVertex<T>[] vertices;
    private final int[][] matrix;
    private final boolean isDirected;


    public MatrixGraph(boolean isDirected, int vertices) {
        this.isDirected = isDirected;
        this.vertices = new MatrixVertex[vertices];
        this.matrix = new int[vertices][vertices];
    }


    public void addVertex(T value) throws VertexAlreadyAddedException {
        boolean stop = false;
        if (searchVertexIndex(value) != -1)
            throw new VertexAlreadyAddedException("There is a vertex with the same value");
        for (int i = 0; i < vertices.length && !stop; i++) {
            if (vertices[i] == null) {
                vertices[i] = new MatrixVertex<>(value);
                stop = true;
            }
        }
    }


    @Override
    public void addEdge(T start, T end, String id, int weight) throws VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        int vertex1 = searchVertexIndex(start);
        int vertex2 = searchVertexIndex(end);

        if (vertex1 == vertex2) throw new LoopsNotAllowedException("Loops are not allowed");
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


    @Override
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


    @Override
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

    @Override
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

    @Override
    public Map<T, T> dijkstra(T startVertex, T endVertex) throws VertexNotFoundException, VertexNotAchievableException {
        int startVertexIndex = searchVertexIndex(startVertex);
        int endVertexIndex = searchVertexIndex(endVertex);

        Map<T, T> chain = new HashMap<>();

        PriorityQueue<MatrixVertex<T>> q = new PriorityQueue<>(Comparator.comparingInt(MatrixVertex::getDistance));

        for (int i = 0; i < vertices.length; i++) {
            if (i != startVertexIndex) {
                vertices[i].setDistance(Integer.MAX_VALUE);
            } else {
                vertices[i].setDistance(0);
            }
            vertices[i].setFather(null);
            q.add(vertices[i]);
        }

        while (!q.isEmpty() && q.peek() != vertices[endVertexIndex]) {
            MatrixVertex<T> u = q.poll();
            int uIndex = searchVertexIndex(u.getValue());
            for (int i = 0; i < vertices.length; i++) {
                if (matrix[uIndex][i] != 0 && i != uIndex) {
                    int alt = u.getDistance() + matrix[uIndex][i];
                    if (alt < vertices[i].getDistance()) {
                        vertices[i].setDistance(alt);
                        vertices[i].setFather(u);

                        chain.put(vertices[i].getValue(), u.getValue());

                        q.add(vertices[i]);
                    }
                }
            }
        }
        return chain;
    }


}
