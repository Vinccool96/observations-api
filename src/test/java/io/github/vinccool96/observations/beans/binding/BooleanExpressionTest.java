package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.property.BooleanProperty;
import io.github.vinccool96.observations.beans.property.SimpleBooleanProperty;
import io.github.vinccool96.observations.beans.value.ObservableBooleanValueStub;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.beans.value.ObservableValueStub;
import io.github.vinccool96.observations.collections.ObservableCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({"SimplifiableJUnitAssertion", "PointlessBooleanExpression", "ConstantConditions"})
public class BooleanExpressionTest {

    private BooleanProperty op1;

    private BooleanProperty op2;

    @Before
    public void setUp() {
        op1 = new SimpleBooleanProperty(true);
        op2 = new SimpleBooleanProperty(false);
    }

    @Test
    public void testGetters() {
        assertEquals(true, op1.get());
        assertEquals(Boolean.TRUE, op1.getValue());

        assertEquals(false, op2.get());
        assertEquals(Boolean.FALSE, op2.getValue());
    }

    @Test
    public void testAND() {
        final BooleanExpression exp = op1.and(op2);
        assertEquals(true && false, exp.get());

        op1.set(false);
        assertEquals(false && false, exp.get());

        op2.set(true);
        assertEquals(false && true, exp.get());

        op1.set(true);
        assertEquals(true && true, exp.get());
    }

    @Test
    public void testOR() {
        final BooleanExpression exp = op1.or(op2);
        assertEquals(true || false, exp.get());

        op1.set(false);
        assertEquals(false || false, exp.get());

        op2.set(true);
        assertEquals(false || true, exp.get());

        op1.set(true);
        assertEquals(true || true, exp.get());
    }

    @Test
    public void testNOT() {
        final BooleanExpression exp = op1.not();
        assertEquals(false, exp.get());

        op1.set(false);
        assertEquals(true, exp.get());

        op1.set(true);
        assertEquals(false, exp.get());
    }

    @Test
    public void testEquals() {
        final BooleanExpression exp = op1.isEqualTo(op2);
        assertEquals(true == false, exp.get());

        op1.set(false);
        assertEquals(false == false, exp.get());

        op2.set(true);
        assertEquals(false == true, exp.get());

        op1.set(true);
        assertEquals(true == true, exp.get());
    }

    @Test
    public void testNotEquals() {
        final BooleanExpression exp = op1.isNotEqualTo(op2);
        assertEquals(true != false, exp.get());

        op1.set(false);
        assertEquals(false != false, exp.get());

        op2.set(true);
        assertEquals(false != true, exp.get());

        op1.set(true);
        assertEquals(true != true, exp.get());
    }

    @Test
    public void testAsString() {
        final StringBinding binding = op1.asString();
        DependencyUtils.checkDependencies(binding.getDependencies(), op1);
        assertEquals("true", binding.get());

        op1.set(false);
        assertEquals("false", binding.get());

        op1.set(true);
        assertEquals("true", binding.get());
    }

    @Test
    public void testAsObject() {
        final ObservableBooleanValueStub valueModel = new ObservableBooleanValueStub();
        final ObjectExpression<Boolean> exp = BooleanExpression.booleanExpression(valueModel).asObject();

        assertEquals(Boolean.FALSE, exp.get());
        valueModel.set(true);
        assertEquals(Boolean.TRUE, exp.get());
        valueModel.set(false);
        assertEquals(Boolean.FALSE, exp.get());
    }

    @Test
    public void testFactory() {
        final ObservableBooleanValueStub valueModel = new ObservableBooleanValueStub();
        final BooleanExpression exp = BooleanExpression.booleanExpression(valueModel);

        assertTrue(exp instanceof BooleanBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((BooleanBinding) exp).getDependencies());

        assertEquals(false, exp.get());
        valueModel.set(true);
        assertEquals(true, exp.get());
        valueModel.set(false);
        assertEquals(false, exp.get());

        // make sure we do not create unnecessary bindings
        assertEquals(op1, BooleanExpression.booleanExpression(op1));
    }

    @Test
    public void testObjectToBoolean() {
        final ObservableValueStub<Boolean> valueModel = new ObservableValueStub<>();
        final BooleanExpression exp = BooleanExpression.booleanExpression(valueModel);

        assertTrue(exp instanceof BooleanBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((BooleanBinding) exp).getDependencies());

        assertEquals(false, exp.get());
        valueModel.set(true);
        assertEquals(true, exp.get());
        valueModel.set(false);
        assertEquals(false, exp.get());

        // make sure we do not create unnecessary bindings
        assertEquals(op1, BooleanExpression.booleanExpression((ObservableValue<Boolean>) op1));
    }

    @Test(expected = NullPointerException.class)
    public void testFactory_Null() {
        BooleanExpression.booleanExpression(null);
    }

}
