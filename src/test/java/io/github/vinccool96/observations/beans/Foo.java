package io.github.vinccool96.observations.beans;

import io.github.vinccool96.observations.sun.property.PropertyReference;

// NOTE: Foo must be a public interface, otherwise the test in PropertySupportTest
// that uses Foo will fail.
public interface Foo {

    PropertyReference<String> NAME = new PropertyReference<>(Foo.class, "name");

    void setName(String name);

    String getName();

}
