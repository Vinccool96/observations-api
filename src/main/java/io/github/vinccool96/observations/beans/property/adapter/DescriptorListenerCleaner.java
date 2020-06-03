package io.github.vinccool96.observations.beans.property.adapter;

import io.github.vinccool96.observations.sun.property.adapter.ReadOnlyPropertyDescriptor;

import java.lang.ref.WeakReference;

class DescriptorListenerCleaner implements Runnable {

    private final ReadOnlyPropertyDescriptor pd;

    private final WeakReference<ReadOnlyPropertyDescriptor.ReadOnlyListener<?>> lRef;

    DescriptorListenerCleaner(ReadOnlyPropertyDescriptor pd, ReadOnlyPropertyDescriptor.ReadOnlyListener<?> l) {
        this.pd = pd;
        this.lRef = new WeakReference<ReadOnlyPropertyDescriptor.ReadOnlyListener<?>>(l);
    }

    @Override
    public void run() {
        ReadOnlyPropertyDescriptor.ReadOnlyListener<?> l = lRef.get();
        if (l != null) {
            pd.removeListener(l);
        }
    }

}
