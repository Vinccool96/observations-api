package io.github.vinccool96.observations.sun.collections.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be used for methods that return unmodifiable collections.
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.SOURCE)
public @interface ReturnsUnmodifiableCollection {

}
