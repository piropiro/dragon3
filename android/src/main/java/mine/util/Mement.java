/*
 * 作成日: 2003/12/28
 *
 */
package mine.util;

import java.util.LinkedList;


/**
 * @author k-saito
 *
 */
public class Mement<B> {

	private int max;
	
	private LinkedList<B> undoList;
	private LinkedList<B> redoList;
	private B current;
	
	public Mement(){
		this(20);
	}

	public Mement(int max){
		this.max = max;
		undoList = new LinkedList<B>();
		redoList = new LinkedList<B>();
	}
	
	public void keep(B obj){
		if (current != null && !current.equals(obj)) {
			undoList.addLast(current);
			if (undoList.size() > max) {
				undoList.removeFirst();
			}
			redoList.clear();
		}
		current = obj;
	}
	
	public B undo(){
		if (!undoList.isEmpty()) {
			redoList.addFirst(current);
			current = undoList.getLast();
			undoList.removeLast();
		}
		return current;
	}
	
	public B redo(){
		if (!redoList.isEmpty()) {
			undoList.addLast(current);
			current = redoList.getFirst();
			redoList.removeFirst();
		}
		return current;
	}
	
	public B getCurrent() {
		return current;
	}

	public boolean isUndoable(){
		return !undoList.isEmpty();
	}
	
	public boolean isRedoable(){
		return !redoList.isEmpty();
	}
}
