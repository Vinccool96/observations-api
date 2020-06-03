package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.WeakListener;

public class ExpressionHelperBase {

    protected static int trim(int size, Object[] listeners) {
        for (int index = 0; index < size; index++) {
            final Object listener = listeners[index];
            if (listener instanceof WeakListener) {
                if (((WeakListener) listener).wasGarbageCollected()) {
                    final int numMoved = size - index - 1;
                    if (numMoved > 0) {
                        System.arraycopy(listeners, index + 1, listeners, index, numMoved);
                    }
                    listeners[--size] = null; // Let gc do its work
                    index--;
                }
            }
        }
        return size;
    }

}
