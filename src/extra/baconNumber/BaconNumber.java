package extra.baconNumber;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import extra.graph.BaconGraph;
import extra.graph.BaconGraphBaconStorage;
import extra.storage.BaconStorage;
import extra.baconNumber.BaconReader.Part;
import extra.exception.UnderflowException;

/** Asks for an Actors name and prints their bacon number. 
 * @author Fredrik Larsson frla9839
 */
public class BaconNumber {
	
	/** Actors read in by {@link #load(FileLoader)} */
	private ArrayList<String> actors = new ArrayList<>();
	/** Works read in by {@link #load(FileLoader)}*/
	private HashMap<String, List<String>> works = new HashMap<>();
	private BaconGraph graph;
	
	private boolean running = false;
	
	public static void main(String[] args) {
		try {
			BaconReader brActors = new BaconReader("./data/actorsFull.list");
			BaconReader brActresses = new BaconReader("./data/actresses.list");
			BaconReader[] lists = {brActors, brActresses};
			BaconNumber bn = new BaconNumber(new FileLoader(new LinkedList<Part>(), lists));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BaconNumber(FileLoader fl) throws IOException {
		initialize(fl);
		run();
	}
	
	/** Main loop of the program, asks user for Actor, finds the shortest path and prints it.
	 * 
	 */
	public void run() {
		running = true;
		Scanner input = new Scanner(System.in);
		while (running) {
			System.out.print("\nActor to find bacon number of? ");
			String userIn = input.nextLine();
			if (userIn.equalsIgnoreCase("exit")) 
				running = false;
			try {
				graph.findShortestPath(userIn, "Bacon, Kevin (I)");
			} catch (NoSuchElementException e) {
				System.out.println("No such actor: " + userIn);
			} catch (IllegalArgumentException e) {
				System.out.println("The same Actor will always have a Bacon number of 0...");
			}
		}
	}
	
	/** initializes the program running {@link #load(FileLoader)} and {@link #fillGraph()}
	 * @param fl the FileLoader to use
	 * @throws IOException if there is issues with the file(s) being read from
	 */
	private void initialize(FileLoader fl) throws IOException {
		System.out.println("Loading the given files");
		long loadStartTime = System.currentTimeMillis();
		load(fl);
		long loadEndTime = System.currentTimeMillis();
		long loadTimeElapsedInSeconds = (loadEndTime - loadStartTime)/1000;
		System.out.println("Finished loading the file\nTime elapsed: " + loadTimeElapsedInSeconds +"s"+ "\nActors: " + actors.size() + "\nWorks: " + works.size() + "\nBeginning to fill graph");
		
		long graphFillStartTime = System.currentTimeMillis();
		fillGraph();
		long graphFillEndTime = System.currentTimeMillis();
		long graphTimeElapsedInSeconds = (graphFillEndTime-graphFillStartTime)/1000;
		System.out.println("Finished filling the graph\nTime elapsed: " + graphTimeElapsedInSeconds +"s");
		
		freeSpace();
	}
	
	/** Removes unnecessary references {@link #actors}, {@link #works}
	 */
	private void freeSpace() {
		actors = null;
		works = null;
	}
	
	/** Fill the graph using {@link #actors} and {@link #works}
	 */
	private void fillGraph() {
		BaconStorage bs = new BaconStorage();
		System.out.println("Begining to fill actors");
		int i = 0;
		for (String act : actors) {
			bs.addActor(new Actor(act));
			i++;
			if (i%(actors.size()/4) == 0)
				System.out.println((int) Math.round( ((double) i)/actors.size()*100 ) + "%");
		}
		int work = 0;
		int workInitialSize = works.size();
		
		System.out.println("Begining to fill works");
		for (Iterator<Map.Entry<String,List<String>>> it = works.entrySet().iterator(); it.hasNext(); ){
			Entry<String,List<String>> entry = it.next();
			Work newWork = new Work(entry.getKey(), new ArrayList<Actor>());
			for (String actor : entry.getValue()) {
					newWork.addActor(bs.getActorNodeByName(actor).getActor());
			}
			if (work%(workInitialSize/4) == 0) 
				System.out.println((int) Math.round( ((double) work)/workInitialSize*100 ) + "%");
			
			bs.addWork(newWork);
			work++;
			it.remove(); // remove from the works as we go to not use unnecessary memory
		}
		graph = new BaconGraphBaconStorage(bs);
	}
	
	/** Loads the graph by quering the {@link FileLoader#}
	 * @param fl
	 * @throws IOException
	 */
	private void load(FileLoader fl) throws IOException {
		try {
			Actor prevActor = null;
			for (BaconItem tmp = fl.next(); tmp != null; tmp = fl.next()) {
				if (tmp instanceof Actor) {
					actors.add(((Actor) tmp).getName());
					prevActor = (Actor) tmp;
				} else {
					if (!( ((Work) tmp).getName() == Work.SUSPENDED_IDENTIFIER ))
						addToWork((Work) tmp, prevActor);
				}
			}
		} catch(UnderflowException e) {
			
		}
	}
	
	private void addToWork(Work wrk, Actor act) {
		List<String> tmp = works.get(wrk.toString());
		if (tmp == null)
			tmp = new ArrayList<>();
		tmp.add(act.getName());
		works.put(wrk.toString(), tmp);
	}
}