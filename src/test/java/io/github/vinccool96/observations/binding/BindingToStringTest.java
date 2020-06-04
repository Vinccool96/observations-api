package io.github.vinccool96.observations.binding;

import io.github.vinccool96.observations.beans.binding.*;
import io.github.vinccool96.observations.beans.property.*;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BindingToStringTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testBooleanToString() {
        final boolean value1 = true;
        final boolean value2 = false;
        final BooleanProperty v = new SimpleBooleanProperty(value1);
        final BooleanBinding binding = new BooleanBinding() {

            {
                bind(v);
            }

            @Override
            protected boolean computeValue() {
                return v.get();
            }
        };

        assertEquals("BooleanBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("BooleanBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("BooleanBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("BooleanBinding [value: " + value2 + "]", binding.toString());
    }

    @Test
    public void testDoubleToString() {
        final double value1 = Math.PI;
        final double value2 = -Math.E;
        final DoubleProperty v = new SimpleDoubleProperty(value1);
        final DoubleBinding binding = new DoubleBinding() {

            {
                bind(v);
            }

            @Override
            protected double computeValue() {
                return v.get();
            }
        };

        assertEquals("DoubleBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("DoubleBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("DoubleBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("DoubleBinding [value: " + value2 + "]", binding.toString());
    }

    @Test
    public void testFloatToString() {
        final float value1 = (float) Math.PI;
        final float value2 = (float) -Math.E;
        final FloatProperty v = new SimpleFloatProperty(value1);
        final FloatBinding binding = new FloatBinding() {

            {
                bind(v);
            }

            @Override
            protected float computeValue() {
                return v.get();
            }
        };

        assertEquals("FloatBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("FloatBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("FloatBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("FloatBinding [value: " + value2 + "]", binding.toString());
    }

    @Test
    public void testIntegerToString() {
        final int value1 = 42;
        final int value2 = 987654321;
        final IntegerProperty v = new SimpleIntegerProperty(value1);
        final IntegerBinding binding = new IntegerBinding() {

            {
                bind(v);
            }

            @Override
            protected int computeValue() {
                return v.get();
            }
        };

        assertEquals("IntegerBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("IntegerBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("IntegerBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("IntegerBinding [value: " + value2 + "]", binding.toString());
    }

    @Test
    public void testLongToString() {
        final long value1 = -987654321234567890L;
        final long value2 = 1234567890987654321L;
        final LongProperty v = new SimpleLongProperty(value1);
        final LongBinding binding = new LongBinding() {

            {
                bind(v);
            }

            @Override
            protected long computeValue() {
                return v.get();
            }
        };

        assertEquals("LongBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("LongBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("LongBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("LongBinding [value: " + value2 + "]", binding.toString());
    }

    @Test
    public void testObjectToString() {
        final Object value1 = new Object();
        final Object value2 = new Object();
        final ObjectProperty<Object> v = new SimpleObjectProperty<Object>(value1);
        final ObjectBinding<Object> binding = new ObjectBinding<Object>() {

            {
                bind(v);
            }

            @Override
            protected Object computeValue() {
                return v.get();
            }
        };

        assertEquals("ObjectBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("ObjectBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("ObjectBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("ObjectBinding [value: " + value2 + "]", binding.toString());
    }

    @Test
    public void testStringToString() {
        final String value1 = "Hello World";
        final String value2 = "Goodbye";
        final StringProperty v = new SimpleStringProperty(value1);
        final StringBinding binding = new StringBinding() {

            {
                bind(v);
            }

            @Override
            protected String computeValue() {
                return v.get();
            }
        };

        assertEquals("StringBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("StringBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("StringBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("StringBinding [value: " + value2 + "]", binding.toString());
    }

    @Test
    public void testListToString() {
        final ObservableList<Object> value1 = ObservableCollections.observableArrayList(new Object());
        final ObservableList<Object> value2 = ObservableCollections.observableArrayList(new Object(), new Object());
        final ListProperty<Object> v = new SimpleListProperty<Object>(value1);
        final ListBinding<Object> binding = new ListBinding<Object>() {

            {
                bind(v);
            }

            @Override
            protected ObservableList<Object> computeValue() {
                return v.get();
            }
        };

        assertEquals("ListBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("ListBinding [value: " + value1 + "]", binding.toString());
        v.set(value2);
        assertEquals("ListBinding [invalid]", binding.toString());
        binding.get();
        assertEquals("ListBinding [value: " + value2 + "]", binding.toString());
    }

}
