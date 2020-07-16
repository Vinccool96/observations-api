package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.sun.collections.ArrayListenerHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

/**
 * Abstract class that serves as a base class for {@link ObservableArray} implementations. The base class provides
 * listener handling functionality by implementing {@code addListener} and {@code removeListener} methods. {@link
 * #fireChange(boolean, int, int)} method is provided for notifying the listeners.
 *
 * @param <T>
 *         actual array instance type
 *
 * @see ObservableArray
 * @see ArrayChangeListener
 */
@SuppressWarnings("unchecked")
public abstract class ObservableArrayBase<T extends ObservableArray<T>> implements ObservableArray<T> {

    private ArrayListenerHelper<T> helper;

    @Override
    public final void addListener(InvalidationListener listener) {
        if (!isInvalidationListenerAlreadyAdded(listener)) {
            helper = ArrayListenerHelper.addListener(helper, (T) this, listener);
        }
    }

    @Override
    public final void removeListener(InvalidationListener listener) {
        if (isInvalidationListenerAlreadyAdded(listener)) {
            helper = ArrayListenerHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public final void addListener(ArrayChangeListener<T> listener) {
        if (!isArrayChangeListenerAlreadyAdded(listener)) {
            helper = ArrayListenerHelper.addListener(helper, (T) this, listener);
        }
    }

    @Override
    public final void removeListener(ArrayChangeListener<T> listener) {
        if (isArrayChangeListenerAlreadyAdded(listener)) {
            helper = ArrayListenerHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isArrayChangeListenerAlreadyAdded(ArrayChangeListener<T> listener) {
        return helper != null && ArrayUtils.getInstance().contains(this.helper.getChangeListeners(), listener);
    }

    /**
     * Notifies all listeners of a change
     *
     * @param sizeChanged
     *         if the size changed
     * @param from
     *         index of the change start
     * @param to
     *         index of the change to
     */
    protected final void fireChange(boolean sizeChanged, int from, int to) {
        ArrayListenerHelper.fireValueChangedEvent(helper, sizeChanged, from, to);
    }

}
