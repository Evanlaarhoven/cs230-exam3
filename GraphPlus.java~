import java.util.LinkedList;

/********************************************************************
  *  DO NOT CHANGE THIS FILE.
  *  
  *  GraphPlus.java  (interface for Exam 3)
  *
  *  Extends the Graph interface used in CS230 Homework 7 with
  *  additional methods.
  *  
  *  For this exam, you must implement the GraphPlus interface methods
  *  in the AdjMatGraphPlus class.
  *
  *  @author CS230 staff
  *********************************************************************/
public interface GraphPlus<T> extends Graph<T> {    
  
  /******************************************************************
    * Creates a new graph that has all the same vertices
    * and arcs as the original.
    * 
    * @return the new graph.
    *****************************************************************/
  public GraphPlus<T> clone();
  
  
  /******************************************************************
    * Checks if a vertex is a sink (points to no other vertex)
    * 
    * @param vertex: the potential sink vertex
    * @return true if the vertex is a sink, false if it is not.
    * @throws IllegalArgumentException if given vertex is not in graph
    ******************************************************************/
  public boolean isSink(T vertex);
  
  /******************************************************************
    * Checks if a vertex is a source (no vertex points to it)
    * 
    * @param vertex: the potential source vertex
    * @return true if the vertex is a source, false if it is not
    * @throws IllegalArgumentException if given vertex is not in graph
    ******************************************************************/
  public boolean isSource(T vertex);
  
  /******************************************************************
    * Retrieves all vertices that are sinks. 
    * 
    * @return all the sinks in a linked list
    ******************************************************************/
  public LinkedList<T> allSinks();
  
  /******************************************************************
    * Retrieves all vertices that are sources. 
    * 
    * @return all the sources in a linked list
    ******************************************************************/
  public LinkedList<T> allSources();
  
  /******************************************************************
    * Checks if a vertex is a isolated, i.e., no vertices
    * point to it and it points to no vertices.
    * 
    * @param vertex: the vertex to check for isolation
    * @return true if the vertex is isolated, false if it is not
    * @throws IllegalArgumentException if given vertex is not in graph
    ******************************************************************/
  public boolean isIsolated(T vertex);
  
  
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
  public LinkedList<T> listByPriority() throws GraphCycleException;
  
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
  public LinkedList<T> dfsTraversal(T startVertex);
  
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
  public LinkedList<T> bfsTraversal(T startVertex);
  
}
