package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class SimpleListPropertyTest {

    private static final Object DEFAULT_BEAN = null;

    private static final String DEFAULT_NAME = "";

    private static final ObservableList<Object> DEFAULT_VALUE = null;

    private static final ObservableList<Object> VALUE_1 = ObservableCollections.observableArrayList(new Object());

    @Test
    public void testConstructor_NoArguments() {
        final ListProperty<Object> v = new SimpleListProperty<>();
        assertEquals(DEFAULT_BEAN, v.getBean());
        assertEquals(DEFAULT_NAME, v.getName());
        assertEquals(DEFAULT_VALUE, v.get());
    }

    @Test
    public void testConstructor_InitialValue() {
        final ListProperty<Object> v1 = new SimpleListProperty<>(VALUE_1);
        assertEquals(DEFAULT_BEAN, v1.getBean());
        assertEquals(DEFAULT_NAME, v1.getName());
        assertEquals(VALUE_1, v1.get());

        final ListProperty<Object> v2 = new SimpleListProperty<>(DEFAULT_VALUE);
        assertEquals(DEFAULT_BEAN, v2.getBean());
        assertEquals(DEFAULT_NAME, v2.getName());
        assertEquals(DEFAULT_VALUE, v2.get());
    }

    @Test
    public void testConstructor_Bean_Name() {
        final Object bean = new Object();
        final String name = "My name";
        final ListProperty<Object> v = new SimpleListProperty<>(bean, name);
        assertEquals(bean, v.getBean());
        assertEquals(name, v.getName());
        assertEquals(DEFAULT_VALUE, v.get());

        final ListProperty<Object> v2 = new SimpleListProperty<>(bean, null);
        assertEquals(bean, v2.getBean());
        assertEquals(DEFAULT_NAME, v2.getName());
        assertEquals(DEFAULT_VALUE, v2.get());
    }

    @Test
    public void testConstructor_Bean_Name_InitialValue() {
        final Object bean = new Object();
        final String name = "My name";
        final ListProperty<Object> v1 = new SimpleListProperty<>(bean, name, VALUE_1);
        assertEquals(bean, v1.getBean());
        assertEquals(name, v1.getName());
        assertEquals(VALUE_1, v1.get());

        final ListProperty<Object> v2 = new SimpleListProperty<>(bean, name, DEFAULT_VALUE);
        assertEquals(bean, v2.getBean());
        assertEquals(name, v2.getName());
        assertEquals(DEFAULT_VALUE, v2.get());

        final ListProperty<Object> v3 = new SimpleListProperty<>(bean, null, VALUE_1);
        assertEquals(bean, v3.getBean());
        assertEquals(DEFAULT_NAME, v3.getName());
        assertEquals(VALUE_1, v3.get());

        final ListProperty<Object> v4 = new SimpleListProperty<>(bean, null, DEFAULT_VALUE);
        assertEquals(bean, v4.getBean());
        assertEquals(DEFAULT_NAME, v4.getName());
        assertEquals(DEFAULT_VALUE, v4.get());
    }

}
