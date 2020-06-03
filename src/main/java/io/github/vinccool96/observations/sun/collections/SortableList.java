package io.github.vinccool96.observations.sun.collections;

import java.util.Comparator;
import java.util.List;

/**
 * SortableList is a list that can sort itself in an efficient way, in contrast to the Collections.sort() method which
 * threat all lists the same way. E.g. ObservableList can sort and fire only one notification.
 *
 * @param <E>
 */
public interface SortableList<E> extends List<E> {

    /**
     * Sort using default comparator
     *
     * @throws ClassCastException
     *         if some of the elements cannot be cast to Comparable
     * @throws UnsupportedOperationException
     *         if list's iterator doesn't support set
     */
    void sort();

    /**
     * Sort using comparator
     *
     * @param comparator
     *         the comparator to use
     *
     * @throws ClassCastException
     *         if the list contains elements that are not
     *         <i>mutually comparable</i> using the specified comparator.
     * @throws UnsupportedOperationException
     *         if the specified list's list-iterator does not support the <tt>set</tt> operation.
     */
    void sort(Comparator<? super E> comparator);

}
