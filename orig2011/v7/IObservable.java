package orig2011.v7;

import java.beans.PropertyChangeListener;

interface IObservable {
    public void addObserver(PropertyChangeListener observer);
    public void removeObserver(PropertyChangeListener observer);
}
