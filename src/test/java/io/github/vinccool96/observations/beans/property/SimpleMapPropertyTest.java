package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.util.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class SimpleMapPropertyTest {

    private static final Object DEFAULT_BEAN = null;

    private static final String DEFAULT_NAME = "";

    private static final ObservableMap<Object, Object> DEFAULT_VALUE = null;

    private static final ObservableMap<Object, Object> VALUE_1 =
            ObservableCollections.observableHashMap(new Pair<>(new Object(), new Object()));

    @Test
    public void testConstructor_NoArguments() {
        final MapProperty<Object, Object> v = new SimpleMapProperty<>();
        assertEquals(DEFAULT_BEAN, v.getBean());
        assertEquals(DEFAULT_NAME, v.getName());
        assertEquals(DEFAULT_VALUE, v.get());
    }

    @Test
    public void testConstructor_InitialValue() {
        final MapProperty<Object, Object> v1 = new SimpleMapProperty<>(VALUE_1);
        assertEquals(DEFAULT_BEAN, v1.getBean());
        assertEquals(DEFAULT_NAME, v1.getName());
        assertEquals(VALUE_1, v1.get());

        final MapProperty<Object, Object> v2 = new SimpleMapProperty<>(DEFAULT_VALUE);
        assertEquals(DEFAULT_BEAN, v2.getBean());
        assertEquals(DEFAULT_NAME, v2.getName());
        assertEquals(DEFAULT_VALUE, v2.get());
    }

    @Test
    public void testConstructor_Bean_Name() {
        final Object bean = new Object();
        final String name = "My name";
        final MapProperty<Object, Object> v = new SimpleMapProperty<>(bean, name);
        assertEquals(bean, v.getBean());
        assertEquals(name, v.getName());
        assertEquals(DEFAULT_VALUE, v.get());

        final MapProperty<Object, Object> v2 = new SimpleMapProperty<>(bean, null);
        assertEquals(bean, v2.getBean());
        assertEquals(DEFAULT_NAME, v2.getName());
        assertEquals(DEFAULT_VALUE, v2.get());
    }

    @Test
    public void testConstructor_Bean_Name_InitialValue() {
        final Object bean = new Object();
        final String name = "My name";
        final MapProperty<Object, Object> v1 = new SimpleMapProperty<>(bean, name, VALUE_1);
        assertEquals(bean, v1.getBean());
        assertEquals(name, v1.getName());
        assertEquals(VALUE_1, v1.get());

        final MapProperty<Object, Object> v2 = new SimpleMapProperty<>(bean, name, DEFAULT_VALUE);
        assertEquals(bean, v2.getBean());
        assertEquals(name, v2.getName());
        assertEquals(DEFAULT_VALUE, v2.get());

        final MapProperty<Object, Object> v3 = new SimpleMapProperty<>(bean, null, VALUE_1);
        assertEquals(bean, v3.getBean());
        assertEquals(DEFAULT_NAME, v3.getName());
        assertEquals(VALUE_1, v3.get());

        final MapProperty<Object, Object> v4 = new SimpleMapProperty<>(bean, null, DEFAULT_VALUE);
        assertEquals(bean, v4.getBean());
        assertEquals(DEFAULT_NAME, v4.getName());
        assertEquals(DEFAULT_VALUE, v4.get());
    }

}
