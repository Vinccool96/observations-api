package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.sun.binding.ExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

/**
 * Base class for all readonly properties wrapping a {@code double}. This class provides a default implementation to
 * attach listener.
 *
 * @see ReadOnlyDoubleProperty
 */
public abstract class ReadOnlyDoublePropertyBase extends ReadOnlyDoubleProperty {

    ExpressionHelper<Number> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        if (helper == null || !isInvalidationListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super Number> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    /**
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners} and {@link ChangeListener
     * ChangeListeners}.
     * <p>
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

}
