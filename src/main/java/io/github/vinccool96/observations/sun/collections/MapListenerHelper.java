package io.github.vinccool96.observations.sun.collections;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.collections.MapChangeListener;
import io.github.vinccool96.observations.collections.MapChangeListener.Change;
import io.github.vinccool96.observations.sun.binding.ExpressionHelperBase;
import io.github.vinccool96.observations.util.ArrayUtils;

import java.util.Arrays;

/**
 *
 */
@SuppressWarnings("unchecked")
public abstract class MapListenerHelper<K, V> extends ExpressionHelperBase {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static methods

    public static <K, V> MapListenerHelper<K, V> addListener(MapListenerHelper<K, V> helper,
            InvalidationListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? new SingleInvalidation<>(listener) : helper.addListener(listener);
    }

    public static <K, V> MapListenerHelper<K, V> removeListener(MapListenerHelper<K, V> helper,
            InvalidationListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <K, V> MapListenerHelper<K, V> addListener(MapListenerHelper<K, V> helper,
            MapChangeListener<? super K, ? super V> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? new SingleChange<>(listener) : helper.addListener(listener);
    }

    public static <K, V> MapListenerHelper<K, V> removeListener(MapListenerHelper<K, V> helper,
            MapChangeListener<? super K, ? super V> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return (helper == null) ? null : helper.removeListener(listener);
    }

    public static <K, V> void fireValueChangedEvent(MapListenerHelper<K, V> helper,
            Change<? extends K, ? extends V> change) {
        if (helper != null) {
            helper.fireValueChangedEvent(change);
        }
    }

    public static <K, V> boolean hasListeners(MapListenerHelper<K, V> helper) {
        return helper != null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Common implementations

    protected abstract MapListenerHelper<K, V> addListener(InvalidationListener listener);

    protected abstract MapListenerHelper<K, V> removeListener(InvalidationListener listener);

    protected abstract MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener);

    protected abstract MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener);

    protected abstract void fireValueChangedEvent(Change<? extends K, ? extends V> change);

    public abstract InvalidationListener[] getInvalidationListeners();

    public abstract MapChangeListener<? super K, ? super V>[] getMapChangeListeners();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Implementations

    private static class SingleInvalidation<K, V> extends MapListenerHelper<K, V> {

        private final InvalidationListener listener;

