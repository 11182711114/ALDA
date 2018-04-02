//fredrik larsson frla9839 flarsson93@gmail.com
package alda.tree;

/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) De ändringar som är tillåtna är dock
 * begränsade av följande:
 * <ul>
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */
public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
		int compare = data.compareTo(this.data);

		if (compare == 0)
			return false;

		if (compare < 0 && left == null) {
			left = new BinarySearchTreeNode<T>(data);
			return true;
		} else if (compare > 0 && right == null) {
			right = new BinarySearchTreeNode<T>(data);
			return true;
		}

		return compare < 0 ? left.add(data) : right.add(data);
	}

	private T findMin() {
		if (left != null) {
			return left.findMin();
		}
		return data;
	}

	public BinarySearchTreeNode<T> remove(T data) {
		int compare = data.compareTo(this.data);

		if (compare < 0) { // element is smaller
			if (left != null) {
				left = left.remove(data);
			} else {
				return this;
			}
		} else if (compare > 0) { // element is larger
			if (right != null) {
				right = right.remove(data);
			} else {
				return this;
			}
		} else { // if we are the element
			if ((right != null || left != null) && !(right != null && left != null)) { // if we have one child
				return right != null ? right : left;
			} else if (right != null && left != null) { // if we have two children
				this.data = right.findMin();
				right = right.remove(this.data);
				return this;
			} else { // if we have no children
				return null;
			}
		}
		return this;
	}

	public boolean contains(T data) {
		int compare = data.compareTo(this.data);
		if (compare <= -1) {
			if (left != null)
				return left.contains(data);
		} else if (compare >= 1) {
			if (right != null)
				return right.contains(data);
		} else {
			return true;
		}
		return false;
	}

	public int size() {
		if (left != null && right != null) {
			return 1 + left.size() + right.size();
		} else if (left != null) {
			return 1 + left.size();
		} else if (right != null) {
			return 1 + right.size();
		} else {
			return 1;
		}
	}

	public int depth() {
		if (left != null && right != null) {
			return 1 + Math.max(left.depth(), right.depth());
		} else if (left != null) {
			return 1 + left.depth();
		} else if (right != null) {
			return 1 + right.depth();
		} else {
			return 0;
		}
	}

	public String toString() {
		if (left == null && right == null)
			return data.toString();
		else if (right == null)
			return left.toString() + ", " + data.toString();
		else if (left == null)
			return data.toString() + ", " + right.toString();

		return left.toString() + ", " + data.toString() + ", " + right.toString();
	}
}
