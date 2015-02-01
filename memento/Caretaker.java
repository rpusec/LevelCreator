package memento;

import java.util.ArrayList;

public class Caretaker {

	private ArrayList<Memento> mementoList = new ArrayList<Memento>();
	private int currMementoIndex = -1;
	
	public void addMemento(Memento m)
	{
		mementoList.add(m);
	}
	
	public Memento getMemento(int index)
	{
		return mementoList.get(index);
	}
	
	public void setCurrMementoIndex(int index)
	{
		currMementoIndex = index;
	}
	
	public int getCurrMementoIndex()
	{
		return currMementoIndex;
	}
	
	public ArrayList<Memento> getMementoList()
	{
		return mementoList;
	}
}
