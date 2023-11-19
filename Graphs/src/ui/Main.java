package ui;
import exceptions.*;
import model.*;
public class Main {

    public Main(){

    }


    public static void main(String[] args) throws VertexNotAchievableException, VertexNotFoundException {

        Main m = new Main();
        m.test1();
        m.test2();



    }
    // TESTING

    void test1(){

        MatrixGraph<String> graph = new MatrixGraph<>(true,8);

        try{
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");

            graph.addEdge("A", "B", "FROM A -> B", 5);
            graph.addEdge("A", "C", "FROM A -> C", 1);
            graph.addEdge("C", "D", "FROM C -> D", 9);


        }catch (VertexAlreadyAddedException e){
            System.err.println("ALREADY ADDED");
        } catch (LoopsNotAllowedException e) {
            throw new RuntimeException(e);
        } catch (MultipleEdgesNotAllowedException e) {
            throw new RuntimeException(e);
        } catch (VertexNotFoundException e) {
            throw new RuntimeException(e);
        }

        try{

            System.out.println(graph.dijkstra("A","C"));

        }catch (Exception e){
            System.err.println("NO SIRVE");
        }

    }



    void test2(){
        MatrixGraph<String> graph = new MatrixGraph<>(true,8);
        try{
            graph.addVertex("A");
            graph.addVertex("B");
            graph.addVertex("C");
            graph.addVertex("D");

            graph.addEdge("A", "B", "FROM A -> B", 5);
            graph.addEdge("A", "C", "FROM A -> C", 1);
            graph.addEdge("C", "D", "FROM C -> D", 9);


        }catch (VertexAlreadyAddedException e){
            System.err.println("ALREADY ADDED");
        } catch (LoopsNotAllowedException e) {
            throw new RuntimeException(e);
        } catch (MultipleEdgesNotAllowedException e) {
            throw new RuntimeException(e);
        } catch (VertexNotFoundException e) {
            throw new RuntimeException(e);
        }

       try{
           graph.DFS("A");

       }catch (Exception e){
           System.err.println("NO SIRVE");
       }

    }
}
