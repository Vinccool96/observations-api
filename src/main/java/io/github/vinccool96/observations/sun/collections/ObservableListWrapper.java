package io.github.vinccool96.observations.sun.collections;

import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.collections.ModifiableObservableListBase;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.sun.collections.NonIterableChange.SimplePermutationChange;
import io.github.vinccool96.observations.util.Callback;

import java.util.*;

/**
 * A List wrapper class that implements observability.
 */
public class ObservableListWrapper<E> extends ModifiableObservableListBase<E>
        implements ObservableList<E>, SortableList<E>, RandomAccess {

    private final List<E> backingList;

    private final ElementObserver<E> elementObserver;

    public ObservableListWrapper(List<E> list) {
        backingList = list;
        elementObserver = null;
    }

    public ObservableListWrapper(List<E> list, Callback<E, Observable[]> extractor) {
        backingList = list;
        this.elementObserver = new ElementObserver<>(extractor, param -> observable -> {
            beginChange();
            int i = 0;
            final int size = size();
            for (; i < size; ++i) {
                if (get(i) == param) {
                    nextUpdate(i);
                }
            }
            endChange();
        }, this);
        for (E e : backingList) {
            elementObserver.attachListener(e);
        }
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    protected void doAdd(int index, E element) {
        if (elementObserver != null) {
            elementObserver.attachListener(element);
        }
        backingList.add(index, element);
    }

    @Override
    protected E doSet(int index, E element) {
        E removed = backingList.set(index, element);
        if (elementObserver != null) {
            elementObserver.detachListener(removed);
            elementObserver.attachListener(element);
        }
        return removed;
    }

    @Override
    protected E doRemove(int index) {
        E removed = backingList.remove(index);
        if (elementObserver != null) {
            elementObserver.detachListener(removed);
        }
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        return backingList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return backingList.lastIndexOf(o);
    }

    @Override
    public boolean contains(Object o) {
        return backingList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backingList.containsAll(c);
    }

    @Override
    public void clear() {
        if (elementObserver != null) {
            for (E e : this) {
                elementObserver.detachListener(e);
            }
        }
        if (hasListeners()) {
            beginChange();
            nextRemove(0, this);
        }
        backingList.clear();
        ++modCount;
        if (hasListeners()) {
            endChange();
        }
    }

    @Override
    public void remove(int fromIndex, int toIndex) {
        beginChange();
        for (int i = fromIndex; i < toIndex; ++i) {
            remove(fromIndex);
        }
        endChange();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        beginChange();
        BitSet bs = new BitSet(c.size());
        for (int i = 0; i < size(); ++i) {
            if (c.contains(get(i))) {
                bs.set(i);
            }
        }
        if (!bs.isEmpty()) {
            int cur = size();
            while ((cur = bs.previousSetBit(cur - 1)) >= 0) {
                remove(cur);
            }
        }
        endChange();
        return !bs.isEmpty();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        beginChange();
        BitSet bs = new BitSet(c.size());
        for (int i = 0; i < size(); ++i) {
            if (!c.contains(get(i))) {
                bs.set(i);
            }
        }
        if (!bs.isEmpty()) {
            int cur = size();
            while ((cur = bs.previousSetBit(cur - 1)) >= 0) {
                remove(cur);
            }
        }
        endChange();
        return !bs.isEmpty();
    }

    private SortHelper helper;

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void sort() {
        if (backingList.isEmpty()) {
            return;
        }
        int[] perm = getSortHelper().sort((List<? extends Comparable>) backingList);
        fireChange(new SimplePermutationChange<>(0, size(), perm, this));
    }

    @Override
    public void sort(Comparator<? super E> comparator) {
        if (backingList.isEmpty()) {
            return;
        }
        int[] perm = getSortHelper().sort(backingList, comparator);
        fireChange(new SimplePermutationChange<>(0, size(), perm, this));
    }

    private SortHelper getSortHelper() {
        if (helper == null) {
            helper = new SortHelper();
        }
        return helper;
    }

}
