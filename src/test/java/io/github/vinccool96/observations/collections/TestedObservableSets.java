package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.property.SimpleSetProperty;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public interface TestedObservableSets {

    Callable<ObservableSet<String>> HASH_SET = () -> ObservableCollections.observableSet(new HashSet<String>());

    Callable<ObservableSet<String>> TREE_SET = new CallableTreeSetImpl();

    Callable<ObservableSet<String>> LINKED_HASH_SET =
            () -> ObservableCollections.observableSet(new LinkedHashSet<String>());

    Callable<ObservableSet<String>> CHECKED_OBSERVABLE_HASH_SET = () -> ObservableCollections
            .checkedObservableSet(ObservableCollections.observableSet(new HashSet()), String.class);

    Callable<ObservableSet<String>> SYNCHRONIZED_OBSERVABLE_HASH_SET = () -> ObservableCollections
            .synchronizedObservableSet(ObservableCollections.observableSet(new HashSet<String>()));

    Callable<ObservableSet<String>> OBSERVABLE_SET_PROPERTY =
            () -> new SimpleSetProperty<>(ObservableCollections.observableSet(new HashSet<String>()));

    static class CallableTreeSetImpl implements Callable<ObservableSet<String>> {

        public CallableTreeSetImpl() {
        }

        @Override
        public ObservableSet<String> call() throws Exception {
            return ObservableCollections.observableSet(new TreeSet<String>());
        }

    }

}
