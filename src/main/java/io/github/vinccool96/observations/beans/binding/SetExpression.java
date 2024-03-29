package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.property.ReadOnlyBooleanProperty;
import io.github.vinccool96.observations.beans.property.ReadOnlyIntegerProperty;
import io.github.vinccool96.observations.beans.value.ObservableSetValue;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.collections.ObservableSet;
import io.github.vinccool96.observations.collections.SetChangeListener;
import io.github.vinccool96.observations.sun.binding.StringFormatter;
import io.github.vinccool96.observations.sun.collections.annotations.ReturnsUnmodifiableCollection;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A {@code SetExpression} is a {@link ObservableSetValue} plus additional convenience methods to generate bindings in a
 * fluent style.
 * <p>
 * A concrete sub-class of {@code SetExpression} has to implement the method {@link ObservableSetValue#get()}, which
 * provides the actual value of this expression.
 * <p>
 * If the wrapped list of a {@code SetExpression} is {@code null}, all methods implementing the {@code Set} interface
 * will behave as if they were applied to an immutable empty set.
 *
 * @param <E>
 *         the type of the {@code Set} elements
 */
public abstract class SetExpression<E> implements ObservableSetValue<E> {

    private final ObservableSet<E> EMPTY_SET = new EmptyObservableSet<>();

    private static class EmptyObservableSet<E> extends AbstractSet<E> implements ObservableSet<E> {

        private final Iterator<E> iterator = new Iterator<E>() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };

        @Override
        public Iterator<E> iterator() {
            return iterator;
        }

        @Override
        public int size() {
            return 0;
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

        @Override
        public void addListener(SetChangeListener<? super E> setChangeListener) {
            // no-op
        }

        @Override
        public void removeListener(SetChangeListener<? super E> setChangeListener) {
            // no-op
        }

        @Override
        public boolean isSetChangeListenerAlreadyAdded(SetChangeListener<? super E> listener) {
            // no-op
            return false;
        }

    }

    @Override
    public ObservableSet<E> getValue() {
        return get();
    }

    /**
     * Returns a {@code SetExpression} that wraps a {@link ObservableSetValue}. If the {@code ObservableSetValue} is
     * already a {@code SetExpression}, it will be returned. Otherwise a new {@link SetBinding} is created that is bound
     * to the {@code ObservableSetValue}.
     *
     * @param value
     *         The source {@code ObservableSetValue}
     * @param <E>
     *         the type of the {@code Set} elements
     *
     * @return A {@code SetExpression} that wraps the {@code ObservableSetValue} if necessary
     *
     * @throws NullPointerException
     *         if {@code value} is {@code null}
     */
    public static <E> SetExpression<E> setExpression(final ObservableSetValue<E> value) {
        if (value == null) {
            throw new NullPointerException("Set must be specified.");
        }
        return value instanceof SetExpression ? (SetExpression<E>) value : new SetBinding<E>() {

            {
                super.bind(value);
            }

            @Override
            public void dispose() {
                super.unbind(value);
            }

            @Override
            protected ObservableSet<E> computeValue() {
                return value.get();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(value);
            }
        };
    }

    /**
     * The size of the set
     *
     * @return the size
     */
    public int getSize() {
        return size();
    }

    /**
     * An integer property that represents the size of the set.
     *
     * @return the property
     */
    public abstract ReadOnlyIntegerProperty sizeProperty();

    /**
     * A boolean property that is {@code true}, if the set is empty.
     *
     * @return the property
     */
    public abstract ReadOnlyBooleanProperty emptyProperty();

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this set is equal to another {@link
     * ObservableSet}.
     *
     * @param other
     *         the other {@code ObservableSet}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if {@code other} is {@code null}
     */
    public BooleanBinding isEqualTo(final ObservableSet<?> other) {
        return Bindings.equal(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if this set is not equal to another {@link
     * ObservableSet}.
     *
     * @param other
     *         the other {@code ObservableSet}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if {@code other} is {@code null}
     */
    public BooleanBinding isNotEqualTo(final ObservableSet<?> other) {
        return Bindings.notEqual(this, other);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the wrapped set is {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNull() {
        return Bindings.isNull(this);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the wrapped set is not {@code null}.
     *
     * @return the new {@code BooleanBinding}
     */
    public BooleanBinding isNotNull() {
        return Bindings.isNotNull(this);
    }

    /**
     * Creates a {@link StringBinding} that holds the value of the {@code SetExpression} turned into a {@code String}.
     * If the value of this {@code SetExpression} changes, the value of the {@code StringBinding} will be updated
     * automatically.
     *
     * @return the new {@code StringBinding}
     */
    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
    }

    @Override
    public int size() {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.size() : set.size();
    }

    @Override
    public boolean isEmpty() {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.isEmpty() : set.isEmpty();
    }

    @Override
    public boolean contains(Object obj) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.contains(obj) : set.contains(obj);
    }

    @Override
    public Iterator<E> iterator() {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.iterator() : set.iterator();
    }

    @Override
    public Object[] toArray() {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.toArray() : set.toArray();
    }

    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public <T> T[] toArray(T[] array) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.toArray(array) : set.toArray(array);
    }

    @Override
    public boolean add(E element) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.add(element) : set.add(element);
    }

    @Override
    public boolean remove(Object obj) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.remove(obj) : set.remove(obj);
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public boolean containsAll(Collection<?> objects) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.contains(objects) : set.containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.addAll(elements) : set.addAll(elements);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.removeAll(objects) : set.removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        final ObservableSet<E> set = get();
        return (set == null) ? EMPTY_SET.retainAll(objects) : set.retainAll(objects);
    }

    @Override
    public void clear() {
        final ObservableSet<E> set = get();
        if (set == null) {
            EMPTY_SET.clear();
        } else {
            set.clear();
        }
    }

}
