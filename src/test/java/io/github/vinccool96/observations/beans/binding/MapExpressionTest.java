package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.property.IntegerProperty;
import io.github.vinccool96.observations.beans.property.MapProperty;
import io.github.vinccool96.observations.beans.property.SimpleIntegerProperty;
import io.github.vinccool96.observations.beans.property.SimpleMapProperty;
import io.github.vinccool96.observations.beans.value.ObservableMapValueStub;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableMap;
import io.github.vinccool96.observations.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

@SuppressWarnings("SimplifiableAssertion")
public class MapExpressionTest {

    private static final Number key1_0 = 4711;

    private static final Number key2_0 = 4711;

    private static final Number key2_1 = 4712;

    private static final Number keyx = 4710;

    private static final Integer data1_0 = 7;

    private static final Integer data2_0 = 42;

    private static final Integer data2_1 = -3;

    private MapProperty<Number, Integer> opNull;

    private MapProperty<Number, Integer> opEmpty;

    private MapProperty<Number, Integer> op1;

    private MapProperty<Number, Integer> op2;

    @Before
    public void setUp() {
        opNull = new SimpleMapProperty<>();
        opEmpty = new SimpleMapProperty<>(
                ObservableCollections.observableMap(Collections.emptyMap()));
        op1 = new SimpleMapProperty<>(ObservableCollections.singletonObservableMap(key1_0, data1_0));
        op2 = new SimpleMapProperty<>(ObservableCollections.observableHashMap(new Pair<>(key2_0, data2_0),
                new Pair<>(key2_1, data2_1)));
    }

    @Test
    public void testGetSize() {
        assertEquals(0, opNull.getSize());
        assertEquals(0, opEmpty.getSize());
        assertEquals(1, op1.getSize());
        assertEquals(2, op2.getSize());
    }

    @Test
    public void testValueAt_Constant() {
        assertNull(opNull.valueAt(0).get());
        assertNull(opEmpty.valueAt(0).get());

        assertEquals(data1_0, op1.valueAt(key1_0).get());
        assertNull(op1.valueAt(keyx).get());

        assertEquals(data2_0, op2.valueAt(key2_0).get());
        assertEquals(data2_1, op2.valueAt(key2_1).get());
        assertNull(op2.valueAt(keyx).get());
    }

    @Test
    public void testValueAt_Variable() {
        final IntegerProperty index = new SimpleIntegerProperty(keyx.intValue());

        assertNull(opNull.valueAt(index).get());
        assertNull(opEmpty.valueAt(index).get());
        assertNull(op1.valueAt(index).get());
        assertNull(op2.valueAt(index).get());

        index.set(key1_0.intValue());
        assertNull(opNull.valueAt(index).get());
        assertNull(opEmpty.valueAt(index).get());
        assertEquals(data1_0, op1.valueAt(index).get());
        assertEquals(data2_0, op2.valueAt(index).get());

        index.set(key2_1.intValue());
        assertNull(opNull.valueAt(index).get());
        assertNull(opEmpty.valueAt(index).get());
        assertNull(op1.valueAt(index).get());
        assertEquals(data2_1, op2.valueAt(index).get());
    }

    @Test
    public void testIsEqualTo() {
        final ObservableMap<Number, Integer> emptyMap = ObservableCollections.observableMap(Collections.emptyMap());
        final ObservableMap<Number, Integer> map1 = ObservableCollections.singletonObservableMap(key1_0, data1_0);
        final ObservableMap<Number, Integer> map2 = ObservableCollections.observableHashMap(new Pair<>(key2_0, data2_0),
                new Pair<>(key2_1, data2_1));

        BooleanBinding binding = opNull.isEqualTo(emptyMap);
        assertEquals(false, binding.get());
        binding = opNull.isEqualTo(map1);
        assertEquals(false, binding.get());
        binding = opNull.isEqualTo(map2);
        assertEquals(false, binding.get());

        binding = opEmpty.isEqualTo(emptyMap);
        assertEquals(true, binding.get());
        binding = opEmpty.isEqualTo(map1);
        assertEquals(false, binding.get());
        binding = opEmpty.isEqualTo(map2);
        assertEquals(false, binding.get());

        binding = op1.isEqualTo(emptyMap);
        assertEquals(false, binding.get());
        binding = op1.isEqualTo(map1);
        assertEquals(true, binding.get());
        binding = op1.isEqualTo(map2);
        assertEquals(false, binding.get());

        binding = op2.isEqualTo(emptyMap);
        assertEquals(false, binding.get());
        binding = op2.isEqualTo(map1);
        assertEquals(false, binding.get());
        binding = op2.isEqualTo(map2);
        assertEquals(true, binding.get());
    }

