package io.github.vinccool96.observations.beans.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.property.Property;
import io.github.vinccool96.observations.beans.value.*;
import io.github.vinccool96.observations.collections.*;
import io.github.vinccool96.observations.sun.binding.*;
import io.github.vinccool96.observations.sun.collections.ImmutableObservableList;
import io.github.vinccool96.observations.sun.collections.annotations.ReturnsUnmodifiableCollection;
import io.github.vinccool96.observations.util.StringConverter;

import java.lang.ref.WeakReference;
import java.text.Format;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Bindings is a helper class with a lot of utility functions to create simple bindings.
 * <p>
 * Usually there are two possibilities to define the same operation: the Fluent API and the factory methods in this
 * class. This allows a developer to define complex expression in a way that is most easy to understand. For instance
 * the expression {@code result = a*b + c*d} can be defined using only the Fluent API:
 * <p>
 * {@code DoubleBinding result = a.multiply(b).add(c.multiply(d));}
 * <p>
 * Or using only factory methods in Bindings:
 * <p>
 * {@code NumberBinding result = add(multiply(a, b), multiply(c,d));}
 * <p>
 * Or mixing both possibilities:
 * <p>
 * {@code NumberBinding result = add (a.multiply(b), c.multiply(d));}
 * <p>
 * The main difference between using the Fluent API and using the factory methods in this class is that the Fluent API
 * requires that at least one of the operands is an Expression (see {@link io.github.vinccool96.observations.beans.binding}).
 * (Every Expression contains a static method that generates an Expression from an {@link ObservableValue}).
 * <p>
 * Also if you watched closely, you might have noticed that the return type of the Fluent API is different in the
 * examples above. In a lot of cases the Fluent API allows to be more specific about the returned type (see {@link
 * NumberExpression} for more details about implicit casting).
 *
 * @see Binding
 * @see NumberBinding
 */
@SuppressWarnings({"unused", "UnusedReturnValue", "RedundantSuppression"})
public final class Bindings {

    private Bindings() {
    }

    // =================================================================================================================
    // Helper functions to create custom bindings

