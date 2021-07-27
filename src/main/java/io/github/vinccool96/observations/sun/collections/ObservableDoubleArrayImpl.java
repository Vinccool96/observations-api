package io.github.vinccool96.observations.sun.collections;

import io.github.vinccool96.observations.collections.ObservableArrayBase;
import io.github.vinccool96.observations.collections.ObservableDoubleArray;

import java.util.Arrays;

/**
 * ObservableDoubleArray default implementation.
 */
public final class ObservableDoubleArrayImpl extends ObservableArrayBase<ObservableDoubleArray>
        implements ObservableDoubleArray {

    private static final double[] INITIAL = new double[0];

    /**
     * The maximum size of array to allocate. Some VMs reserve some header words in an array. Attempts to allocate
     * larger arrays may result in OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private double[] array = INITIAL;

    private int size = 0;

    /**
     * Creates empty observable double array
     */
    public ObservableDoubleArrayImpl() {
    }

    /**
     * Creates observable double array with copy of given initial values
     *
     * @param elements
     *         initial values to copy to observable double array
     */
    public ObservableDoubleArrayImpl(double... elements) {
        setAll(elements);
    }

    /**
     * Creates observable double array with copy of given observable double array
     *
     * @param src
     *         observable double array to copy
     */
    public ObservableDoubleArrayImpl(ObservableDoubleArray src) {
        setAll(src);
    }

    @Override
    public void clear() {
        resize(0);
    }

    @Override
    public int size() {
        return size;
    }

    private void addAllInternal(double[] src, int srcIndex, int length) {
        growCapacity(length);
        System.arraycopy(src, srcIndex, array, size, length);
        size += length;
        fireChange(length != 0, size - length, size);
    }

    private void addAllInternal(ObservableDoubleArray src, int srcIndex, int length) {
        growCapacity(length);
        src.copyTo(srcIndex, array, size, length);
        size += length;
        fireChange(length != 0, size - length, size);
    }

    @Override
    public void addAll(double... elements) {
        addAllInternal(elements, 0, elements.length);
    }

    @Override
    public void addAll(ObservableDoubleArray src) {
        addAllInternal(src, 0, src.size());
    }

    @Override
    public void addAll(double[] src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        addAllInternal(src, srcIndex, length);
    }

    @Override
    public void addAll(ObservableDoubleArray src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        addAllInternal(src, srcIndex, length);
    }

    private void setAllInternal(double[] src, int srcIndex, int length) {
        boolean sizeChanged = size() != length;
        size = 0;
        ensureCapacity(length);
        System.arraycopy(src, srcIndex, array, 0, length);
        size = length;
        fireChange(sizeChanged, 0, size);
    }

    private void setAllInternal(ObservableDoubleArray src, int srcIndex, int length) {
        boolean sizeChanged = size() != length;
        if (src == this) {
            if (srcIndex == 0) {
                resize(length);
            } else {
                System.arraycopy(array, srcIndex, array, 0, length);
                size = length;
                fireChange(sizeChanged, 0, size);
            }
        } else {
            size = 0;
            ensureCapacity(length);
            src.copyTo(srcIndex, array, 0, length);
            size = length;
            fireChange(sizeChanged, 0, size);
        }
    }

    @Override
    public void setAll(double... src) {
        setAllInternal(src, 0, src.length);
    }

    @Override
    public void setAll(ObservableDoubleArray src) {
        setAllInternal(src, 0, src.size());
    }

    @Override
    public void setAll(double[] src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        setAllInternal(src, srcIndex, length);
    }

    @Override
    public void setAll(ObservableDoubleArray src, int srcIndex, int length) {
        rangeCheck(src, srcIndex, length);
        setAllInternal(src, srcIndex, length);
    }

    @Override
    public void set(int destIndex, double[] src, int srcIndex, int length) {
        rangeCheck(destIndex + length);
        System.arraycopy(src, srcIndex, array, destIndex, length);
        fireChange(false, destIndex, destIndex + length);
    }

    @Override
    public void set(int destIndex, ObservableDoubleArray src, int srcIndex, int length) {
        rangeCheck(destIndex + length);
        src.copyTo(srcIndex, array, destIndex, length);
        fireChange(false, destIndex, destIndex + length);
    }

    @Override
    public double get(int index) {
        rangeCheck(index + 1);
        return array[index];
    }

    @Override
    public void set(int index, double value) {
        rangeCheck(index + 1);
        array[index] = value;
        fireChange(false, index, index + 1);
    }

    @Override
    public double[] toArray(double[] dest) {
        if (dest == null || size() > dest.length) {
            dest = new double[size()];
        }
        System.arraycopy(array, 0, dest, 0, size());
        return dest;
    }

    @Override
    public double[] toArray(int index, double[] dest, int length) {
        rangeCheck(index + length);
        if (dest == null || length > dest.length) {
            dest = new double[length];
        }
        System.arraycopy(array, index, dest, 0, length);
        return dest;
    }

    @Override
    public void copyTo(int srcIndex, double[] dest, int destIndex, int length) {
        rangeCheck(srcIndex + length);
        System.arraycopy(array, srcIndex, dest, destIndex, length);
    }

    @Override
    public void copyTo(int srcIndex, ObservableDoubleArray dest, int destIndex, int length) {
        rangeCheck(srcIndex + length);
        dest.set(destIndex, array, srcIndex, length);
    }

    @Override
    public void resize(int newSize) {
        if (newSize < 0) {
            throw new NegativeArraySizeException("Can't resize to negative value: " + newSize);
        }
        ensureCapacity(newSize);
        int minSize = Math.min(size, newSize);
        boolean sizeChanged = size != newSize;
        size = newSize;
        Arrays.fill(array, minSize, size, 0);
        fireChange(sizeChanged, minSize, newSize);
    }

    private void growCapacity(int length) {
        int minCapacity = size + length;
        int oldCapacity = array.length;
        if (minCapacity > array.length) {
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            if (newCapacity > MAX_ARRAY_SIZE) {
                newCapacity = hugeCapacity(minCapacity);
            }
            ensureCapacity(newCapacity);
        } else if (length > 0 && minCapacity < 0) {
            throw new OutOfMemoryError(); // overflow
        }
    }

    @Override
    public void ensureCapacity(int capacity) {
        if (array.length < capacity) {
            array = Arrays.copyOf(array, capacity);
        }
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) { // overflow
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public void trimToSize() {
        if (array.length != size) {
            double[] newArray = new double[size];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    private void rangeCheck(int size) {
        if (size > this.size) {
            throw new ArrayIndexOutOfBoundsException(this.size);
        }
    }

    private void rangeCheck(double[] src, int srcIndex, int length) {
        if (src == null) {
            throw new NullPointerException();
        }
        if (srcIndex < 0 || srcIndex + length > src.length) {
            throw new ArrayIndexOutOfBoundsException(src.length);
        }
        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException(-1);
        }
    }

    private void rangeCheck(ObservableDoubleArray src, int srcIndex, int length) {
        if (src == null) {
            throw new NullPointerException();
        }
        if (srcIndex < 0 || srcIndex + length > src.size()) {
            throw new ArrayIndexOutOfBoundsException(src.size());
        }
        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException(-1);
        }
    }

    @Override
    public String toString() {
        if (array == null) {
            return "null";
        }

        int iMax = size() - 1;
        if (iMax == -1) {
            return "[]";
        }

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(array[i]);
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(", ");
        }
    }

}
