package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.binding.Bindings;
import io.github.vinccool96.observations.beans.property.*;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
@SuppressWarnings({"ConstantConditions", "EqualsWithItself", "EqualsBetweenInconvertibleTypes"})
public class BidirectionalBindingTest<T> {

    @FunctionalInterface
    private interface PropertyFactory<T> {

        Property<T> createProperty();

    }

    private static class Factory<T> {

        private final PropertyFactory<T> propertyFactory;

        private final T[] values;

        public Factory(PropertyFactory<T> propertyFactory, T[] values) {
            this.propertyFactory = propertyFactory;
            this.values = values;
        }

        public Property<T> createProperty() {
            return propertyFactory.createProperty();
        }

        public T[] getValues() {
            return values;
        }

    }

    private final Factory<T> factory;

    private Property<T> op1;

    private Property<T> op2;

    private Property<T> op3;

    private Property<T> op4;

    private T[] v;

    public BidirectionalBindingTest(Factory<T> factory) {
        this.factory = factory;
    }

    @Before
    public void setUp() {
        op1 = factory.createProperty();
        op2 = factory.createProperty();
        op3 = factory.createProperty();
        op4 = factory.createProperty();
        v = factory.getValues();
        op1.setValue(v[0]);
        op2.setValue(v[1]);
    }

    @Test
    public void testBind() {
        Bindings.bindBidirectional(op1, op2);
        Bindings.bindBidirectional(op1, op2);
        System.gc(); // making sure we did not not overdo weak references
        assertEquals(v[1], op1.getValue());
        assertEquals(v[1], op2.getValue());

        op1.setValue(v[2]);
        assertEquals(v[2], op1.getValue());
        assertEquals(v[2], op2.getValue());

        op2.setValue(v[3]);
        assertEquals(v[3], op1.getValue());
        assertEquals(v[3], op2.getValue());
    }

    @Test
    public void testUnbind() {
        // unbind non-existing binding => no-op
        Bindings.unbindBidirectional(op1, op2);

        // unbind properties of different beans
        Bindings.bindBidirectional(op1, op2);
        System.gc(); // making sure we did not not overdo weak references
        assertEquals(v[1], op1.getValue());
        assertEquals(v[1], op2.getValue());

        Bindings.unbindBidirectional(op1, op2);
        System.gc();
        assertEquals(v[1], op1.getValue());
        assertEquals(v[1], op2.getValue());

        op1.setValue(v[2]);
        assertEquals(v[2], op1.getValue());
        assertEquals(v[1], op2.getValue());

        op2.setValue(v[3]);
        assertEquals(v[2], op1.getValue());
        assertEquals(v[3], op2.getValue());
    }

    @Test
    public void testChaining() {
        op3.setValue(v[2]);
        Bindings.bindBidirectional(op1, op2);
        Bindings.bindBidirectional(op2, op3);
        System.gc(); // making sure we did not not overdo weak references
        assertEquals(v[2], op1.getValue());
        assertEquals(v[2], op2.getValue());
        assertEquals(v[2], op3.getValue());

        op1.setValue(v[3]);
        assertEquals(v[3], op1.getValue());
        assertEquals(v[3], op2.getValue());
        assertEquals(v[3], op3.getValue());

        op2.setValue(v[0]);
        assertEquals(v[0], op1.getValue());
        assertEquals(v[0], op2.getValue());
        assertEquals(v[0], op3.getValue());

        op3.setValue(v[1]);
        assertEquals(v[1], op1.getValue());
        assertEquals(v[1], op2.getValue());
        assertEquals(v[1], op3.getValue());

        // now unbind
        Bindings.unbindBidirectional(op1, op2);
        System.gc(); // making sure we did not not overdo weak references
        assertEquals(v[1], op1.getValue());
        assertEquals(v[1], op2.getValue());
        assertEquals(v[1], op3.getValue());

        op1.setValue(v[2]);
        assertEquals(v[2], op1.getValue());
        assertEquals(v[1], op2.getValue());
        assertEquals(v[1], op3.getValue());

        op2.setValue(v[3]);
        assertEquals(v[2], op1.getValue());
        assertEquals(v[3], op2.getValue());
        assertEquals(v[3], op3.getValue());

        op3.setValue(v[0]);
        assertEquals(v[2], op1.getValue());
        assertEquals(v[0], op2.getValue());
        assertEquals(v[0], op3.getValue());
    }

    private int getListenerCount(ObservableValue<T> v) {
        return ExpressionHelperUtility.getChangeListeners(v).size();
    }

    @Test
    public void testWeakReferencing() {
        Bindings.bindBidirectional(op1, op2);

        assertEquals(1, getListenerCount(op1));
        assertEquals(1, getListenerCount(op2));

        op1 = null;
        System.gc();
        op2.setValue(v[2]);
        assertEquals(0, getListenerCount(op2));

        Bindings.bindBidirectional(op2, op3);
        assertEquals(1, getListenerCount(op2));
        assertEquals(1, getListenerCount(op3));

        op3 = null;
        System.gc();
        op2.setValue(v[0]);
        assertEquals(0, getListenerCount(op2));
    }

