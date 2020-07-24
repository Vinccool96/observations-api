package io.github.vinccool96.observations.beans;

public class WeakInvalidationListenerMock implements InvalidationListener, WeakListener {

    @Override
    public void invalidated(Observable observable) {
    }

    @Override
    public boolean wasGarbageCollected() {
        return true;
    }

}
