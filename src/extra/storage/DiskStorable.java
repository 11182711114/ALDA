package extra.storage;

public interface DiskStorable<T> {
	/** Factory method
	 * @return a new T object
	 */
	public T create();
	/** How to store the object on disk to be able to read it later
	 * @return a String[] where each cell contains one line of the corresponding file.
	 */
	public String[] store();
	/** loads data read from disk to the object
	 * @param identifier Object identifier
	 * @param references references to other objects
	 */
	public void load(String identifier, Object[] references);
	
	/** Gets the unique identifier for this object
	 * @return unique identifier
	 */
	public String getIdentifier();
}
