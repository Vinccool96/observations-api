package io.github.vinccool96.observations.beans.property;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.binding.BooleanBinding;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableBooleanValue;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.sun.binding.ExpressionHelper;
import io.github.vinccool96.observations.util.ArrayUtils;

import java.lang.ref.WeakReference;

/**
 * The class {@code BooleanPropertyBase} is the base class for a property wrapping a {@code boolean} value.
 * <p>
 * It provides all the functionality required for a property except for the {@link #getBean()} and {@link #getName()}
 * methods, which must be implemented by extending classes.
 *
 * @see BooleanProperty
 * @since JavaFX 2.0
 */
public abstract class BooleanPropertyBase extends BooleanProperty {

    private boolean value;

    private ObservableBooleanValue observable = null;

    private InvalidationListener listener = null;

    private boolean valid = true;

    private ExpressionHelper<Boolean> helper = null;

    /**
     * The constructor of the {@code BooleanPropertyBase}.
     */
    public BooleanPropertyBase() {
    }

    /**
     * The constructor of the {@code BooleanPropertyBase}.
     *
     * @param initialValue
     *         the initial value of the wrapped value
     */
    public BooleanPropertyBase(boolean initialValue) {
        this.value = initialValue;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        if (helper == null || !isInvalidationListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public boolean isInvalidationListenerAlreadyAdded(InvalidationListener listener) {
        return ArrayUtils.getInstance().contains(helper.getInvalidationListeners(), listener);
    }

    @Override
    public void addListener(ChangeListener<? super Boolean> listener) {
        if (!isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.addListener(helper, this, listener);
        }
    }

    @Override
    public void removeListener(ChangeListener<? super Boolean> listener) {
        if (isChangeListenerAlreadyAdded(listener)) {
            helper = ExpressionHelper.removeListener(helper, listener);
        }
    }

    @Override
    public boolean isChangeListenerAlreadyAdded(ChangeListener<? super Boolean> listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        return helper != null && ArrayUtils.getInstance().contains(helper.getChangeListeners(), listener);
    }

    /**
     * Sends notifications to all attached {@link InvalidationListener InvalidationListeners} and {@link ChangeListener
     * ChangeListeners}.
     * <p>
     * This method is called when the value is changed, either manually by calling {@link #set(boolean)} or in case of a
     * bound property, if the binding becomes invalid.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    /**
     * The method {@code invalidated()} can be overridden to receive invalidation notifications. This is the preferred
     * option in {@code Objects} defining the property, because it requires less memory.
     * <p>
     * The default implementation is empty.
     */
    protected void invalidated() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean get() {
        valid = true;
        return observable == null ? value : observable.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(boolean newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : " : "") +
                    "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound() {
        return observable != null;
    }

    /**
     * {@inheritDoc} Note:
     */
    @Override
    public void bind(final ObservableValue<? extends Boolean> rawObservable) {
        if (rawObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        final ObservableBooleanValue newObservable =
                (rawObservable instanceof ObservableBooleanValue) ? (ObservableBooleanValue) rawObservable
                        : new BooleanBinding() {

                    {
                        super.bind(rawObservable);
                    }

                    @Override
                    protected boolean computeValue() {
                        final Boolean value = rawObservable.getValue();
                        return (value == null) ? false : value;
                    }
                };

        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.get();
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * Returns a string representation of this {@code BooleanPropertyBase} object.
     *
     * @return a string representation of this {@code BooleanPropertyBase} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("BooleanProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(get());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener implements InvalidationListener {

        private final WeakReference<BooleanPropertyBase> wref;

        public Listener(BooleanPropertyBase ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            BooleanPropertyBase ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }

    }

}
