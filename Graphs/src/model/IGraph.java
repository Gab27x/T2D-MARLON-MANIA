package model;

import exceptions.*;
import java.util.ArrayList;
import java.util.Map;


public interface IGraph<T> {

    void addVertex(T vertex) throws VertexAlreadyAddedException;


    void addEdge(T start, T end, String id, int weight) throws VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException;


    boolean searchEdge(T start, T end, String id) throws VertexNotFoundException;


    void deleteVertex(T vertex) throws VertexNotFoundException;


    void deleteEdge(T start, T end, String id) throws EdgeNotFoundException, VertexNotFoundException;

    Map<T, T> dijkstra(T startVertex, T endVertex)throws VertexNotFoundException, VertexNotAchievableException;

    void DFS(T startVertex ) throws VertexNotFoundException, VertexNotAchievableException;


    void DFS(T[] vertexes) throws VertexNotFoundException, VertexNotAchievableException;
}