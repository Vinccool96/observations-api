package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.collections.MapChangeListener;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.sun.binding.MapExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

import java.lang.ref.WeakReference;

/**
 * The class {@code MapPropertyBase} is the base class for a property wrapping an {@link ObservableMap}.
 * <p>
 * It provides all the functionality required for a property except for the {@link #getBean()} and {@link #getName()}
 * methods, which must be implemented by extending classes.
 *
 * @param <K>
 *         the type of the key elements of the {@code Map}
 * @param <V>
 *         the type of the value elements of the {@code Map}
 *
 * @see ObservableMap
 * @see MapProperty
 */
public abstract class MapPropertyBase<K, V> extends MapProperty<K, V> {

    private ObservableMap<K, V> value;

    private ObservableValue<? extends ObservableMap<K, V>> observable = null;

    private InvalidationListener listener = null;

    private boolean valid = true;

    private MapExpressionHelper<K, V> helper = null;

    private final MapChangeListener<K, V> mapChangeListener = change -> {
        invalidateProperties();
        invalidated();
        fireValueChangedEvent(change);
    };

    private SizeProperty size0;

    private EmptyProperty empty0;

    /**
     * The constructor of {@code MapPropertyBase}
     */
    public MapPropertyBase() {
    }

    /**
     * The constructor of the {@code MapPropertyBase}.
     *
     * @param initialValue
     *         the initial value of the wrapped value
     */
    public MapPropertyBase(ObservableMap<K, V> initialValue) {
        this.value = initialValue;
        if (initialValue != null) {
            initialValue.addListener(mapChangeListener);
        }
    }

    @Override
    public ReadOnlyIntegerProperty sizeProperty() {
        if (size0 == null) {
            size0 = new SizeProperty();
        }
        return size0;
    }

    private class SizeProperty extends ReadOnlyIntegerPropertyBase {

        @Override
        public int get() {
            return size();
        }

        @Override
        public Object getBean() {
            return MapPropertyBase.this;
        }

        @Override
        public String getName() {
            return "size";
        }

        @Override
        protected void fireValueChangedEvent() {
            super.fireValueChangedEvent();
        }

    }

    @Override
    public ReadOnlyBooleanProperty emptyProperty() {
        if (empty0 == null) {
            empty0 = new EmptyProperty();
        }
        return empty0;
    }

    private class EmptyProperty extends ReadOnlyBooleanPropertyBase {

        @Override
        public boolean get() {
            return isEmpty();
        }

        @Override
        public Object getBean() {
            return MapPropertyBase.this;
        }

        @Override
        public String getName() {
            return "empty";
        }

        @Override
        protected void fireValueChangedEvent() {
            super.fireValueChangedEvent();
        }

    }

    @Override
    public void addListener(InvalidationListener listener) {
        if (!isInvalidationListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        if (isInvalidationListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
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
        if (!isMapChangeListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        if (isMapChangeListenerAlreadyAdded(listener)) {
            helper = MapExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isMapChangeListenerAlreadyAdded(MapChangeListener<? super K, ? super V> listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getMapChangeListeners(), listener);
    }

    /**
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link MapChangeListener}.
     * <p>
     * This method is called when the value is changed, either manually by calling {@link #set(ObservableMap)} or in
     * case of a bound property, if the binding becomes invalid.
     */
    protected void fireValueChangedEvent() {
        MapExpressionHelper.fireValueChangedEvent(helper);
    }

    /**
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners}, {@link ChangeListener
     * ChangeListeners}, and {@link MapChangeListener}.
     * <p>
     * This method is called when the content of the list changes.
     *
     * @param change
     *         the change that needs to be propagated
     */
    protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> change) {
        MapExpressionHelper.fireValueChangedEvent(helper, change);
    }

    private void invalidateProperties() {
        if (size0 != null) {
            size0.fireValueChangedEvent();
        }
        if (empty0 != null) {
            empty0.fireValueChangedEvent();
        }
    }

    private void markInvalid(ObservableMap<K, V> oldValue) {
        if (valid) {
            if (oldValue != null) {
                oldValue.removeListener(mapChangeListener);
            }
            valid = false;
            invalidateProperties();
            invalidated();
            fireValueChangedEvent();
        }
    }

    /**
     * The method {@code invalidated()} can be overridden to receive invalidation notifications. This is the preferred
     * option in {@code Objects} defining the property, because it requires less memory.
     * <p>
     * The default implementation is empty.
     */
    protected void invalidated() {
    }

    @Override
    public ObservableMap<K, V> get() {
        if (!valid) {
            value = observable == null ? value : observable.getValue();
            valid = true;
            if (value != null) {
                value.addListener(mapChangeListener);
            }
        }
        return value;
    }

    @Override
    public void set(ObservableMap<K, V> newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") +
                    "A bound value cannot be set.");
        }
        if (value != newValue) {
            final ObservableMap<K, V> oldValue = value;
            value = newValue;
            markInvalid(oldValue);
        }
    }

    @Override
    public boolean isBound() {
        return observable != null;
    }

    @Override
    public void bind(final ObservableValue<? extends ObservableMap<K, V>> newObservable) {
        if (newObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }
        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener<>(this);
            }
            observable.addListener(listener);
            markInvalid(value);
        }
    }

    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.getValue();
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * Returns a string representation of this {@code MapPropertyBase} object.
     *
     * @return a string representation of this {@code MapPropertyBase} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("MapProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if (name != null && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(get());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener<K, V> implements InvalidationListener {

        private final WeakReference<MapPropertyBase<K, V>> wref;

        public Listener(MapPropertyBase<K, V> ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            MapPropertyBase<K, V> ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid(ref.value);
            }
        }

    }

}
