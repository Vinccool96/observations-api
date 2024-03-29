package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.property.SimpleMapProperty;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public interface TestedObservableMaps {

    Callable<ObservableMap<String, String>> HASH_MAP = () -> ObservableCollections.observableMap(new HashMap<>());

    Callable<ObservableMap<String, String>> TREE_MAP = new CallableTreeMapImpl();

    Callable<ObservableMap<String, String>> LINKED_HASH_MAP =
            () -> ObservableCollections.observableMap(new LinkedHashMap<>());

    Callable<ObservableMap<String, String>> CONCURRENT_HASH_MAP = new CallableConcurrentHashMapImpl();

    Callable<ObservableMap<String, String>> CHECKED_OBSERVABLE_HASH_MAP = () -> ObservableCollections
            .checkedObservableMap(ObservableCollections.observableMap(new HashMap<>()), String.class, String.class);

    Callable<ObservableMap<String, String>> SYNCHRONIZED_OBSERVABLE_HASH_MAP = () -> ObservableCollections
            .synchronizedObservableMap(ObservableCollections.observableMap(new HashMap<>()));

    Callable<ObservableMap<String, String>> OBSERVABLE_MAP_PROPERTY =
            () -> new SimpleMapProperty<>(ObservableCollections.observableMap(new HashMap<>()));

    class CallableTreeMapImpl implements Callable<ObservableMap<String, String>> {

        public CallableTreeMapImpl() {
        }

        @Override
        public ObservableMap<String, String> call() throws Exception {
            return ObservableCollections.observableMap(new TreeMap<>());
        }

    }

    class CallableConcurrentHashMapImpl implements Callable<ObservableMap<String, String>> {

        public CallableConcurrentHashMapImpl() {
        }

        @Override
        public ObservableMap<String, String> call() throws Exception {
            return ObservableCollections.observableMap(new ConcurrentHashMap<>());
        }

    }

}
