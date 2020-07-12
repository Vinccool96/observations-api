package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.property.IntegerProperty;
import io.github.vinccool96.observations.beans.property.SimpleIntegerProperty;
import io.github.vinccool96.observations.beans.value.ObservableIntegerValueStub;
import io.github.vinccool96.observations.beans.value.ObservableValueStub;
import io.github.vinccool96.observations.collections.ObservableCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegerExpressionTest {

    private static final float EPSILON = 1e-6f;

    private int data;

    private IntegerProperty op1;

    private double double1;

    private float float1;

    private long long1;

    private int integer1;

    private short short1;

    private byte byte1;

    @Before
    public void setUp() {
        data = 34258;
        op1 = new SimpleIntegerProperty(data);
        double1 = -234.234;
        float1 = 111.9f;
        long1 = 2009234L;
        integer1 = -234734;
        short1 = 9824;
        byte1 = -123;
    }

    @Test
    public void testGetters() {
        assertEquals(data, op1.doubleValue(), EPSILON);
        assertEquals((float) data, op1.floatValue(), EPSILON);
        assertEquals(data, op1.longValue());
        assertEquals(data, op1.intValue());
    }

    @Test
    public void testNegation() {
        final IntegerBinding binding1 = op1.negate();
        assertEquals(-data, binding1.intValue());
    }

    @Test
    public void testPlus() {
        final DoubleBinding binding1 = op1.add(double1);
        assertEquals(data + double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.add(float1);
        assertEquals(data + float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.add(long1);
        assertEquals(data + long1, binding3.longValue());

        final IntegerBinding binding4 = op1.add(integer1);
        assertEquals(data + integer1, binding4.intValue());

        final IntegerBinding binding5 = op1.add(short1);
        assertEquals(data + short1, binding5.intValue());

        final IntegerBinding binding6 = op1.add(byte1);
        assertEquals(data + byte1, binding6.intValue());
    }

    @Test
    public void testMinus() {
        final DoubleBinding binding1 = op1.subtract(double1);
        assertEquals(data - double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.subtract(float1);
        assertEquals(data - float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.subtract(long1);
        assertEquals(data - long1, binding3.longValue());

        final IntegerBinding binding4 = op1.subtract(integer1);
        assertEquals(data - integer1, binding4.intValue());

        final IntegerBinding binding5 = op1.subtract(short1);
        assertEquals(data - short1, binding5.intValue());

        final IntegerBinding binding6 = op1.subtract(byte1);
        assertEquals(data - byte1, binding6.intValue());
    }

    @Test
    public void testTimes() {
        final DoubleBinding binding1 = op1.multiply(double1);
        assertEquals(data * double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.multiply(float1);
        assertEquals(data * float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.multiply(long1);
        assertEquals(data * long1, binding3.longValue());

        final IntegerBinding binding4 = op1.multiply(integer1);
        assertEquals(data * integer1, binding4.intValue());

        final IntegerBinding binding5 = op1.multiply(short1);
        assertEquals(data * short1, binding5.intValue());

        final IntegerBinding binding6 = op1.multiply(byte1);
        assertEquals(data * byte1, binding6.intValue());
    }

    @Test
    public void testDividedBy() {
        final DoubleBinding binding1 = op1.divide(double1);
        assertEquals(data / double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.divide(float1);
        assertEquals(data / float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.divide(long1);
        assertEquals(data / long1, binding3.longValue());

        final IntegerBinding binding4 = op1.divide(integer1);
        assertEquals(data / integer1, binding4.intValue());

        final IntegerBinding binding5 = op1.divide(short1);
        assertEquals(data / short1, binding5.intValue());

        final IntegerBinding binding6 = op1.divide(byte1);
        assertEquals(data / byte1, binding6.intValue());
    }

    @Test
    public void testFactory() {
        final ObservableIntegerValueStub valueModel = new ObservableIntegerValueStub();
        final IntegerExpression exp = IntegerExpression.integerExpression(valueModel);

        assertTrue(exp instanceof IntegerBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((IntegerBinding) exp).getDependencies());

        assertEquals(0, exp.intValue());
        valueModel.set(data);
        assertEquals(data, exp.intValue());
        valueModel.set(integer1);
        assertEquals(integer1, exp.intValue());

        // make sure we do not create unnecessary bindings
        assertEquals(op1, IntegerExpression.integerExpression(op1));
    }

    @Test
    public void testAsObject() {
        final ObservableIntegerValueStub valueModel = new ObservableIntegerValueStub();
        final ObjectExpression<Integer> exp = IntegerExpression.integerExpression(valueModel).asObject();

        assertEquals(Integer.valueOf(0), exp.getValue());
        valueModel.set(data);
        assertEquals(Integer.valueOf(data), exp.getValue());
        valueModel.set(integer1);
        assertEquals(Integer.valueOf(integer1), exp.getValue());
    }

    @Test
    public void testObjectToInteger() {
        final ObservableValueStub<Integer> valueModel = new ObservableValueStub<>();
        final IntegerExpression exp = IntegerExpression.integerExpression(valueModel);

        assertTrue(exp instanceof IntegerBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((IntegerBinding) exp).getDependencies());

        assertEquals(0, exp.intValue());
        valueModel.set(data);
        assertEquals(data, exp.intValue());
        valueModel.set(integer1);
        assertEquals(integer1, exp.intValue());

        // make sure we do not create unnecessary bindings
        assertEquals(op1, IntegerExpression.integerExpression(op1));
    }

    @Test(expected = NullPointerException.class)
    public void testFactory_Null() {
        IntegerExpression.integerExpression(null);
    }

}