    @Test
    public void testHashCode() {
        final int hc1 = BidirectionalBinding.bind(op1, op2).hashCode();
        final int hc2 = BidirectionalBinding.bind(op2, op1).hashCode();
        assertEquals(hc1, hc2);
    }

    @Test
    public void testEquals() {
        final BidirectionalBinding<T> golden = BidirectionalBinding.bind(op1, op2);

        assertEquals(golden, golden);
        assertNotNull(golden);
        assertNotEquals(golden, op1);
        assertEquals(golden, BidirectionalBinding.bind(op1, op2));
        assertEquals(golden, BidirectionalBinding.bind(op2, op1));
        assertNotEquals(golden, BidirectionalBinding.bind(op1, op3));
        assertNotEquals(golden, BidirectionalBinding.bind(op3, op1));
        assertNotEquals(golden, BidirectionalBinding.bind(op3, op2));
        assertNotEquals(golden, BidirectionalBinding.bind(op2, op3));
    }

    @Test
    public void testEqualsWithGCedProperty() {
        final BidirectionalBinding<T> binding1 = BidirectionalBinding.bind(op1, op2);
        final BidirectionalBinding<T> binding2 = BidirectionalBinding.bind(op1, op2);
        final BidirectionalBinding<T> binding3 = BidirectionalBinding.bind(op2, op1);
        final BidirectionalBinding<T> binding4 = BidirectionalBinding.bind(op2, op1);
        op1 = null;
        System.gc();

        assertEquals(binding1, binding1);
        assertNotEquals(binding1, binding2);
        assertNotEquals(binding1, binding3);

        assertEquals(binding3, binding3);
        assertNotEquals(binding3, binding1);
        assertNotEquals(binding3, binding4);
    }

    @Test(expected = NullPointerException.class)
    public void testBind_Null_X() {
        Bindings.bindBidirectional(null, op2);
    }

    @Test(expected = NullPointerException.class)
    public void testBind_X_Null() {
        Bindings.bindBidirectional(op1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBind_X_Self() {
        Bindings.bindBidirectional(op1, op1);
    }

    @Test(expected = NullPointerException.class)
    public void testUnbind_Null_X() {
        Bindings.unbindBidirectional(null, op2);
    }

    @Test(expected = NullPointerException.class)
    public void testUnbind_X_Null() {
        Bindings.unbindBidirectional(op1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnbind_X_Self() {
        Bindings.unbindBidirectional(op1, op1);
    }

    @Test
    public void testBrokenBind() {
        Bindings.bindBidirectional(op1, op2);
        op1.bind(op3);
        assertEquals(op3.getValue(), op1.getValue());
        assertEquals(op2.getValue(), op1.getValue());

        op2.setValue(v[2]);
        assertEquals(op3.getValue(), op1.getValue());
        assertEquals(op2.getValue(), op1.getValue());
    }

    @Test
    public void testDoubleBrokenBind() {
        Bindings.bindBidirectional(op1, op2);
        op1.bind(op3);
        op4.setValue(v[0]);

        op2.bind(op4);
        assertEquals(op4.getValue(), op2.getValue());
        assertEquals(op3.getValue(), op1.getValue());
        // Test that bidirectional binding was unbound in this case
        op3.setValue(v[0]);
        op4.setValue(v[1]);
        assertEquals(op4.getValue(), op2.getValue());
        assertEquals(op3.getValue(), op1.getValue());
        assertEquals(v[0], op1.getValue());
        assertEquals(v[1], op2.getValue());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        final Boolean[] booleanData = new Boolean[]{true, false, true, false};
        final Double[] doubleData = new Double[]{2348.2345, -92.214, -214.0214, -908.214};
        final Float[] floatData = new Float[]{-3592.9f, 234872.8347f, 3897.274f, 3958.938745f};
        final Long[] longData = new Long[]{9823984L, 2908934L, -234234L, 9089234L};
        final Integer[] integerData = new Integer[]{248, -9384, -234, -34};
        final Object[] objectData = new Object[]{new Object(), new Object(), new Object(), new Object()};
        final String[] stringData = new String[]{"A", "B", "C", "D"};

        return Arrays.asList(new Object[][]{
                {new Factory<>(SimpleBooleanProperty::new, booleanData)},
                {new Factory<>(SimpleDoubleProperty::new, doubleData)},
                {new Factory<>(SimpleFloatProperty::new, floatData)},
                {new Factory<>(SimpleIntegerProperty::new, integerData)},
                {new Factory<>(SimpleLongProperty::new, longData)},
                {new Factory<>(SimpleObjectProperty::new, objectData)},
                {new Factory<>(SimpleStringProperty::new, stringData)},
                {new Factory<>(ReadOnlyBooleanWrapper::new, booleanData)},
                {new Factory<>(ReadOnlyDoubleWrapper::new, doubleData)},
                {new Factory<>(ReadOnlyFloatWrapper::new, floatData)},
                {new Factory<>(ReadOnlyLongWrapper::new, longData)},
                {new Factory<>(ReadOnlyIntegerWrapper::new, integerData)},
                {new Factory<>(ReadOnlyObjectWrapper::new, objectData)},
                {new Factory<>(ReadOnlyStringWrapper::new, stringData)},
        });
    }

}
