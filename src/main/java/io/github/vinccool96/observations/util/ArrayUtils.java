package io.github.vinccool96.observations.util;

import java.lang.reflect.Array;

public class ArrayUtils {

    private static ArrayUtils instance;

    public static ArrayUtils getInstance() {
        if (instance == null) {
            instance = new ArrayUtils();
        }
        return instance;
    }

    private ArrayUtils() {
    }

    @SuppressWarnings("unchecked")
    public <T> T[] clone(T[] array, Class<T> tClass) {
        T[] clone;
        if (array != null) {
            clone = (T[]) Array.newInstance(tClass, array.length);
            System.arraycopy(array, 0, clone, 0, array.length);
        } else {
            clone = (T[]) Array.newInstance(tClass, 0);
        }
        return clone;
    }

    public <T> boolean contains(T[] array, T value) {
        boolean contained = false;
        if (array != null) {
            for (T element : array) {
                if (value.equals(element)) {
                    contained = true;
                    break;
                }
            }
        }
        return contained;
    }

}