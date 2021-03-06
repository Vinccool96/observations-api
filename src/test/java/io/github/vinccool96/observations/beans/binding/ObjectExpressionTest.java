package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.property.ObjectProperty;
import io.github.vinccool96.observations.beans.property.SimpleObjectProperty;
import io.github.vinccool96.observations.beans.value.ObservableObjectValueStub;
import io.github.vinccool96.observations.collections.ObservableCollections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SimplifiableJUnitAssertion")
public class ObjectExpressionTest {

    private Object data1;

    private Object data2;

    private ObjectProperty<Object> op1;

    private ObjectProperty<Object> op2;

    @Before
    public void setUp() {
        data1 = new Object();
        data2 = new Object();
        op1 = new SimpleObjectProperty<>(data1);
        op2 = new SimpleObjectProperty<>(data2);
    }

    @Test
    public void testEquals() {
        BooleanBinding binding = op1.isEqualTo(op1);
        assertEquals(true, binding.get());

        binding = op1.isEqualTo(op2);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(data1);
        assertEquals(true, binding.get());

        binding = op1.isEqualTo(data2);
        assertEquals(false, binding.get());
    }

    @Test
    public void testNotEquals() {
        BooleanBinding binding = op1.isNotEqualTo(op1);
        assertEquals(false, binding.get());

        binding = op1.isNotEqualTo(op2);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(data1);
        assertEquals(false, binding.get());

        binding = op1.isNotEqualTo(data2);
        assertEquals(true, binding.get());
    }

    @Test
    public void testIsNull() {
        BooleanBinding binding = op1.isNull();
        assertEquals(false, binding.get());

        ObjectProperty<Object> op3 = new SimpleObjectProperty<>(null);
        binding = op3.isNull();
        assertEquals(true, binding.get());
    }

    @Test
    public void testIsNotNull() {
        BooleanBinding binding = op1.isNotNull();
        assertEquals(true, binding.get());

        ObjectProperty<Object> op3 = new SimpleObjectProperty<>(null);
        binding = op3.isNotNull();
        assertEquals(false, binding.get());
    }

    @Test
    public void testFactory() {
        final ObservableObjectValueStub<Object> valueModel = new ObservableObjectValueStub<>();
        final ObjectExpression<Object> exp = ObjectExpression.objectExpression(valueModel);

        assertTrue(exp instanceof ObjectBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((ObjectBinding<Object>) exp).getDependencies());

        assertEquals(null, exp.get());
        valueModel.set(data1);
        assertEquals(data1, exp.get());
        valueModel.set(data2);
        assertEquals(data2, exp.get());

        // make sure we do not create unnecessary bindings
        assertEquals(op1, ObjectExpression.objectExpression(op1));
    }

    @Test(expected = NullPointerException.class)
    public void testFactory_Null() {
        ObjectExpression.objectExpression(null);
    }

    @Test
    public void testAsString() {
        final StringBinding binding = op1.asString();
        DependencyUtils.checkDependencies(binding.getDependencies(), op1);

        assertEquals(op1.get().toString(), binding.get());

        op1.set(new Object() {

            @Override
            public String toString() {
                return "foo";
            }

        });
        assertEquals("foo", binding.get());
    }

    @Test
    public void testAsString_Format() {
        final StringBinding binding = op1.asString("%h");
        DependencyUtils.checkDependencies(binding.getDependencies(), op1);
        op1.set(new Object() {

            @Override
            public String toString() {
                return "foo";
            }

        });
        assertEquals(Integer.toHexString(op1.get().hashCode()), binding.get());
    }

}
