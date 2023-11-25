package com.example.marlonmania.model;

import  com.example.marlonmania.exceptions.*;

import java.util.*;

public class ListGraph<T> implements IGraph<T> {
    private final boolean isGuided;
    private final boolean isMultiple;
    private final boolean allowsLoop;
    private final ArrayList<ListVertex<T>> list;


    public ListGraph(boolean isGuided, boolean isMultiple, boolean allowsLoop) {
        list = new ArrayList<>();
        this.isGuided = isGuided;
        this.isMultiple = isMultiple;
        this.allowsLoop = allowsLoop;
    }


    @Override
    public void addVertex(T vertex,int posX,int posY) throws VertexAlreadyAddedException {
        if (searchVertexIndex(vertex) == -1) {
           ListVertex<T> newVertex= new ListVertex<>(vertex,posX,posY);
           newVertex.setState(State.EMPTY);
            list.add(newVertex);
        } else {
            System.err.println("ALGO RARO");
            /*throw new VertexAlreadyAddedException("Vertex found: " + vertex);*/
        }
    }

    @Override
    public void addEdge(T start, T end, String id, int weight)
            throws VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        int startVertex = searchVertexIndex(start);
        int endVertex = searchVertexIndex(end);

        // Verifica si los vértices existen
        if (startVertex == -1) {
            throw new VertexNotFoundException("Error. Start vertex not found: " + start);
        }

        if (endVertex == -1) {
            throw new VertexNotFoundException("Error. End vertex not found: " + end);
        }

        // Verifica si hay bucles
        if (startVertex == endVertex && !allowsLoop) {
            throw new LoopsNotAllowedException("Error. Loops not allowed.");
        }

        // Verifica si ya existe la arista
        if (searchEdgeIndex(list.get(startVertex), list.get(endVertex), id) != -1 && !isMultiple) {
            throw new MultipleEdgesNotAllowedException("Error. Multiple edges between vertex not allowed.");
        }

