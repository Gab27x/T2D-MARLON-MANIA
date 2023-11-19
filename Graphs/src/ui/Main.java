package ui;
import exceptions.*;
import model.*;
public class Main {

    public Main(){

    }


    public static void main(String[] args) throws VertexNotAchievableException, VertexNotFoundException {

        Main m = new Main();
        try {
            try {
                m.test1();
            } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException e) {
                throw new RuntimeException(e);
            }
        } catch (VertexAlreadyAddedException e) {
            throw new RuntimeException(e);
        }
        try {
            try {
                m.test2();
            } catch (LoopsNotAllowedException | MultipleEdgesNotAllowedException e) {
                throw new RuntimeException(e);
            }
        } catch (VertexAlreadyAddedException e) {
            throw new RuntimeException(e);
        }


    }
    // TESTING

    void test1() throws VertexAlreadyAddedException, VertexNotAchievableException, VertexNotFoundException, LoopsNotAllowedException, MultipleEdgesNotAllowedException {
        MatrixGraph<String> graph = new MatrixGraph<>(false,4);

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addEdge("A","B","A-B",1);
        graph.addEdge("A","C","A-C",2);
        graph.addEdge("C","D","C-D",2);
        graph.addEdge("B","D","C-D",2);


        System.out.println(graph.dijkstra("A","D"));



    }



    void test2() throws VertexAlreadyAddedException, LoopsNotAllowedException, MultipleEdgesNotAllowedException, VertexNotFoundException, VertexNotAchievableException {
        ListGraph<String> graphList = new ListGraph<>(false,true,false);
        graphList.addVertex("A");
        graphList.addVertex("B");
        graphList.addVertex("C");
        graphList.addVertex("D");
        graphList.addEdge("A","B","A-B",1);
        graphList.addEdge("A","C","A-C",2);
        graphList.addEdge("C","D","C-D",2);
        graphList.addEdge("B","D","C-D",2);


        System.out.println("LIST"+graphList.dijkstra("A","D"));


    }
}
