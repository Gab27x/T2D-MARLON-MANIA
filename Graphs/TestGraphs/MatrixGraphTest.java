import exceptions.*;
import model.MatrixGraph;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MatrixGraphTest {
    private MatrixGraph<String> graph;

    @Before
    public void setUp() {
        graph = new MatrixGraph<>(true, 6);
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
            graph.addVertex("K");
            graph.addEdge("F", "G", "testEdge1", 5);
            graph.addEdge("G", "H", "testEdge1", 1);
            graph.addEdge("F", "H", "testEdge2", 3);
            graph.addEdge("F", "I", "testEdge3", 2);
            graph.addEdge("I", "J", "testEdge4", 2);
            graph.addEdge("J", "H", "testEdge5", 2);
            graph.addEdge("F", "K", "testEdge6", 4);
            graph.addEdge("K", "I", "testEdge7", 1);
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
            graph.addVertex("K");
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

    @Test
    public void testAddEdgeWithMissingVertex() throws Exception {
        graph.addVertex("A");
        graph.addVertex("B");
        assertThrows(VertexNotFoundException.class, () -> graph.addEdge("A", "C", "edge", 1));
    }

    @Test(expected = LoopsNotAllowedException.class)
    public void testAddLoopEdge() throws Exception {
        graph.addVertex("A");
        graph.addEdge("A", "A", "loop", 1);
    }

    @Test(expected = MultipleEdgesNotAllowedException.class)
    public void testAddMultipleEdges() throws Exception {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", "edge1", 1);
        graph.addEdge("A", "B", "edge2", 1);
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

/*    @Test
    public void testDijkstra() throws VertexNotAchievableException, VertexNotFoundException {
        setUpExistingNodes();
        Map<String, String> shortestPath = graph.dijkstra("F", "H");
        assertEquals("F", shortestPath.get("H"));
    }*/

    @Test
    public void testDijkstraWithMissingVertex() throws VertexNotFoundException, VertexNotAchievableException {
        setUpExistingNodes();
        assertThrows(VertexNotFoundException.class, () -> graph.dijkstra("W","H"));

    }


    //que se añade excepción
    @Test
    public void testDijkstraWithUnreachableVertex() throws VertexNotFoundException, VertexNotAchievableException, VertexAlreadyAddedException {
        setUpExistingNodes();
        assertThrows(VertexNotAchievableException.class,()->graph.dijkstra("F", "K"));
    }
    @Test
    public void testDFS() throws VertexNotFoundException, VertexNotAchievableException {
        setUpExistingNodesAllConnectedDFS();
        boolean[] visited = new boolean[graph.getVertices().length];
        graph.DFS("F");
        for (int i = 0; i < graph.getVertices().length; i++) {
            visited[i] = graph.getVertices()[i].isVisited();
        }
        for (boolean isVisited : visited) {

            assertTrue(isVisited);
        }
    }

    @Test
    public void testDFSNotAllConnected() throws VertexNotFoundException, VertexNotAchievableException {
        setUpExistingNodesNotAllConnectedDFS();
        boolean[] visited = new boolean[graph.getVertices().length];
        graph.DFS("F");
        for (int i = 0; i < graph.getVertices().length; i++) {
            visited[i] = graph.getVertices()[i].isVisited();

            if (i==3 || i==4) {
                assertFalse(visited[i]);
            }
        }

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
        graph = new MatrixGraph<>(false, 5);
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", "edge1", 1);
        assertTrue(graph.searchEdge("A", "B", "edge1"));
        assertTrue(graph.searchEdge("B", "A", "edge1"));
    }
}
