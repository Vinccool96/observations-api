package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.collections.ObservableSet;
import io.github.vinccool96.observations.collections.SetChangeListener;
import io.github.vinccool96.observations.sun.binding.SetExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

/**
 * Base class for all readonly properties wrapping an {@link ObservableSet}. This class provides a default
 * implementation to attach listener.
 *
 * @param <E>
 *         the type of the {@code Set} elements
 *
 * @see ReadOnlySetProperty
 * @since JavaFX 2.1
 */
public abstract class ReadOnlySetPropertyBase<E> extends ReadOnlySetProperty<E> {

    private SetExpressionHelper<E> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        if (helper == null || !isInvalidationListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableSet<E>> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableSet<E>> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableSet<E>> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        if (helper == null || !isSetChangeListenerAlreadyAdded(listener)) {
            helper = SetExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
        helper = SetExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isSetChangeListenerAlreadyAdded(SetChangeListener<? super E> listener) {
        return ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    /**
     * This method needs to be called if the reference to the {@link ObservableSet} changes.
     * <p>
     * It sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link SetChangeListener}.
     * <p>
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        SetExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * This method needs to be called if the content of the referenced {@link ObservableSet} changes.
     * <p>
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link SetChangeListener}.
     * <p>
     * This method is called when the content of the list changes.
     *
     * @param change
     *         the change that needs to be propagated
     */
    protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> change) {
        SetExpressionHelper.fireValueChangedEvent(helper, change);
    }

}
