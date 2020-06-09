package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.collections.transformation.FilteredList;
import io.github.vinccool96.observations.collections.transformation.SortedList;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * A list that allows listeners to track changes when they occur.
 *
 * @param <E>
 *         the list element type
 *
 * @see ListChangeListener
 * @see ListChangeListener.Change
 * @since JavaFX 2.0
 */
public interface ObservableList<E> extends List<E>, Observable {

    /**
     * Add a listener to this observable list.
     *
     * @param listener
     *         the listener for listening to the list changes
     */
    void addListener(ListChangeListener<? super E> listener);

    /**
     * Tries to removed a listener from this observable list. If the listener is not attached to this list, nothing
     * happens.
     *
     * @param listener
     *         a listener to remove
     */
    void removeListener(ListChangeListener<? super E> listener);

    /**
     * Verify if a {@code ArrayChangeListener} already exist for this {@code ObservableArray}.
     *
     * @param listener
     *         the {@code ArrayChangeListener} to verify
     *
     * @return {@code true}, if the listener already listens, {@code false} otherwise.
     */
    boolean isChangeListenerAlreadyAdded(ListChangeListener<? super E> listener);

    /**
     * A convenient method for var-arg adding of elements.
     *
     * @param elements
     *         the elements to add
     *
     * @return true (as specified by Collection.add(E))
     */
    boolean addAll(E... elements);

    /**
     * Clears the ObservableList and add all the elements passed as var-args.
     *
     * @param elements
     *         the elements to set
     *
     * @return true (as specified by Collection.add(E))
     *
     * @throws NullPointerException
     *         if the specified arguments contain one or more null elements
     */
    boolean setAll(E... elements);

    /**
     * Clears the ObservableList and add all elements from the collection.
     *
     * @param col
     *         the collection with elements that will be added to this observableArrayList
     *
     * @return true (as specified by Collection.add(E))
     *
     * @throws NullPointerException
     *         if the specified collection contains one or more null elements
     */
    boolean setAll(Collection<? extends E> col);

    /**
     * A convenient method for var-arg usage of removaAll method.
     *
     * @param elements
     *         the elements to be removed
     *
     * @return true if list changed as a result of this call
     */
    boolean removeAll(E... elements);

    /**
     * A convenient method for var-arg usage of retain method.
     *
     * @param elements
     *         the elements to be retained
     *
     * @return true if list changed as a result of this call
     */
    boolean retainAll(E... elements);

    /**
     * Basically a shortcut to sublist(from, to).clear() As this is a common operation, ObservableList has this method
     * for convenient usage.
     *
     * @param from
     *         the start of the range to remove (inclusive)
     * @param to
     *         the end of the range to remove (exclusive)
     *
     * @throws IndexOutOfBoundsException
     *         if an illegal range is provided
     */
    void remove(int from, int to);

    /**
     * Creates a {@link FilteredList} wrapper of this list using the specified predicate.
     *
     * @param predicate
     *         the predicate to use
     *
     * @return new {@code FilteredList}
     *
     * @since JavaFX 8.0
     */
    default FilteredList<E> filtered(Predicate<E> predicate) {
        return new FilteredList<E>(this, predicate);
    }

    /**
     * Creates a {@link SortedList} wrapper of this list using the specified comparator.
     *
     * @param comparator
     *         the comparator to use or null for unordered List
     *
     * @return new {@code SortedList}
     *
     * @since JavaFX 8.0
     */
    default SortedList<E> sorted(Comparator<E> comparator) {
        return new SortedList<E>(this, comparator);
    }

    /**
     * Creates a {@link SortedList} wrapper of this list with the natural ordering.
     *
     * @return new {@code SortedList}
     *
     * @since JavaFX 8.0
     */
    default SortedList<E> sorted() {
        Comparator<E> naturalOrder = new Comparator<E>() {

            @Override
            public int compare(E o1, E o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }

                if (o1 instanceof Comparable) {
                    return ((Comparable<E>) o1).compareTo(o2);
                }

                return Collator.getInstance().compare(o1.toString(), o2.toString());
            }
        };
        return sorted(naturalOrder);
    }

}
