package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;

import java.util.ListIterator;

import static org.junit.Assert.assertTrue;

public class DependencyUtils {

    public static void checkDependencies(ObservableList<?> seq, Object... deps) {
        // we want to check the source dependencies, therefore we have to remove all intermediate bindings
        final ObservableList<Object> copy = ObservableCollections.observableArrayList(seq);
        final ListIterator<Object> it = copy.listIterator();
        while (it.hasNext()) {
            final Object obj = it.next();
            if (obj instanceof Binding) {
                it.remove();
                final Binding<?> binding = (Binding<?>) obj;
                for (final Object newDep : binding.getDependencies()) {
                    it.add(newDep);
                }
            }
        }
        for (final Object obj : deps) {
            assertTrue(copy.contains(obj));
        }
    }

}
