package io.github.vinccool96.observations.collections.transformation;

import io.github.vinccool96.observations.collections.ListChangeListener.Change;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class TransformationListTest {

    private static class TransformationListImpl extends TransformationList<String, String> {

        public TransformationListImpl(ObservableList<String> list) {
            super(list);
        }

        @Override
        protected void sourceChanged(Change<? extends String> change) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String get(int index) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean addAll(String... es) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean setAll(String... es) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean setAll(Collection<? extends String> clctn) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getSourceIndex(int i) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean removeAll(String... es) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean retainAll(String... es) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void remove(int i, int i1) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private TransformationList<String, String> list1, list2;

    private ObservableList<String> list3;

    @Before
    public void setUp() {
        list3 = ObservableCollections.observableArrayList();
        list2 = new TransformationListImpl(list3);
        list1 = new TransformationListImpl(list2);
    }

    @Test
    public void testDirect() {
        assertEquals(list2, list1.getSource());
        assertEquals(list3, list2.getSource());
    }

}
