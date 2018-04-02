package extra.graph;

import extra.baconNumber.Actor;
import extra.baconNumber.Work;

public interface BaconGraph {
	static final int DEFAULT_INTINIAL_NODE_SIZE = 10;
	
	/** Adds a Node to the graph
	 * @param toAdd String to identify the Node
	 */
	public void add(Actor toAdd);
	
	/** Returns the Node
	 * @param toGet the Node to return
	 * @return the Node identified by toGet
	 */
	public Node getNode(Actor toGet);
	
//	/** adds an edge to the Node identified by "from" to the Node "to"
//	 * @param from the Node to add the edge to
//	 * @param to the Node the edge should be referencing
//	 */
//	public void addEdge(String from, String to);
	
	public void addWork(Work toAdd);
	
	/** returns the number of nodes in the graph
	 * @return number of nodes in the graph
	 */
	public int size();
	
	/** Finds the shortest path between two nodes using breadth first search and prints the reverse path in console
	 * @param from String identifying the node to start the search from
	 * @param to String identifying the node to reach
	 */
	public void findShortestPath(String from, String to);

}
