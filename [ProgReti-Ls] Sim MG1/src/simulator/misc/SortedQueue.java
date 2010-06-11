package simulator.misc;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class SortedQueue<E extends Comparable<E>> implements Queue<E> {
	
	private LinkedList<E> queue;
	
	public SortedQueue(){
		queue = new LinkedList<E>();
	}
	@Override
	public boolean add(E e) {
		int i =0;
		while(i<queue.size() && queue.get(i).compareTo(e) >= 0)
			i++;
		queue.add(i, e);
		return true;
	}

	@Override
	public E element() {
		return queue.element();
	}

	@Override
	public boolean offer(E e) {
		return add(e);
	}

	@Override
	public E peek() {
		return queue.peek();
	}

	@Override
	public E poll() {
		return queue.poll();
	}

	@Override
	public E remove() {
		return queue.remove();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return queue.addAll(c);
	}

	@Override
	public void clear() {
		queue.clear();
	}

	@Override
	public boolean contains(Object o) {
		return queue.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return queue.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return queue.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return queue.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return queue.retainAll(c);
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return queue.toArray(a);
	}

}