        // Añade la arista
        if (!isGuided) {
            // Si no es dirigido, añade la arista en ambas direcciones
            list.get(endVertex).getEdges().add(new ListEdge<>(list.get(endVertex), list.get(startVertex), id, weight));
        }
        list.get(startVertex).getEdges().add(new ListEdge<>(list.get(startVertex), list.get(endVertex), id, weight));
    }





    @Override
    public boolean searchEdge(T start, T end, String id) throws VertexNotFoundException {
        if (searchVertexIndex(start) == -1 || searchVertexIndex(end) == -1) {
            throw new VertexNotFoundException("Error. One vertex not found.");
        }
        int startIndex = searchVertexIndex(start);
        for (int i = 0; i < list.get(startIndex).getEdges().size(); i++) {
            ListEdge<T> edge = list.get(startIndex).getEdges().get(i);
            if (edge.getRightVertex().getValue() == end && edge.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void deleteVertex(T vertex) throws VertexNotFoundException {
        int vertexIndex = searchVertexIndex(vertex);
        if (vertexIndex == -1) {
            throw new VertexNotFoundException("Error. Vertex not found: " + vertex);
        }
        for (ListVertex<T> tVertex : list) {
            for (int j = 0; j < tVertex.getEdges().size(); j++) {
                if (tVertex.getEdges().get(j).getLeftVertex() == list.get(vertexIndex) || tVertex.getEdges().get(j).getRightVertex() == list.get(vertexIndex)) {
                    tVertex.getEdges().remove(j);
                }
            }
        }
        list.remove(vertexIndex);
    }


    @Override
    public void deleteEdge(T start, T end, String id) throws EdgeNotFoundException, VertexNotFoundException {
        int startIndex = searchVertexIndex(start);
        int endIndex = searchVertexIndex(end);
        if (startIndex == -1 || endIndex == -1) {
            throw new VertexNotFoundException("Error. One vertex not found.");
        }
        if (searchEdgeIndex(list.get(startIndex), list.get(endIndex), id) == -1) {
            throw new EdgeNotFoundException("Error. Edge not found: " + start + " -> " + end + " (" + id + ")");
        }
        if (!isGuided) {
            list.get(endIndex).getEdges().remove(searchEdgeIndex(list.get(endIndex), list.get(startIndex), id));
        }
        list.get(startIndex).getEdges().remove(searchEdgeIndex(list.get(startIndex), list.get(endIndex), id));
    }


    public int searchVertexIndex(T vertex) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue().equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    public ListVertex<T> obtainVertex(int index){
        return list.get(index);
    }


    private int searchEdgeIndex(ListVertex<T> start, ListVertex<T> end, String id) {
        for (int i = 0; i < start.getEdges().size(); i++) {
            if (start.getEdges().get(i).getLeftVertex() == start && start.getEdges().get(i).getRightVertex() == end && start.getEdges().get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }


    public boolean checkShortPath(
            ListVertex<T>[] subgraph
    ) throws VertexNotAchievableException, VertexNotFoundException {
        var res = dijkstra(subgraph[0].getValue(), subgraph[subgraph.length - 1].getValue());
        int subgraphValue = travelSubgraph(subgraph[0]);

        return res == subgraphValue;
    }

    public int travelSubgraph(ListVertex<T> node) {
        if (node == null) return 0;

        for (ListEdge<T> edge : node.getEdges()) {
            return node.getDistance() + edge.getWeight() + travelSubgraph(edge.getRightVertex());
        }

        return node.getDistance();
    }


    @Override
    public int dijkstra(T startVertex, T endVertex) throws VertexNotFoundException, VertexNotAchievableException {
        int startVertexIndex = searchVertexIndex(startVertex);
        int endVertexIndex = searchVertexIndex(endVertex);

        if (startVertexIndex == -1 || endVertexIndex == -1) {
            throw new VertexNotFoundException("Start or end vertex not found.");
        }

        PriorityQueue<ListVertex<T>> q = new PriorityQueue<>(Comparator.comparingInt(ListVertex::getDistance));

        for (ListVertex<T> vertex : list) {
            vertex.setDistance(Integer.MAX_VALUE);
            vertex.setFather(null);
            q.add(vertex);
        }

        list.get(startVertexIndex).setDistance(0);

        while (!q.isEmpty()) {
            ListVertex<T> u = q.poll();
            if (u == null) {
                throw new VertexNotAchievableException("No path found between the specified vertices.");
            }

            for (ListEdge<T> edge : u.getEdges()) {
                ListVertex<T> v = edge.getRightVertex();
                int alt = u.getDistance() + edge.getWeight();
                if (alt < v.getDistance()) {
                    v.setDistance(alt);
                    v.setFather(u);
                    q.remove(v);
                    q.add(v);
                }
            }
        }

        ListVertex<T> currentVertex = list.get(endVertexIndex);
        if (currentVertex.getDistance() == Integer.MAX_VALUE) {
            throw new VertexNotAchievableException("End Vertex Not Achievable");
        }



        return currentVertex.getDistance();
    }




    @Override
    public void DFS(T startVertex) throws VertexNotFoundException, VertexNotAchievableException {
        int startIndex = searchVertexIndex(startVertex);

        if (startIndex == -1) {
            throw new VertexNotFoundException("Start vertex not found.");
        }

        Stack<ListVertex<T>> stack = new Stack<>();
        stack.push(list.get(startIndex));

        while (!stack.isEmpty()) {
            ListVertex<T> currentVertex = stack.pop();

            if (!currentVertex.isVisited()) {
                currentVertex.setVisited(true);

                for (ListEdge<T> edge : currentVertex.getEdges()) {
                    ListVertex<T> neighbor = edge.getRightVertex();
                    if (!neighbor.isVisited()) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }

    private ListVertex<T> getVertexWithMinDistance(List<ListVertex<T>> vertices) {
        ListVertex<T> minVertex = null;
        int minDistance = Integer.MAX_VALUE;
        for (ListVertex<T> vertex : vertices) {
            if (vertex.getDistance() < minDistance) {
                minVertex = vertex;
                minDistance = vertex.getDistance();
            }
        }
        return minVertex;
    }

    @Override
    public boolean DFSVALIDATOR(ArrayList<T> vertexes) throws VertexNotFoundException, VertexNotAchievableException {
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<ListVertex<T>> temps = new ArrayList<>();
        ArrayList<ListVertex<T>> sub_graph = new ArrayList<>();
        for (T vertex : vertexes) {
            indexes.add(searchVertexIndex(vertex));
        }

        for (Integer index : indexes) {
            if (index == -1) {
                throw new VertexNotFoundException("Start vertex not found.");
            }

            temps.add(list.get(index));
        }

        sub_graph.add(temps.get(0).clone());
        sub_graph.get(0).setVisited(false);

        for (int i = 1; i < temps.size(); i++) {
            sub_graph.add(temps.get(i).clone());
            sub_graph.get(i).setVisited(false);
            sub_graph.get(i - 1).getEdges().add(new ListEdge<>(sub_graph.get(i - 1), sub_graph.get(i), i + "", 0));
        }


        return depthFirstSearchRecursive(sub_graph.get(0));
    }

    //me permite saber si el subgrafo tiene correctamente las conexciones, el metodo
    //de validar las correcciones se encuentra en el enum
    private boolean depthFirstSearchRecursive(ListVertex<T> vertex) {

        vertex.setVisited(true); // Marca el vértice actual como visitado
        //System.out.println(vertex.getValue());


        // Recorre los vértices adyacentes no visitados
        for (ListEdge<T> edge : vertex.getEdges()) {
            ListVertex<T> neighbor = edge.getRightVertex();
            System.out.println("ACTUAL"+vertex.getValue());
            System.out.println("VECINO"+neighbor.getValue());
            if (!neighbor.isVisited()) {

                if (vertex.getState().checkConnection(neighbor.getState(),vertex.getPosX(),vertex.getPosY(),neighbor.getPosX(),neighbor.getPosY())) {
                    depthFirstSearchRecursive(neighbor);
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (ListVertex<T> u : list) {
            int limit = 6;
            String valueStr = String.format("%" + limit + "s", u.getValue());
            ans.append(String.format("%s -> { ", valueStr));
            for (ListVertex<T> v : u.getEdges().stream().map(ListEdge::getRightVertex).toList()) {
                ans.append(String.format("%s, ", v.getValue()));
            }
            if (!u.getEdges().isEmpty()) ans.replace(ans.length() - 2, ans.length(), "");
            ans.append(" }\n");
        }
        return ans.toString();

    }




    public ArrayList<ListVertex<T>> getList() {
        return list;
    }
}

