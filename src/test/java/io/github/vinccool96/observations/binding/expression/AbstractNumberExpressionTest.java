package io.github.vinccool96.observations.binding.expression;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.binding.*;
import io.github.vinccool96.observations.beans.property.DoubleProperty;
import io.github.vinccool96.observations.beans.property.IntegerProperty;
import io.github.vinccool96.observations.beans.property.SimpleDoubleProperty;
import io.github.vinccool96.observations.beans.property.SimpleIntegerProperty;
import io.github.vinccool96.observations.beans.value.*;
import io.github.vinccool96.observations.binding.DependencyUtils;
import io.github.vinccool96.observations.collections.ObservableCollections;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("WebTest")
public class AbstractNumberExpressionTest {

    private static final float EPSILON = 1e-6f;

    private double data1;

    private int data2;

    private DoubleProperty op1;

    private IntegerProperty op2;

    private double double1;

    private float float1;

    private long long1;

    private int integer1;

    private short short1;

    private byte byte1;

    @Before
    public void setUp() {
        data1 = 90224.8923;
        data2 = -13;
        op1 = new SimpleDoubleProperty(data1);
        op2 = new SimpleIntegerProperty(data2);
        double1 = -234.234;
        float1 = 111.9f;
        long1 = 2009234L;
        integer1 = -234734;
        short1 = 9824;
        byte1 = -123;
    }

    @Test
    public void testArithmetic() {
        final NumberBinding binding1 = op1.add(op2);
        assertEquals(data1 + data2, binding1.doubleValue(), EPSILON);

        final NumberBinding binding2 = op1.subtract(op2);
        assertEquals(data1 - data2, binding2.doubleValue(), EPSILON);

        final NumberBinding binding3 = op1.multiply(op2);
        assertEquals(data1 * data2, binding3.doubleValue(), EPSILON);

        final NumberBinding binding4 = op1.divide(op2);
        assertEquals(data1 / data2, binding4.doubleValue(), EPSILON);

    }

