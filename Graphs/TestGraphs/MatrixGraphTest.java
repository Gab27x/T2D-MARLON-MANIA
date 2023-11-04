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
        graph = new MatrixGraph<>(true, 5);
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

    @Test
    public void testDijkstra() throws VertexNotFoundException, VertexNotAchievableException, VertexAlreadyAddedException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B", "edge1", 1);
        graph.addEdge("B", "C", "edge2", 2);

        var shortestPath = graph.dijkstra("A", "C");
        assertEquals("A", shortestPath.get("C"));
    }


    //tira excepción
    @Test(expected = VertexNotFoundException.class)
    public void testDijkstraWithMissingVertex() throws VertexNotFoundException, VertexNotAchievableException {
        graph.dijkstra("A", "D");
    }


    //que se añade excepción
    @Test(expected = VertexNotAchievableException.class)
    public void testDijkstraWithUnreachableVertex() throws VertexNotFoundException, VertexNotAchievableException, VertexAlreadyAddedException {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.dijkstra("A", "B");
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
