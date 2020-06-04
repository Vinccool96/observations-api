package io.github.vinccool96.observations.binding.expression;

import io.github.vinccool96.observations.beans.binding.*;
import io.github.vinccool96.observations.beans.property.LongProperty;
import io.github.vinccool96.observations.beans.property.SimpleLongProperty;
import io.github.vinccool96.observations.beans.value.ObservableLongValueStub;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.beans.value.ObservableValueStub;
import io.github.vinccool96.observations.collections.ObservableCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LongExpressionTest {

    private static final float EPSILON = 1e-6f;

    private long data;

    private LongProperty op1;

    private double double1;

    private float float1;

    private long long1;

    private int integer1;

    private short short1;

    private byte byte1;

    @Before
    public void setUp() {
        data = 34258;
        op1 = new SimpleLongProperty(data);
        double1 = -234.234;
        float1 = 111.9f;
        long1 = 2009234L;
        integer1 = -234734;
        short1 = 9824;
        byte1 = -123;
    }

    @Test
    public void testGetters() {
        assertEquals((double) data, op1.doubleValue(), EPSILON);
        assertEquals((float) data, op1.floatValue(), EPSILON);
        assertEquals((long) data, op1.longValue());
        assertEquals((int) data, op1.intValue());
    }

    @Test
    public void testNegation() {
        final LongBinding binding1 = op1.negate();
        assertEquals(-data, binding1.longValue());
    }

    @Test
    public void testPlus() {
        final DoubleBinding binding1 = op1.add(double1);
        assertEquals(data + double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.add(float1);
        assertEquals(data + float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.add(long1);
        assertEquals(data + long1, binding3.longValue());

        final LongBinding binding4 = op1.add(integer1);
        assertEquals(data + integer1, binding4.longValue());

        final LongBinding binding5 = op1.add(short1);
        assertEquals(data + short1, binding5.longValue());

        final LongBinding binding6 = op1.add(byte1);
        assertEquals(data + byte1, binding6.longValue());
    }

    @Test
    public void testMinus() {
        final DoubleBinding binding1 = op1.subtract(double1);
        assertEquals(data - double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.subtract(float1);
        assertEquals(data - float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.subtract(long1);
        assertEquals(data - long1, binding3.longValue());

        final LongBinding binding4 = op1.subtract(integer1);
        assertEquals(data - integer1, binding4.longValue());

        final LongBinding binding5 = op1.subtract(short1);
        assertEquals(data - short1, binding5.longValue());

        final LongBinding binding6 = op1.subtract(byte1);
        assertEquals(data - byte1, binding6.longValue());
    }

    @Test
    public void testTimes() {
        final DoubleBinding binding1 = op1.multiply(double1);
        assertEquals(data * double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.multiply(float1);
        assertEquals(data * float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.multiply(long1);
        assertEquals(data * long1, binding3.longValue());

        final LongBinding binding4 = op1.multiply(integer1);
        assertEquals(data * integer1, binding4.longValue());

        final LongBinding binding5 = op1.multiply(short1);
        assertEquals(data * short1, binding5.longValue());

        final LongBinding binding6 = op1.multiply(byte1);
        assertEquals(data * byte1, binding6.longValue());
    }

    @Test
    public void testDividedBy() {
        final DoubleBinding binding1 = op1.divide(double1);
        assertEquals(data / double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.divide(float1);
        assertEquals(data / float1, binding2.floatValue(), EPSILON);

        final LongBinding binding3 = op1.divide(long1);
        assertEquals(data / long1, binding3.longValue());

        final LongBinding binding4 = op1.divide(integer1);
        assertEquals(data / integer1, binding4.longValue());

        final LongBinding binding5 = op1.divide(short1);
        assertEquals(data / short1, binding5.longValue());

        final LongBinding binding6 = op1.divide(byte1);
        assertEquals(data / byte1, binding6.longValue());
    }

    @Test
    public void testFactory() {
        final ObservableLongValueStub valueModel = new ObservableLongValueStub();
        final LongExpression exp = LongExpression.longExpression(valueModel);

        assertTrue(exp instanceof LongBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel), ((LongBinding) exp).getDependencies());

        assertEquals(0L, exp.longValue());
        valueModel.set(data);
        assertEquals(data, exp.longValue());
        valueModel.set(long1);
        assertEquals(long1, exp.longValue());

        // make sure we do not create unnecessary bindings
        assertEquals(op1, LongExpression.longExpression(op1));
    }

    @Test
    public void testAsObject() {
        final ObservableLongValueStub valueModel = new ObservableLongValueStub();
        final ObjectExpression<Long> exp = LongExpression.longExpression(valueModel).asObject();

        assertEquals(Long.valueOf(0L), exp.getValue());
        valueModel.set(data);
        assertEquals(Long.valueOf(data), exp.getValue());
        valueModel.set(long1);
        assertEquals(Long.valueOf(long1), exp.getValue());
    }

    @Test
    public void testObjectToLong() {
        final ObservableValueStub<Long> valueModel = new ObservableValueStub<Long>();
        final LongExpression exp = LongExpression.longExpression(valueModel);

        assertTrue(exp instanceof LongBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel), ((LongBinding) exp).getDependencies());

        assertEquals(0L, exp.longValue());
        valueModel.set(data);
        assertEquals(data, exp.longValue());
        valueModel.set(long1);
        assertEquals(long1, exp.longValue());

        // make sure we do not create unnecessary bindings
        assertEquals(op1, LongExpression.longExpression((ObservableValue) op1));
    }

    @Test(expected = NullPointerException.class)
    public void testFactory_Null() {
        LongExpression.longExpression(null);
    }

}
