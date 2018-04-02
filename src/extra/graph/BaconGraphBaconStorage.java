package extra.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import extra.baconNumber.Actor;
import extra.baconNumber.Work;
import extra.storage.BaconStorage;

public class BaconGraphBaconStorage implements BaconGraph{
	
	private static final int DISTANCE_CUTOFF = 6;
	private BaconStorage data = new BaconStorage();
	private Queue<Node> breadthFirstBuffer = new LinkedList<Node>();
	private List<Node> checked = new LinkedList<Node>();
	int size;
	
	public BaconGraphBaconStorage(BaconStorage bs){
		data = bs;
		size = 0;
	}


	@Override
	public void add(Actor toAdd) {
		data.addActor(toAdd);	
		size++;
	}

	@Override
	public void addWork(Work toAdd) {
		data.addWork(toAdd);
	}

	@Override
	public Node getNode(Actor toGet) {
		return data.getActorNode(toGet);
	}

	@Override
	public int size() {
		return size;
	}
	
	public void findShortestPath(String from, String to) throws NoSuchElementException, IllegalArgumentException{
		Long startTime = System.currentTimeMillis();
		
		Node start = data.getActorNodeByName(from);
		Node toFind = data.getActorNodeByName(to); 
		
		if (start == null || toFind == null)
			throw new NoSuchElementException();
		if (start == toFind) 
			throw new IllegalArgumentException();
		
		start.setDist(0);
		breadthFirstBuffer.offer(start);
		checked.add(start);
		for (Node current = breadthFirstBuffer.poll(); current != null; current = breadthFirstBuffer.poll()) {
			for (Node n : data.getAdj(current.getActor())) {
				if (n.getDist() == -1) {
					n.setDist(current.getDist() + 1);
					n.setPrev(current);
					
					if (n.getDist() > DISTANCE_CUTOFF) {
						System.out.println("Reached cutoff distance: " + DISTANCE_CUTOFF);
						current = null;
						break;
					}
					if(n == toFind) {
						printPath(n);
						breadthFirstBuffer.clear();
						break;
					}
					breadthFirstBuffer.add(n); // 1
					checked.add(n); // 1
					
				}
			}
		}
		for (Node n : checked) { // n
			n.reset();
		}
		breadthFirstBuffer.clear(); // n
		checked.clear(); // n
		start.reset();
		toFind.reset(); 
		Long endTime = System.currentTimeMillis();
		Long time = endTime - startTime;
		System.out.println("Path time: " + time + "ms");
	}
	
	/** Prints {@link Node#getPrev()} and traverses back through the path until {@code prev} is no longer {@code prev != null} 
	 * @param n the Node to start the traversal from, likely Kevin Bacon
	 */
	private void printPath(Node n) {
		System.out.println("Bacon number: " + n.getDist() + "\n");
		for (Node prev = n; prev != null; prev = prev.getPrev()) {
			System.out.println(prev.toString());
			if (prev.getPrev() != null)
				System.out.println("     |");
		}
	}
}
