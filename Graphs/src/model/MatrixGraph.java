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
        // Busca el índice de los vértices de inicio y final en el arreglo "vertices"
        int startVertexIndex = searchVertexIndex(startVertex);
        int endVertexIndex = searchVertexIndex(endVertex);

        // Si alguno de los vértices no se encuentra en el arreglo, lanza una excepción
        if (startVertexIndex == -1 || endVertexIndex == -1) {
            throw new VertexNotFoundException("Vertex was not found");
        }

        // Crea un mapa llamado "chain" que almacenará la cadena de vértices de la solución
        Map<T, T> chain = new HashMap<>();

        // Crea una cola de prioridades "q" para almacenar los vértices por procesar
        // Los vértices se ordenarán en función de su distancia
        PriorityQueue<MatrixVertex<T>> q = new PriorityQueue<>(Comparator.comparingInt(MatrixVertex::getDistance));

        // Inicializa las distancias de todos los vértices, excepto el inicial, a un valor máximo
        for (int i = 0; i < vertices.length; i++) {
            if (i != startVertexIndex) {
                vertices[i].setDistance(Integer.MAX_VALUE); // La distancia de todos los elementos menos del inicial es la máxima
            } else {
                vertices[i].setDistance(0); // La distancia del vértice inicial es 0
            }
            vertices[i].setFather(null); // Establece el padre de cada vértice como nulo
            q.add(vertices[i]); // Agrega los vértices a la cola de prioridades para su procesamiento
        }

        // Mientras la cola de prioridades no esté vacía y el vértice final no se haya procesado
        while (!q.isEmpty() && q.peek() != vertices[endVertexIndex]) {
            // Extrae el vértice con la menor distancia (primer elemento de la cola de prioridades); recordar que estan organizadas según su distancia
            MatrixVertex<T> u = q.poll(); // Vértice temporal para realizar comparaciones
            int uIndex = searchVertexIndex(u.getValue()); // Obtiene el índice del vértice temporal en el arreglo de vértices

            // Recorre todos los vértices y sus distancias desde el vértice temporal
            for (int i = 0; i < vertices.length; i++) {
                // Si hay una conexión entre el vértice actual y el vértice i
                if (matrix[uIndex][i] != 0 && i != uIndex) {
                    int alt = u.getDistance() + matrix[uIndex][i]; // Calcula una nueva distancia potencial

                    // Si la nueva distancia es menor que la distancia almacenada en el vértice i
                    if (alt < vertices[i].getDistance()) {
                        // Actualiza la distancia y el padre del vértice i
                        vertices[i].setDistance(alt);
                        vertices[i].setFather(u);

                        // Agrega el vértice i y su padre a la cadena de la solución
                        chain.put(vertices[i].getValue(), u.getValue());

                        // Agrega el vértice i a la cola de prioridades para su procesamiento
                        q.add(vertices[i]);
                    }
                }
            }
        }

        // Si la distancia del vértice final sigue siendo el valor máximo, lanza una excepción
        if (vertices[endVertexIndex].getDistance() == Integer.MAX_VALUE) {
            throw new VertexNotAchievableException("The end vertex is not reachable from the start vertex.");
        }

        // Retorna la cadena de vértices de la solución
        return chain;
    }


    @Override
    public void DFS(T startVertex) throws VertexNotFoundException, VertexNotAchievableException {
        // Crea una stack para realizar el DFS
        Stack<MatrixVertex<T>> stack = new Stack<>();

        // Encuentra el índice del vértice de inicio en el arreglo "vertices" para trabajar con la matriz de adyacencia
        int startIndex = searchVertexIndex(startVertex);

        if (startIndex == -1) {
            // Lanza una excepción si el vértice de inicio no se encuentra en el grafo
            throw new VertexNotFoundException("Vertex was not found");
        }

        // Coloca el vértice de inicio en la pila y marca como visitado
        stack.push(vertices[startIndex]);
        vertices[startIndex].setVisited(true);

        // Comienza el recorrido DFS, utilizando una pila para almacenar los nuevos vértices
        while (!stack.isEmpty()) {
            MatrixVertex<T> vertex = stack.pop(); // Obtén el vértice superior de la pila

            //System.out.println(vertex.getValue());

            // Recorre todos los vértices adyacentes al vértice actual
            for (int i = 0; i < vertices.length; i++) {
                int currentIndex = searchVertexIndex(vertex.getValue()); // Obtiene el índice del vértice actual en el arreglo "vertices"

                // Verifica si el vértice "i" es adyacente y no ha sido visitado
                if (!vertices[i].isVisited() && matrix[currentIndex][i] != 0) {
                    stack.push(vertices[i]); // Agrega el vértice adyacente a la pila
                    vertices[i].setVisited(true); // Marca el vértice como visitado
                }
            }
        }
    }


    public MatrixVertex<T>[] getVertices() {
        return vertices;
    }
}
