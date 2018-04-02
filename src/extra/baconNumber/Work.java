package extra.baconNumber;

import java.util.List;

import extra.baconNumber.BaconReader.Part;

/** A Work, i.e. TV-Show or movie. Subclass of {@link BaconItem}
 * @author Fredrik
 *
 */
public class Work extends BaconItem {
	/** Identifies work marked with the {@code SUSPENDED} tag */
	public static final String SUSPENDED_IDENTIFIER = ">>SUSPENDED<<";
	
	private String id;
	private String year;
	private List<Actor> actors;
	
	public Work (String name, List<Actor> actors) {
		super(name);
		this.actors = actors;
	}
	
	public Work (String name, String year, String id, List<Actor> actors) {
		this(name, actors);
		this.year = year;
		this.id = id;
	}
	
	public Work (String name, String year, List<Actor> actors) {
		this(name, actors);
		this.year = year;
	}
	
	/** Constructs a Work from a Part array, array MUST have title part first
	 * @param parts the array to construct the Work from
	 */
	public Work(Part[] parts, List<Actor> actors) {
		super(parts[0].text);
		for (Part p : parts) {
			switch(p.type) {
			case ID:
				this.id = p.text;
				break;
			case YEAR:
				this.year = p.text;
			default: break;
			}
		}
		this.actors = actors;
	}

	/** Constructs a Work from a Part array, array MUST have title part first
	 * @param parts the array to construct the Work from
	 */
	public Work(Part[] parts) {
		super(parts[0].text);
		for (Part p : parts) {
			switch(p.type) {
			case ID:
				this.id = p.text;
				break;
			case YEAR:
				this.year = p.text;
			case INFO:
				if (p.text.equals("SUSPENDED"))
					this.name = SUSPENDED_IDENTIFIER;
			default: break;
			}
		}
	}

	public void setId(String id){
		this.id = id;
	}
	public void setYear(String year){
		this.year = year;
	}
	
	@Override
	public String toString(){
		return id != null ? (year != null ? getName()+year+id : getName()+id) : (year != null ? getName() + year : getName());
	}
	
	public List<Actor> getActors() {
		return actors;
	}
	
	public void addActor(Actor act) {
		actors.add(act);
	}
}
