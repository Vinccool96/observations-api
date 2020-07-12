package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.property.ObjectProperty;
import io.github.vinccool96.observations.beans.property.SimpleObjectProperty;
import io.github.vinccool96.observations.beans.property.SimpleStringProperty;
import io.github.vinccool96.observations.beans.property.StringProperty;

public class Variable {

    public int numChangedListenersForNext = 0;

    public int numChangedListenersForName = 0;

    public Variable(String name) {
        this.name.set(name);
    }

    private final ObjectProperty<Object> next = new SimpleObjectProperty<Object>() {

        @Override
        public void addListener(InvalidationListener listener) {
            super.addListener(listener);
            numChangedListenersForNext++;
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            super.removeListener(listener);
            numChangedListenersForNext = Math.max(0, numChangedListenersForNext - 1);
        }
    };

    public Object getNext() {
        return next.get();
    }

    public void setNext(Object value) {
        next.set(value);
    }

    public ObjectProperty<Object> nextProperty() {
        return next;
    }

    private final StringProperty name = new SimpleStringProperty() {

        @Override
        public void addListener(InvalidationListener listener) {
            super.addListener(listener);
            numChangedListenersForName++;
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            super.removeListener(listener);
            numChangedListenersForName = Math.max(0, numChangedListenersForName - 1);
        }

    };

    public final String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return name.get();
    }

}
