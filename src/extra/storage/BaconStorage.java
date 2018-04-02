package extra.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import extra.baconNumber.Actor;
import extra.baconNumber.Work;
import extra.graph.Node;

public class BaconStorage {
	
	/** A Work-Actor map containing all the tv shows and movies*/
//	private Map<Work, List<Node>> works = new HashMap<>();
	/** Contains all the actors */
	private Map<String, Node> actors = new HashMap<>();
	
	
	public BaconStorage() {
		
	}

	/** Finds an Actor by name
	 * @param act the name to find Actor of
	 * @return the Actor with the name
	 */
	public Node getActorNodeByName(String act) {
		return actors.get(act);
	}
	
	public void addActor(Actor act) {
		actors.put(act.getName(), new Node(act));
	}
	
	public void addWork(Work wrk) {
		for (Actor act : wrk.getActors()) {
			Node actor = actors.get(act.getName());
			if (actor == null)
				actor = new Node(new Actor(act.getName()));
			actor.getActor().addWork(wrk);
//			addWorkToWorks(wrk);
		}			
	}
	
	/** Returns a List of nodes with all the Actors this (act) Actor has worked with.
	 * @param act the Actor to find colleagues of.
	 * @return a List<Node> of all the Actors act has worked with.
	 */
	public List<Node> getAdj(Actor act) {
		List<Node> adj = new ArrayList<>();
		
		for (Work wrk : act.getWork()) {
			for (Actor a : wrk.getActors()) {
				if (!a.equals(act))
					adj.add(actors.get(a.getName()));
			}
		}
		
		return adj;
	}

	public Node getActorNode(Actor toGet) {
		return actors.get(toGet.getName());
	}
	
	/** Adds the given Work to works by making a new List
	 * @param wrk
	 */
//	private void addWorkToWorks(Work wrk) {
//		List<Node> work = works.get(wrk);
//		if (work == null)
//			work = new ArrayList<>();
//		for (Actor act : wrk.getActors())
//			work.add(actors.get(act.getName()));
//		works.put(wrk, work);
//	}

}
