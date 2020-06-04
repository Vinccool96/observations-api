package io.github.vinccool96.observations.beans;

import io.github.vinccool96.observations.beans.property.*;
import io.github.vinccool96.observations.sun.property.PropertyReference;

/**
 *
 */
public class Person {

    private final StringProperty name = new SimpleStringProperty();

    public final String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    private final IntegerProperty age = new SimpleIntegerProperty();

    public final int getAge() {
        return age.get();
    }

    public void setAge(int value) {
        age.set(value);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    private final BooleanProperty retired = new SimpleBooleanProperty();

    public final boolean getRetired() {
        return retired.get();
    }

    public void setRetired(boolean value) {
        retired.set(value);
    }

    public BooleanProperty retiredProperty() {
        return retired;
    }

    private final IntegerProperty weight = new SimpleIntegerProperty(); // in cm?? :-)

    public final int getWeight() {
        return weight.get();
    }

    public void setWeight(int value) {
        weight.set(value);
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    private final LongProperty income = new SimpleLongProperty(); // wow, can have a HUGE income!

    public final long getIncome() {
        return income.get();
    }

    public void setIncome(long value) {
        income.set(value);
    }

    public LongProperty incomeProperty() {
        return income;
    }

    private final FloatProperty miles = new SimpleFloatProperty();

    public final float getMiles() {
        return miles.get();
    }

    public void setMiles(float value) {
        miles.set(value);
    }

    public FloatProperty milesProperty() {
        return miles;
    }

    private final DoubleProperty something = new SimpleDoubleProperty(); // I have no idea...

    public final double getSomething() {
        return something.get();
    }

    public void setSomething(double value) {
        something.set(value);
    }

    public DoubleProperty somethingProperty() {
        return something;
    }

    private final ObjectProperty<Object> data = new SimpleObjectProperty<Object>();

    public final Object getData() {
        return data.get();
    }

    public void setData(Object value) {
        data.set(value);
    }

    public ObjectProperty<Object> dataProperty() {
        return data;
    }

    public final ReadOnlyIntegerWrapper noWrite = new ReadOnlyIntegerWrapper();

    public static final PropertyReference<Integer> NO_WRITE = new PropertyReference<Integer>(Person.class, "noWrite");

    public final int getNoWrite() {
        return noWrite.get();
    }

    public ReadOnlyIntegerProperty noWriteProperty() {
        return noWrite.getReadOnlyProperty();
    }

    public final IntegerProperty noRead = new SimpleIntegerProperty(); // do not expect it back

    public static final PropertyReference<Integer> NO_READ = new PropertyReference<Integer>(Person.class, "noRead");

    public void setNoRead(int value) {
        noRead.set(value);
    }

    int noReadWrite;

    public static final PropertyReference<Integer> NO_READ_WRITE =
            new PropertyReference<Integer>(Person.class, "noReadWrite");

}
