package com.example.marlonmania.model;


import  com.example.marlonmania.exceptions.*;


public interface IGraph<T> {

    void addVertex(T vertex, int posX, int posY) throws VertexAlreadyAddedException;

    void addEdge(
            T start, T end, String id, int weight
    ) throws VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException;


    boolean searchEdge(T start, T end, String id) throws VertexNotFoundException;


    void deleteVertex(T vertex) throws VertexNotFoundException;


    void deleteEdge(T start, T end, String id) throws EdgeNotFoundException, VertexNotFoundException;

    int dijkstra(T startVertex, T endVertex) throws VertexNotFoundException, VertexNotAchievableException;

    void DFS(T startVertex) throws VertexNotFoundException, VertexNotAchievableException;


    boolean DFSVALIDATOR(T[] vertexes) throws VertexNotFoundException, VertexNotAchievableException;
}