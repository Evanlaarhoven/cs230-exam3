/*
 * AdjMatGraphPlus.java
 * Emily Van Laarhoven
 * CS230 Exam 3
 * Due: Monday 12/5/16 11:59pm
 */

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.*; //imported for use of Iterator<T>
import javafoundations.*; //imported for use of additional data structures

/**
 * An extended Adjacency Matrix graph implementation.  Complete this template for the exam.
 */
public class AdjMatGraphPlus<T> extends AdjMatGraph<T> implements GraphPlus<T> {
 
 /** DO NOT MODIFY THE FIRST THREE METHODS **************************
  * The methods you will implement follow below.
  */

 /**
  * Construct an empty graph.
  */
 public AdjMatGraphPlus() {
  super();
 }
 
 /**
  * Construct a graph with the same vertices and edges as the given original.
  * @param original
  */
 public AdjMatGraphPlus(AdjMatGraph<T> original) {
  super(original);
 }
 
 /**
  * Read a TGF file and create an AdjMatGraphPlus<String> from it.
  * @param tgfFile - the TGF file to read
  * @return a graph created from the TGF file
  * @throws FileNotFoundException if TGF file is not found.
  */
 public static AdjMatGraphPlus<String> fromTGF(String tgfFile) throws FileNotFoundException {
  AdjMatGraphPlus<String> g = new AdjMatGraphPlus<String>();
  AdjMatGraph.loadTGF(tgfFile, g);
  return g;
 }
 

 
 /**** IMPLEMENT THE METHODS BELOW *********************************
  * Replace "throw new UnsupportedOperationException();" with
  * a working implementation.
  ******************************************************************/
 

  /******************************************************************
    * Creates a new graph that has all the same vertices
    * and arcs as the original.
    * 
    * @return the new graph.
    *****************************************************************/
 public GraphPlus<T> clone() {
   GraphPlus<T> graph2 = new AdjMatGraphPlus<T>();
   Iterator<T> iter = super.iterator(); //returns an iterator of vertices
   Iterator<T> iter2 = super.iterator(); //second iterator to add in arcs
   while (iter.hasNext()) { //copy vertices
     T vertex = iter.next();
     graph2.addVertex(vertex); // adds same vertices to new graph
   }
   while (iter2.hasNext()) { //copy arcs
     T origVertex = iter2.next();
     LinkedList<T> arcs = super.getSuccessors(origVertex); //linked list of arcs
     for (int i=0; i < arcs.size(); i++) {
       T destVertex = arcs.get(i);
       graph2.addArc(origVertex,destVertex); //adds arc from origVertex to destVertex
     }
   }
   return graph2;
 }

   /******************************************************************
    * Checks if a vertex is a sink (points to no other vertex)
    * 
    * @param vertex: the potential sink vertex
    * @return true if the vertex is a sink, false if it is not.
    * @throws IllegalArgumentException if given vertex is not in graph
    ******************************************************************/
 public boolean isSink(T vertex) throws IllegalArgumentException {
   if (!super.containsVertex(vertex)) {
     throw new IllegalArgumentException("This vertex is not in the graph");
   } else {
     return ((super.getSuccessors(vertex).size()==0) && (super.getPredecessors(vertex).size()!=0));
   }
 }

  /******************************************************************
    * Retrieves all vertices that are sinks. 
    * 
    * @return all the sinks in a linked list
    ******************************************************************/
 public LinkedList<T> allSinks() {
  LinkedList<T> list = new LinkedList<T>();
  Iterator<T> iter = super.iterator();
  while (iter.hasNext()) {
    T vertex = iter.next();
    if (isSink(vertex)) {
      list.add(vertex);
    }
  }
  return list;
 }

  /******************************************************************
    * Checks if a vertex is a source (no vertex points to it)
    * 
    * @param vertex: the potential source vertex
    * @return true if the vertex is a source, false if it is not
    * @throws IllegalArgumentException if given vertex is not in graph
    ******************************************************************/
 public boolean isSource(T vertex) throws IllegalArgumentException {
   if (!super.containsVertex(vertex)) {
     throw new IllegalArgumentException("This vertex is not in the graph");
   } else {
     return ((super.getPredecessors(vertex).size()==0) && (super.getSuccessors(vertex).size()!=0));
   }
 }

   /******************************************************************
    * Retrieves all vertices that are sources. 
    * 
    * @return all the sources in a linked list
    ******************************************************************/
 public LinkedList<T> allSources() {
  LinkedList<T> list = new LinkedList<T>();
  Iterator<T> iter = super.iterator();
  while (iter.hasNext()) {
    T vertex = iter.next();
    if (isSource(vertex)) {
      list.add(vertex);
    }
  }
  return list;
 }

