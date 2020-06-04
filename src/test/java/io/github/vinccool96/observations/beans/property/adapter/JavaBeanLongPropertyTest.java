package io.github.vinccool96.observations.beans.property.adapter;

import io.github.vinccool96.observations.beans.property.Property;
import io.github.vinccool96.observations.beans.property.SimpleLongProperty;

import java.beans.PropertyVetoException;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class JavaBeanLongPropertyTest extends JavaBeanPropertyTestBase<Number> {

    private static final long[] VALUES = new long[]{Long.MAX_VALUE, Long.MIN_VALUE};

    @Override
    protected BeanStub<Number> createBean(Number initialValue) {
        return new LongPOJO(initialValue.longValue());
    }

    @Override
    protected void check(Number actual, Number expected) {
        assertEquals(actual.longValue(), expected.longValue());
    }

    @Override
    protected Number getValue(int index) {
        return VALUES[index];
    }

    @Override
    protected Property<Number> createObservable(Number value) {
        return new SimpleLongProperty(value.longValue());
    }

    @Override
    protected JavaBeanProperty<Number> extractProperty(Object bean) throws NoSuchMethodException {
        return JavaBeanLongPropertyBuilder.create().bean(bean).name("x").build();
    }

    public class LongPOJO extends BeanStub<Number> {

        private Long x;

        private boolean failureMode;

        public LongPOJO(Long x) {
            this.x = x;
        }

        public Long getX() {
            if (failureMode) {
                throw new RuntimeException("FailureMode activated");
            } else {
                return x;
            }
        }

        public void setX(Long x) {
            if (failureMode) {
                throw new RuntimeException("FailureMode activated");
            } else {
                this.x = x;
            }
        }

        @Override
        public Long getValue() {
            return getX();
        }

        @Override
        public void setValue(Number value) throws PropertyVetoException {
            setX(value.longValue());
        }

        @Override
        public void setFailureMode(boolean failureMode) {
            this.failureMode = failureMode;
        }

    }

}
