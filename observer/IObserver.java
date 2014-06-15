package observer;

import java.util.ArrayList;

public interface IObserver<T> {
	void notifyObserver(ArrayList<T> updatedStage);
}