    @Test
    public void testIsNotEqualTo() {
        final ObservableMap<Number, Integer> emptyMap = ObservableCollections.observableMap(Collections.emptyMap());
        final ObservableMap<Number, Integer> map1 = ObservableCollections.singletonObservableMap(key1_0, data1_0);
        final ObservableMap<Number, Integer> map2 = ObservableCollections.observableHashMap(new Pair<>(key2_0, data2_0),
                new Pair<>(key2_1, data2_1));

        BooleanBinding binding = opNull.isNotEqualTo(emptyMap);
        assertEquals(true, binding.get());
        binding = opNull.isNotEqualTo(map1);
        assertEquals(true, binding.get());
        binding = opNull.isNotEqualTo(map2);
        assertEquals(true, binding.get());

        binding = opEmpty.isNotEqualTo(emptyMap);
        assertEquals(false, binding.get());
        binding = opEmpty.isNotEqualTo(map1);
        assertEquals(true, binding.get());
        binding = opEmpty.isNotEqualTo(map2);
        assertEquals(true, binding.get());

        binding = op1.isNotEqualTo(emptyMap);
        assertEquals(true, binding.get());
        binding = op1.isNotEqualTo(map1);
        assertEquals(false, binding.get());
        binding = op1.isNotEqualTo(map2);
        assertEquals(true, binding.get());

        binding = op2.isNotEqualTo(emptyMap);
        assertEquals(true, binding.get());
        binding = op2.isNotEqualTo(map1);
        assertEquals(true, binding.get());
        binding = op2.isNotEqualTo(map2);
        assertEquals(false, binding.get());
    }

    @Test
    public void testIsNull() {
        assertTrue(opNull.isNull().get());
        assertFalse(opEmpty.isNull().get());
        assertFalse(op1.isNull().get());
        assertFalse(op2.isNull().get());
    }

    @Test
    public void testIsNotNull() {
        assertFalse(opNull.isNotNull().get());
        assertTrue(opEmpty.isNotNull().get());
        assertTrue(op1.isNotNull().get());
        assertTrue(op2.isNotNull().get());
    }

    @Test
    public void testAsString() {
        assertEquals("null", opNull.asString().get());
        assertEquals(Collections.emptyMap().toString(), opEmpty.asString().get());
        assertEquals(Collections.singletonMap(key1_0, data1_0).toString(), op1.asString().get());
    }

    @Test
    public void testSize() {
        assertEquals(0, opNull.size());
        assertEquals(0, opEmpty.size());
        assertEquals(1, op1.size());
        assertEquals(2, op2.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(opNull.isEmpty());
        assertTrue(opEmpty.isEmpty());
        assertFalse(op1.isEmpty());
        assertFalse(op2.isEmpty());
    }

    @Test
    public void testContainsKey() {
        assertFalse(opNull.containsKey(key1_0));
        assertFalse(opNull.containsKey(key2_0));
        assertFalse(opNull.containsKey(key2_1));

        assertFalse(opEmpty.containsKey(key1_0));
        assertFalse(opEmpty.containsKey(key2_0));
        assertFalse(opEmpty.containsKey(key2_1));

        assertTrue(op1.containsKey(key1_0));
        assertFalse(op1.containsKey(key2_1));

        assertTrue(op2.containsKey(key2_0));
        assertTrue(op2.containsKey(key2_1));
    }

    @Test
    public void testContainsValue() {
        assertFalse(opNull.containsValue(data1_0));
        assertFalse(opNull.containsValue(data2_0));
        assertFalse(opNull.containsValue(data2_1));

        assertFalse(opEmpty.containsValue(data1_0));
        assertFalse(opEmpty.containsValue(data2_0));
        assertFalse(opEmpty.containsValue(data2_1));

        assertTrue(op1.containsValue(data1_0));
        assertFalse(op1.containsValue(data2_0));
        assertFalse(op1.containsValue(data2_1));

        assertFalse(op2.containsValue(data1_0));
        assertTrue(op2.containsValue(data2_0));
        assertTrue(op2.containsValue(data2_1));
    }

    @Test
    public void testObservableMapValueToExpression() {
        final ObservableMapValueStub<Number, Integer> valueModel = new ObservableMapValueStub<>();
        final MapExpression<Number, Integer> exp = MapExpression.mapExpression(valueModel);
        final Number k1 = 1.0;
        final Number k2 = 2.0f;
        final Number k3 = 3L;
        final Integer v1 = 4;
        final Integer v2 = 5;
        final Integer v3 = 6;

        assertTrue(exp instanceof MapBinding);
        assertEquals(ObservableCollections.singletonObservableList(valueModel),
                ((MapBinding<Number, Integer>) exp).getDependencies());

        assertEquals(null, exp.get());
        valueModel.set(ObservableCollections.observableHashMap(new Pair<>(k1, v1)));
        assertEquals(ObservableCollections.singletonObservableMap(k1, v1), exp.get());
        valueModel.get().put(k2, v2);
        assertEquals(ObservableCollections.observableHashMap(new Pair<>(k1, v1), new Pair<>(k2, v2)), exp.get());
        exp.put(k3, v3);
        assertEquals(ObservableCollections.observableHashMap(new Pair<>(k1, v1), new Pair<>(k2, v2),
                new Pair<>(k3, v3)), valueModel.get());

        // make sure we do not create unnecessary bindings
        assertSame(op1, MapExpression.mapExpression(op1));
    }

}
