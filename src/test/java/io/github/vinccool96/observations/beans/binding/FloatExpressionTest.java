package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.property.FloatProperty;
import io.github.vinccool96.observations.beans.property.SimpleFloatProperty;
import io.github.vinccool96.observations.beans.value.ObservableFloatValueStub;
import io.github.vinccool96.observations.beans.value.ObservableValueStub;
import io.github.vinccool96.observations.collections.ObservableCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FloatExpressionTest {

    private static final float EPSILON = 1e-6f;

    private float data;

    private FloatProperty op1;

    private double double1;

    private float float1;

    private long long1;

    private int integer1;

    private short short1;

    private byte byte1;

    @Before
    public void setUp() {
        data = 2.1f;
        op1 = new SimpleFloatProperty(data);
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
        assertEquals(data, op1.floatValue(), EPSILON);
        assertEquals((long) data, op1.longValue());
        assertEquals((int) data, op1.intValue());
    }

    @Test
    public void testNegation() {
        final FloatBinding binding1 = op1.negate();
        assertEquals(-data, binding1.floatValue(), EPSILON);
    }

    @Test
    public void testPlus() {
        final DoubleBinding binding1 = op1.add(double1);
        assertEquals(data + double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.add(float1);
        assertEquals(data + float1, binding2.floatValue(), EPSILON);

        final FloatBinding binding3 = op1.add(long1);
        assertEquals(data + long1, binding3.floatValue(), EPSILON);

        final FloatBinding binding4 = op1.add(integer1);
        assertEquals(data + integer1, binding4.floatValue(), EPSILON);

        final FloatBinding binding5 = op1.add(short1);
        assertEquals(data + short1, binding5.floatValue(), EPSILON);

        final FloatBinding binding6 = op1.add(byte1);
        assertEquals(data + byte1, binding6.floatValue(), EPSILON);
    }

    @Test
    public void testMinus() {
        final DoubleBinding binding1 = op1.subtract(double1);
        assertEquals(data - double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.subtract(float1);
        assertEquals(data - float1, binding2.floatValue(), EPSILON);

        final FloatBinding binding3 = op1.subtract(long1);
        assertEquals(data - long1, binding3.floatValue(), EPSILON);

        final FloatBinding binding4 = op1.subtract(integer1);
        assertEquals(data - integer1, binding4.floatValue(), EPSILON);

        final FloatBinding binding5 = op1.subtract(short1);
        assertEquals(data - short1, binding5.floatValue(), EPSILON);

        final FloatBinding binding6 = op1.subtract(byte1);
        assertEquals(data - byte1, binding6.floatValue(), EPSILON);
    }

    @Test
    public void testTimes() {
        final DoubleBinding binding1 = op1.multiply(double1);
        assertEquals(data * double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.multiply(float1);
        assertEquals(data * float1, binding2.floatValue(), EPSILON);

        final FloatBinding binding3 = op1.multiply(long1);
        assertEquals(data * long1, binding3.floatValue(), EPSILON);

        final FloatBinding binding4 = op1.multiply(integer1);
        assertEquals(data * integer1, binding4.floatValue(), EPSILON);

        final FloatBinding binding5 = op1.multiply(short1);
        assertEquals(data * short1, binding5.floatValue(), EPSILON);

        final FloatBinding binding6 = op1.multiply(byte1);
        assertEquals(data * byte1, binding6.floatValue(), EPSILON);
    }

    @Test
    public void testDividedBy() {
        final DoubleBinding binding1 = op1.divide(double1);
        assertEquals(data / double1, binding1.doubleValue(), EPSILON);

        final FloatBinding binding2 = op1.divide(float1);
        assertEquals(data / float1, binding2.floatValue(), EPSILON);

        final FloatBinding binding3 = op1.divide(long1);
        assertEquals(data / long1, binding3.floatValue(), EPSILON);

        final FloatBinding binding4 = op1.divide(integer1);
        assertEquals(data / integer1, binding4.floatValue(), EPSILON);

        final FloatBinding binding5 = op1.divide(short1);
        assertEquals(data / short1, binding5.floatValue(), EPSILON);

        final FloatBinding binding6 = op1.divide(byte1);
        assertEquals(data / byte1, binding6.floatValue(), EPSILON);
    }

    @Test
    public void testFactory() {
        final ObservableFloatValueStub valueModel = new ObservableFloatValueStub();
        final FloatExpression exp = FloatExpression.floatExpression(valueModel);

        assertTrue(exp instanceof FloatBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel), ((FloatBinding) exp).getDependencies());

        assertEquals(0.0f, exp.floatValue(), EPSILON);
        valueModel.set(data);
        assertEquals(data, exp.floatValue(), EPSILON);
        valueModel.set(float1);
        assertEquals(float1, exp.floatValue(), EPSILON);

        // make sure we do not create unnecessary bindings
        assertEquals(op1, FloatExpression.floatExpression(op1));
    }

    @Test
    public void testAsObject() {
        final ObservableFloatValueStub valueModel = new ObservableFloatValueStub();
        final ObjectExpression<Float> exp = FloatExpression.floatExpression(valueModel).asObject();

        assertEquals(0.0f, exp.getValue(), EPSILON);
        valueModel.set(data);
        assertEquals(data, exp.getValue(), EPSILON);
        valueModel.set(float1);
        assertEquals(float1, exp.getValue(), EPSILON);

    }

    @Test
    public void testObjectToFloat() {
        final ObservableValueStub<Float> valueModel = new ObservableValueStub<>();
        final FloatExpression exp = FloatExpression.floatExpression(valueModel);

        assertTrue(exp instanceof FloatBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel), ((FloatBinding) exp).getDependencies());

        assertEquals(0.0f, exp.floatValue(), EPSILON);
        valueModel.set(data);
        assertEquals(data, exp.floatValue(), EPSILON);
        valueModel.set(float1);
        assertEquals(float1, exp.floatValue(), EPSILON);

        // make sure we do not create unnecessary bindings
        assertEquals(op1, FloatExpression.floatExpression(op1));
    }

    @Test(expected = NullPointerException.class)
    public void testFactory_Null() {
        FloatExpression.floatExpression(null);
    }

}