   /******************************************************************
    * Checks if a vertex is a isolated, i.e., no vertices
    * point to it and it points to no vertices.
    * 
    * @param vertex: the vertex to check for isolation
    * @return true if the vertex is isolated, false if it is not
    * @throws IllegalArgumentException if given vertex is not in graph
    ******************************************************************/
 public boolean isIsolated(T vertex) throws IllegalArgumentException {
     if (!super.containsVertex(vertex)) {
     throw new IllegalArgumentException("This vertex is not in the graph");
     } else {
       Iterator<T> iter = super.iterator();
       while (iter.hasNext()) {
         T vertex2 = iter.next();
         if (isArc(vertex,vertex2) || isArc(vertex2,vertex)) {
           return false;
         }
       }
     }
     return true;       
 }

   /******************************************************************
    * Returns a list of vertices in a directed acyclic graph (DAG)
    * such that for any vertices x and y, if there is a directed
    * edge from x to y in the graph then x comes before y in the
    * returned list. There may be multiple lists that satisfy the
    * abovementioned constraints. This method returns one such list.
    * If the input graph is not a DAG, a GraphCycleException is thrown.
    *
    * The following algorithm (described in English, you should
    * convert it to Java code in the method body) is one way to
    * generate such a list.
    *   Start with an empty list
    *   While the list does not contain all the vertices:
    *     Select a vertex that has no predecessor
    *     Remove the selected vertex from the graph (along with all associated arcs)
    *     Add the vertex to the end of the list
    * 
    * @return the vertices in a linked list satisfying the order described above.
    * @throws GraphCycleException if called on a non-DAG
    ******************************************************************/
 public LinkedList<T> listByPriority() { //throws GraphCycleException - I removed because ran into error finding exception
   LinkedList<T> list = new LinkedList<T>();
   AdjMatGraphPlus<T> copy = (AdjMatGraphPlus<T>) this.clone(); //create a copy to remove vertices
   int n = copy.n(); //number of vertices
   for (int i=0; i<n; i++) { //iterate through # of vertices
     LinkedList<T> sources = copy.allSources();
     if (sources.size()==0) { //if there are no sources this means there is either a cycle or isolated points
       Iterator<T> iter = copy.iterator();
       while (iter.hasNext()) { //go through remaining vertices
         T vertex = iter.next();
         if (copy.isIsolated(vertex)) { //if it's an isolated point, add to end of list and remove from graph
           list.addLast(vertex);
           copy.removeVertex(vertex);
         } else { //otherwise this is evidence of a cycle - throw exception (I removed it because it couldn't find the exception)
           System.out.println("Throw exception - this is not a DAG");
//           throw new GraphCycleException("not a DAG"); //if could find exception, would uncomment this instead of print statement
         }
       }   
     } else { 
       T source = sources.pop();
       copy.removeVertex(source); //remove the first vertex in the list of sources from the graph
       list.addLast(source); //add to the end of the list
     }
   }
   return list;
 }


   /******************************************************************
    * Returns a LinkedList containing a DEPTH-first search
    * traversal of the graph starting at the given vertex. The result
    * list should contain all vertices visited during the traversal in
    * the order they were visited.
    * You can use pseudocode from class materials as a starting point.
    * 
    * @param vertex: the starting vertex for the traversal
    * @return a linked list with the vertices in depth-first order
    * @throws IllegalArgumentException if given vertex is not in graph
    *****************************************************************/
 public LinkedList<T> dfsTraversal(T startVertex) throws IllegalArgumentException {
   if (!super.containsVertex(startVertex)) { //if vertex not in graph
     throw new IllegalArgumentException();
   } else { //vertex in graph
     LinkedList<T> visited = new LinkedList<T>(); //create linked list of visited vertices
     LinkedStack<T> stack = new LinkedStack<T>(); //create stack to be used in DFS traversal
     T vertex = startVertex; //initialize
     stack.push(vertex); //push startVertex into stack so stack is not empty
     visited.add(vertex); //mark startVertex as visited
     while (!stack.isEmpty()) { //while stack is not empty, continue to traverse graph
       LinkedList<T> successors = super.getSuccessors(vertex);
       if (successors.size()==0) { //if list of successors is empty
         stack.pop(); //backtrack by popping from stack
         if (!stack.isEmpty()) {
           vertex = stack.peek(); //set vertex to the most recently examined vertex from the stack
         } else { //if stack is empty as well
           return visited; //traversal is finished
         }
       } else { //successors list is not empty
       vertex = successors.pop(); //set vertex to be first in list of successors
       while (visited.contains(vertex)) { //if this vertex has already been visited go on to next one
         if (successors.size()==0) { //if list of successors is now empty
           if (!stack.isEmpty()) { //backtrack by popping from stack
           vertex = stack.pop(); //set vertex to the most recently examined vertex from the stack
           } else { //if stack is empty 
             return visited; //traversal is finished
           }
         } else {
           vertex = successors.pop(); //pop and set vertex to next vertex in list of successors
         }
       }
       visited.add(vertex); //add to list of vistied vertices
       stack.push(vertex); //push vertex into stack if moving on into list of successors
       }
     }
     return visited;
   }
 }





