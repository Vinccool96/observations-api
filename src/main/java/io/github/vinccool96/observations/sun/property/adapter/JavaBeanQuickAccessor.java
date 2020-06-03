package io.github.vinccool96.observations.sun.property.adapter;

import io.github.vinccool96.observations.beans.property.adapter.ReadOnlyJavaBeanObjectProperty;
import io.github.vinccool96.observations.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder;

public final class JavaBeanQuickAccessor {

    private JavaBeanQuickAccessor() {
    }

    public static <T> ReadOnlyJavaBeanObjectProperty<T> createReadOnlyJavaBeanObjectProperty(Object bean, String name)
            throws NoSuchMethodException {
        return ReadOnlyJavaBeanObjectPropertyBuilder.<T>create().bean(bean).name(name).build();
    }

}
