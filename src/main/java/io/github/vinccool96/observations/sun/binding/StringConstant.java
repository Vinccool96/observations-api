package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.binding.StringExpression;
import io.github.vinccool96.observations.beans.value.ChangeListener;

public class StringConstant extends StringExpression {

    private final String value;

    private StringConstant(String value) {
        this.value = value;
    }

    public static StringConstant valueOf(String value) {
        return new StringConstant(value);
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void addListener(InvalidationListener observer) {
        // no-op
    }

    @Override
    public void addListener(ChangeListener<? super String> observer) {
        // no-op
    }

    @Override
    public void removeListener(InvalidationListener observer) {
        // no-op
    }

    @Override
    public void removeListener(ChangeListener<? super String> observer) {
        // no-op
    }

}
