package memento;

import java.util.ArrayList;

/**
 * 
 * Saves and loads all mementos. 
 * @author Roman Pusec
 *
 */
public class Caretaker {

	private ArrayList<Memento> mementoList = new ArrayList<Memento>();
	private int currMementoIndex = -1;
	
	/**
	 * Adds a memento to the list. 
	 * @param m The target memento. 
	 */
	public void addMemento(Memento m)
	{
		currMementoIndex++;
		mementoList.add(currMementoIndex, m);
	}
	
	/**
	 * Retrieves a memento based on the specified index. 
	 * @param index The index of the memento. 
	 * @return The target memento. 
	 */
	public Memento getMemento(int index)
	{
		return mementoList.get(index);
	}
	
	/**
	 * Sets the current memento Index. 
	 * @param index The index of the currently referenced memento. 
	 */
	public void setCurrMementoIndex(int index)
	{
		currMementoIndex = index;
	}
	
	/**
	 * Gets the current memento index. 
	 * @return The index of the current memento. 
	 */
	public int getCurrMementoIndex()
	{
		return currMementoIndex;
	}
	
	/**
	 * Gets the ArrayList containing all of the mementos. 
	 * @return The ArrayList containing all mementos. 
	 */
	public ArrayList<Memento> getMementoList()
	{
		return mementoList;
	}
	
	/**
	 * Removes all of the mementos from the list and 
	 * resets the memento index. 
	 */
	public void emptyMementos()
	{
		mementoList.clear();
		currMementoIndex = -1;
	}
}
