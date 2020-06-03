package io.github.vinccool96.observations.sun.collections.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is to be used for methods that return unmodifiable collections.
 */
@Target(value = {java.lang.annotation.ElementType.METHOD})
@Retention(value = java.lang.annotation.RetentionPolicy.SOURCE)
public @interface ReturnsUnmodifiableCollection {

}
