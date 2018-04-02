package extra.baconNumber;

import java.util.ArrayList;
import java.util.List;

import extra.baconNumber.BaconReader.Part;

/** An actor, subclass of {@link BaconItem}
 * @author Fredrik
 *
 */
public class Actor extends BaconItem {
	
	/** All the works this {@code Actor} has been in */
	private List<Work> work;
	
	/** Constructs an {@code Actor} with name
	 * @param name - the name of the {@code Actor} to create
	 */
	public Actor(String name){
		super(name);
		work = new ArrayList<Work>();
	}
	
	/** Constructs an Actor from a Part array, array MUST be arranged in the following order: { $NAME }
	 * @param parts
	 */
	public Actor(Part[] parts) {
		super(parts[0].text);
	}
	
	/** Adds the given work to the works of this {@code Actor}
	 * @param w - the {@code Work} to add
	 */
	public void addWork(Work w) {
		work.add(w);
	}
	
	/** Returns the list of works this actor has been in.
	 * @return {@link Actor#work}
	 */
	public List<Work> getWork() {
		return work;
	}
	
	@Override
	public String toString(){
		return name + work.toString();
	}
}