   /******************************************************************
    * Returns a LinkedList containing a BREADTH-first search
    * traversal of a graph starting at the given vertex.  The result
    * list should contain all vertices visited during the traversal in
    * the order they were visited.
    * You can use pseudocode from class materials as a starting point.
    * 
    * @param vertex: the starting vertex for the traversal
    * @return a linked list with the vertices in breadth-first order
    * @throws IllegalArgumentException if given vertex is not in graph
    *****************************************************************/
 public LinkedList<T> bfsTraversal(T startVertex) {
   if (!super.containsVertex(startVertex)) { //if vertex not in graph
     throw new IllegalArgumentException();
   } else { //vertex in graph
     LinkedList<T> visited = new LinkedList<T>();
     T v = startVertex; //initialize
     LinkedQueue<LinkedList> bfsQ = new LinkedQueue<LinkedList>(); //queue for use in traversal
     LinkedList<T> path = new LinkedList<T>(); //list is path of vertices taken
     path.add(v); //add the single point path of starting point
     bfsQ.enqueue(path); //enqueue this path
     visited.add(v); //mark first point as visited
     while (!bfsQ.isEmpty()) { //while bfsQ is not empty
       T w = (T) bfsQ.dequeue().removeLast(); //get most recently added vertex to the most recent path
       LinkedList<T> adjacent = super.getSuccessors(w); //get neighbors of w
       for (int i=0; i<adjacent.size(); i++) { //iterate through successors of w
         T u = adjacent.get(i);
         if (!visited.contains(u)) { //if u has not yet been visited
           path.add(u);
           bfsQ.enqueue(path);
           visited.add(u);
         }
       }
     }
     return visited;
   }
 }
         

 
 
 public static void main (String [] args) {
//   AdjMatGraph<String> G = new AdjMatGraph<String>();
//   G.addVertex("A"); G.addVertex("B"); G.addVertex("C");
//   G.addArc("A","B"); G.addArc("A","C");
//   G.addVertex("D"); G.addVertex("E"); G.addVertex("F");
//   G.addVertex("G"); //isolated point
//   G.addArc("A", "B"); G.addArc("B", "C"); G.addArc("C", "D");
//   G.addArc("F", "B"); G.addArc("A", "D"); G.addArc("F","E");
//   System.out.println("Predecessors of D are "+G.getPredecessors("D"));
//   System.out.println("Predecessors of F are "+G.getPredecessors("F"));
//   System.out.println(G);
//   AdjMatGraphPlus<String> plusTest = new AdjMatGraphPlus<String>(G);
//   System.out.println(plusTest);
//   System.out.println("D is a sink (true): "+plusTest.isSink("D"));
//   System.out.println("F is a sink (false): "+plusTest.isSink("F"));
//   System.out.println("A is a sink (false): "+plusTest.isSink("A"));
////   System.out.println("Sink for vertex not in graph: "+plusTest.isSink("poop"));
//   System.out.println("D is a source (false): "+plusTest.isSource("D"));
//   System.out.println("F is a source (true): "+plusTest.isSource("F"));
//   System.out.println("A is a source (true): "+plusTest.isSource("A"));
////   System.out.println("Source for vertex not in graph: "+plusTest.isSource("poop"));
//   GraphPlus<String> cloneTest = plusTest.clone();
//   System.out.println(cloneTest);
//   System.out.println("all sinks: (D,E) "+plusTest.allSinks());
//   System.out.println("all sources: (A,F) "+plusTest.allSources());
//   System.out.println("A is isolated (false): "+plusTest.isIsolated("A"));
////   System.out.println("G is isolated (true): "+plusTest.isIsolated("G"));
//   System.out.println(plusTest.listByPriority());
//   System.out.println(plusTest.dfsTraversal("A"));
//   System.out.println(plusTest.bfsTraversal("A"));


 }

}