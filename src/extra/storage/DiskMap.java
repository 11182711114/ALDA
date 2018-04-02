package extra.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import extra.graph.Node;

public class DiskMap<K, V extends DiskStorable<Node>> implements Map<K,V>{
	
//	public static void main(String[] args) {
//		DiskMap<String, Node> map = new DiskMap<String, Node>("./data/map/", new Node());
//		map.clear();
//		Node testNode = new Node("Test TEsett test");
//		Node testNodeTo = new Node("test edge");
//		testNode.addEdge(testNodeTo);
//		map.put(testNode.getData(), testNode);
//		map.put(testNodeTo.getData(), testNodeTo);
//		map.remove(testNode.getData());
//		
//		System.out.println(map.get(testNode.getData()));
//		System.out.println(map.get("sda"));
//	}
	
	private static final char DATA_DELIMITER = ',';
	private static final char DATA_START = '{';
	private static final char DATA_END = '}';
	
	private File rootDirectory;
	private int size = 0;
	private V factory;
	private String defaultFileExtension = ".dmd";	
	
	public DiskMap(File rootDirectory,  V factory) {
		this.factory = factory;
		this.rootDirectory = rootDirectory;
		if (!rootDirectory.exists())
			rootDirectory.mkdirs();
		clear(rootDirectory);
	}
	
	public DiskMap(String string, V factory) {
		this(new File(string), factory);
	}

	public void setDiskLocation(File location) {
		rootDirectory = location;
	}
	
	public void setDiskLocation(String location) {
		rootDirectory = new File(location);
	}
	
	@Override
	public void clear() {
		clear(rootDirectory);
		size = 0;
	}

	private void clear(File f) {
		for (File c : rootDirectory.listFiles()) {
			if (c.isDirectory() && c.list().length != 0)
				clear(c);
			c.delete();
		}
	}
	
	@Override
	public boolean containsKey(Object key) {
		if (size == 0)
			return false;
		
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		if (!(value instanceof DiskStorable) || size == 0)
			return false;
		
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}
	
	private String getRelativeFileName(String s) {
		return s.startsWith("-") ? s.substring(0,3) : s.substring(0,2);
	}
	
	private File getRelativeFile(String s) {
		return new File(rootDirectory + File.separator + getRelativeFileName(s) + defaultFileExtension);
	}
	private File getRelativeFile(int i) {
		return getRelativeFile(""+i);
	}

	@Override
	public V put(K key, V value) {
		File file = getRelativeFile(key.hashCode());
		V oldValue = get(key);
		remove(key);
		
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, true))){
			pw.print(key.hashCode() + "\n" + DATA_START);
			for (String line : value.store())
				pw.print(line + DATA_DELIMITER);
			pw.println(DATA_END);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return oldValue;
	}
	
	private BufferedReader getReaderAtData(Object toGet) throws IOException {
		File file = getRelativeFile(toGet.hashCode());
		String identifier = ""+toGet.hashCode();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (line.startsWith(identifier))
				return br;
		}
		
		return null;
	}
	
	int timeAccesed = 0;
	@Override
	public V get(Object toGet) {
		timeAccesed++;
		if (timeAccesed == 23)
			System.out.println();
		try (BufferedReader br = getReaderAtData(toGet)) {
			if (br == null)
				return null;
			List<V> buffer = new ArrayList<>();
			br.read();
			String ident = readToDelimiter(br);
			for (String tmp = readToDelimiter(br); tmp != null; tmp = readToDelimiter(br))
				buffer.add(get(tmp));
			
			V toReturn = (V) factory.create();
			toReturn.load(ident, buffer.toArray(new Object[0]));
			return toReturn;
		} catch (FileNotFoundException e) { // No such element -> return null
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String readToDelimiter(BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		for (int c = br.read(); (c != -1 && (char) c != DATA_DELIMITER) ; c = br.read()) {
			if ((char) c == DATA_END)
				return null;
			sb.append((char)c);
		}
		return sb.toString();
	}
	
//	@Override
//	public V get(Object toGet) {
//		File file = getRelativeFile(toGet.hashCode());
//		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//			List<V> buffer = new ArrayList<>();
//			String ident = br.readLine();
//			for (String tmp = br.readLine(); tmp != null; tmp = br.readLine())
//				buffer.add(get(tmp));
//			
//			V toReturn = (V) factory.create();
//			toReturn.load(ident, buffer.toArray(new Object[0]));
//			return toReturn;
//		} catch (FileNotFoundException e) { // No such element -> return null
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V remove(Object toRemove) {
		Object toReturn = get(toRemove);
		String ident = ""+toRemove.hashCode();
		File file = getRelativeFile(toRemove.hashCode());
		File tmpFile = new File(file.getPath() + "~");
		int linesWritten = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file));
				PrintWriter pw = new PrintWriter(new FileOutputStream(tmpFile, true));) {
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (line.startsWith(ident)) {
					for (String lineToRemove = br.readLine(); !lineToRemove.endsWith("}"); lineToRemove = br.readLine()) {
					}
				} else {
					pw.println(line);
					linesWritten++;
				}
			}
			br.close();
			pw.close();
			file.delete();
			if (linesWritten>0)
				tmpFile.renameTo(file);
			else 
				tmpFile.delete();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return (V) toReturn;
	}

	@Override
	public int size() {
		return size;
	}
	
	// No need to support these at this time
	@Override
	public Collection<V> values() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();		
	}
	
	@Override
	public Set<K> keySet() {
		throw new UnsupportedOperationException();	
	}
}