        private SingleInvalidation(InvalidationListener listener) {
            this.listener = listener;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(InvalidationListener listener) {
            return new Generic<>(this.listener, listener);
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(InvalidationListener listener) {
            return (listener.equals(this.listener)) ? null : this;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
            return new Generic<>(this.listener, listener);
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
            return this;
        }

        @Override
        protected void fireValueChangedEvent(Change<? extends K, ? extends V> change) {
            try {
                listener.invalidated(change.getMap());
            } catch (Exception e) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }

        @Override
        public InvalidationListener[] getInvalidationListeners() {
            return new InvalidationListener[]{this.listener};
        }

        @Override
        public MapChangeListener<? super K, ? super V>[] getMapChangeListeners() {
            return new MapChangeListener[0];
        }

    }

    private static class SingleChange<K, V> extends MapListenerHelper<K, V> {

        private final MapChangeListener<? super K, ? super V> listener;

        private SingleChange(MapChangeListener<? super K, ? super V> listener) {
            this.listener = listener;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(InvalidationListener listener) {
            return new Generic<>(listener, this.listener);
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(InvalidationListener listener) {
            return this;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
            return new Generic<>(this.listener, listener);
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
            return (listener.equals(this.listener)) ? null : this;
        }

        @Override
        protected void fireValueChangedEvent(Change<? extends K, ? extends V> change) {
            try {
                listener.onChanged(change);
            } catch (Exception e) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
            }
        }

        @Override
        public InvalidationListener[] getInvalidationListeners() {
            return new InvalidationListener[0];
        }

        @Override
        public MapChangeListener<? super K, ? super V>[] getMapChangeListeners() {
            return new MapChangeListener[]{this.listener};
        }

    }

    private static class Generic<K, V> extends MapListenerHelper<K, V> {

        private InvalidationListener[] invalidationListeners;

        private MapChangeListener<? super K, ? super V>[] changeListeners;

        private int invalidationSize;

        private int changeSize;

        private boolean locked;

        private Generic(InvalidationListener listener0, InvalidationListener listener1) {
            this.invalidationListeners = new InvalidationListener[]{listener0, listener1};
            this.invalidationSize = 2;
        }

        private Generic(MapChangeListener<? super K, ? super V> listener0,
                MapChangeListener<? super K, ? super V> listener1) {
            this.changeListeners = new MapChangeListener[]{listener0, listener1};
            this.changeSize = 2;
        }

        private Generic(InvalidationListener invalidationListener,
                MapChangeListener<? super K, ? super V> changeListener) {
            this.invalidationListeners = new InvalidationListener[]{invalidationListener};
            this.invalidationSize = 1;
            this.changeListeners = new MapChangeListener[]{changeListener};
            this.changeSize = 1;
        }

        @Override
        protected Generic<K, V> addListener(InvalidationListener listener) {
            if (invalidationListeners == null) {
                invalidationListeners = new InvalidationListener[]{listener};
                invalidationSize = 1;
            } else {
                final int oldCapacity = invalidationListeners.length;
                if (locked) {
                    final int newCapacity = (invalidationSize < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
                    invalidationListeners = Arrays.copyOf(invalidationListeners, newCapacity);
                } else if (invalidationSize == oldCapacity) {
                    invalidationSize = trim(invalidationSize, invalidationListeners);
                    if (invalidationSize == oldCapacity) {
                        final int newCapacity = (oldCapacity * 3) / 2 + 1;
                        invalidationListeners = Arrays.copyOf(invalidationListeners, newCapacity);
                    }
                }
                invalidationListeners[invalidationSize++] = listener;
            }
            return this;
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(InvalidationListener listener) {
            if (invalidationListeners != null) {
                for (int index = 0; index < invalidationSize; index++) {
                    if (listener.equals(invalidationListeners[index])) {
                        if (invalidationSize == 1) {
                            if (changeSize == 1) {
                                return new SingleChange<>(changeListeners[0]);
                            }
                            invalidationListeners = null;
                            invalidationSize = 0;
                        } else if ((invalidationSize == 2) && (changeSize == 0)) {
                            return new SingleInvalidation<>(invalidationListeners[1 - index]);
                        } else {
                            final int numMoved = invalidationSize - index - 1;
                            final InvalidationListener[] oldListeners = invalidationListeners;
                            if (locked) {
                                invalidationListeners = new InvalidationListener[invalidationListeners.length];
                                System.arraycopy(oldListeners, 0, invalidationListeners, 0, index);
                            }
                            if (numMoved > 0) {
                                System.arraycopy(oldListeners, index + 1, invalidationListeners, index, numMoved);
                            }
                            invalidationSize--;
                            if (!locked) {
                                invalidationListeners[invalidationSize] = null; // Let gc do its work
                            }
                        }
                        break;
                    }
                }
            }
            return this;
        }

        @Override
        protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> listener) {
            if (changeListeners == null) {
                changeListeners = new MapChangeListener[]{listener};
                changeSize = 1;
            } else {
                final int oldCapacity = changeListeners.length;
                if (locked) {
                    final int newCapacity = (changeSize < oldCapacity) ? oldCapacity : (oldCapacity * 3) / 2 + 1;
                    changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                } else if (changeSize == oldCapacity) {
                    changeSize = trim(changeSize, changeListeners);
                    if (changeSize == oldCapacity) {
                        final int newCapacity = (oldCapacity * 3) / 2 + 1;
                        changeListeners = Arrays.copyOf(changeListeners, newCapacity);
                    }
                }
                changeListeners[changeSize++] = listener;
            }
            return this;
        }

        @Override
        protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> listener) {
            if (changeListeners != null) {
                for (int index = 0; index < changeSize; index++) {
                    if (listener.equals(changeListeners[index])) {
                        if (changeSize == 1) {
                            if (invalidationSize == 1) {
                                return new SingleInvalidation<>(invalidationListeners[0]);
                            }
                            changeListeners = null;
                            changeSize = 0;
                        } else if ((changeSize == 2) && (invalidationSize == 0)) {
                            return new SingleChange<>(changeListeners[1 - index]);
                        } else {
                            final int numMoved = changeSize - index - 1;
                            final MapChangeListener<? super K, ? super V>[] oldListeners = changeListeners;
                            if (locked) {
                                changeListeners = new MapChangeListener[changeListeners.length];
                                System.arraycopy(oldListeners, 0, changeListeners, 0, index);
                            }
                            if (numMoved > 0) {
                                System.arraycopy(oldListeners, index + 1, changeListeners, index, numMoved);
                            }
                            changeSize--;
                            if (!locked) {
                                changeListeners[changeSize] = null; // Let gc do its work
                            }
                        }
                        break;
                    }
                }
            }
            return this;
        }

        @Override
        protected void fireValueChangedEvent(Change<? extends K, ? extends V> change) {
            final InvalidationListener[] curInvalidationList = invalidationListeners;
            final int curInvalidationSize = invalidationSize;
            final MapChangeListener<? super K, ? super V>[] curChangeList = changeListeners;
            final int curChangeSize = changeSize;

            try {
                locked = true;
                for (int i = 0; i < curInvalidationSize; i++) {
                    try {
                        curInvalidationList[i].invalidated(change.getMap());
                    } catch (Exception e) {
                        Thread.currentThread().getUncaughtExceptionHandler()
                                .uncaughtException(Thread.currentThread(), e);
                    }
                }
                for (int i = 0; i < curChangeSize; i++) {
                    try {
                        curChangeList[i].onChanged(change);
                    } catch (Exception e) {
                        Thread.currentThread().getUncaughtExceptionHandler()
                                .uncaughtException(Thread.currentThread(), e);
                    }
                }
            } finally {
                locked = false;
            }
        }

        @Override
        public InvalidationListener[] getInvalidationListeners() {
            return ArrayUtils.getInstance().clone(this.invalidationListeners, InvalidationListener.class);
        }

        @Override
        public MapChangeListener<? super K, ? super V>[] getMapChangeListeners() {
            return ArrayUtils.getInstance().clone(this.changeListeners, MapChangeListener.class);
        }

    }

}
