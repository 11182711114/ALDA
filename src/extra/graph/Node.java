package extra.graph;

import extra.baconNumber.Actor;

public class Node {
	private Actor actor;
	/** the distance from original Node	 */
	private int dist = -1;
	/** Previous node in the chain */
	private Node prev;
	
	public Node(){
	}
	
	public Node(Actor act){
		this.actor = act;
	}

	public Actor getActor() {
		return actor;
	}

	@Override
	public String toString() {
		return actor.getName();
	}
	
	/** Returns {@link #dist}
	 * @return Returns {@link #dist}
	 */
	public int getDist() {
		return dist;
	}
	
	/** Sets the {@link #dist}
	 * @param dist the int to set {@link #dist} to
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	/** Resets the node so it can be used in another bacon number search */
	public void reset() {
		dist = -1;
		prev = null;
	}
}