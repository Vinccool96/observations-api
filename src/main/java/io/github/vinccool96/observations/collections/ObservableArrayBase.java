package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.sun.collections.ArrayListenerHelper;

/**
 * Abstract class that serves as a base class for {@link ObservableArray} implementations. The base class provides
 * listener handling functionality by implementing {@code addListener} and {@code removeListener} methods. {@link
 * #fireChange(boolean, int, int) } method is provided for notifying the listeners.
 *
 * @param <T>
 *         actual array instance type
 *
 * @see ObservableArray
 * @see ArrayChangeListener
 * @since JavaFX 8.0
 */
public abstract class ObservableArrayBase<T extends ObservableArray<T>> implements
        ObservableArray<T> {

    private ArrayListenerHelper<T> listenerHelper;

    @Override public final void addListener(InvalidationListener listener) {
        listenerHelper = ArrayListenerHelper.<T>addListener(listenerHelper, (T) this, listener);
    }

    @Override public final void removeListener(InvalidationListener listener) {
        listenerHelper = ArrayListenerHelper.removeListener(listenerHelper, listener);
    }

    @Override public final void addListener(ArrayChangeListener<T> listener) {
        listenerHelper = ArrayListenerHelper.<T>addListener(listenerHelper, (T) this, listener);
    }

    @Override public final void removeListener(ArrayChangeListener<T> listener) {
        listenerHelper = ArrayListenerHelper.removeListener(listenerHelper, listener);
    }

    /**
     * Notifies all listeners of a change
     *
     * @param change
     */
    protected final void fireChange(boolean sizeChanged, int from, int to) {
        ArrayListenerHelper.fireValueChangedEvent(listenerHelper, sizeChanged, from, to);
    }

}
