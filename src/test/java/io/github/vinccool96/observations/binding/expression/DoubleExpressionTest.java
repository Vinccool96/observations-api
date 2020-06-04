package io.github.vinccool96.observations.binding.expression;

import io.github.vinccool96.observations.beans.binding.DoubleBinding;
import io.github.vinccool96.observations.beans.binding.DoubleExpression;
import io.github.vinccool96.observations.beans.binding.ObjectExpression;
import io.github.vinccool96.observations.beans.property.DoubleProperty;
import io.github.vinccool96.observations.beans.property.SimpleDoubleProperty;
import io.github.vinccool96.observations.beans.value.ObservableDoubleValueStub;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.beans.value.ObservableValueStub;
import io.github.vinccool96.observations.collections.ObservableCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoubleExpressionTest {

    private static final float EPSILON = 1e-6f;

    private double data;

    private DoubleProperty op1;

    private double double1;

    private float float1;

    private long long1;

    private int integer1;

    private short short1;

    private byte byte1;

    @Before
    public void setUp() {
        data = -67.0975;
        op1 = new SimpleDoubleProperty(data);
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
        final DoubleBinding binding1 = op1.negate();
        assertEquals(-data, binding1.doubleValue(), EPSILON);
    }

    @Test
    public void testPlus() {
        final DoubleBinding binding1 = op1.add(double1);
        assertEquals(data + double1, binding1.doubleValue(), EPSILON);

        final DoubleBinding binding2 = op1.add(float1);
        assertEquals(data + float1, binding2.doubleValue(), EPSILON);

        final DoubleBinding binding3 = op1.add(long1);
        assertEquals(data + long1, binding3.doubleValue(), EPSILON);

        final DoubleBinding binding4 = op1.add(integer1);
        assertEquals(data + integer1, binding4.doubleValue(), EPSILON);

        final DoubleBinding binding5 = op1.add(short1);
        assertEquals(data + short1, binding5.doubleValue(), EPSILON);

        final DoubleBinding binding6 = op1.add(byte1);
        assertEquals(data + byte1, binding6.doubleValue(), EPSILON);
    }

    @Test
    public void testMinus() {
        final DoubleBinding binding1 = op1.subtract(double1);
        assertEquals(data - double1, binding1.doubleValue(), EPSILON);

        final DoubleBinding binding2 = op1.subtract(float1);
        assertEquals(data - float1, binding2.doubleValue(), EPSILON);

        final DoubleBinding binding3 = op1.subtract(long1);
        assertEquals(data - long1, binding3.doubleValue(), EPSILON);

        final DoubleBinding binding4 = op1.subtract(integer1);
        assertEquals(data - integer1, binding4.doubleValue(), EPSILON);

        final DoubleBinding binding5 = op1.subtract(short1);
        assertEquals(data - short1, binding5.doubleValue(), EPSILON);

        final DoubleBinding binding6 = op1.subtract(byte1);
        assertEquals(data - byte1, binding6.doubleValue(), EPSILON);
    }

    @Test
    public void testTimes() {
        final DoubleBinding binding1 = op1.multiply(double1);
        assertEquals(data * double1, binding1.doubleValue(), EPSILON);

        final DoubleBinding binding2 = op1.multiply(float1);
        assertEquals(data * float1, binding2.doubleValue(), EPSILON);

        final DoubleBinding binding3 = op1.multiply(long1);
        assertEquals(data * long1, binding3.doubleValue(), EPSILON);

        final DoubleBinding binding4 = op1.multiply(integer1);
        assertEquals(data * integer1, binding4.doubleValue(), EPSILON);

        final DoubleBinding binding5 = op1.multiply(short1);
        assertEquals(data * short1, binding5.doubleValue(), EPSILON);

        final DoubleBinding binding6 = op1.multiply(byte1);
        assertEquals(data * byte1, binding6.doubleValue(), EPSILON);
    }

    @Test
    public void testDividedBy() {
        final DoubleBinding binding1 = op1.divide(double1);
        assertEquals(data / double1, binding1.doubleValue(), EPSILON);

        final DoubleBinding binding2 = op1.divide(float1);
        assertEquals(data / float1, binding2.doubleValue(), EPSILON);

        final DoubleBinding binding3 = op1.divide(long1);
        assertEquals(data / long1, binding3.doubleValue(), EPSILON);

        final DoubleBinding binding4 = op1.divide(integer1);
        assertEquals(data / integer1, binding4.doubleValue(), EPSILON);

        final DoubleBinding binding5 = op1.divide(short1);
        assertEquals(data / short1, binding5.doubleValue(), EPSILON);

        final DoubleBinding binding6 = op1.divide(byte1);
        assertEquals(data / byte1, binding6.doubleValue(), EPSILON);
    }

    @Test
    public void testAsObject() {
        final ObservableDoubleValueStub valueModel = new ObservableDoubleValueStub();
        final ObjectExpression<Double> exp = DoubleExpression.doubleExpression(valueModel).asObject();

        assertEquals(0.0, exp.getValue(), EPSILON);
        valueModel.set(data);
        assertEquals(data, exp.getValue(), EPSILON);
        valueModel.set(double1);
        assertEquals(double1, exp.getValue(), EPSILON);
    }

    @Test
    public void testFactory() {
        final ObservableDoubleValueStub valueModel = new ObservableDoubleValueStub();
        final DoubleExpression exp = DoubleExpression.doubleExpression(valueModel);

        assertTrue(exp instanceof DoubleBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((DoubleBinding) exp).getDependencies());

        assertEquals(0.0f, exp.doubleValue(), EPSILON);
        valueModel.set(data);
        assertEquals(data, exp.doubleValue(), EPSILON);
        valueModel.set(double1);
        assertEquals(double1, exp.doubleValue(), EPSILON);

        // make sure we do not create unnecessary bindings
        assertEquals(op1, DoubleExpression.doubleExpression(op1));
    }

    @Test
    public void testObjectToDouble() {
        final ObservableValueStub<Double> valueModel = new ObservableValueStub<Double>();
        final DoubleExpression exp = DoubleExpression.doubleExpression(valueModel);

        assertTrue(exp instanceof DoubleBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((DoubleBinding) exp).getDependencies());

        assertEquals(0.0, exp.doubleValue(), EPSILON);
        valueModel.set(data);
        assertEquals(data, exp.doubleValue(), EPSILON);
        valueModel.set(double1);
        assertEquals(double1, exp.doubleValue(), EPSILON);

        // make sure we do not create unnecessary bindings
        assertEquals(op1, DoubleExpression.doubleExpression((ObservableValue) op1));
    }

    @Test(expected = NullPointerException.class)
    public void testFactory_Null() {
        DoubleExpression.doubleExpression(null);
    }

}
