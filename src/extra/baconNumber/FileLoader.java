package extra.baconNumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;

import extra.baconNumber.BaconReader.Part;
import extra.baconNumber.BaconReader.PartType;
import extra.exception.UnderflowException;

/** Loads in data from {@link BaconReader}
 * @author Fredrik
 *
 */
public class FileLoader {
	private static final PartType[] DELIMITERS = { PartType.NAME, PartType.TITLE };
	private BaconReader[] readers;
	private Queue<Part> partBuffer;
	private boolean endOfFile = false;
	
	private int activeReader = 0;
	
	public FileLoader(Queue<Part> buffer, BaconReader[] br) {
		this.readers = br;
		partBuffer = buffer;
	}
	
	/** Queries BaconReader and interprets the results 
	 * @return a BaconItem based on the {@link PartType} of the {@link Part}
	 */
	public BaconItem next() {
		if(endOfFile && partBuffer.isEmpty() && activeReader == readers.length-1)
			throw new UnderflowException();
		else if (endOfFile && activeReader<readers.length-1) {
			activeReader += 1;
			endOfFile = false;
		}
		readToDelimiter(readers[activeReader]);
		
		ArrayList<Part> parts = new ArrayList<Part>();
		int delimiters = 0;
		for(Part tmp = partBuffer.peek(); tmp != null; tmp = partBuffer.peek()) {
			if (isDelimiter(tmp.type))
				delimiters++;
			if (delimiters == 2) // 1 delimiter is expected for the current item, 2nd is new item
				break;
			parts.add(partBuffer.poll());
		}
		if (partBuffer.isEmpty())
			endOfFile = true;
		return BaconItem.getBaconItem(parts.toArray(new Part[0]));
	}
	
	/** Queries BaconReader until it finds a {@link #DELIMITERS delimiter}.
	 * @param br the BaconReader to query
	 */
	public void readToDelimiter(BaconReader br) {
		try {
			if(partBuffer.isEmpty())
				partBuffer.offer(br.getNextPart());
			
			for (Part tmp = br.getNextPart(); tmp != null; tmp = br.getNextPart()) {
				partBuffer.offer(tmp);
				if(isDelimiter(tmp.type))
					return;
			}
			endOfFile = true;
		} catch(IOException e) { e.printStackTrace(); }
	}
	
	/** Checks whether pt is a delimiter
	 * @param pt the PartType to check
	 * @return true if pt is a delimier
	 */
	private boolean isDelimiter(PartType pt) {
		for (PartType tmp : DELIMITERS) {
			if (tmp.equals(pt)) 
				return true;
		}
		return false;
	}
}
