package model;

import java.util.*;

import exceptions.*;


public class MatrixGraph<T> implements IGraph<T> {
    private final MatrixVertex<T>[] vertices;
    private final int[][] matrix;
    private final boolean isDirected;
    private final int[][] DIRS = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};


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
    public void addEdge(
            T start, T end, String id, int weight
    ) throws VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
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


    //DFS normal para recorrer todo el arbol
    @Override
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

    @Override
    public void DFS(T[] vertexes) throws VertexNotFoundException, VertexNotAchievableException {
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<MatrixVertex<T>> temps = new ArrayList<>();
        ArrayList<MatrixVertex<T>> sub_graph = new ArrayList<>();
        for (T vertex : vertexes) {
            indexes.add(searchVertexIndex(vertex));
        }

        for (int i = 0; i < vertices.length; i++) {
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
        dfsSimplified(sub_graph);
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
            if (!neighbor.isVisited() && vertex.getState().checkConnection(neighbor.getState())) {
                if (!depthFirstSearchRecursive(neighbor, subGraph)) {
                    return false;
                }
            }
        }

        return true;
    }


    public MatrixVertex<T>[] getVertices() {
        return vertices;
    }
}