    @Test
    public void testEquals() {
        BooleanBinding binding = op1.isEqualTo(op1, EPSILON);
        assertEquals(true, binding.get());

        binding = op2.isEqualTo(op2);
        assertEquals(true, binding.get());

        binding = op1.isEqualTo(op2, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(data1, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isEqualTo(data2, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(double1, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(float1, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(long1, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(long1);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(integer1, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(integer1);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(short1, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(short1);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(byte1, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(byte1);
        assertEquals(false, binding.get());
    }

    @Test
    public void testNotEquals() {
        BooleanBinding binding = op1.isNotEqualTo(op1, EPSILON);
        assertEquals(false, binding.get());

        binding = op2.isNotEqualTo(op2);
        assertEquals(false, binding.get());

        binding = op1.isNotEqualTo(op2, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(data1, EPSILON);
        assertEquals(false, binding.get());

        binding = op1.isNotEqualTo(data2, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(double1, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(float1, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(long1, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(long1);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(integer1, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(integer1);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(short1, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(short1);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(byte1, EPSILON);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(byte1);
        assertEquals(true, binding.get());
    }

    @Test
    public void testGreater() {
        BooleanBinding binding = op1.greaterThan(op1);
        assertEquals(data1 > data1, binding.get());

        binding = op1.greaterThan(op2);
        assertEquals(data1 > data2, binding.get());

        binding = op2.greaterThan(op1);
        assertEquals(data2 > data1, binding.get());

        binding = op2.greaterThan(op2);
        assertEquals(data2 > data2, binding.get());

        binding = op1.greaterThan(data1);
        assertEquals(data1 > data1, binding.get());

        binding = op1.greaterThan(data2);
        assertEquals(data1 > data2, binding.get());

        binding = op2.greaterThan(data1);
        assertEquals(data2 > data1, binding.get());

        binding = op2.greaterThan(data2);
        assertEquals(data2 > data2, binding.get());

        binding = op1.greaterThan(double1);
        assertEquals(data1 > double1, binding.get());

        binding = op1.greaterThan(float1);
        assertEquals(data1 > float1, binding.get());

        binding = op1.greaterThan(long1);
        assertEquals(data1 > long1, binding.get());

        binding = op1.greaterThan(integer1);
        assertEquals(data1 > integer1, binding.get());

        binding = op1.greaterThan(short1);
        assertEquals(data1 > short1, binding.get());

        binding = op1.greaterThan(byte1);
        assertEquals(data1 > byte1, binding.get());
    }

    @Test
    public void testLesser() {
        BooleanBinding binding = op1.lessThan(op1);
        assertEquals(data1 < data1, binding.get());

        binding = op1.lessThan(op2);
        assertEquals(data1 < data2, binding.get());

        binding = op2.lessThan(op1);
        assertEquals(data2 < data1, binding.get());

        binding = op2.lessThan(op2);
        assertEquals(data2 < data2, binding.get());

        binding = op1.lessThan(data1);
        assertEquals(data1 < data1, binding.get());

        binding = op1.lessThan(data2);
        assertEquals(data1 < data2, binding.get());

        binding = op2.lessThan(data1);
        assertEquals(data2 < data1, binding.get());

        binding = op2.lessThan(data2);
        assertEquals(data2 < data2, binding.get());

        binding = op1.lessThan(double1);
        assertEquals(data1 < double1, binding.get());

        binding = op1.lessThan(float1);
        assertEquals(data1 < float1, binding.get());

        binding = op1.lessThan(long1);
        assertEquals(data1 < long1, binding.get());

        binding = op1.lessThan(integer1);
        assertEquals(data1 < integer1, binding.get());

        binding = op1.lessThan(short1);
        assertEquals(data1 < short1, binding.get());

        binding = op1.lessThan(byte1);
        assertEquals(data1 < byte1, binding.get());
    }

    @Test
    public void testGreaterOrEqual() {
        BooleanBinding binding = op1.greaterThanOrEqualTo(op1);
        assertEquals(data1 >= data1, binding.get());

        binding = op1.greaterThanOrEqualTo(op2);
        assertEquals(data1 >= data2, binding.get());

        binding = op2.greaterThanOrEqualTo(op1);
        assertEquals(data2 >= data1, binding.get());

        binding = op2.greaterThanOrEqualTo(op2);
        assertEquals(data2 >= data2, binding.get());

        binding = op1.greaterThanOrEqualTo(data1);
        assertEquals(data1 >= data1, binding.get());

        binding = op1.greaterThanOrEqualTo(data2);
        assertEquals(data1 >= data2, binding.get());

        binding = op2.greaterThanOrEqualTo(data1);
        assertEquals(data2 >= data1, binding.get());

        binding = op2.greaterThanOrEqualTo(data2);
        assertEquals(data2 >= data2, binding.get());

        binding = op1.greaterThanOrEqualTo(double1);
        assertEquals(data1 >= double1, binding.get());

        binding = op1.greaterThanOrEqualTo(float1);
        assertEquals(data1 >= float1, binding.get());

        binding = op1.greaterThanOrEqualTo(long1);
        assertEquals(data1 >= long1, binding.get());

        binding = op1.greaterThanOrEqualTo(integer1);
        assertEquals(data1 >= integer1, binding.get());

        binding = op1.greaterThanOrEqualTo(short1);
        assertEquals(data1 >= short1, binding.get());

        binding = op1.greaterThanOrEqualTo(byte1);
        assertEquals(data1 >= byte1, binding.get());
    }

    @Test
    public void testLesserOrEqual() {
        BooleanBinding binding = op1.lessThanOrEqualTo(op1);
        assertEquals(data1 <= data1, binding.get());

        binding = op1.lessThanOrEqualTo(op2);
        assertEquals(data1 <= data2, binding.get());

        binding = op2.lessThanOrEqualTo(op1);
        assertEquals(data2 <= data1, binding.get());

        binding = op2.lessThanOrEqualTo(op2);
        assertEquals(data2 <= data2, binding.get());

        binding = op1.lessThanOrEqualTo(data1);
        assertEquals(data1 <= data1, binding.get());

        binding = op1.lessThanOrEqualTo(data2);
        assertEquals(data1 <= data2, binding.get());

        binding = op2.lessThanOrEqualTo(data1);
        assertEquals(data2 <= data1, binding.get());

        binding = op2.lessThanOrEqualTo(data2);
        assertEquals(data2 <= data2, binding.get());

        binding = op1.lessThanOrEqualTo(double1);
        assertEquals(data1 <= double1, binding.get());

        binding = op1.lessThanOrEqualTo(float1);
        assertEquals(data1 <= float1, binding.get());

        binding = op1.lessThanOrEqualTo(long1);
        assertEquals(data1 <= long1, binding.get());

        binding = op1.lessThanOrEqualTo(integer1);
        assertEquals(data1 <= integer1, binding.get());

        binding = op1.lessThanOrEqualTo(short1);
        assertEquals(data1 <= short1, binding.get());

        binding = op1.lessThanOrEqualTo(byte1);
        assertEquals(data1 <= byte1, binding.get());
    }

    public void testFactory() {
        assertEquals(op1, NumberExpressionBase.numberExpression(op1));

        final ObservableDoubleValueStub double2 = new ObservableDoubleValueStub();
        double2.set(double1);
        NumberExpressionBase exp = NumberExpressionBase.numberExpression(double2);
        assertTrue(exp instanceof DoubleBinding);
        assertEquals(ObservableCollections.singletonObservableList(double2), ((NumberBinding) exp).getDependencies());
        assertEquals(double1, exp.doubleValue(), EPSILON);
        double2.set(0.0);
        assertEquals(0.0, exp.doubleValue(), EPSILON);

        final ObservableFloatValueStub float2 = new ObservableFloatValueStub();
        float2.set(float1);
        exp = NumberExpressionBase.numberExpression(float2);
        assertTrue(exp instanceof FloatBinding);
        assertEquals(ObservableCollections.singletonObservableList(float2), ((NumberBinding) exp).getDependencies());
        assertEquals(float1, exp.floatValue(), EPSILON);
        float2.set(0.0f);
        assertEquals(0.0f, exp.floatValue(), EPSILON);

        final ObservableLongValueStub long2 = new ObservableLongValueStub();
        long2.set(long1);
        exp = NumberExpressionBase.numberExpression(long2);
        assertTrue(exp instanceof LongBinding);
        assertEquals(ObservableCollections.singletonObservableList(long2), ((NumberBinding) exp).getDependencies());
        assertEquals(long1, exp.longValue());
        long2.set(0L);
        assertEquals(0, exp.longValue());

        final ObservableIntegerValueStub integer2 = new ObservableIntegerValueStub();
        integer2.set(integer1);
        exp = NumberExpressionBase.numberExpression(integer2);
        assertTrue(exp instanceof IntegerBinding);
        assertEquals(ObservableCollections.singletonObservableList(integer2), ((NumberBinding) exp).getDependencies());
        assertEquals(integer1, exp.intValue());
        integer2.set(0);
        assertEquals(0, exp.intValue());
    }

    @Test(expected = NullPointerException.class)
    public void testFactory_Null() {
        NumberExpressionBase.numberExpression(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFactory_UnknownClass() {
        NumberExpressionBase.numberExpression(new ObservableNumberValue() {

            @Override public void addListener(InvalidationListener observer) {
            }

            @Override public void addListener(ChangeListener observer) {
            }

            @Override public void removeListener(InvalidationListener observer) {
            }

            @Override public void removeListener(ChangeListener observer) {
            }

            @Override public Number getValue() {
                return null;
            }

            @Override public int intValue() {
                return 0;
            }

            @Override public long longValue() {
                return 0L;
            }

            @Override public float floatValue() {
                return 0.0f;
            }

            @Override public double doubleValue() {
                return 0.0;
            }
        });
    }

    @Test
    public void testAsString() {
        final IntegerProperty i = new SimpleIntegerProperty();
        final StringBinding s = i.asString();
        DependencyUtils.checkDependencies(s.getDependencies(), i);
        assertEquals("0", s.get());
        i.set(42);
        assertEquals("42", s.get());
    }

    @Test
    public void testAsString_Format() {
        final Locale defaultLocale = Locale.getDefault();
        try {
            // checking German default
            Locale.setDefault(Locale.GERMAN);
            final DoubleProperty d = new SimpleDoubleProperty(Math.PI);
            StringBinding s = d.asString("%.4f");
            DependencyUtils.checkDependencies(s.getDependencies(), d);
            assertEquals("3,1416", s.get());
            d.set(Math.E);
            assertEquals("2,7183", s.get());

            // checking US default
            Locale.setDefault(Locale.US);
            d.set(Math.PI);
            assertEquals("3.1416", s.get());
            d.set(Math.E);
            assertEquals("2.7183", s.get());
        } finally {
            Locale.setDefault(defaultLocale);
        }
    }

    @Test
    public void testAsString_LocaleFormat() {
        // checking German default
        final DoubleProperty d = new SimpleDoubleProperty(Math.PI);
        StringBinding s = d.asString(Locale.GERMAN, "%.4f");
        DependencyUtils.checkDependencies(s.getDependencies(), d);
        assertEquals("3,1416", s.get());
        d.set(Math.E);
        assertEquals("2,7183", s.get());

        // checking US default
        s = d.asString(Locale.US, "%.4f");
        DependencyUtils.checkDependencies(s.getDependencies(), d);
        d.set(Math.PI);
        assertEquals("3.1416", s.get());
        d.set(Math.E);
        assertEquals("2.7183", s.get());
    }

}
