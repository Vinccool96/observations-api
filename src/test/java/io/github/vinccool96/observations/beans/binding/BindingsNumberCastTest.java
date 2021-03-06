package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.property.*;
import io.github.vinccool96.observations.beans.value.ObservableNumberValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class BindingsNumberCastTest {

    public interface Functions {

        Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2);

        void check(double op1, double op2, Binding<?> binding);

    }

    private static final double EPSILON = 1e-5;

    private final Functions func;

    private Double double0;

    private Float float0;

    private Long long0;

    private Integer integer0;

    private DoubleProperty double1;

    private FloatProperty float1;

    private LongProperty long1;

    private IntegerProperty integer1;

    public BindingsNumberCastTest(Functions func) {
        this.func = func;
    }

    @Before
    public void setUp() {
        double0 = 3.1415;
        float0 = 2.71f;
        long0 = 111L;
        integer0 = 42;

        double1 = new SimpleDoubleProperty(double0);
        float1 = new SimpleFloatProperty(float0);
        long1 = new SimpleLongProperty(long0);
        integer1 = new SimpleIntegerProperty(integer0);
    }

    @Test
    public void testDouble() {
        Binding<?> binding = func.generateExpression(double1, double1);
        assertTrue(binding instanceof DoubleExpression || binding instanceof BooleanExpression);
        func.check(double0, double0, binding);

        binding = func.generateExpression(double1, float1);
        assertTrue(binding instanceof DoubleExpression || binding instanceof BooleanExpression);
        func.check(double0, float0, binding);

        binding = func.generateExpression(double1, long1);
        assertTrue(binding instanceof DoubleExpression || binding instanceof BooleanExpression);
        func.check(double0, long0, binding);

        binding = func.generateExpression(double1, integer1);
        assertTrue(binding instanceof DoubleExpression || binding instanceof BooleanExpression);
        func.check(double0, integer0, binding);
    }

    @Test
    public void testFloat() {
        Binding<?> binding = func.generateExpression(float1, double1);
        assertTrue(binding instanceof DoubleExpression || binding instanceof BooleanExpression);
        func.check(float0, double0, binding);

        binding = func.generateExpression(float1, float1);
        assertTrue(binding instanceof FloatExpression || binding instanceof BooleanExpression);
        func.check(float0, float0, binding);

        binding = func.generateExpression(float1, long1);
        assertTrue(binding instanceof FloatExpression || binding instanceof BooleanExpression);
        func.check(float0, long0, binding);

        binding = func.generateExpression(float1, integer1);
        assertTrue(binding instanceof FloatExpression || binding instanceof BooleanExpression);
        func.check(float0, integer0, binding);
    }

    @Test
    public void testLong() {
        Binding<?> binding = func.generateExpression(long1, double1);
        assertTrue(binding instanceof DoubleExpression || binding instanceof BooleanExpression);
        func.check(long0, double0, binding);

        binding = func.generateExpression(long1, float1);
        assertTrue(binding instanceof FloatExpression || binding instanceof BooleanExpression);
        func.check(long0, float0, binding);

        binding = func.generateExpression(long1, long1);
        assertTrue(binding instanceof LongExpression || binding instanceof BooleanExpression);
        func.check(long0, long0, binding);

        binding = func.generateExpression(long1, integer1);
        assertTrue(binding instanceof LongExpression || binding instanceof BooleanExpression);
        func.check(long0, integer0, binding);
    }

    @Test
    public void testInteger() {
        Binding<?> binding = func.generateExpression(integer1, double1);
        assertTrue(binding instanceof DoubleExpression || binding instanceof BooleanExpression);
        func.check(integer0, double0, binding);

        binding = func.generateExpression(integer1, float1);
        assertTrue(binding instanceof FloatExpression || binding instanceof BooleanExpression);
        func.check(integer0, float0, binding);

        binding = func.generateExpression(integer1, long1);
        assertTrue(binding instanceof LongExpression || binding instanceof BooleanExpression);
        func.check(integer0, long0, binding);

        binding = func.generateExpression(integer1, integer1);
        assertTrue(binding instanceof IntegerExpression || binding instanceof BooleanExpression);
        func.check(integer0, integer0, binding);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {
                        new Functions() {

                            @Override
                            public Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2) {
                                return Bindings.add(op1, op2);
                            }

                            @Override
                            public void check(double op1, double op2, Binding<?> binding) {
                                assertTrue(binding instanceof NumberExpression);
                                assertEquals(op1 + op2, ((NumberExpression) binding).doubleValue(), EPSILON);
                            }

                        }
                },
                {
                        new Functions() {

                            @Override
                            public Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2) {
                                return Bindings.multiply(op1, op2);
                            }

                            @Override
                            public void check(double op1, double op2, Binding<?> binding) {
                                assertTrue(binding instanceof NumberExpression);
                                assertEquals(op1 * op2, ((NumberExpression) binding).doubleValue(), EPSILON);
                            }

                        }
                },
                {
                        new Functions() {

                            @Override
                            public Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2) {
                                return Bindings.divide(op1, op2);
                            }

                            @Override
                            public void check(double op1, double op2, Binding<?> binding) {
                                assertTrue(binding instanceof NumberExpression);
                                if ((binding instanceof DoubleExpression) || (binding instanceof FloatExpression)) {
                                    assertEquals(op1 / op2, ((NumberExpression) binding).doubleValue(), EPSILON);
                                } else {
                                    assertEquals((long) op1 / (long) op2, ((NumberExpression) binding).longValue());
                                }
                            }

                        }
                },
                {
                        new Functions() {

                            @Override
                            public Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2) {
                                return Bindings.min(op1, op2);
                            }

                            @Override
                            public void check(double op1, double op2, Binding<?> binding) {
                                assertTrue(binding instanceof NumberExpression);
                                assertEquals(Math.min(op1, op2), ((NumberExpression) binding).doubleValue(), EPSILON);
                            }

                        }
                },
                {
                        new Functions() {

                            @Override
                            public Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2) {
                                return Bindings.max(op1, op2);
                            }

                            @Override
                            public void check(double op1, double op2, Binding<?> binding) {
                                assertTrue(binding instanceof NumberExpression);
                                assertEquals(Math.max(op1, op2), ((NumberExpression) binding).doubleValue(), EPSILON);
                            }

                        }
                },
                {
                        new Functions() {

                            @Override
                            public Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2) {
                                return Bindings.equal(op1, op2);
                            }

                            @Override
                            public void check(double op1, double op2, Binding<?> binding) {
                                assertTrue(binding instanceof BooleanExpression);
                                assertEquals(Math.abs(op1 - op2) < EPSILON, ((BooleanExpression) binding).get());
                            }

                        }
                },
                {
                        new Functions() {

                            @Override
                            public Binding<?> generateExpression(ObservableNumberValue op1, ObservableNumberValue op2) {
                                return Bindings.greaterThan(op1, op2);
                            }

                            @Override
                            public void check(double op1, double op2, Binding<?> binding) {
                                assertTrue(binding instanceof BooleanExpression);
                                assertEquals(op1 > op2, ((BooleanExpression) binding).get());
                            }

                        }
                },
        });
    }

}
