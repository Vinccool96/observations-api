package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.collections.ListChangeListener;
import io.github.vinccool96.observations.collections.ListChangeListener.Change;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.sun.binding.ListExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

/**
 * Base class for all readonly properties wrapping a {@link ObservableList}. This class provides a default
 * implementation to attach listener.
 *
 * @see ReadOnlyListProperty
 */
public abstract class ReadOnlyListPropertyBase<E> extends ReadOnlyListProperty<E> {

    private ListExpressionHelper<E> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        if (!isInvalidationListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        if (isInvalidationListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableList<E>> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableList<E>> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableList<E>> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        if (!isListChangeListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        if (isListChangeListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isListChangeListenerAlreadyAdded(ListChangeListener<? super E> listener) {
        return helper != null && ArrayUtils.getInstance().contains(this.helper.getListChangeListeners(), listener);
    }

    /**
     * This method needs to be called if the reference to the {@link ObservableList} changes.
     * <p>
     * It sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link ListChangeListener ListChangeListeners}.
     * <p>
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        ListExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * This method needs to be called if the content of the referenced {@link ObservableList} changes.
     * <p>
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link ListChangeListener ListChangeListeners}.
     * <p>
     * This method is called when the content of the list changes.
     *
     * @param change
     *         the change that needs to be propagated
     */
    protected void fireValueChangedEvent(Change<? extends E> change) {
        ListExpressionHelper.fireValueChangedEvent(helper, change);
    }

}
