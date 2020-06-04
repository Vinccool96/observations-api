package io.github.vinccool96.observations.beans;

import java.lang.annotation.*;

/**
 * Specifies a property to which child elements will be added or set when an explicit property is not given.
 *
 * @since JavaFX 2.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefaultProperty {

    /**
     * The name of the default property.
     *
     * @return the name
     */
    String value();

}
