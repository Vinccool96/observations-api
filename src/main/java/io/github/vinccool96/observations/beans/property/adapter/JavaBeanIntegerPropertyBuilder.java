package io.github.vinccool96.observations.beans.property.adapter;

import io.github.vinccool96.observations.sun.property.adapter.JavaBeanPropertyBuilderHelper;
import io.github.vinccool96.observations.sun.property.adapter.PropertyDescriptor;

import java.lang.reflect.Method;

/**
 * A {@code JavaBeanIntegerPropertyBuilder} can be used to create {@link JavaBeanIntegerProperty
 * JavaBeanIntegerProperties}. To create a {@code JavaBeanIntegerProperty} one first has to call {@link #create()} to
 * generate a builder, set the required properties, and then one can call {@link #build()} to generate the property.
 * <p>
 * Not all properties of a builder have to specified, there are several combinations possible. As a minimum the {@link
 * #name(String)} of the property and the {@link #bean(Object)} have to be specified. If the names of the getter and
 * setter follow the conventions, this is sufficient. Otherwise it is possible to specify an alternative name for the
 * getter and setter ({@link #getter(String)} and {@link #setter(String)}) or the getter and setter {@code Methods}
 * directly ({@link #getter(Method)} and {@link #setter(Method)}).
 * <p>
 * All methods to change properties return a reference to this builder, to enable method chaining.
 * <p>
 * If you have to generate adapters for the same property of several instances of the same class, you can reuse a {@code
 * JavaBeanIntegerPropertyBuilder}. by switching the Java Bean instance (with {@link #bean(Object)} and calling {@link
 * #build()}.
 *
 * @see JavaBeanIntegerProperty
*/
public final class JavaBeanIntegerPropertyBuilder {

    private JavaBeanPropertyBuilderHelper helper = new JavaBeanPropertyBuilderHelper();

    /**
     * Create a new instance of {@code JavaBeanIntegerPropertyBuilder}
     *
     * @return the new {@code JavaBeanIntegerPropertyBuilder}
     */
    public static JavaBeanIntegerPropertyBuilder create() {
        return new JavaBeanIntegerPropertyBuilder();
    }

    /**
     * Generate a new {@link JavaBeanIntegerProperty} with the current settings.
     *
     * @return the new {@code JavaBeanIntegerProperty}
     *
     * @throws NoSuchMethodException
     *         if the settings were not sufficient to find the getter and the setter of the Java Bean property
     * @throws IllegalArgumentException
     *         if the Java Bean property is not of type {@code int} or {@code Integer}
     */
    public JavaBeanIntegerProperty build() throws NoSuchMethodException {
        final PropertyDescriptor descriptor = helper.getDescriptor();
        if (!int.class.equals(descriptor.getType()) && !Number.class.isAssignableFrom(descriptor.getType())) {
            throw new IllegalArgumentException("Not an int property");
        }
        return new JavaBeanIntegerProperty(descriptor, helper.getBean());
    }

    /**
     * Set the name of the property
     *
     * @param name
     *         the name of the property
     *
     * @return a reference to this builder to enable method chaining
     */
    public JavaBeanIntegerPropertyBuilder name(String name) {
        helper.name(name);
        return this;
    }

    /**
     * Set the Java Bean instance the adapter should connect to
     *
     * @param bean
     *         the Java Bean instance
     *
     * @return a reference to this builder to enable method chaining
     */
    public JavaBeanIntegerPropertyBuilder bean(Object bean) {
        helper.bean(bean);
        return this;
    }

    /**
     * Set the Java Bean class in which the getter and setter should be searched. This can be useful, if the builder
     * should generate adapters for several Java Beans of different types.
     *
     * @param beanClass
     *         the Java Bean class
     *
     * @return a reference to this builder to enable method chaining
     */
    public JavaBeanIntegerPropertyBuilder beanClass(Class<?> beanClass) {
        helper.beanClass(beanClass);
        return this;
    }

    /**
     * Set an alternative name for the getter. This can be omitted, if the name of the getter follows Java Bean naming
     * conventions.
     *
     * @param getter
     *         the alternative name of the getter
     *
     * @return a reference to this builder to enable method chaining
     */
    public JavaBeanIntegerPropertyBuilder getter(String getter) {
        helper.getterName(getter);
        return this;
    }

    /**
     * Set an alternative name for the setter. This can be omitted, if the name of the setter follows Java Bean naming
     * conventions.
     *
     * @param setter
     *         the alternative name of the setter
     *
     * @return a reference to this builder to enable method chaining
     */
    public JavaBeanIntegerPropertyBuilder setter(String setter) {
        helper.setterName(setter);
        return this;
    }

    /**
     * Set the getter method directly. This can be omitted, if the name of the getter follows Java Bean naming
     * conventions.
     *
     * @param getter
     *         the getter
     *
     * @return a reference to this builder to enable method chaining
     */
    public JavaBeanIntegerPropertyBuilder getter(Method getter) {
        helper.getter(getter);
        return this;
    }

    /**
     * Set the setter method directly. This can be omitted, if the name of the setter follows Java Bean naming
     * conventions.
     *
     * @param setter
     *         the setter
     *
     * @return a reference to this builder to enable method chaining
     */
    public JavaBeanIntegerPropertyBuilder setter(Method setter) {
        helper.setter(setter);
        return this;
    }

}
