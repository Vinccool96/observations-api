package io.github.vinccool96.observations.sun.collections;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.collections.ObservableListBase;
import io.github.vinccool96.observations.util.Callback;

import java.util.IdentityHashMap;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "unused"})
final class ElementObserver<E> {

    private static class ElementsMapElement {

        InvalidationListener listener;

        int counter;

        public ElementsMapElement(InvalidationListener listener) {
            this.listener = listener;
            this.counter = 1;
        }

        public void increment() {
            counter++;
        }

        public int decrement() {
            return --counter;
        }

        private InvalidationListener getListener() {
            return listener;
        }

    }

    private Callback<E, Observable[]> extractor;

    private final Callback<E, InvalidationListener> listenerGenerator;

    private final ObservableListBase<E> list;

    private IdentityHashMap<E, ElementsMapElement> elementsMap = new IdentityHashMap<>();

    ElementObserver(Callback<E, Observable[]> extractor, Callback<E, InvalidationListener> listenerGenerator,
            ObservableListBase<E> list) {
        this.extractor = extractor;
        this.listenerGenerator = listenerGenerator;
        this.list = list;
    }

    void attachListener(final E e) {
        if (elementsMap != null && e != null) {
            if (elementsMap.containsKey(e)) {
                elementsMap.get(e).increment();
            } else {
                InvalidationListener listener = listenerGenerator.call(e);
                for (Observable o : extractor.call(e)) {
                    o.addListener(listener);
                }
                elementsMap.put(e, new ElementsMapElement(listener));
            }
        }
    }

    void detachListener(E e) {
        if (elementsMap != null && e != null) {
            if (elementsMap.containsKey(e)) {
                ElementsMapElement el = elementsMap.get(e);
                for (Observable o : extractor.call(e)) {
                    o.removeListener(el.getListener());
                }
                if (el.decrement() == 0) {
                    elementsMap.remove(e);
                }
            }
        }
    }

}
