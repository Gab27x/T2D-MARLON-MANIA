import exceptions.*;
import model.ListGraph;
import model.ListVertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ListGraphTest {
    private ListGraph<String> graph;

    @Before
    public void setUp() {
        graph = new ListGraph<>(true, true, false);
    }


    public void setUpLoop(){
        graph = new ListGraph<>(true, true, true);
    }


    public void setUpExistingNodes() {
        try {
            graph.addVertex("F");
            graph.addVertex("G");
            graph.addVertex("H");
            graph.addVertex("I");
            graph.addVertex("J");
            graph.addVertex("K");
            graph.addEdge("F", "G", "testEdge1", 5);
            graph.addEdge("G", "H", "testEdge1", 1);
            graph.addEdge("F", "H", "testEdge2", 3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUpExistingNodesAllConnectedDFS() {
        try {
            graph.addVertex("F");
            graph.addVertex("G");
            graph.addVertex("H");
            graph.addVertex("I");
            graph.addVertex("J");
            graph.addEdge("F", "G", "testEdge1", 5);
            graph.addEdge("G", "H", "testEdge1", 1);
            graph.addEdge("F", "H", "testEdge2", 3);
            graph.addEdge("F", "I", "testEdge3", 2);
            graph.addEdge("I", "J", "testEdge4", 2);
            graph.addEdge("J", "H", "testEdge5", 2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUpExistingNodesNotAllConnectedDFS() {
        try {
            graph.addVertex("F");
            graph.addVertex("G");
            graph.addVertex("H");
            graph.addVertex("I");
            graph.addVertex("J");
            graph.addEdge("F", "G", "testEdge1", 5);
            graph.addEdge("G", "H", "testEdge1", 1);
            graph.addEdge("F", "H", "testEdge2", 3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testAddVertex() throws VertexAlreadyAddedException {
        graph.addVertex("A");
        assertTrue(graph.searchVertexIndex("A") != -1);
    }

    @Test(expected = VertexAlreadyAddedException.class)
    public void testAddDuplicateVertex() throws VertexAlreadyAddedException {
        graph.addVertex("A");
        graph.addVertex("A");
    }

    @Test
    public void testAddEdge() throws Exception {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", "edge1", 1);
        assertTrue(graph.searchEdge("A", "B", "edge1"));
    }

    @Test(expected = VertexNotFoundException.class)
    public void testAddEdgeWithMissingVertex() throws Exception {
        graph.addEdge("A", "B", "edge1", 1);
    }

    @Test
    public void testNonAddLoopEdge() throws VertexAlreadyAddedException {
        graph.addVertex("A");
        assertThrows(LoopsNotAllowedException.class, () -> graph.addEdge("A","A","edgeLoop",1));
    }

    @Test
    public void testAddLoopEdge() throws VertexAlreadyAddedException, LoopsNotAllowedException, MultipleEdgesNotAllowedException, VertexNotFoundException {
        setUpLoop();
        graph.addVertex("A");
        graph.addEdge("A","A","edgeLoop",1);
        assertTrue(graph.searchEdge("A","A","edgeLoop"));
    }

    @Test
    public void testAddMultipleEdges() throws VertexAlreadyAddedException, LoopsNotAllowedException, MultipleEdgesNotAllowedException, VertexNotFoundException {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", "edge1", 1);
        graph.addEdge("A", "B", "edge2", 2);

        assertEquals(2,  graph.getList().get(graph.searchVertexIndex("A")).getEdges().size());

    }

    @Test
    public void testDeleteVertex() throws VertexNotFoundException, VertexAlreadyAddedException {
        graph.addVertex("A");
        graph.deleteVertex("A");
        assertTrue(graph.searchVertexIndex("A") == -1);
    }

    @Test(expected = VertexNotFoundException.class)
    public void testDeleteMissingVertex() throws VertexNotFoundException {
        graph.deleteVertex("A");
    }

    @Test
    public void testDeleteEdge() throws Exception {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", "edge1", 1);
        graph.deleteEdge("A", "B", "edge1");
        assertFalse(graph.searchEdge("A", "B", "edge1"));
    }

    @Test(expected = EdgeNotFoundException.class)
    public void testDeleteMissingEdge() throws EdgeNotFoundException, VertexAlreadyAddedException, VertexNotFoundException {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.deleteEdge("A", "B", "edge1");
    }
/*
    @Test
    public void testDijkstra() throws VertexNotFoundException, VertexNotAchievableException, VertexAlreadyAddedException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B", "edge1", 1);
         graph.addEdge("B", "C", "edge2", 2);

        Map<String, String> shortestPath = graph.dijkstra("A", "C");
        assertEquals("B", shortestPath.get("C"));
    }*/

    @Test(expected = VertexNotFoundException.class)
    public void testDijkstraWithMissingVertex() throws VertexNotFoundException, VertexNotAchievableException {
        graph.dijkstra("A", "D");
    }

    @Test
    public void testDFS() throws VertexNotFoundException, VertexNotAchievableException {
        setUpExistingNodesAllConnectedDFS();
        boolean[] visited = new boolean[graph.getList().size()];
        graph.DFS("F");
        for (int i = 0; i < graph.getList().size(); i++) {
            visited[i] = graph.getList().get(i).isVisited();
        }
        for (boolean isVisited : visited) {

            assertTrue(isVisited);
        }
    }

    @Test
    public void testDFSNotAllConnected() throws VertexNotFoundException, VertexNotAchievableException {
        setUpExistingNodesNotAllConnectedDFS();
        boolean[] visited = new boolean[graph.getList().size()];
        graph.DFS("F");
        for (int i = 0; i < graph.getList().size(); i++) {
            visited[i] = graph.getList().get(i).isVisited();
            if (i==3 || i==4) {
                assertFalse(visited[i]);
            }
        }

    }

    @Test
    public void testDijkstraWithUnreachableVertex() throws VertexNotFoundException, VertexNotAchievableException, VertexAlreadyAddedException {
        setUpExistingNodes();
        assertThrows(VertexNotAchievableException.class,()->graph.dijkstra("F", "K"));
    }



    @Test
    public void testSearchEdgeWithDirectedGraph() throws Exception {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", "edge1", 1);
        assertTrue(graph.searchEdge("A", "B", "edge1"));
        assertFalse(graph.searchEdge("B", "A", "edge1"));
    }

    @Test
    public void testSearchEdgeWithUndirectedGraph() throws Exception {
        graph = new ListGraph<>(false, false, false);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", "edge1", 1);
        assertTrue(graph.searchEdge("A", "B", "edge1"));
        assertTrue(graph.searchEdge("B", "A", "edge1"));
    }


}
