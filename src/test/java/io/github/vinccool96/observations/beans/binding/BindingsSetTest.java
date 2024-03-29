package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.property.SetProperty;
import io.github.vinccool96.observations.beans.property.SimpleSetProperty;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("OverwrittenKey")
public class BindingsSetTest {

    private static final Object data1 = new Object();

    private static final Object data2 = new Object();

    private SetProperty<Object> property;

    private ObservableSet<Object> set1;

    private ObservableSet<Object> set2;

    @Before
    public void setUp() {
        property = new SimpleSetProperty<>();
        set1 = ObservableCollections.observableSet(data1, data2);
        set2 = ObservableCollections.observableSet();
    }

    @Test
    public void testSize() {
        final IntegerBinding size = Bindings.size(property);
        DependencyUtils.checkDependencies(size.getDependencies(), property);

        assertEquals(0, size.get());
        property.set(set1);
        assertEquals(2, size.get());
        set1.remove(data2);
        assertEquals(1, size.get());
        property.set(set2);
        assertEquals(0, size.get());
        property.add(data2);
        property.add(data2);
        assertEquals(1, size.get());
        property.set(null);
        assertEquals(0, size.get());
    }

    @Test(expected = NullPointerException.class)
    public void testSize_Null() {
        Bindings.size((ObservableSet<Object>) null);
    }

    @Test
    public void testIsEmpty() {
        final BooleanBinding empty = Bindings.isEmpty(property);
        DependencyUtils.checkDependencies(empty.getDependencies(), property);

        assertTrue(empty.get());
        property.set(set1);
        assertFalse(empty.get());
        set1.remove(data2);
        assertFalse(empty.get());
        property.set(set2);
        assertTrue(empty.get());
        property.add(data2);
        property.add(data2);
        assertFalse(empty.get());
        property.set(null);
        assertTrue(empty.get());
    }

    @Test(expected = NullPointerException.class)
    public void testIsEmpty_Null() {
        Bindings.isEmpty((ObservableSet<Object>) null);
    }

    @Test
    public void testIsNotEmpty() {
        final BooleanBinding notEmpty = Bindings.isNotEmpty(property);
        DependencyUtils.checkDependencies(notEmpty.getDependencies(), property);

        assertFalse(notEmpty.get());
        property.set(set1);
        assertTrue(notEmpty.get());
        set1.remove(data2);
        assertTrue(notEmpty.get());
        property.set(set2);
        assertFalse(notEmpty.get());
        property.add(data2);
        property.add(data2);
        assertTrue(notEmpty.get());
        property.set(null);
        assertFalse(notEmpty.get());
    }

    @Test(expected = NullPointerException.class)
    public void testIsNotEmpty_Null() {
        Bindings.isNotEmpty((ObservableSet<Object>) null);
    }

}
