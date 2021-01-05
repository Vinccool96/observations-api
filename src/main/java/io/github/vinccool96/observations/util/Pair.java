package io.github.vinccool96.observations.util;

import io.github.vinccool96.observations.beans.NamedArg;

import java.io.Serializable;

/**
 * A convenience class to represent name-value pairs.
 *
 * @param <K>
 *         The type of the key
 * @param <V>
 *         The type of the value
 */
public class Pair<K, V> implements Serializable {

    /**
     * Key of this {@code Pair}.
     */
    private K key;

    /**
     * Gets the key for this pair.
     *
     * @return key for this pair
     */
    public K getKey() {
        return key;
    }

    /**
     * Value of this this {@code Pair}.
     */
    private V value;

    /**
     * Gets the value for this pair.
     *
     * @return value for this pair
     */
    public V getValue() {
        return value;
    }

    /**
     * Creates a new pair
     *
     * @param key
     *         The key for this pair
     * @param value
     *         The value to use for this pair
     */
    public Pair(@NamedArg("key") K key, @NamedArg("value") V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * {@code String} representation of this {@code Pair}.
     * <p>
     * The default name/value delimiter '=' is always used.
     *
     * @return {@code String} representation of this {@code Pair}
     */
    @Override
    public String toString() {
        return key + "=" + value;
    }

    /**
     * Generate a hash code for this {@code Pair}.
     * <p>
     * The hash code is calculated using both the name and the value of the {@code Pair}.
     *
     * @return hash code for this {@code Pair}
     */
    @Override
    public int hashCode() {
        // name's hashCode is multiplied by an arbitrary prime number (13)
        // in order to make sure there is a difference in the hashCode between
        // these two parameters:
        //  name: a  value: aa
        //  name: aa value: a
        return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
    }

    /**
     * Test this {@code Pair} for equality with another {@code Object}.
     * <p>
     * If the {@code Object} to be tested is not a {@code Pair} or is {@code null}, then this method returns {@code
     * false}.
     * <p>
     * Two {@code Pair}s are considered equal if and only if both the names and values are equal.
     *
     * @param o
     *         the {@code Object} to test for equality with this {@code Pair}
     *
     * @return {@code true} if the given {@code Object} is equal to this {@code Pair} else {@code false}
     */
    @Override
    @SuppressWarnings("EqualsReplaceableByObjectsCall")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Pair) {
            Pair<?, ?> pair = (Pair<?, ?>) o;
            if (key != null ? !key.equals(pair.key) : pair.key != null) {
                return false;
            }
            if (value != null ? !value.equals(pair.value) : pair.value != null) {
                return false;
            }
            return true;
        }
        return false;
    }

}
