package io.github.vinccool96.observations.beans;

import io.github.vinccool96.observations.sun.property.PropertyReference;

// NOTE: Foo must be a public interface, otherwise the test in PropertySupportTest
// that uses Foo will fail.
public interface Foo {

    public static final PropertyReference<String> NAME = new PropertyReference<String>(Foo.class, "name");

    public void setName(String name);

    public String getName();

}
