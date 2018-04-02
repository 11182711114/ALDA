package extra.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

public class BaconGraphHashMap implements BaconGraph{
	
	private static final int DEFAULT_INTINIAL_NODE_SIZE = 10;
	
	private Map<String,Node> nodes = new HashMap<>(10000,1);
	private Queue<Node> breadthFirstBuffer = new LinkedList<Node>();
	int size;

	public BaconGraphHashMap(int nodeSize) {
		size = 0;
	}
	
	public BaconGraphHashMap() {
		this(DEFAULT_INTINIAL_NODE_SIZE);
	}
	
	public void add(String toAdd) {
		nodes.put(toAdd, new Node(toAdd));
	}
	
	public Node getNode(String toGet) {
		Node tmp = nodes.get(toGet);
		
		if (tmp == null) {
			tmp = new Node(toGet);
			nodes.put(toGet, tmp);
		}
		return tmp;
	}
	
	public void addEdge(String from, String to) {
		getNode(from).addEdge(getNode(to));
	}
	
	public int size() {
		return size;
	}
	
	public void findShortestPath(String from, String to) {
		breadthFirstBuffer.clear();
		Node start = nodes.get(from);
		Node toFind = nodes.get(to);
		
		if (start == null || toFind == null)
			throw new NoSuchElementException();
		
		start.setDist(0);
		breadthFirstBuffer.offer(start);
		for (Node current = breadthFirstBuffer.poll(); current != null; current = breadthFirstBuffer.poll()) {
			for (Edge e : current.getAdj()) {
				Node n = e.getTo();
				
				if (n.getDist() == -1) {
					n.setDist(current.getDist() + 1);
					n.setPrev(current);

					if(n == toFind)
						printPath(n);
					breadthFirstBuffer.add(n);
				}
			}
		}
	}
	
	private void printPath(Node n) {
		for (Node prev = n; prev != null; prev = prev.getPrev()) {
			System.out.println(prev.toString());
			if (prev.getPrev() != null)
				System.out.println("     |");
		}
	}
}
