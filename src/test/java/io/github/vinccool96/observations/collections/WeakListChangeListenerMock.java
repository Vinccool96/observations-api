package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.WeakListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;

public class WeakListChangeListenerMock implements ChangeListener<Object>, WeakListener {

    @Override public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
    }

    @Override public boolean wasGarbageCollected() {
        return true;
    }

}
