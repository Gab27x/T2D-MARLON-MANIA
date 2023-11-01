package model;

import exceptions.*;

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
    public void addVertex(T vertex) throws VertexAlreadyAddedException {
        if (searchVertexIndex(vertex) == -1) {
            list.add(new ListVertex<>(vertex));
        } else {
            throw new VertexAlreadyAddedException("Vertex found: " + vertex);
        }
    }


    @Override
    public void addEdge(T start, T end, String id, int weight) throws VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        int startVertex = searchVertexIndex(start);
        int endVertex = searchVertexIndex(end);
        if (startVertex == -1 || endVertex == -1) {
            throw new VertexNotFoundException("Error. Vertex not found.");
        }
        if (startVertex == endVertex && !allowsLoop) {
            throw new LoopsNotAllowedException("Error. Loops not allowed.");
        }
        if (searchEdgeIndex(list.get(startVertex), list.get(endVertex), id) != -1 && !isMultiple) {
            throw new MultipleEdgesNotAllowedException("Error. Multiple edges between vertex not allowed.");
        }
        if (!isGuided) {
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


    private int searchVertexIndex(T vertex) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getValue() == vertex) {
                return i;
            }
        }
        return -1;
    }


    private int searchEdgeIndex(ListVertex<T> start, ListVertex<T> end, String id) {
        for (int i = 0; i < start.getEdges().size(); i++) {
            if (start.getEdges().get(i).getLeftVertex() == start && start.getEdges().get(i).getRightVertex() == end && start.getEdges().get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
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
            if (u.getEdges().size() > 0) ans.replace(ans.length() - 2, ans.length(), "");
            ans.append(" }\n");
        }
        return ans.toString();

    }
}

