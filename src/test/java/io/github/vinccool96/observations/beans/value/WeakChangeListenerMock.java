package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.beans.WeakListener;

public class WeakChangeListenerMock implements ChangeListener<Object>, WeakListener {

    @Override
    public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
    }

    @Override
    public boolean wasGarbageCollected() {
        return true;
    }

}
