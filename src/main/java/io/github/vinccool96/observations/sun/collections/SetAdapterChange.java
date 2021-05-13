package io.github.vinccool96.observations.sun.collections;

import io.github.vinccool96.observations.collections.ObservableSet;
import io.github.vinccool96.observations.collections.SetChangeListener.Change;

public class SetAdapterChange<E> extends Change<E> {

    private final Change<? extends E> change;

    public SetAdapterChange(ObservableSet<E> set, Change<? extends E> change) {
        super(set);
        this.change = change;
    }

    @Override
    public boolean wasAdded() {
        return change.wasAdded();
    }

    @Override
    public boolean wasRemoved() {
        return change.wasRemoved();
    }

    @Override
    public E getElementAdded() {
        return change.getElementAdded();
    }

    @Override
    public E getElementRemoved() {
        return change.getElementRemoved();
    }

    @Override
    public String toString() {
        return change.toString();
    }

}
