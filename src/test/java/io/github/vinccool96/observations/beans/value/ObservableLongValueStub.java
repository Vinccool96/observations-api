package io.github.vinccool96.observations.beans.value;

public class ObservableLongValueStub extends ObservableValueBase<Number> implements ObservableLongValue {

    private long value;

    public ObservableLongValueStub() {
    }

    public ObservableLongValueStub(long initialValue) {
        value = initialValue;
    }

    public void set(long value) {
        this.value = value;
        this.fireValueChangedEvent();
    }

    @Override
    public long get() {
        return value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public short shortValue() {
        return (short) get();
    }

}