    /**
     * Helper function to create a custom {@link BooleanBinding}.
     *
     * @param func
     *         The function that calculates the value of this binding
     * @param dependencies
     *         The dependencies of this binding
     *
     * @return The generated binding
     */
    public static BooleanBinding createBooleanBinding(final Callable<Boolean> func, final Observable... dependencies) {
        return new BooleanBinding() {

            {
                bind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return false;
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        ObservableCollections.emptyObservableList() :
                        (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                                new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Helper function to create a custom {@link DoubleBinding}.
     *
     * @param func
     *         The function that calculates the value of this binding
     * @param dependencies
     *         The dependencies of this binding
     *
     * @return The generated binding
     */
    public static DoubleBinding createDoubleBinding(final Callable<Double> func, final Observable... dependencies) {
        return new DoubleBinding() {

            {
                bind(dependencies);
            }

            @Override
            protected double computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return 0.0;
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        ObservableCollections.emptyObservableList() :
                        (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                                new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Helper function to create a custom {@link FloatBinding}.
     *
     * @param func
     *         The function that calculates the value of this binding
     * @param dependencies
     *         The dependencies of this binding
     *
     * @return The generated binding
     */
    public static FloatBinding createFloatBinding(final Callable<Float> func, final Observable... dependencies) {
        return new FloatBinding() {

            {
                bind(dependencies);
            }

            @Override
            protected float computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return 0.0f;
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        ObservableCollections.emptyObservableList() :
                        (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                                new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Helper function to create a custom {@link IntegerBinding}.
     *
     * @param func
     *         The function that calculates the value of this binding
     * @param dependencies
     *         The dependencies of this binding
     *
     * @return The generated binding
     */
    public static IntegerBinding createIntegerBinding(final Callable<Integer> func, final Observable... dependencies) {
        return new IntegerBinding() {

            {
                bind(dependencies);
            }

            @Override
            protected int computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return 0;
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        ObservableCollections.emptyObservableList() :
                        (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                                new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Helper function to create a custom {@link LongBinding}.
     *
     * @param func
     *         The function that calculates the value of this binding
     * @param dependencies
     *         The dependencies of this binding
     *
     * @return The generated binding
     */
    public static LongBinding createLongBinding(final Callable<Long> func, final Observable... dependencies) {
        return new LongBinding() {

            {
                bind(dependencies);
            }

            @Override
            protected long computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return 0L;
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        ObservableCollections.emptyObservableList() :
                        (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                                new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Helper function to create a custom {@link ObjectBinding}.
     *
     * @param func
     *         The function that calculates the value of this binding
     * @param dependencies
     *         The dependencies of this binding
     * @param <T>
     *         the type of the wrapped {@code Object}
     *
     * @return The generated binding
     */
    public static <T> ObjectBinding<T> createObjectBinding(final Callable<T> func, final Observable... dependencies) {
        return new ObjectBinding<T>() {

            {
                bind(dependencies);
            }

            @Override
            protected T computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return null;
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        ObservableCollections.emptyObservableList() :
                        (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                                new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Helper function to create a custom {@link StringBinding}.
     *
     * @param func
     *         The function that calculates the value of this binding
     * @param dependencies
     *         The dependencies of this binding
     *
     * @return The generated binding
     */
    public static StringBinding createStringBinding(final Callable<String> func, final Observable... dependencies) {
        return new StringBinding() {

            {
                bind(dependencies);
            }

            @Override
            protected String computeValue() {
                try {
                    return func.call();
                } catch (Exception e) {
                    Logging.getLogger().warning("Exception while evaluating binding", e);
                    return "";
                }
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ((dependencies == null) || (dependencies.length == 0)) ?
                        ObservableCollections.emptyObservableList() :
                        (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                                new ImmutableObservableList<>(dependencies);
            }
        };
    }

    // =================================================================================================================
    // Select Bindings

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code null} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being the right type etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
     *
     * @param root
     *         The root {@link ObservableValue}
     * @param steps
     *         The property names to reach the final property
     * @param <T>
     *         the type of the wrapped {@code Object}
     *
     * @return the created {@link ObjectBinding}
     */
    public static <T> ObjectBinding<T> select(ObservableValue<?> root, String... steps) {
        return new SelectBinding.AsObject<>(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0.0} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
     *
     * @param root
     *         The root {@link ObservableValue}
     * @param steps
     *         The property names to reach the final property
     *
     * @return the created {@link DoubleBinding}
     */
    public static DoubleBinding selectDouble(ObservableValue<?> root, String... steps) {
        return new SelectBinding.AsDouble(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0.0f} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
     *
     * @param root
     *         The root {@link ObservableValue}
     * @param steps
     *         The property names to reach the final property
     *
     * @return the created {@link FloatBinding}
     */
    public static FloatBinding selectFloat(ObservableValue<?> root, String... steps) {
        return new SelectBinding.AsFloat(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
     *
     * @param root
     *         The root {@link ObservableValue}
     * @param steps
     *         The property names to reach the final property
     *
     * @return the created {@link IntegerBinding}
     */
    public static IntegerBinding selectInteger(ObservableValue<?> root, String... steps) {
        return new SelectBinding.AsInteger(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0L} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
     *
     * @param root
     *         The root {@link ObservableValue}
     * @param steps
     *         The property names to reach the final property
     *
     * @return the created {@link LongBinding}
     */
    public static LongBinding selectLong(ObservableValue<?> root, String... steps) {
        return new SelectBinding.AsLong(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code false} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b}
     * being {@code null}, or {@code c} not being a {@code boolean} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
     *
     * @param root
     *         The root {@link ObservableValue}
     * @param steps
     *         The property names to reach the final property
     *
     * @return the created {@link ObjectBinding}
     */
    public static BooleanBinding selectBoolean(ObservableValue<?> root, String... steps) {
        return new SelectBinding.AsBoolean(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code ""} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code String} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
     *
     * @param root
     *         The root {@link ObservableValue}
     * @param steps
     *         The property names to reach the final property
     *
     * @return the created {@link ObjectBinding}
     */
    public static StringBinding selectString(ObservableValue<?> root, String... steps) {
        return new SelectBinding.AsString(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code null} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being the right type etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * If root has JavaFX properties, this call is equivalent to {@link #select(ObservableValue, String[])}, with the
     * {@code root} and {@code step[0]} being substituted with the relevant property object.
     *
     * @param root
     *         The root bean.
     * @param steps
     *         The property names to reach the final property. The first step must be specified as it marks the property
     *         of the root bean.
     * @param <T>
     *         the type of the wrapped {@code Object}
     *
     * @return the created {@link ObjectBinding}
     */
    public static <T> ObjectBinding<T> select(Object root, String... steps) {
        return new SelectBinding.AsObject<>(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0.0} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * If root has JavaFX properties, this call is equivalent to {@link #selectDouble(ObservableValue, String[])}, with
     * the {@code root} and {@code step[0]} being substituted with the relevant property object.
     *
     * @param root
     *         The root bean.
     * @param steps
     *         The property names to reach the final property. The first step must be specified as it marks the property
     *         of the root bean.
     *
     * @return the created {@link DoubleBinding }
     */
    public static DoubleBinding selectDouble(Object root, String... steps) {
        return new SelectBinding.AsDouble(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0.0f} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * If root has JavaFX properties, this call is equivalent to {@link #selectFloat(ObservableValue, String[])}, with
     * the {@code root} and {@code step[0]} being substituted with the relevant property object.
     *
     * @param root
     *         The root bean.
     * @param steps
     *         The property names to reach the final property. The first step must be specified as it marks the property
     *         of the root bean.
     *
     * @return the created {@link FloatBinding }
     */
    public static FloatBinding selectFloat(Object root, String... steps) {
        return new SelectBinding.AsFloat(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * If root has JavaFX properties, this call is equivalent to {@link #selectInteger(ObservableValue, String[])}, with
     * the {@code root} and {@code step[0]} being substituted with the relevant property object.
     *
     * @param root
     *         The root bean.
     * @param steps
     *         The property names to reach the final property. The first step must be specified as it marks the property
     *         of the root bean.
     *
     * @return the created {@link IntegerBinding }
     */
    public static IntegerBinding selectInteger(Object root, String... steps) {
        return new SelectBinding.AsInteger(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code 0L} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code Number} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * If root has JavaFX properties, this call is equivalent to {@link #selectLong(ObservableValue, String[])}, with
     * the {@code root} and {@code step[0]} being substituted with the relevant property object.
     *
     * @param root
     *         The root bean.
     * @param steps
     *         The property names to reach the final property. The first step must be specified as it marks the property
     *         of the root bean.
     *
     * @return the created {@link LongBinding }
     */
    public static LongBinding selectLong(Object root, String... steps) {
        return new SelectBinding.AsLong(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code false} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b}
     * being {@code null}, or {@code c} not being a {@code boolean} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * If root has JavaFX properties, this call is equivalent to {@link #selectBoolean(ObservableValue, String[])}, with
     * the {@code root} and {@code step[0]} being substituted with the relevant property object.
     *
     * @param root
     *         The root bean.
     * @param steps
     *         The property names to reach the final property. The first step must be specified as it marks the property
     *         of the root bean.
     *
     * @return the created {@link ObjectBinding }
     */
    public static BooleanBinding selectBoolean(Object root, String... steps) {
        return new SelectBinding.AsBoolean(root, steps);
    }

    /**
     * Creates a binding used to get a member, such as {@code a.b.c}. The value of the binding will be {@code c}, or
     * {@code ""} if {@code c} could not be reached (due to {@code b} not having a {@code c} property, {@code b} being
     * {@code null}, or {@code c} not being a {@code String} etc.).
     * <p>
     * All classes and properties used in a select-binding have to be public.
     * <p>
     * If root has JavaFX properties, this call is equivalent to {@link #selectString(ObservableValue, String[])}, with
     * the {@code root} and {@code step[0]} being substituted with the relevant property object.
     *
     * @param root
     *         The root bean.
     * @param steps
     *         The property names to reach the final property. The first step must be specified as it marks the property
     *         of the root bean.
     *
     * @return the created {@link ObjectBinding }
     */
    public static StringBinding selectString(Object root, String... steps) {
        return new SelectBinding.AsString(root, steps);
    }

    /**
     * Creates a binding that calculates the result of a ternary expression. See the description of class {@link When}
     * for details.
     *
     * @param condition
     *         the condition of the ternary expression
     *
     * @return an intermediate class to build the complete binding
     *
     * @see When
     */
    public static When when(final ObservableBooleanValue condition) {
        return new When(condition);
    }

    // =================================================================================================================
    // Bidirectional Bindings

    /**
     * Generates a bidirectional binding (or "bind with inverse") between two instances of {@link Property}.
     * <p>
     * A bidirectional binding is a binding that works in both directions. If two properties {@code a} and {@code b} are
     * linked with a bidirectional binding and the value of {@code a} changes, {@code b} is set to the same value
     * automatically. And vice versa, if {@code b} changes, {@code a} is set to the same value.
     * <p>
     * A bidirectional binding can be removed with {@link #unbindBidirectional(Property, Property)}.
     * <p>
     * Note: this implementation of a bidirectional binding behaves differently from all other bindings here in two
     * important aspects. A property that is linked to another property with a bidirectional binding can still be set
     * (usually bindings would throw an exception). Secondly bidirectional bindings are calculated eagerly, i.e. a bound
     * property is updated immediately.
     *
     * @param <T>
     *         the types of the properties
     * @param property1
     *         the first {@code Property<T>}
     * @param property2
     *         the second {@code Property<T>}
     *
     * @throws NullPointerException
     *         if one of the properties is {@code null}
     * @throws IllegalArgumentException
     *         if both properties are equal
     */
    public static <T> void bindBidirectional(Property<T> property1, Property<T> property2) {
        BidirectionalBinding.bind(property1, property2);
    }

    /**
     * Delete a bidirectional binding that was previously defined with {@link #bindBidirectional(Property, Property)}.
     *
     * @param <T>
     *         the types of the properties
     * @param property1
     *         the first {@code Property<T>}
     * @param property2
     *         the second {@code Property<T>}
     *
     * @throws NullPointerException
     *         if one of the properties is {@code null}
     * @throws IllegalArgumentException
     *         if both properties are equal
     */
    public static <T> void unbindBidirectional(Property<T> property1, Property<T> property2) {
        BidirectionalBinding.unbind(property1, property2);
    }

    /**
     * Delete a bidirectional binding that was previously defined with {@link #bindBidirectional(Property, Property)} or
     * {@link #bindBidirectional(Property, Property, Format)}.
     *
     * @param property1
     *         the first {@code Property<T>}
     * @param property2
     *         the second {@code Property<T>}
     *
     * @throws NullPointerException
     *         if one of the properties is {@code null}
     * @throws IllegalArgumentException
     *         if both properties are equal
     */
    public static void unbindBidirectional(Object property1, Object property2) {
        BidirectionalBinding.unbind(property1, property2);
    }

    /**
     * Generates a bidirectional binding (or "bind with inverse") between a {@code String}-{@link Property} and another
     * {@code Property} using the specified {@code Format} for conversion.
     * <p>
     * A bidirectional binding is a binding that works in both directions. If two properties {@code a} and {@code b} are
     * linked with a bidirectional binding and the value of {@code a} changes, {@code b} is set to the same value
     * automatically. And vice versa, if {@code b} changes, {@code a} is set to the same value.
     * <p>
     * A bidirectional binding can be removed with {@link #unbindBidirectional(Object, Object)}.
     * <p>
     * Note: this implementation of a bidirectional binding behaves differently from all other bindings here in two
     * important aspects. A property that is linked to another property with a bidirectional binding can still be set
     * (usually bindings would throw an exception). Secondly bidirectional bindings are calculated eagerly, i.e. a bound
     * property is updated immediately.
     *
     * @param stringProperty
     *         the {@code String} {@code Property}
     * @param otherProperty
     *         the other (non-{@code String}) {@code Property}
     * @param format
     *         the {@code Format} used to convert between the properties
     *
     * @throws NullPointerException
     *         if one of the properties or the {@code format} is {@code null}
     * @throws IllegalArgumentException
     *         if both properties are equal
     */
    public static void bindBidirectional(Property<String> stringProperty, Property<?> otherProperty, Format format) {
        BidirectionalBinding.bind(stringProperty, otherProperty, format);
    }

    /**
     * Generates a bidirectional binding (or "bind with inverse") between a {@code String}-{@link Property} and another
     * {@code Property} using the specified {@link StringConverter} for conversion.
     * <p>
     * A bidirectional binding is a binding that works in both directions. If two properties {@code a} and {@code b} are
     * linked with a bidirectional binding and the value of {@code a} changes, {@code b} is set to the same value
     * automatically. And vice versa, if {@code b} changes, {@code a} is set to the same value.
     * <p>
     * A bidirectional binding can be removed with {@link #unbindBidirectional(Object, Object)}.
     * <p>
     * Note: this implementation of a bidirectional binding behaves differently from all other bindings here in two
     * important aspects. A property that is linked to another property with a bidirectional binding can still be set
     * (usually bindings would throw an exception). Secondly bidirectional bindings are calculated eagerly, i.e. a bound
     * property is updated immediately.
     *
     * @param stringProperty
     *         the {@code String} {@code Property}
     * @param otherProperty
     *         the other (non-{@code String}) {@code Property}
     * @param converter
     *         the {@code StringConverter} used to convert between the properties
     * @param <T>
     *         the type of the wrapped value
     *
     * @throws NullPointerException
     *         if one of the properties or the {@code converter} is {@code null}
     * @throws IllegalArgumentException
     *         if both properties are equal
     */
    public static <T> void bindBidirectional(Property<String> stringProperty, Property<T> otherProperty,
            StringConverter<T> converter) {
        BidirectionalBinding.bind(stringProperty, otherProperty, converter);
    }

    /**
     * Generates a bidirectional binding (or "bind with inverse") between two instances of {@link ObservableList}.
     * <p>
     * A bidirectional binding is a binding that works in both directions. If two properties {@code a} and {@code b} are
     * linked with a bidirectional binding and the value of {@code a} changes, {@code b} is set to the same value
     * automatically. And vice versa, if {@code b} changes, {@code a} is set to the same value.
     * <p>
     * Only the content of the two lists is synchronized, which means that both lists are different, but they contain
     * the same elements.
     * <p>
     * A bidirectional content-binding can be removed with {@link #unbindContentBidirectional(Object, Object)}.
     * <p>
     * Note: this implementation of a bidirectional binding behaves differently from all other bindings here in two
     * important aspects. A property that is linked to another property with a bidirectional binding can still be set
     * (usually bindings would throw an exception). Secondly bidirectional bindings are calculated eagerly, i.e. a bound
     * property is updated immediately.
     *
     * @param <E>
     *         the type of the list elements
     * @param list1
     *         the first {@code ObservableList<E>}
     * @param list2
     *         the second {@code ObservableList<E>}
     *
     * @throws NullPointerException
     *         if one of the lists is {@code null}
     * @throws IllegalArgumentException
     *         if {@code list1} == {@code list2}
     */
    public static <E> void bindContentBidirectional(ObservableList<E> list1, ObservableList<E> list2) {
        BidirectionalContentBinding.bind(list1, list2);
    }

    /**
     * Generates a bidirectional binding (or "bind with inverse") between two instances of {@link ObservableSet}.
     * <p>
     * A bidirectional binding is a binding that works in both directions. If two properties {@code a} and {@code b} are
     * linked with a bidirectional binding and the value of {@code a} changes, {@code b} is set to the same value
     * automatically. And vice versa, if {@code b} changes, {@code a} is set to the same value.
     * <p>
     * Only the content of the two sets is synchronized, which means that both sets are different, but they contain the
     * same elements.
     * <p>
     * A bidirectional content-binding can be removed with {@link #unbindContentBidirectional(Object, Object)}.
     * <p>
     * Note: this implementation of a bidirectional binding behaves differently from all other bindings here in two
     * important aspects. A property that is linked to another property with a bidirectional binding can still be set
     * (usually bindings would throw an exception). Secondly bidirectional bindings are calculated eagerly, i.e. a bound
     * property is updated immediately.
     *
     * @param <E>
     *         the type of the set elements
     * @param set1
     *         the first {@code ObservableSet<E>}
     * @param set2
     *         the second {@code ObservableSet<E>}
     *
     * @throws NullPointerException
     *         if one of the sets is {@code null}
     * @throws IllegalArgumentException
     *         if {@code set1} == {@code set2}
     */
    public static <E> void bindContentBidirectional(ObservableSet<E> set1, ObservableSet<E> set2) {
        BidirectionalContentBinding.bind(set1, set2);
    }

    /**
     * Generates a bidirectional binding (or "bind with inverse") between two instances of {@link ObservableMap}.
     * <p>
     * A bidirectional binding is a binding that works in both directions. If two properties {@code a} and {@code b} are
     * linked with a bidirectional binding and the value of {@code a} changes, {@code b} is set to the same value
     * automatically. And vice versa, if {@code b} changes, {@code a} is set to the same value.
     * <p>
     * Only the content of the two maps is synchronized, which means that both maps are different, but they contain the
     * same elements.
     * <p>
     * A bidirectional content-binding can be removed with {@link #unbindContentBidirectional(Object, Object)}.
     * <p>
     * Note: this implementation of a bidirectional binding behaves differently from all other bindings here in two
     * important aspects. A property that is linked to another property with a bidirectional binding can still be set
     * (usually bindings would throw an exception). Secondly bidirectional bindings are calculated eagerly, i.e. a bound
     * property is updated immediately.
     *
     * @param <K>
     *         the type of the key elements
     * @param <V>
     *         the type of the value elements
     * @param map1
     *         the first {@code ObservableMap<K, V>}
     * @param map2
     *         the second {@code ObservableMap<K, V>}
     */
    public static <K, V> void bindContentBidirectional(ObservableMap<K, V> map1, ObservableMap<K, V> map2) {
        BidirectionalContentBinding.bind(map1, map2);
    }

    /**
     * Remove a bidirectional content binding.
     *
     * @param obj1
     *         the first {@code Object}
     * @param obj2
     *         the second {@code Object}
     */
    public static void unbindContentBidirectional(Object obj1, Object obj2) {
        BidirectionalContentBinding.unbind(obj1, obj2);
    }

    /**
     * Generates a content binding between an {@link ObservableList} and a {@link List}.
     * <p>
     * A content binding ensures that the {@code List} contains the same elements as the {@code ObservableList}. If the
     * content of the {@code ObservableList} changes, the {@code List} will be updated automatically.
     * <p>
     * Once a {@code List} is bound to an {@code ObservableList}, the {@code List} must not be changed directly anymore.
     * Doing so would lead to unexpected results.
     * <p>
     * A content-binding can be removed with {@link #unbindContent(Object, Object)}.
     *
     * @param <E>
     *         the type of the {@code List} elements
     * @param list1
     *         the {@code List}
     * @param list2
     *         the {@code ObservableList}
     */
    public static <E> void bindContent(List<E> list1, ObservableList<? extends E> list2) {
        ContentBinding.bind(list1, list2);
    }

    /**
     * Generates a content binding between an {@link ObservableSet} and a {@link Set}.
     * <p>
     * A content binding ensures that the {@code Set} contains the same elements as the {@code ObservableSet}. If the
     * content of the {@code ObservableSet} changes, the {@code Set} will be updated automatically.
     * <p>
     * Once a {@code Set} is bound to an {@code ObservableSet}, the {@code Set} must not be changed directly anymore.
     * Doing so would lead to unexpected results.
     * <p>
     * A content-binding can be removed with {@link #unbindContent(Object, Object)}.
     *
     * @param <E>
     *         the type of the {@code Set} elements
     * @param set1
     *         the {@code Set}
     * @param set2
     *         the {@code ObservableSet}
     *
     * @throws NullPointerException
     *         if one of the sets is {@code null}
     * @throws IllegalArgumentException
     *         if {@code set1 == set2}
     */
    public static <E> void bindContent(Set<E> set1, ObservableSet<? extends E> set2) {
        ContentBinding.bind(set1, set2);
    }

    /**
     * Generates a content binding between an {@link ObservableMap} and a {@link Map}.
     * <p>
     * A content binding ensures that the {@code Map} contains the same elements as the {@code ObservableMap}. If the
     * content of the {@code ObservableMap} changes, the {@code Map} will be updated automatically.
     * <p>
     * Once a {@code Map} is bound to an {@code ObservableMap}, the {@code Map} must not be changed directly anymore.
     * Doing so would lead to unexpected results.
     * <p>
     * A content-binding can be removed with {@link #unbindContent(Object, Object)}.
     *
     * @param <K>
     *         the type of the key elements of the {@code Map}
     * @param <V>
     *         the type of the value elements of the {@code Map}
     * @param map1
     *         the {@code Map}
     * @param map2
     *         the {@code ObservableMap}
     *
     * @throws NullPointerException
     *         if one of the maps is {@code null}
     * @throws IllegalArgumentException
     *         if {@code map1 == map2}
     */
    public static <K, V> void bindContent(Map<K, V> map1, ObservableMap<? extends K, ? extends V> map2) {
        ContentBinding.bind(map1, map2);
    }

    /**
     * Remove a content binding.
     *
     * @param obj1
     *         the first {@code Object}
     * @param obj2
     *         the second {@code Object}
     *
     * @throws NullPointerException
     *         if one of the {@code Objects} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code obj1} == {@code obj2}
     */
    public static void unbindContent(Object obj1, Object obj2) {
        ContentBinding.unbind(obj1, obj2);
    }

    // Numbers
    // =================================================================================================================

    // =================================================================================================================
    // Negation

    /**
     * Creates a new {@link NumberBinding} that calculates the negation of a {@link ObservableNumberValue}.
     *
     * @param value
     *         the operand
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the value is {@code null}
     */
    public static NumberBinding negate(final ObservableNumberValue value) {
        if (value == null) {
            throw new NullPointerException("Operand cannot be null.");
        }

        if (value instanceof ObservableDoubleValue) {
            return new DoubleBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {
                    super.unbind(value);
                }

                @Override
                protected double computeValue() {
                    return -value.doubleValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return ObservableCollections.singletonObservableList(value);
                }
            };
        } else if (value instanceof ObservableFloatValue) {
            return new FloatBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {
                    super.unbind(value);
                }

                @Override
                protected float computeValue() {
                    return -value.floatValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return ObservableCollections.singletonObservableList(value);
                }
            };
        } else if (value instanceof ObservableLongValue) {
            return new LongBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {
                    super.unbind(value);
                }

                @Override
                protected long computeValue() {
                    return -value.longValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return ObservableCollections.singletonObservableList(value);
                }
            };
        } else {
            return new IntegerBinding() {

                {
                    super.bind(value);
                }

                @Override
                public void dispose() {
                    super.unbind(value);
                }

                @Override
                protected int computeValue() {
                    return -value.intValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return ObservableCollections.singletonObservableList(value);
                }
            };
        }
    }

    // =================================================================================================================
    // Sum

    private static NumberBinding add(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {
                    return op1.doubleValue() + op2.doubleValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {
                    return op1.floatValue() + op2.floatValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {
                    return op1.longValue() + op2.longValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {
                    return op1.intValue() + op2.intValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the sum of the values of two instances of {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static NumberBinding add(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return add(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding add(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) add(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding add(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) add(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding add(final ObservableNumberValue op1, float op2) {
        return add(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding add(float op1, final ObservableNumberValue op2) {
        return add(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding add(final ObservableNumberValue op1, long op2) {
        return add(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding add(long op1, final ObservableNumberValue op2) {
        return add(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding add(final ObservableNumberValue op1, int op2) {
        return add(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the sum of the value of a {@link ObservableNumberValue} and a
     * constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding add(int op1, final ObservableNumberValue op2) {
        return add(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Diff

    private static NumberBinding subtract(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {
                    return op1.doubleValue() - op2.doubleValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {
                    return op1.floatValue() - op2.floatValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {
                    return op1.longValue() - op2.longValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {
                    return op1.intValue() - op2.intValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                            new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the difference of the values of two instances of {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static NumberBinding subtract(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return subtract(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the difference of the value of a {@link
     * ObservableNumberValue} and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding subtract(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) subtract(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the difference of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding subtract(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) subtract(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the difference of the value of a {@link
     * ObservableNumberValue} and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding subtract(final ObservableNumberValue op1, float op2) {
        return subtract(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the difference of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding subtract(float op1, final ObservableNumberValue op2) {
        return subtract(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the difference of the value of a {@link
     * ObservableNumberValue} and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding subtract(final ObservableNumberValue op1, long op2) {
        return subtract(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the difference of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding subtract(long op1, final ObservableNumberValue op2) {
        return subtract(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the difference of the value of a {@link
     * ObservableNumberValue} and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding subtract(final ObservableNumberValue op1, int op2) {
        return subtract(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the difference of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding subtract(int op1, final ObservableNumberValue op2) {
        return subtract(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Multiply

    private static NumberBinding multiply(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {
                    return op1.doubleValue() * op2.doubleValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {
                    return op1.floatValue() * op2.floatValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {
                    return op1.longValue() * op2.longValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {
                    return op1.intValue() * op2.intValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the product of the values of two instances of {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static NumberBinding multiply(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return multiply(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding multiply(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) multiply(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding multiply(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) multiply(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding multiply(final ObservableNumberValue op1, float op2) {
        return multiply(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding multiply(float op1, final ObservableNumberValue op2) {
        return multiply(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding multiply(final ObservableNumberValue op1, long op2) {
        return multiply(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding multiply(long op1, final ObservableNumberValue op2) {
        return multiply(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding multiply(final ObservableNumberValue op1, int op2) {
        return multiply(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the product of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding multiply(int op1, final ObservableNumberValue op2) {
        return multiply(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Divide

    private static NumberBinding divide(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {
                    return op1.doubleValue() / op2.doubleValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {
                    return op1.floatValue() / op2.floatValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {
                    return op1.longValue() / op2.longValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {
                    return op1.intValue() / op2.intValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the division of the values of two instances of {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static NumberBinding divide(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return divide(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the division of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding divide(final ObservableNumberValue op1, double op2) {
        return (DoubleBinding) divide(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the division of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding divide(double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) divide(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the division of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding divide(final ObservableNumberValue op1, float op2) {
        return divide(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the division of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding divide(float op1, final ObservableNumberValue op2) {
        return divide(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the division of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding divide(final ObservableNumberValue op1, long op2) {
        return divide(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the division of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding divide(long op1, final ObservableNumberValue op2) {
        return divide(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the division of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding divide(final ObservableNumberValue op1, int op2) {
        return divide(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the division of a constant value and the value of a {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding divide(int op1, final ObservableNumberValue op2) {
        return divide(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Equals

    private static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final double epsilon, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.doubleValue() - op2.doubleValue()) <= epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.floatValue() - op2.floatValue()) <= epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.longValue() - op2.longValue()) <= epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.intValue() - op2.intValue()) <= epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableNumberValue} are equal (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final double epsilon) {
        return equal(op1, op2, epsilon, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableNumberValue} are equal.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #equal(ObservableNumberValue,
     * ObservableNumberValue, double) equal()} method that allows a small tolerance.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return equal(op1, op2, 0.0, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final double op2, final double epsilon) {
        return equal(op1, DoubleConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final double op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(DoubleConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final float op2, final double epsilon) {
        return equal(op1, FloatConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final float op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(FloatConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final long op2, final double epsilon) {
        return equal(op1, LongConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #equal(ObservableNumberValue, long,
     * double) equal()} method that allows a small tolerance.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final long op2) {
        return equal(op1, LongConstant.valueOf(op2), 0.0, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final long op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(LongConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #equal(long, ObservableNumberValue,
     * double) equal()} method that allows a small tolerance.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final long op1, final ObservableNumberValue op2) {
        return equal(LongConstant.valueOf(op1), op2, 0.0, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final int op2, final double epsilon) {
        return equal(op1, IntegerConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #equal(ObservableNumberValue, int,
     * double) equal()} method that allows a small tolerance.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableNumberValue op1, final int op2) {
        return equal(op1, IntegerConstant.valueOf(op2), 0.0, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final int op1, final ObservableNumberValue op2, final double epsilon) {
        return equal(IntegerConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #equal(int, ObservableNumberValue,
     * double) equal()} method that allows a small tolerance.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding equal(final int op1, final ObservableNumberValue op2) {
        return equal(IntegerConstant.valueOf(op1), op2, 0.0, op2);
    }

    // =================================================================================================================
    // Not Equal

    private static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final double epsilon, final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.doubleValue() - op2.doubleValue()) > epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.floatValue() - op2.floatValue()) > epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.longValue() - op2.longValue()) > epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return Math.abs(op1.intValue() - op2.intValue()) > epsilon;
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableNumberValue} are not equal (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final double epsilon) {
        return notEqual(op1, op2, epsilon, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableNumberValue} are not equal.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #notEqual(ObservableNumberValue,
     * ObservableNumberValue, double) notEqual()} method that allows a small tolerance.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return notEqual(op1, op2, 0.0, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final double op2, final double epsilon) {
        return notEqual(op1, DoubleConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final double op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(DoubleConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final float op2, final double epsilon) {
        return notEqual(op1, FloatConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final float op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(FloatConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final long op2, final double epsilon) {
        return notEqual(op1, LongConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #notEqual(ObservableNumberValue, long,
     * double) notEqual()} method that allows a small tolerance.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final long op2) {
        return notEqual(op1, LongConstant.valueOf(op2), 0.0, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final long op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(LongConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #notEqual(long, ObservableNumberValue,
     * double) notEqual()} method that allows a small tolerance.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final long op1, final ObservableNumberValue op2) {
        return notEqual(LongConstant.valueOf(op1), op2, 0.0, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final int op2, final double epsilon) {
        return notEqual(op1, IntegerConstant.valueOf(op2), epsilon, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #notEqual(ObservableNumberValue, int,
     * double) notEqual()} method that allows a small tolerance.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableNumberValue op1, final int op2) {
        return notEqual(op1, IntegerConstant.valueOf(op2), 0.0, op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value (with a tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point numbers because of rounding-errors.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     * @param epsilon
     *         the permitted tolerance
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final int op1, final ObservableNumberValue op2, final double epsilon) {
        return notEqual(IntegerConstant.valueOf(op1), op2, epsilon, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * not equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the {@link #notEqual(int, ObservableNumberValue,
     * double) notEqual()} method that allows a small tolerance.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding notEqual(final int op1, final ObservableNumberValue op2) {
        return notEqual(IntegerConstant.valueOf(op1), op2, 0.0, op2);
    }

    // =================================================================================================================
    // Greater Than

    private static BooleanBinding greaterThan(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.doubleValue() > op2.doubleValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.floatValue() > op2.floatValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.longValue() > op2.longValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.intValue() > op2.intValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableNumberValue} is greater than the value of the second.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return greaterThan(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final double op2) {
        return greaterThan(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final double op1, final ObservableNumberValue op2) {
        return greaterThan(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final float op2) {
        return greaterThan(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final float op1, final ObservableNumberValue op2) {
        return greaterThan(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final long op2) {
        return greaterThan(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final long op1, final ObservableNumberValue op2) {
        return greaterThan(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final ObservableNumberValue op1, final int op2) {
        return greaterThan(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final int op1, final ObservableNumberValue op2) {
        return greaterThan(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Less Than

    private static BooleanBinding lessThan(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        return greaterThan(op2, op1, dependencies);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableNumberValue} is less than the value of the second.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding lessThan(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return lessThan(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final ObservableNumberValue op1, final double op2) {
        return lessThan(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final double op1, final ObservableNumberValue op2) {
        return lessThan(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final ObservableNumberValue op1, final float op2) {
        return lessThan(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final float op1, final ObservableNumberValue op2) {
        return lessThan(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final ObservableNumberValue op1, final long op2) {
        return lessThan(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final long op1, final ObservableNumberValue op2) {
        return lessThan(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final ObservableNumberValue op1, final int op2) {
        return lessThan(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than the value of a
     * {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThan(final int op1, final ObservableNumberValue op2) {
        return lessThan(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Greater Than or Equal

    private static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.doubleValue() >= op2.doubleValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.floatValue() >= op2.floatValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.longValue() >= op2.longValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new BooleanBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected boolean computeValue() {
                    return op1.intValue() >= op2.intValue();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableNumberValue} is greater than or equal to the value of the second.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final double op2) {
        return greaterThanOrEqual(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final double op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final float op2) {
        return greaterThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final float op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final long op2) {
        return greaterThanOrEqual(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final long op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * greater than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final ObservableNumberValue op1, final int op2) {
        return greaterThanOrEqual(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final int op1, final ObservableNumberValue op2) {
        return greaterThanOrEqual(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Less Than or Equal

    private static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2,
            Observable... dependencies) {
        return greaterThanOrEqual(op2, op1, dependencies);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableNumberValue} is less than or equal to the value of the second.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final double op2) {
        return lessThanOrEqual(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final double op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final float op2) {
        return lessThanOrEqual(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final float op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final long op2) {
        return lessThanOrEqual(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final long op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableNumberValue} is
     * less than or equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final ObservableNumberValue op1, final int op2) {
        return lessThanOrEqual(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than or equal to the
     * value of a {@link ObservableNumberValue}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final int op1, final ObservableNumberValue op2) {
        return lessThanOrEqual(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Minimum

    private static NumberBinding min(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {
                    return Math.min(op1.doubleValue(), op2.doubleValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {
                    return Math.min(op1.floatValue(), op2.floatValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {
                    return Math.min(op1.longValue(), op2.longValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {
                    return Math.min(op1.intValue(), op2.intValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the minimum of the values of two instances of {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static NumberBinding min(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return min(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding min(final ObservableNumberValue op1, final double op2) {
        return (DoubleBinding) min(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding min(final double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) min(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding min(final ObservableNumberValue op1, final float op2) {
        return min(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding min(final float op1, final ObservableNumberValue op2) {
        return min(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding min(final ObservableNumberValue op1, final long op2) {
        return min(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding min(final long op1, final ObservableNumberValue op2) {
        return min(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding min(final ObservableNumberValue op1, final int op2) {
        return min(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the minimum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding min(final int op1, final ObservableNumberValue op2) {
        return min(IntegerConstant.valueOf(op1), op2, op2);
    }

    // =================================================================================================================
    // Maximum

    private static NumberBinding max(final ObservableNumberValue op1, final ObservableNumberValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
            return new DoubleBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected double computeValue() {
                    return Math.max(op1.doubleValue(), op2.doubleValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
            return new FloatBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected float computeValue() {
                    return Math.max(op1.floatValue(), op2.floatValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
            return new LongBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected long computeValue() {
                    return Math.max(op1.longValue(), op2.longValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        } else {
            return new IntegerBinding() {

                {
                    super.bind(dependencies);
                }

                @Override
                public void dispose() {
                    super.unbind(dependencies);
                }

                @Override
                protected int computeValue() {
                    return Math.max(op1.intValue(), op2.intValue());
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<?> getDependencies() {
                    return (dependencies.length == 1) ?
                            ObservableCollections.singletonObservableList(dependencies[0])
                            : new ImmutableObservableList<>(dependencies);
                }
            };
        }
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the maximum of the values of two instances of {@link
     * ObservableNumberValue}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static NumberBinding max(final ObservableNumberValue op1, final ObservableNumberValue op2) {
        return max(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding max(final ObservableNumberValue op1, final double op2) {
        return (DoubleBinding) max(op1, DoubleConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link DoubleBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static DoubleBinding max(final double op1, final ObservableNumberValue op2) {
        return (DoubleBinding) max(DoubleConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding max(final ObservableNumberValue op1, final float op2) {
        return max(op1, FloatConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding max(final float op1, final ObservableNumberValue op2) {
        return max(FloatConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding max(final ObservableNumberValue op1, final long op2) {
        return max(op1, LongConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding max(final long op1, final ObservableNumberValue op2) {
        return max(LongConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the {@code ObservableNumberValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding max(final ObservableNumberValue op1, final int op2) {
        return max(op1, IntegerConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link NumberBinding} that calculates the maximum of the value of a {@link ObservableNumberValue}
     * and a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableNumberValue}
     *
     * @return the new {@code NumberBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableNumberValue} is {@code null}
     */
    public static NumberBinding max(final int op1, final ObservableNumberValue op2) {
        return max(IntegerConstant.valueOf(op1), op2, op2);
    }

    // boolean
    // =================================================================================================================

    private static class BooleanAndBinding extends BooleanBinding {

        private final ObservableBooleanValue op1;

        private final ObservableBooleanValue op2;

        private final InvalidationListener observer;

        public BooleanAndBinding(ObservableBooleanValue op1, ObservableBooleanValue op2) {
            this.op1 = op1;
            this.op2 = op2;

            observer = new ShortCircuitAndInvalidator(this);

            op1.addListener(observer);
            op2.addListener(observer);
        }

        @Override
        public void dispose() {
            op1.removeListener(observer);
            op2.removeListener(observer);
        }

        @Override
        protected boolean computeValue() {
            return op1.get() && op2.get();
        }

        @Override
        @ReturnsUnmodifiableCollection
        public ObservableList<?> getDependencies() {
            return new ImmutableObservableList<>(op1, op2);
        }

    }

    private static class ShortCircuitAndInvalidator implements InvalidationListener {

        private final WeakReference<BooleanAndBinding> ref;

        private ShortCircuitAndInvalidator(BooleanAndBinding binding) {
            assert binding != null;
            ref = new WeakReference<>(binding);
        }

        @Override
        public void invalidated(Observable observable) {
            final BooleanAndBinding binding = ref.get();
            if (binding == null) {
                observable.removeListener(this);
            } else {
                // short-circuit invalidation. This BooleanBinding becomes only invalid if the first operator changes or
                // the first parameter is true.
                if ((binding.op1.equals(observable) || (binding.isValid() && binding.op1.get()))) {
                    binding.invalidate();
                }
            }
        }

    }

    /**
     * Creates a {@link BooleanBinding} that calculates the conditional-AND operation on the value of two instance of
     * {@link ObservableBooleanValue}.
     *
     * @param op1
     *         first {@code ObservableBooleanValue}
     * @param op2
     *         second {@code ObservableBooleanValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding and(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanAndBinding(op1, op2);
    }

    private static class BooleanOrBinding extends BooleanBinding {

        private final ObservableBooleanValue op1;

        private final ObservableBooleanValue op2;

        private final InvalidationListener observer;

        public BooleanOrBinding(ObservableBooleanValue op1, ObservableBooleanValue op2) {
            this.op1 = op1;
            this.op2 = op2;
            observer = new ShortCircuitOrInvalidator(this);
            op1.addListener(observer);
            op2.addListener(observer);
        }

        @Override
        public void dispose() {
            op1.removeListener(observer);
            op2.removeListener(observer);
        }

        @Override
        protected boolean computeValue() {
            return op1.get() || op2.get();
        }

        @Override
        @ReturnsUnmodifiableCollection
        public ObservableList<?> getDependencies() {
            return new ImmutableObservableList<>(op1, op2);
        }

    }

    private static class ShortCircuitOrInvalidator implements InvalidationListener {

        private final WeakReference<BooleanOrBinding> ref;

        private ShortCircuitOrInvalidator(BooleanOrBinding binding) {
            assert binding != null;
            ref = new WeakReference<>(binding);
        }

        @Override
        public void invalidated(Observable observable) {
            final BooleanOrBinding binding = ref.get();
            if (binding == null) {
                observable.removeListener(this);
            } else {
                // short circuit invalidation. This BooleanBinding becomes only invalid if the first operator changes or
                // the first parameter is false.
                if ((binding.op1.equals(observable) || (binding.isValid() && !binding.op1.get()))) {
                    binding.invalidate();
                }
            }
        }

    }

    /**
     * Creates a {@link BooleanBinding} that calculates the conditional-OR operation on the value of two instance of
     * {@link ObservableBooleanValue}.
     *
     * @param op1
     *         first {@code ObservableBooleanValue}
     * @param op2
     *         second {@code ObservableBooleanValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding or(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanOrBinding(op1, op2);
    }

    /**
     * Creates a {@link BooleanBinding} that calculates the inverse of the value of a {@link ObservableBooleanValue}.
     *
     * @param op
     *         the {@code ObservableBooleanValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the operand is {@code null}
     */
    public static BooleanBinding not(final ObservableBooleanValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return !op.get();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableBooleanValue} are equal.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding equal(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op1, op2);
            }

            @Override
            public void dispose() {
                super.unbind(op1, op2);
            }

            @Override
            protected boolean computeValue() {
                return op1.get() == op2.get();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op1, op2);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableBooleanValue} are not equal.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableBooleanValue op1, final ObservableBooleanValue op2) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op1, op2);
            }

            @Override
            public void dispose() {
                super.unbind(op1, op2);
            }

            @Override
            protected boolean computeValue() {
                return op1.get() != op2.get();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op1, op2);
            }
        };
    }

    // String
    // =================================================================================================================

    /**
     * Returns a {@link StringExpression} that wraps a {@link ObservableValue}. If the {@code ObservableValue} is
     * already a {@code StringExpression}, it will be returned. Otherwise, a new {@link StringBinding} is created that
     * holds the value of the {@code ObservableValue} converted to a {@code String}.
     *
     * @param observableValue The source {@code ObservableValue}
     * @return A {@code StringExpression} that wraps the {@code ObservableValue} if necessary
     * @throws NullPointerException if {@code observableValue} is {@code null}
     */
    public static StringExpression convert(ObservableValue<?> observableValue) {
        return StringFormatter.convert(observableValue);
    }

    /**
     * Returns a {@link StringExpression} that holds the value of the concatenation of multiple {@code Objects}.
     * <p>
     * If one of the arguments implements {@link ObservableValue} and the value of this {@code ObservableValue} changes,
     * the change is automatically reflected in the {@code StringExpression}.
     * <p>
     * If {@code null} or an empty array is passed to this method, a {@code StringExpression} that contains an empty
     * {@code String} is returned
     *
     * @param args
     *         the {@code Objects} that should be concatenated
     *
     * @return the new {@code StringExpression}
     */
    public static StringExpression concat(Object... args) {
        return StringFormatter.concat(args);
    }

    /**
     * Creates a {@link StringExpression} that holds the value of multiple {@code Objects} formatted according to a
     * format {@code String}.
     * <p>
     * If one of the arguments implements {@link ObservableValue} and the value of this {@code ObservableValue} changes,
     * the change is automatically reflected in the {@code StringExpression}.
     * <p>
     * See {@link java.util.Formatter} for formatting rules.
     *
     * @param format
     *         the formatting {@code String}
     * @param args
     *         the {@code Objects} that should be inserted in the formatting {@code String}
     *
     * @return the new {@code StringExpression}
     */
    public static StringExpression format(String format, Object... args) {
        return StringFormatter.format(format, args);
    }

    /**
     * Creates a {@link StringExpression} that holds the value of multiple {@code Objects} formatted according to a
     * format {@code String} and a specified {@code Locale}
     * <p>
     * If one of the arguments implements {@link ObservableValue} and the value of this {@code ObservableValue} changes,
     * the change is automatically reflected in the {@code StringExpression}.
     * <p>
     * See {@link java.util.Formatter} for formatting rules. See {@link Locale} for details on {@code Locale}.
     *
     * @param locale
     *         the {@code Locale} to use during formatting
     * @param format
     *         the formatting {@code String}
     * @param args
     *         the {@code Objects} that should be inserted in the formatting {@code String}
     *
     * @return the new {@code StringExpression}
     */
    public static StringExpression format(Locale locale, String format, Object... args) {
        return StringFormatter.format(locale, format, args);
    }

    private static String getStringSafe(String value) {
        return value == null ? "" : value;
    }

    private static BooleanBinding equal(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final String s1 = getStringSafe(op1.get());
                final String s2 = getStringSafe(op2.get());
                return s1.equals(s2);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                        new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableStringValue} are equal.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding equal(final ObservableStringValue op1, final ObservableStringValue op2) {
        return equal(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableStringValue op1, String op2) {
        return equal(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding equal(String op1, final ObservableStringValue op2) {
        return equal(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqual(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final String s1 = getStringSafe(op1.get());
                final String s2 = getStringSafe(op2.get());
                return !s1.equals(s2);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                        new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableStringValue} are not equal.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableStringValue op1, final ObservableStringValue op2) {
        return notEqual(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * not equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableStringValue op1, String op2) {
        return notEqual(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * not equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding notEqual(String op1, final ObservableStringValue op2) {
        return notEqual(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final String s1 = getStringSafe(op1.get());
                final String s2 = getStringSafe(op2.get());
                return s1.equalsIgnoreCase(s2);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                        new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableStringValue} are equal ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2) {
        return equalIgnoreCase(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * equal to a constant value ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding equalIgnoreCase(final ObservableStringValue op1, String op2) {
        return equalIgnoreCase(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * equal to a constant value ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding equalIgnoreCase(String op1, final ObservableStringValue op2) {
        return equalIgnoreCase(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final String s1 = getStringSafe(op1.get());
                final String s2 = getStringSafe(op2.get());
                return !s1.equalsIgnoreCase(s2);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                        new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableStringValue} are not equal ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, final ObservableStringValue op2) {
        return notEqualIgnoreCase(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * not equal to a constant value ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding notEqualIgnoreCase(final ObservableStringValue op1, String op2) {
        return notEqualIgnoreCase(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * not equal to a constant value ignoring case.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding notEqualIgnoreCase(String op1, final ObservableStringValue op2) {
        return notEqualIgnoreCase(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding greaterThan(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final String s1 = getStringSafe(op1.get());
                final String s2 = getStringSafe(op2.get());
                return s1.compareTo(s2) > 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                        new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableStringValue} is greater than the value of the second.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding greaterThan(final ObservableStringValue op1, final ObservableStringValue op2) {
        return greaterThan(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * greater than a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding greaterThan(final ObservableStringValue op1, String op2) {
        return greaterThan(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a constant value is greater than the
     * value of a {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding greaterThan(String op1, final ObservableStringValue op2) {
        return greaterThan(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding lessThan(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        return greaterThan(op2, op1, dependencies);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableStringValue} is less than the value of the second.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding lessThan(final ObservableStringValue op1, final ObservableStringValue op2) {
        return lessThan(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * less than a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding lessThan(final ObservableStringValue op1, String op2) {
        return lessThan(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than the value of a
     * {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding lessThan(String op1, final ObservableStringValue op2) {
        return lessThan(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final String s1 = getStringSafe(op1.get());
                final String s2 = getStringSafe(op2.get());
                return s1.compareTo(s2) >= 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ? ObservableCollections.singletonObservableList(dependencies[0]) :
                        new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableStringValue} is greater than or equal to the value of the second.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2) {
        return greaterThanOrEqual(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * greater than or equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(final ObservableStringValue op1, String op2) {
        return greaterThanOrEqual(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is greater than or equal to the
     * value of a {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding greaterThanOrEqual(String op1, final ObservableStringValue op2) {
        return greaterThanOrEqual(StringConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2,
            final Observable... dependencies) {
        return greaterThanOrEqual(op2, op1, dependencies);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of the first {@link
     * ObservableStringValue} is less than or equal to the value of the second.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, final ObservableStringValue op2) {
        return lessThanOrEqual(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * less than or equal to a constant value.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the {@code ObservableStringValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(final ObservableStringValue op1, String op2) {
        return lessThanOrEqual(op1, StringConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a constant value is less than or equal to the
     * value of a {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered equal to an empty {@code String}.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding lessThanOrEqual(String op1, final ObservableStringValue op2) {
        return lessThanOrEqual(StringConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link IntegerBinding} that holds the length of a {@link ObservableStringValue}.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered to have a length of {@code 0}.
     *
     * @param op
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static IntegerBinding length(final ObservableStringValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                return getStringSafe(op.get()).length();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * empty.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered to be empty.
     *
     * @param op
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding isEmpty(final ObservableStringValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return getStringSafe(op.get()).isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of a {@link ObservableStringValue} is
     * not empty.
     * <p>
     * Note: In this comparison a {@code String} that is {@code null} is considered to be empty.
     *
     * @param op
     *         the {@code ObservableStringValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableStringValue} is {@code null}
     */
    public static BooleanBinding isNotEmpty(final ObservableStringValue op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return !getStringSafe(op.get()).isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    // Object
    // =================================================================================================================

    private static BooleanBinding equal(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final Object obj1 = op1.get();
                final Object obj2 = op2.get();
                return Objects.equals(obj1, obj2);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ?
                        ObservableCollections.singletonObservableList(dependencies[0])
                        : new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableObjectValue} are equal.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding equal(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2) {
        return equal(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of an {@link ObservableObjectValue} is
     * equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableCharacterValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableCharacterValue} is {@code null}
     */
    public static BooleanBinding equal(final ObservableObjectValue<?> op1, Object op2) {
        return equal(op1, ObjectConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of an {@link ObservableObjectValue} is
     * equal to a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableCharacterValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableCharacterValue} is {@code null}
     */
    public static BooleanBinding equal(Object op1, final ObservableObjectValue<?> op2) {
        return equal(ObjectConstant.valueOf(op1), op2, op2);
    }

    private static BooleanBinding notEqual(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2,
            final Observable... dependencies) {
        if ((op1 == null) || (op2 == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }
        assert (dependencies != null) && (dependencies.length > 0);

        return new BooleanBinding() {

            {
                super.bind(dependencies);
            }

            @Override
            public void dispose() {
                super.unbind(dependencies);
            }

            @Override
            protected boolean computeValue() {
                final Object obj1 = op1.get();
                final Object obj2 = op2.get();
                return !Objects.equals(obj1, obj2);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return (dependencies.length == 1) ?
                        ObservableCollections.singletonObservableList(dependencies[0])
                        : new ImmutableObservableList<>(dependencies);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the values of two instances of {@link
     * ObservableObjectValue} are not equal.
     *
     * @param op1
     *         the first operand
     * @param op2
     *         the second operand
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if one of the operands is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableObjectValue<?> op1, final ObservableObjectValue<?> op2) {
        return notEqual(op1, op2, op1, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of an {@link ObservableObjectValue} is
     * not equal to a constant value.
     *
     * @param op1
     *         the {@code ObservableObjectValue}
     * @param op2
     *         the constant value
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableObjectValue} is {@code null}
     */
    public static BooleanBinding notEqual(final ObservableObjectValue<?> op1, Object op2) {
        return notEqual(op1, ObjectConstant.valueOf(op2), op1);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of an {@link ObservableObjectValue} is
     * not equal to a constant value.
     *
     * @param op1
     *         the constant value
     * @param op2
     *         the {@code ObservableObjectValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableObjectValue} is {@code null}
     */
    public static BooleanBinding notEqual(Object op1, final ObservableObjectValue<?> op2) {
        return notEqual(ObjectConstant.valueOf(op1), op2, op2);
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of an {@link ObservableObjectValue} is
     * {@code null}.
     *
     * @param op
     *         the {@code ObservableObjectValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableObjectValue} is {@code null}
     */
    public static BooleanBinding isNull(final ObservableObjectValue<?> op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return op.get() == null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if the value of an {@link ObservableObjectValue} is
     * not {@code null}.
     *
     * @param op
     *         the {@code ObservableObjectValue}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableObjectValue} is {@code null}
     */
    public static BooleanBinding isNotNull(final ObservableObjectValue<?> op) {
        if (op == null) {
            throw new NullPointerException("Operand cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return op.get() != null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    // List
    // =================================================================================================================

    /**
     * Creates a new {@link IntegerBinding} that contains the size of an {@link ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param <E>
     *         type of the {@code List} elements
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     */
    public static <E> IntegerBinding size(final ObservableList<E> op) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                return op.size();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a given {@link ObservableList} is empty.
     *
     * @param op
     *         the {@code ObservableList}
     * @param <E>
     *         type of the {@code List} elements
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     */
    public static <E> BooleanBinding isEmpty(final ObservableList<E> op) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return op.isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a given {@link ObservableList} is not empty.
     *
     * @param op
     *         the {@code ObservableList}
     * @param <E>
     *         type of the {@code List} elements
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     */
    public static <E> BooleanBinding isNotEmpty(final ObservableList<E> op) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return !op.isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link ObjectBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code ObjectBinding} will contain {@code null}, if the {@code index} points behind the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     * @param <E>
     *         the type of the {@code List} elements
     *
     * @return the new {@code ObjectBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static <E> ObjectBinding<E> valueAt(final ObservableList<E> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new ObjectBinding<E>() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected E computeValue() {
                try {
                    return op.get(index);
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link ObjectBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code ObjectBinding} will contain {@code null}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     * @param <E>
     *         the type of the {@code List} elements
     *
     * @return the new {@code ObjectBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static <E> ObjectBinding<E> valueAt(final ObservableList<E> op, final ObservableIntegerValue index) {
        return valueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link ObjectBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code ObjectBinding} will contain {@code null}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}, converted to int
     * @param <E>
     *         the type of the {@code List} elements
     *
     * @return the new {@code ObjectBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static <E> ObjectBinding<E> valueAt(final ObservableList<E> op, final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new ObjectBinding<E>() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected E computeValue() {
                try {
                    return op.get(index.intValue());
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code BooleanBinding} will hold {@code false}, if the {@code index} points behind the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static BooleanBinding booleanValueAt(final ObservableList<Boolean> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                try {
                    final Boolean value = op.get(index);
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value;
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return false;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code BooleanBinding} will hold {@code false}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static BooleanBinding booleanValueAt(final ObservableList<Boolean> op, final ObservableIntegerValue index) {
        return booleanValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link BooleanBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code BooleanBinding} will hold {@code false}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}, converted to int
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static BooleanBinding booleanValueAt(final ObservableList<Boolean> op, final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected boolean computeValue() {
                try {
                    final Boolean value = op.get(index.intValue());
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value;
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return false;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }
        };
    }

    /**
     * Creates a new {@link DoubleBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code DoubleBinding} will hold {@code 0.0}, if the {@code index} points behind the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static DoubleBinding doubleValueAt(final ObservableList<? extends Number> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new DoubleBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected double computeValue() {
                try {
                    final Number value = op.get(index);
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.doubleValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0.0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link DoubleBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code DoubleBinding} will hold {@code 0.0}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static DoubleBinding doubleValueAt(final ObservableList<? extends Number> op,
            final ObservableIntegerValue index) {
        return doubleValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link DoubleBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code DoubleBinding} will hold {@code 0.0}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}, converted to int
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static DoubleBinding doubleValueAt(final ObservableList<? extends Number> op,
            final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new DoubleBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected double computeValue() {
                try {
                    final Number value = op.get(index.intValue());
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.doubleValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0.0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }
        };
    }

    /**
     * Creates a new {@link FloatBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code FloatBinding} will hold {@code 0.0f}, if the {@code index} points behind the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static FloatBinding floatValueAt(final ObservableList<? extends Number> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new FloatBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected float computeValue() {
                try {
                    final Number value = op.get(index);
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.floatValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0.0f;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link FloatBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code FloatBinding} will hold {@code 0.0f}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static FloatBinding floatValueAt(final ObservableList<? extends Number> op,
            final ObservableIntegerValue index) {
        return floatValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link FloatBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code FloatBinding} will hold {@code 0.0f}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}, converted to int
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static FloatBinding floatValueAt(final ObservableList<? extends Number> op,
            final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new FloatBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected float computeValue() {
                try {
                    final Number value = op.get(index.intValue());
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.floatValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0.0f;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }
        };
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code IntegerBinding} will hold {@code 0}, if the {@code index} points behind the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static IntegerBinding integerValueAt(final ObservableList<? extends Number> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                try {
                    final Number value = op.get(index);
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.intValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code IntegerBinding} will hold {@code 0}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static IntegerBinding integerValueAt(final ObservableList<? extends Number> op,
            final ObservableIntegerValue index) {
        return integerValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code IntegerBinding} will hold {@code 0}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}, converted to int
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static IntegerBinding integerValueAt(final ObservableList<? extends Number> op,
            final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected int computeValue() {
                try {
                    final Number value = op.get(index.intValue());
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.intValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }
        };
    }

    /**
     * Creates a new {@link LongBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code LongBinding} will hold {@code 0L}, if the {@code index} points behind the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code LongBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static LongBinding longValueAt(final ObservableList<? extends Number> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new LongBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected long computeValue() {
                try {
                    final Number value = op.get(index);
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.longValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0L;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link LongBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code LongBinding} will hold {@code 0L}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code LongBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static LongBinding longValueAt(final ObservableList<? extends Number> op,
            final ObservableIntegerValue index) {
        return longValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link LongBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code LongBinding} will hold {@code 0L}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}, converted to int
     *
     * @return the new {@code LongBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static LongBinding longValueAt(final ObservableList<? extends Number> op,
            final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new LongBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected long computeValue() {
                try {
                    final Number value = op.get(index.intValue());
                    if (value == null) {
                        Logging.getLogger().fine("List element is null, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.longValue();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0L;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }
        };
    }

    /**
     * Creates a new {@link StringBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code StringBinding} will hold {@code null}, if the {@code index} points behind the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code StringBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static StringBinding stringValueAt(final ObservableList<String> op, final int index) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new StringBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected String computeValue() {
                try {
                    return op.get(index);
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link StringBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code StringBinding} will hold {@code ""}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}
     *
     * @return the new {@code StringBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static StringBinding stringValueAt(final ObservableList<String> op, final ObservableIntegerValue index) {
        return stringValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link StringBinding} that contains the element of an {@link ObservableList} at the specified
     * position. The {@code StringBinding} will hold {@code ""}, if the {@code index} is outside the {@code
     * ObservableList}.
     *
     * @param op
     *         the {@code ObservableList}
     * @param index
     *         the position in the {@code List}, converted to int
     *
     * @return the new {@code StringBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableList} or {@code index} is {@code null}
     */
    public static StringBinding stringValueAt(final ObservableList<String> op, final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new StringBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected String computeValue() {
                try {
                    return op.get(index.intValue());
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }
        };
    }

    // Set
    // =================================================================================================================

    /**
     * Creates a new {@link IntegerBinding} that contains the size of an {@link ObservableSet}.
     *
     * @param op
     *         the {@code ObservableSet}
     * @param <E>
     *         the type of the {@code Set} elements
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableSet} is {@code null}
     */
    public static <E> IntegerBinding size(final ObservableSet<E> op) {
        if (op == null) {
            throw new NullPointerException("Set cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                return op.size();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a given {@link ObservableSet} is empty.
     *
     * @param op
     *         the {@code ObservableSet}
     * @param <E>
     *         the type of the {@code Set} elements
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableSet} is {@code null}
     */
    public static <E> BooleanBinding isEmpty(final ObservableSet<E> op) {
        if (op == null) {
            throw new NullPointerException("Set cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return op.isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a given {@link ObservableSet} is not empty.
     *
     * @param op
     *         the {@code ObservableSet}
     * @param <E>
     *         the type of the {@code Set} elements
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableSet} is {@code null}
     */
    public static <E> BooleanBinding isNotEmpty(final ObservableSet<E> op) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return !op.isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    // Array
    // =================================================================================================================

    /**
     * Creates a new {@link IntegerBinding} that contains the size of an {@link ObservableArray}.
     *
     * @param op
     *         the {@code ObservableArray}
     * @param <T>
     *         actual array instance type
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableArray} is {@code null}
     */
    public static <T extends ObservableArray<T>> IntegerBinding size(final ObservableArray<T> op) {
        if (op == null) {
            throw new NullPointerException("Array cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                return op.size();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link FloatBinding} that contains the element of an {@link ObservableArray} at the specified
     * position. The {@code FloatBinding} will hold {@code 0.0f}, if the {@code index} points behind the {@code
     * ObservableArray}.
     *
     * @param op
     *         the {@code ObservableArray}
     * @param index
     *         the position in the {@code ObservableArray}
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableArray} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index < 0}
     */
    public static FloatBinding floatValueAt(final ObservableFloatArray op, final int index) {
        if (op == null) {
            throw new NullPointerException("Array cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new FloatBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected float computeValue() {
                try {
                    return op.get(index);
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0.0f;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }

        };
    }

    /**
     * Creates a new {@link FloatBinding} that contains the element of an {@link ObservableArray} at the specified
     * position. The {@code FloatBinding} will hold {@code 0.0f}, if the {@code index} is outside the {@code
     * ObservableArray}.
     *
     * @param op
     *         the {@code ObservableArray}
     * @param index
     *         the position in the {@code ObservableArray}
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableArray} or {@code index} is {@code null}
     */
    public static FloatBinding floatValueAt(final ObservableFloatArray op, final ObservableIntegerValue index) {
        return floatValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link FloatBinding} that contains the element of an {@link ObservableArray} at the specified
     * position. The {@code FloatBinding} will hold {@code 0.0f}, if the {@code index} is outside the {@code
     * ObservableArray}.
     *
     * @param op
     *         the {@code ObservableArray}
     * @param index
     *         the position in the {@code ObservableArray}, converted to int
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableArray} or {@code index} is {@code null}
     */
    public static FloatBinding floatValueAt(final ObservableFloatArray op, final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new FloatBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected float computeValue() {
                try {
                    return op.get(index.intValue());
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0.0f;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }

        };
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the element of an {@link ObservableArray} at the specified
     * position. The {@code IntegerBinding} will hold {@code 0}, if the {@code index} points behind the {@code
     * ObservableArray}.
     *
     * @param op
     *         the {@code ObservableArray}
     * @param index
     *         the position in the {@code ObservableArray}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableArray} is {@code null}
     * @throws IllegalArgumentException
     *         if {@code index &lt; 0}
     */
    public static IntegerBinding integerValueAt(final ObservableIntegerArray op, final int index) {
        if (op == null) {
            throw new NullPointerException("Array cannot be null.");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                try {
                    return op.get(index);
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }

        };
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the element of an {@link ObservableArray} at the specified
     * position. The {@code IntegerBinding} will hold {@code 0}, if the {@code index} is outside the {@code
     * ObservableArray}.
     *
     * @param op
     *         the {@code ObservableArray}
     * @param index
     *         the position in the {@code ObservableArray}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableArray} or {@code index} is {@code null}
     */
    public static IntegerBinding integerValueAt(final ObservableIntegerArray op, final ObservableIntegerValue index) {
        return integerValueAt(op, (ObservableNumberValue) index);
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the element of an {@link ObservableArray} at the specified
     * position. The {@code IntegerBinding} will hold {@code 0}, if the {@code index} is outside the {@code
     * ObservableArray}.
     *
     * @param op
     *         the {@code ObservableArray}
     * @param index
     *         the position in the {@code ObservableArray}, converted to int
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableArray} or {@code index} is {@code null}
     */
    public static IntegerBinding integerValueAt(final ObservableIntegerArray op, final ObservableNumberValue index) {
        if ((op == null) || (index == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op, index);
            }

            @Override
            public void dispose() {
                super.unbind(op, index);
            }

            @Override
            protected int computeValue() {
                try {
                    return op.get(index.intValue());
                } catch (IndexOutOfBoundsException ex) {
                    Logging.getLogger().fine("Exception while evaluating binding", ex);
                }
                return 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, index);
            }

        };
    }

    // Map
    // =================================================================================================================

    /**
     * Creates a new {@link IntegerBinding} that contains the size of an {@link ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param <K>
     *         type of the key elements of the {@code Map}
     * @param <V>
     *         type of the value elements of the {@code Map}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K, V> IntegerBinding size(final ObservableMap<K, V> op) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                return op.size();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a given {@link ObservableMap} is empty.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param <K>
     *         type of the key elements of the {@code Map}
     * @param <V>
     *         type of the value elements of the {@code Map}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K, V> BooleanBinding isEmpty(final ObservableMap<K, V> op) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return op.isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that holds {@code true} if a given {@link ObservableMap} is not empty.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param <K>
     *         type of the key elements of the {@code Map}
     * @param <V>
     *         type of the value elements of the {@code Map}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K, V> BooleanBinding isNotEmpty(final ObservableMap<K, V> op) {
        if (op == null) {
            throw new NullPointerException("List cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                return !op.isEmpty();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link ObjectBinding} that contains the mapping of a specific key in an {@link ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     * @param <V>
     *         type of the value elements of the {@code Map}
     *
     * @return the new {@code ObjectBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K, V> ObjectBinding<V> valueAt(final ObservableMap<K, V> op, final K key) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new ObjectBinding<V>() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected V computeValue() {
                try {
                    return op.get(key);
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link ObjectBinding} that contains the mapping of a specific key in an {@link ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     * @param <V>
     *         type of the value elements of the {@code Map}
     *
     * @return the new {@code ObjectBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} or {@code key} is {@code null}
     */
    public static <K, V> ObjectBinding<V> valueAt(final ObservableMap<K, V> op,
            final ObservableValue<? extends K> key) {
        if ((op == null) || (key == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new ObjectBinding<V>() {

            {
                super.bind(op, key);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected V computeValue() {
                try {
                    return op.get(key.getValue());
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, key);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code BooleanBinding} will hold {@code false}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K> BooleanBinding booleanValueAt(final ObservableMap<K, Boolean> op, final K key) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected boolean computeValue() {
                try {
                    final Boolean value = op.get(key);
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value;
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return false;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link BooleanBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code BooleanBinding} will hold {@code false}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code BooleanBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} or {@code key} is {@code null}
     */
    public static <K> BooleanBinding booleanValueAt(final ObservableMap<K, Boolean> op,
            final ObservableValue<? extends K> key) {
        if ((op == null) || (key == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new BooleanBinding() {

            {
                super.bind(op, key);
            }

            @Override
            public void dispose() {
                super.unbind(op, key);
            }

            @Override
            protected boolean computeValue() {
                try {
                    final Boolean value = op.get(key.getValue());
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value;
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return false;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, key);
            }
        };
    }

    /**
     * Creates a new {@link DoubleBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code DoubleBinding} will hold {@code 0.0}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K> DoubleBinding doubleValueAt(final ObservableMap<K, ? extends Number> op, final K key) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new DoubleBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected double computeValue() {
                try {
                    final Number value = op.get(key);
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.doubleValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0.0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link DoubleBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code DoubleBinding} will hold {@code 0.0}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code DoubleBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} or {@code key} is {@code null}
     */
    public static <K> DoubleBinding doubleValueAt(final ObservableMap<K, ? extends Number> op,
            final ObservableValue<? extends K> key) {
        if ((op == null) || (key == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new DoubleBinding() {

            {
                super.bind(op, key);
            }

            @Override
            public void dispose() {
                super.unbind(op, key);
            }

            @Override
            protected double computeValue() {
                try {
                    final Number value = op.get(key.getValue());
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.doubleValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0.0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, key);
            }
        };
    }

    /**
     * Creates a new {@link FloatBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code FloatBinding} will hold {@code 0.0f}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K> FloatBinding floatValueAt(final ObservableMap<K, ? extends Number> op, final K key) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new FloatBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected float computeValue() {
                try {
                    final Number value = op.get(key);
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.floatValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0.0f;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link FloatBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code FloatBinding} will hold {@code 0.0f}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code FloatBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} or {@code key} is {@code null}
     */
    public static <K> FloatBinding floatValueAt(final ObservableMap<K, ? extends Number> op,
            final ObservableValue<? extends K> key) {
        if ((op == null) || (key == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new FloatBinding() {

            {
                super.bind(op, key);
            }

            @Override
            public void dispose() {
                super.unbind(op, key);
            }

            @Override
            protected float computeValue() {
                try {
                    final Number value = op.get(key.getValue());
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.floatValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0.0f;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, key);
            }
        };
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code IntegerBinding} will hold {@code 0}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K> IntegerBinding integerValueAt(final ObservableMap<K, ? extends Number> op, final K key) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected int computeValue() {
                try {
                    final Number value = op.get(key);
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.intValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link IntegerBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code IntegerBinding} will hold {@code 0}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code IntegerBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} or {@code key} is {@code null}
     */
    public static <K> IntegerBinding integerValueAt(final ObservableMap<K, ? extends Number> op,
            final ObservableValue<? extends K> key) {
        if ((op == null) || (key == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new IntegerBinding() {

            {
                super.bind(op, key);
            }

            @Override
            public void dispose() {
                super.unbind(op, key);
            }

            @Override
            protected int computeValue() {
                try {
                    final Number value = op.get(key.getValue());
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.intValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, key);
            }
        };
    }

    /**
     * Creates a new {@link LongBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code LongBinding} will hold {@code 0L}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code LongBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K> LongBinding longValueAt(final ObservableMap<K, ? extends Number> op, final K key) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new LongBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected long computeValue() {
                try {
                    final Number value = op.get(key);
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.longValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0L;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link LongBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code LongBinding} will hold {@code 0L}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code LongBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} or {@code key} is {@code null}
     */
    public static <K> LongBinding longValueAt(final ObservableMap<K, ? extends Number> op,
            final ObservableValue<? extends K> key) {
        if ((op == null) || (key == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new LongBinding() {

            {
                super.bind(op, key);
            }

            @Override
            public void dispose() {
                super.unbind(op, key);
            }

            @Override
            protected long computeValue() {
                try {
                    final Number value = op.get(key.getValue());
                    if (value == null) {
                        Logging.getLogger().fine("Element not found in map, returning default value instead.",
                                new NullPointerException());
                    } else {
                        return value.longValue();
                    }
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return 0L;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, key);
            }
        };
    }

    /**
     * Creates a new {@link StringBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code StringBinding} will hold {@code null}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code StringBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} is {@code null}
     */
    public static <K> StringBinding stringValueAt(final ObservableMap<K, String> op, final K key) {
        if (op == null) {
            throw new NullPointerException("Map cannot be null.");
        }

        return new StringBinding() {

            {
                super.bind(op);
            }

            @Override
            public void dispose() {
                super.unbind(op);
            }

            @Override
            protected String computeValue() {
                try {
                    return op.get(key);
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return ObservableCollections.singletonObservableList(op);
            }
        };
    }

    /**
     * Creates a new {@link StringBinding} that contains the mapping of a specific key in an {@link ObservableMap}. The
     * {@code StringBinding} will hold {@code ""}, if the {@code key} cannot be found in the {@code ObservableMap}.
     *
     * @param op
     *         the {@code ObservableMap}
     * @param key
     *         the key in the {@code Map}
     * @param <K>
     *         type of the key elements of the {@code Map}
     *
     * @return the new {@code StringBinding}
     *
     * @throws NullPointerException
     *         if the {@code ObservableMap} or {@code key} is {@code null}
     */
    public static <K> StringBinding stringValueAt(final ObservableMap<K, String> op,
            final ObservableValue<? extends K> key) {
        if ((op == null) || (key == null)) {
            throw new NullPointerException("Operands cannot be null.");
        }

        return new StringBinding() {

            {
                super.bind(op, key);
            }

            @Override
            public void dispose() {
                super.unbind(op, key);
            }

            @Override
            protected String computeValue() {
                try {
                    return op.get(key.getValue());
                } catch (ClassCastException | NullPointerException ex) {
                    Logging.getLogger().warning("Exception while evaluating binding", ex);
                    // ignore
                }
                return null;
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<?> getDependencies() {
                return new ImmutableObservableList<>(op, key);
            }
        };
    }

}
