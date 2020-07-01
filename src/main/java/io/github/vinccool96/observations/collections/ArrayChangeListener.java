package io.github.vinccool96.observations.collections;

/**
 * Interface that receives notifications of changes to an ObservableArray.
 *
 * @param <T>
 *         The type of the {@code ObservableArray}
 *
 * @see ObservableArray
*/
public interface ArrayChangeListener<T extends ObservableArray<T>> {

    /**
     * Called after a change has been made to an {@link ObservableArray}.
     *
     * @param observableArray
     *         The {@code ObservableArray}
     * @param sizeChanged
     *         indicates size of array changed
     * @param from
     *         A beginning (inclusive) of an interval related to the change
     * @param to
     *         An end (exclusive) of an interval related to the change.
     */
    void onChanged(T observableArray, boolean sizeChanged, int from, int to);

}
