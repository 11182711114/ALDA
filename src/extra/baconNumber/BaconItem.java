package extra.baconNumber;

import extra.baconNumber.BaconReader.Part;

/** Super class of {@link Actor} and {@link Work}.
 * @author Fredrik
 */
public abstract class BaconItem {
	String name;
	
	public BaconItem(String name) {
		this.name = name;
	}
	
	/** Factory method
	 * @param parts - Array of parts to construct new concrete {@code BaconItem}, either {@link Actor} or {@link Work} depending on PartType.
	 * @return
	 */
	public static BaconItem getBaconItem(Part... parts) {
		switch(parts[0].type) {
		case NAME:
			return new Actor(parts);
		case TITLE:
			return new Work(parts);
			default:
		}
		return null;
	}
	
	/** Returns the name of this BaconItem
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}

}
