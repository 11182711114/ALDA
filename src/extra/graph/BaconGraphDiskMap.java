package extra.graph;

import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

import extra.storage.DiskMapBuffer;

public class BaconGraphDiskMap implements BaconGraph{
	
	private static final int DEFAULT_INTINIAL_NODE_SIZE = 10;
	Map<String, Node> nodes = new DiskMapBuffer<String, Node>("./data/map/", new Node());
	private Queue<Node> breadthFirstBuffer = new LinkedList<Node>();
	int size;

	public BaconGraphDiskMap(int nodeSize) {
		size = 0;
	}
	
	public BaconGraphDiskMap() {
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
		Node n = getNode(from);
		n.addEdge(getNode(to));
		nodes.put(from, n);
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
