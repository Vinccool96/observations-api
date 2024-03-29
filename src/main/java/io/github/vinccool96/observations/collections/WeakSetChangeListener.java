package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.NamedArg;
import io.github.vinccool96.observations.beans.WeakListener;

import java.lang.ref.WeakReference;

/**
 * A {@code WeakSetChangeListener} can be used, if an {@link ObservableSet} should only maintain a weak reference to the
 * listener. This helps to avoid memory leaks, that can occur if observers are not unregistered from observed objects
 * after use.
 * <p>
 * {@code WeakSetChangeListener} are created by passing in the original {@link SetChangeListener}. The {@code
 * WeakSetChangeListener} should then be registered to listen for changes of the observed object.
 * <p>
 * Note: You have to keep a reference to the {@code SetChangeListener}, that was passed in as long as it is in use,
 * otherwise it will be garbage collected too soon.
 *
 * @param <E>
 *         The type of the observed value
 *
 * @see SetChangeListener
 * @see ObservableSet
 * @see WeakListener
 */
public final class WeakSetChangeListener<E> implements SetChangeListener<E>, WeakListener {

    private final WeakReference<SetChangeListener<E>> ref;

    /**
     * The constructor of {@code WeakSetChangeListener}.
     *
     * @param listener
     *         The original listener that should be notified
     */
    public WeakSetChangeListener(@NamedArg("listener") SetChangeListener<E> listener) {
        if (listener == null) {
            throw new NullPointerException("Listener must be specified.");
        }
        this.ref = new WeakReference<>(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean wasGarbageCollected() {
        return ref.get() == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onChanged(Change<? extends E> change) {
        final SetChangeListener<E> listener = ref.get();
        if (listener != null) {
            listener.onChanged(change);
        } else {
            // The weakly reference listener has been garbage collected, so this WeakListener will now unhook itself
            // from the source bean
            change.getSet().removeListener(this);
        }
    }

}
