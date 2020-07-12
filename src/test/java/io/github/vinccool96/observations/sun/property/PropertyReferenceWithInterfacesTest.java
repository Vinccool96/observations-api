package io.github.vinccool96.observations.sun.property;

import io.github.vinccool96.observations.beans.property.SimpleStringProperty;
import io.github.vinccool96.observations.beans.property.StringProperty;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PropertyReferenceWithInterfacesTest {

    @Test
    public void shouldBeAbleToReadPropertyValueFromPropertyReferenceDeclaredOnInterface() {
        NamedBean test = new NamedBean();
        test.setName("A");
        assertEquals("A", test.getName());
        assertEquals("A", Named.NAME.get(test));
    }

    public interface Named {

        PropertyReference<String> NAME = new PropertyReference<>(Named.class, "name");

        String getName();

    }

    public static class NamedBean implements Named {

        private final StringProperty name = new SimpleStringProperty();

        @Override public final String getName() {
            return name.get();
        }

        public final void setName(String value) {
            name.set(value);
        }

    }

}
