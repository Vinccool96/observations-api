package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.NamedArg;
import io.github.vinccool96.observations.beans.WeakListener;

import java.lang.ref.WeakReference;

/**
 * A {@code WeakListChangeListener} can be used, if an {@link ObservableList} should only maintain a weak reference to
 * the listener. This helps to avoid memory leaks, that can occur if observers are not unregistered from observed
 * objects after use.
 * <p>
 * {@code WeakListChangeListener} are created by passing in the original {@link ListChangeListener}. The {@code
 * WeakListChangeListener} should then be registered to listen for changes of the observed object.
 * <p>
 * Note: You have to keep a reference to the {@code ListChangeListener}, that was passed in as long as it is in use,
 * otherwise it will be garbage collected to soon.
 *
 * @param <E>
 *         The type of the observed value
 *
 * @see ListChangeListener
 * @see ObservableList
 * @see WeakListener
 */
public final class WeakListChangeListener<E> implements ListChangeListener<E>, WeakListener {

    private final WeakReference<ListChangeListener<E>> ref;

    /**
     * The constructor of {@code WeakListChangeListener}.
     *
     * @param listener
     *         The original listener that should be notified
     */
    public WeakListChangeListener(@NamedArg("listener") ListChangeListener<E> listener) {
        if (listener == null) {
            throw new NullPointerException("Listener must be specified.");
        }
        this.ref = new WeakReference<ListChangeListener<E>>(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean wasGarbageCollected() {
        return (ref.get() == null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChanged(Change<? extends E> change) {
        final ListChangeListener<E> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            // The weakly reference listener has been garbage collected,
            // so this WeakListener will now unhook itself from the
            // source bean
            change.getList().removeListener(this);
        }
    }

}
