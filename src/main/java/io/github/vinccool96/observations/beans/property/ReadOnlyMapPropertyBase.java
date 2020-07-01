package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.collections.MapChangeListener;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.sun.binding.MapExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

/**
 * Base class for all readonly properties wrapping an {@link ObservableMap}. This class provides a default
 * implementation to attach listener.
 *
 * @see ReadOnlyMapProperty
 */
public abstract class ReadOnlyMapPropertyBase<K, V> extends ReadOnlyMapProperty<K, V> {

    private MapExpressionHelper<K, V> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        if (helper == null || !isInvalidationListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableMap<K, V>> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> listener) {
        if (helper == null || !isMapChangeListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        helper = MapExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isMapChangeListenerAlreadyAdded(MapChangeListener<? super K, ? super V> listener) {
        return ArrayUtils.getInstance().contains(helper.getMapChangeListeners(), listener);
    }

    /**
     * This method needs to be called if the reference to the {@link io.github.vinccool96.observations.collections.ObservableList}
     * changes.
     * <p>
     * It sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link io.github.vinccool96.observations.collections.ListChangeListener}.
     * <p>
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        MapExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * This method needs to be called if the content of the referenced {@link io.github.vinccool96.observations.collections.ObservableList}
     * changes.
     * <p>
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link io.github.vinccool96.observations.collections.ListChangeListener}.
     * <p>
     * This method is called when the content of the list changes.
     *
     * @param change
     *         the change that needs to be propagated
     */
    protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {
        MapExpressionHelper.fireValueChangedEvent(helper, change);
    }

}
