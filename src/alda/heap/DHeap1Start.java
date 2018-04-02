//Fredrik Larsson - frla9839 - flarsson93@gmail.com
package alda.heap;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Mark Allen Weiss
 */
@SuppressWarnings("unchecked")
public class DHeap1Start<AnyType extends Comparable<? super AnyType>> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int DEFAULT_ARITY = 2;

	private int currentSize; // Number of elements in heap
	private int arity;
	private AnyType[] array; // The heap array
	
	/**
	 * Construct a d-ary heap with default arity 2, and default capacity 10
	 */
	public DHeap1Start() {
		this(DEFAULT_CAPACITY, DEFAULT_ARITY);
	}
	
	/**
	 * Constuct the heap with default initial capacity
	 * @param arity
	 * 				the arity of the heap
	 */
	public DHeap1Start(int arity) {
		this(DEFAULT_CAPACITY, arity);
	}

	
	/** 
	 * constuct the heap
	 * @param capacity 
	 * 					the initial capacity of the heap
	 * @param d
	 * 					the arity of the heap
	 */
	public DHeap1Start(int capacity, int d) {
		if (d < 2 )
			throw new IllegalArgumentException("arity has to be larger or equal to 2");
		
		currentSize = 0;
		this.arity = d;
		array = (AnyType[]) new Comparable[capacity + 1];
	}

	/**
	 * Insert into the priority queue, maintaining heap order. Duplicates are
	 * allowed.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(AnyType x) {
		if (currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);
			
		// Percolate up
		int hole = currentSize + 1;
		while (hole != 1 && x.compareTo(array[parentIndex(hole)]) < 0) {
			array[hole] = array[parentIndex(hole)];
			hole = parentIndex(hole);
		}
		array[hole] = x;
		currentSize++;
	}
	
	private void enlargeArray(int newSize) {
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for (int i = 1; i < old.length; i++)
			array[i] = old[i];
	}

	/**
	 * Find the smallest item in the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	/**
	 * Remove the smallest item from the priority queue.
	 * 
	 * @return the smallest item, or throw an UnderflowException if empty.
	 */
	public AnyType deleteMin() {
		if (isEmpty())
			throw new UnderflowException(); // not necessary as findMin() will throw it on the next line.
		
		AnyType minItem = findMin();
		currentSize--;
		array[1] = array[currentSize];
		percolateDown(1);

		return minItem;
	}

	/**
	 * Test if the priority queue is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return currentSize == 0;
	}

	/**
	 * Make the priority queue logically empty.
	 */
	public void makeEmpty() {
		currentSize = 0;
	}

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param hole
	 *            the index at which the percolate begins.
	 */
	private void percolateDown(int hole) {
		AnyType tmp = array[hole];
		int childIndex = 0;
		
//		while (childIndex < currentSize && tmp.compareTo(array[childIndex]) > 0) {
//			int firstChild = firstChildIndex(hole);
//			childIndex = findSmallestInRange(firstChild, firstChild + arity);
//			
//			if (childIndex < currentSize && tmp.compareTo(array[childIndex]) > 0) {
//				array[hole] = array[childIndex];
//				hole = childIndex;
//			}
//		}
		while(childIndex < currentSize) {
			int firstChild = firstChildIndex(hole);
			childIndex = findSmallestInRange(firstChild, firstChild + arity);
			
			if (childIndex < currentSize && tmp.compareTo(array[childIndex]) > 0){
				array[hole] = array[childIndex];
				hole = childIndex;
			} else {
				break;
			}
		}
		
		array[hole] = tmp;
	}
	
	private int findSmallestInRange(int startIndex, int endIndex) {
		int smallest = startIndex;
		
		for (int i = startIndex + 1; i <= endIndex; i++) {
			if (i >= currentSize)
				break;
			if (array[smallest].compareTo(array[i]) > 0)
				smallest = i;
		}
		
		return smallest;
	}

	// Test program
	public static void main(String[] args) {
		int numItems = 100;
		DHeap1Start<Integer> h = new DHeap1Start<>();
		int i = 37;

		for (i = 37; i != 0; i = (i + 37) % numItems)
			h.insert(i);
		for (i = 1; i < numItems; i++)
			if (h.deleteMin() != i)
				System.out.println("Oops! " + i);
	}

	public int parentIndex(int i) {
		return (i + 1) / arity;
	}

	public int firstChildIndex(int i) {
		return (i*arity)-arity+2;
	}
	public int size() {
		return currentSize; 
	}
	  
	public AnyType get(int index) { 
		return array[index]; 
	}
    
	public String toString() {
		String output = "[";
		
		if (array.length == 0)
			return "[]";
		
		output+=array[1];	
		for (int i = 1; i < array.length; i++) {
			output+=", "+array[i];
		}
		
		return output + "]";
	}
	
	
}
