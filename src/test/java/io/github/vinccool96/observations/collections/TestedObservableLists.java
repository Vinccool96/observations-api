package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.property.SimpleListProperty;

import java.util.ArrayList;
import java.util.LinkedList;

public interface TestedObservableLists {

    Callable<ObservableList<String>> ARRAY_LIST = () -> ObservableCollections.observableList(new ArrayList<String>());

    Callable<ObservableList<String>> LINKED_LIST = () -> ObservableCollections.observableList(new LinkedList<String>());

    Callable<ObservableList<String>> CHECKED_OBSERVABLE_ARRAY_LIST = () -> ObservableCollections
            .checkedObservableList(ObservableCollections.observableList(new ArrayList<>()), String.class);

    Callable<ObservableList<String>> SYNCHRONIZED_OBSERVABLE_ARRAY_LIST = () -> ObservableCollections
            .synchronizedObservableList(ObservableCollections.observableList(new ArrayList<String>()));

    Callable<ObservableList<String>> OBSERVABLE_LIST_PROPERTY =
            () -> new SimpleListProperty<>(ObservableCollections.observableList(new ArrayList<String>()));

}
