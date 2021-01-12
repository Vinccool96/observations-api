package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.collections.MapChangeListener;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.sun.binding.MapExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

import java.util.*;

@SuppressWarnings("unused")
public class ObservableMapValueStub<K, V> implements ObservableMapValue<K, V> {

    private final ObservableMap<K, V> EMPTY_MAP = new EmptyObservableMap<>();

    private ObservableMap<K, V> value;

    public ObservableMapValueStub() {
    }

    public ObservableMapValueStub(ObservableMap<K, V> value) {
        this.value = value;
    }

    public void set(ObservableMap<K, V> value) {
        this.value = value;
        this.fireValueChangedEvent();
    }

    @Override
    public ObservableMap<K, V> get() {
        return value;
    }

    @Override
    public ObservableMap<K, V> getValue() {
        return value;
    }

    private MapExpressionHelper<K, V> helper;

    @Override
    public void addListener(InvalidationListener listener) {
        if (!isInvalidationListenerAlreadyAdded(listener)) {
            this.helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        if (isInvalidationListenerAlreadyAdded(listener)) {
            this.helper = MapExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            this.helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super ObservableMap<K, V>> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            this.helper = MapExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super ObservableMap<K, V>> listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    @Override
    public void addListener(MapChangeListener<? super K, ? super V> listener) {
        if (!isMapChangeListenerAlreadyAdded(listener)) {
            this.helper = MapExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(MapChangeListener<? super K, ? super V> listener) {
        if (isMapChangeListenerAlreadyAdded(listener)) {
            this.helper = MapExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isMapChangeListenerAlreadyAdded(MapChangeListener<? super K, ? super V> listener) {
        return helper != null && ArrayUtils.getInstance().contains(helper.getMapChangeListeners(), listener);
    }

    protected void fireValueChangedEvent() {
        MapExpressionHelper.fireValueChangedEvent(helper);
    }

    @Override
    public int size() {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.size() : map.size();
    }

    @Override
    public boolean isEmpty() {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.isEmpty() : map.isEmpty();
    }

    @Override
    public boolean containsKey(Object obj) {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.containsKey(obj) : map.containsKey(obj);
    }

    @Override
    public boolean containsValue(Object obj) {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.containsValue(obj) : map.containsValue(obj);
    }

    @Override
    public V put(K key, V value) {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.put(key, value) : map.put(key, value);
    }

    @Override
    public V remove(Object obj) {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.remove(obj) : map.remove(obj);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> elements) {
        final ObservableMap<K, V> map = get();
        if (map == null) {
            EMPTY_MAP.putAll(elements);
        } else {
            map.putAll(elements);
        }
    }

    @Override
    public void clear() {
        final ObservableMap<K, V> map = get();
        if (map == null) {
            EMPTY_MAP.clear();
        } else {
            map.clear();
        }
    }

    @Override
    public Set<K> keySet() {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.keySet() : map.keySet();
    }

    @Override
    public Collection<V> values() {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.values() : map.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.entrySet() : map.entrySet();
    }

    @Override
    public V get(Object key) {
        final ObservableMap<K, V> map = get();
        return (map == null) ? EMPTY_MAP.get(key) : map.get(key);
    }

    private static class EmptyObservableMap<K, V> extends AbstractMap<K, V> implements ObservableMap<K, V> {

        @Override
        public Set<Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }

        @Override
        public void addListener(MapChangeListener<? super K, ? super V> mapChangeListener) {
            // no-op
        }

        @Override
        public void removeListener(MapChangeListener<? super K, ? super V> mapChangeListener) {
            // no-op
        }

        @Override
        public boolean isMapChangeListenerAlreadyAdded(MapChangeListener<? super K, ? super V> listener) {
            // no-op
            return false;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            // no-op
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            // no-op
        }

        @Override
        public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
            // no-op
            return false;
        }

    }

}
