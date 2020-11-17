package io.github.vinccool96.observations.beans.value;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.collections.ListChangeListener;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.sun.binding.ListExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressWarnings("SuspiciousToArrayCall")
public class ObservableListValueStub<E> implements ObservableListValue<E> {

    private final ObservableList<E> EMPTY_LIST = ObservableCollections.emptyObservableList();

    private ObservableList<E> value;

    public ObservableListValueStub() {
    }

    public ObservableListValueStub(ObservableList<E> value) {
        this.value = value;
    }

    public void set(ObservableList<E> value) {
        this.value = value;
        this.fireValueChangedEvent();
    }

    @Override
    public ObservableList<E> get() {
        return value;
    }

    @Override
    public ObservableList<E> getValue() {
        return value;
    }

    private ListExpressionHelper<E> helper;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(InvalidationListener listener) {
        if (!isInvalidationListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.addListener(helper, this, listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(InvalidationListener listener) {
        if (isInvalidationListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(ChangeListener<? super ObservableList<E>> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = ListExpressionHelper.addListener(helper, this, listener);
        }
    }

    /**
     * {@inheritDoc}
     */
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

    protected void fireValueChangedEvent() {
        ListExpressionHelper.fireValueChangedEvent(helper);
    }

    @Override
    public int size() {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.size() : list.size();
    }

    @Override
    public boolean isEmpty() {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.isEmpty() : list.isEmpty();
    }

    @Override
    public boolean contains(Object obj) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.contains(obj) : list.contains(obj);
    }

    @Override
    public Iterator<E> iterator() {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.iterator() : list.iterator();
    }

    @Override
    public Object[] toArray() {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.toArray() : list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.toArray(array) : list.toArray(array);
    }

    @Override
    public boolean add(E element) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.add(element) : list.add(element);
    }

    @Override
    public boolean remove(Object obj) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.remove(obj) : list.remove(obj);
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.containsAll(objects) : list.containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.addAll(elements) : list.addAll(elements);
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> elements) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.addAll(i, elements) : list.addAll(i, elements);
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.removeAll(objects) : list.removeAll(objects);
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.retainAll(objects) : list.retainAll(objects);
    }

    @Override
    public void clear() {
        final ObservableList<E> list = get();
        if (list == null) {
            EMPTY_LIST.clear();
        } else {
            list.clear();
        }
    }

    @Override
    public E get(int i) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.get(i) : list.get(i);
    }

    @Override
    public E set(int i, E element) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.set(i, element) : list.set(i, element);
    }

    @Override
    public void add(int i, E element) {
        final ObservableList<E> list = get();
        if (list == null) {
            EMPTY_LIST.add(i, element);
        } else {
            list.add(i, element);
        }
    }

    @Override
    public E remove(int i) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.remove(i) : list.remove(i);
    }

    @Override
    public int indexOf(Object obj) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.indexOf(obj) : list.indexOf(obj);
    }

    @Override
    public int lastIndexOf(Object obj) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.lastIndexOf(obj) : list.lastIndexOf(obj);
    }

    @Override
    public ListIterator<E> listIterator() {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.listIterator() : list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.listIterator(i) : list.listIterator(i);
    }

    @Override
    public List<E> subList(int from, int to) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.subList(from, to) : list.subList(from, to);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(E... elements) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.addAll(elements) : list.addAll(elements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setAll(E... elements) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.setAll(elements) : list.setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends E> elements) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.setAll(elements) : list.setAll(elements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeAll(E... elements) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.removeAll(elements) : list.removeAll(elements);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean retainAll(E... elements) {
        final ObservableList<E> list = get();
        return (list == null) ? EMPTY_LIST.retainAll(elements) : list.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        final ObservableList<E> list = get();
        if (list == null) {
            EMPTY_LIST.remove(from, to);
        } else {
            list.remove(from, to);
        }
    }

}
