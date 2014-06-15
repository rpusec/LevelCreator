package observer;

import java.util.ArrayList;
import model.DisplayTexture;

public interface IObservable<T> {
	void subscribe(IObserver<DisplayTexture> o);
	void unsubscribe(IObserver<DisplayTexture> o);
	void updateStage(ArrayList<T> updatedStage);
}
