package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.InvalidationListenerMock;
import io.github.vinccool96.observations.beans.property.ObjectProperty;
import io.github.vinccool96.observations.beans.property.SimpleObjectProperty;
import io.github.vinccool96.observations.beans.property.SimpleStringProperty;
import io.github.vinccool96.observations.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BindingsIsNullTest {

    private ObjectProperty<Object> oo;

    private StringProperty os;

    private InvalidationListenerMock observer;

    @Before
    public void setUp() {
        oo = new SimpleObjectProperty<>();
        os = new SimpleStringProperty();
        observer = new InvalidationListenerMock();
    }

    @Test
    public void test_Object_IsNull() {
        final BooleanBinding binding = Bindings.isNull(oo);
        binding.addListener(observer);

        // check initial value
        assertTrue(binding.get());
        DependencyUtils.checkDependencies(binding.getDependencies(), oo);
        observer.reset();

        // change operand
        oo.set(new Object());
        assertFalse(binding.get());
        observer.check(binding, 1);

        // change again
        oo.set(null);
        assertTrue(binding.get());
        observer.check(binding, 1);
    }

    @Test
    public void test_Object_IsNotNull() {
        final BooleanBinding binding = Bindings.isNotNull(oo);
        binding.addListener(observer);

        // check initial value
        assertFalse(binding.get());
        DependencyUtils.checkDependencies(binding.getDependencies(), oo);
        observer.reset();

        // change operand
        oo.set(new Object());
        assertTrue(binding.get());
        observer.check(binding, 1);

        // change again
        oo.set(null);
        assertFalse(binding.get());
        observer.check(binding, 1);
    }

    @Test
    public void test_String_IsNull() {
        final BooleanBinding binding = Bindings.isNull(os);
        binding.addListener(observer);

        // check initial value
        assertTrue(binding.get());
        DependencyUtils.checkDependencies(binding.getDependencies(), os);
        observer.reset();

        // change operand
        os.set("Hello World");
        assertFalse(binding.get());
        observer.check(binding, 1);

        // change again
        os.set(null);
        assertTrue(binding.get());
        observer.check(binding, 1);
    }

    @Test
    public void test_String_IsNotNull() {
        final BooleanBinding binding = Bindings.isNotNull(os);
        binding.addListener(observer);

        // check initial value
        assertFalse(binding.get());
        DependencyUtils.checkDependencies(binding.getDependencies(), os);
        observer.reset();

        // change operand
        os.set("Hello World");
        assertTrue(binding.get());
        observer.check(binding, 1);

        // change again
        os.set(null);
        assertFalse(binding.get());
        observer.check(binding, 1);
    }

    @Test(expected = NullPointerException.class)
    public void test_IsNull_NPE() {
        Bindings.isNull(null);
    }

    @Test(expected = NullPointerException.class)
    public void test_IsNotNull_NPE() {
        Bindings.isNotNull(null);
    }

}
