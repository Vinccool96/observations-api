package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.property.StringProperty;
import io.github.vinccool96.observations.beans.property.StringPropertyBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Person implements Comparable<Person> {

    public StringProperty name = new StringPropertyBase("foo") {

        @Override
        public Object getBean() {
            return Person.this;
        }

        @Override
        public String getName() {
            return "name";
        }
    };

    public Person(String name) {
        this.name.set(name);
    }

    public Person() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.name.get() != other.name.get() &&
                (this.name.get() == null || !this.name.get().equals(other.name.get()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.name.get() != null ? this.name.get().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Person[" + name.get() + "]";
    }

    @Override
    public int compareTo(Person o) {
        return this.name.get().compareTo(o.name.get());
    }

    public static ObservableList<Person> createPersonsList(Person... persons) {
        ObservableList<Person> list = ObservableCollections.observableArrayList(
                (Person p) -> new Observable[]{p.name});
        list.addAll(persons);
        return list;
    }

    public static List<Person> createPersonsFromNames(String... names) {
        return Arrays.asList(names).stream().
                map(name -> new Person(name)).collect(Collectors.toList());
    }

    public static ObservableList<Person> createPersonsList(String... names) {
        ObservableList<Person> list = ObservableCollections.observableArrayList(
                (Person p) -> new Observable[]{p.name});
        list.addAll(createPersonsFromNames(names));
        return list;
    }

}
