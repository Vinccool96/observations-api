package io.github.vinccool96.observations.collections;

import io.github.vinccool96.observations.collections.ListChangeListener.Change;
import io.github.vinccool96.observations.sun.collections.ObservableListWrapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ListChangeBuilderTest {

    private ListChangeBuilder<String> builder;

    private ObservableListWrapper<String> observableList;

    private ArrayList<String> list;

    private MockListObserver<String> observer;

    @Before
    public void setUp() {
        observer = new MockListObserver<>();
        list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        observableList = new ObservableListWrapper<>(list);
        observableList.addListener(observer);
        builder = new ListChangeBuilder<>(observableList);
    }

    @Test
    public void testAddRemove() {
        builder.beginChange();
        list.remove(2);
        builder.nextRemove(2, "c");
        list.add(2, "cc");
        list.add(3, "ccc");
        builder.nextAdd(2, 4);
        list.remove(2);
        builder.nextRemove(2, "cc");
        list.remove(3);
        builder.nextRemove(3, "d");
        list.add(0, "aa");
        builder.nextAdd(0, 1);
        builder.endChange();

        assertEquals(Arrays.asList("aa", "a", "b", "ccc"), list);

        observer.checkAddRemove(0, observableList, Collections.emptyList(), 0, 1);
        observer.checkAddRemove(1, observableList, Arrays.asList("c", "d"), 3, 4);
    }

    @Test
    public void testAddRemove_2() {
        builder.beginChange();
        list.add("e");
        builder.nextAdd(4, 5);
        list.add(3, "dd");
        builder.nextAdd(3, 4);
        list.remove(4);
        builder.nextRemove(4, "d");

        list.remove(0);
        builder.nextRemove(0, "a");
        builder.endChange();

        assertEquals(Arrays.asList("b", "c", "dd", "e"), list);

        observer.checkAddRemove(0, observableList, Collections.singletonList("a"), 0, 0);
        observer.checkAddRemove(1, observableList, Collections.singletonList("d"), 2, 4);
    }

    @Test
    public void testAddRemove_3() {
        builder.beginChange();
        list.add("e");
        builder.nextAdd(4, 5);

        list.set(0, "aa");
        builder.nextReplace(0, 1, Collections.singletonList("a"));

        list.remove(4);
        builder.nextRemove(4, "e");

        list.remove(0);
        builder.nextRemove(0, "aa");
        builder.endChange();

        assertEquals(Arrays.asList("b", "c", "d"), list);

        observer.check1AddRemove(observableList, Collections.singletonList("a"), 0, 0);
    }

    @Test
    public void testAddRemove_4() {
        builder.beginChange();

        list.add("e");
        builder.nextAdd(4, 5);

        list.remove(1);
        builder.nextRemove(1, "b");

        list.add(1, "bb");
        builder.nextAdd(1, 2);

        builder.endChange();

        assertEquals(Arrays.asList("a", "bb", "c", "d", "e"), list);

        observer.checkAddRemove(0, observableList, Collections.singletonList("b"), 1, 2);
        observer.checkAddRemove(1, observableList, Collections.emptyList(), 4, 5);
    }

    //RT-37089
    @Test
    public void testAddRemove_5() {
        builder.beginChange();

        list.addAll(1, Arrays.asList("x", "y"));
        builder.nextAdd(1, 3);

        list.remove(2);
        builder.nextRemove(2, "y");

        builder.endChange();

        observer.check1AddRemove(observableList, Collections.emptyList(), 1, 2);
    }

    @Test
    public void testAdd() {
        builder.beginChange();

        list.add(1, "aa");
        builder.nextAdd(1, 2);
        list.add(5, "e");
        builder.nextAdd(5, 6);
        list.add(1, "aa");
        builder.nextAdd(1, 2);
        list.add(2, "aa");
        builder.nextAdd(2, 3);
        list.add(4, "aa");
        builder.nextAdd(4, 5);

        builder.endChange();

        assertEquals(Arrays.asList("a", "aa", "aa", "aa", "aa", "b", "c", "d", "e"), list);

        observer.checkAddRemove(0, observableList, Collections.emptyList(), 1, 5);
        observer.checkAddRemove(1, observableList, Collections.emptyList(), 8, 9);
    }

    @Test
    public void testRemove() {
        builder.beginChange();
        list.remove(0);
        builder.nextRemove(0, "a");
        list.remove(2);
        builder.nextRemove(2, "d");
        list.remove(0);
        builder.nextRemove(0, "b");
        builder.endChange();

        assertEquals(Collections.singletonList("c"), list);

        observer.checkAddRemove(0, observableList, Arrays.asList("a", "b"), 0, 0);
        observer.checkAddRemove(1, observableList, Collections.singletonList("d"), 1, 1);
    }

    @Test
    public void testRemove_2() {
        builder.beginChange();
        list.remove(1);
        builder.nextRemove(1, "b");
        list.remove(2);
        builder.nextRemove(2, "d");
        list.remove(0);
        builder.nextRemove(0, "a");
        builder.endChange();

        assertEquals(Collections.singletonList("c"), list);

        observer.checkAddRemove(0, observableList, Arrays.asList("a", "b"), 0, 0);
        observer.checkAddRemove(1, observableList, Collections.singletonList("d"), 1, 1);
    }

    @Test
    public void testUpdate() {
        builder.beginChange();
        builder.nextUpdate(1);
        builder.nextUpdate(0);
        builder.nextUpdate(3);
        builder.endChange();

        observer.checkUpdate(0, observableList, 0, 2);
        observer.checkUpdate(1, observableList, 3, 4);
    }

    @Test
    public void testUpdate_2() {
        builder.beginChange();
        builder.nextUpdate(3);
        builder.nextUpdate(1);
        builder.nextUpdate(0);
        builder.nextUpdate(0);
        builder.nextUpdate(2);
        builder.endChange();

        observer.checkUpdate(0, observableList, 0, 4);
    }

    @Test
    public void testPermutation() {
        builder.beginChange();

        builder.nextPermutation(0, 4, new int[]{3, 2, 1, 0});
        builder.nextPermutation(1, 4, new int[]{3, 2, 1});
        builder.endChange();

        observer.check1Permutation(observableList, new int[]{1, 2, 3, 0});
    }

    @Test
    public void testUpdateAndAddRemove() {
        builder.beginChange();
        builder.nextUpdate(1);
        builder.nextUpdate(2);
        list.remove(2);
        builder.nextRemove(2, "c");
        list.add(2, "cc");
        list.add(3, "ccc");
        builder.nextAdd(2, 4);
        builder.nextUpdate(2);
        list.remove(2);
        builder.nextRemove(2, "cc");
        list.remove(3);
        builder.nextRemove(3, "d");
        list.add(0, "aa");
        builder.nextAdd(0, 1);
        builder.endChange();

        assertEquals(Arrays.asList("aa", "a", "b", "ccc"), list);

        observer.checkAddRemove(0, observableList, Collections.emptyList(), 0, 1);
        observer.checkAddRemove(1, observableList, Arrays.asList("c", "d"), 3, 4);
        observer.checkUpdate(2, observableList, 2, 3);
    }

    @Test
    public void testUpdateAndAddRemove_2() {
        builder.beginChange();
        builder.nextUpdate(0);
        builder.nextUpdate(1);
        list.add(1, "aaa");
        list.add(1, "aa");
        builder.nextAdd(1, 3);
        builder.endChange();

        assertEquals(Arrays.asList("a", "aa", "aaa", "b", "c", "d"), list);

        observer.checkAddRemove(0, observableList, Collections.emptyList(), 1, 3);
        observer.checkUpdate(1, observableList, 0, 1);
        observer.checkUpdate(2, observableList, 3, 4);
    }

    @Test
    public void testUpdateAndAddRemove_3() {
        builder.beginChange();
        builder.nextUpdate(2);
        builder.nextUpdate(3);
        list.add(1, "aa");
        builder.nextAdd(1, 2);
        list.remove(0);
        builder.nextRemove(0, "a");
        builder.endChange();

        assertEquals(Arrays.asList("aa", "b", "c", "d"), list);

        observer.checkAddRemove(0, observableList, Collections.singletonList("a"), 0, 1);
        observer.checkUpdate(1, observableList, 2, 4);
    }

    @Test
    public void testUpdateAndPermutation() {
        builder.beginChange();

        builder.nextUpdate(1);
        builder.nextUpdate(2);
        builder.nextPermutation(1, 4, new int[]{3, 2, 1});
        builder.endChange();

        observer.checkPermutation(0, observableList, 1, 4, new int[]{3, 2, 1});
        observer.checkUpdate(1, observableList, 2, 4);
    }

    @Test
    public void testUpdateAndPermutation_2() {
        builder.beginChange();

        builder.nextUpdate(0);
        builder.nextUpdate(2);
        builder.nextPermutation(0, 4, new int[]{1, 3, 2, 0});
        builder.endChange();

        observer.checkPermutation(0, observableList, 0, 4, new int[]{1, 3, 2, 0});
        observer.checkUpdate(1, observableList, 1, 3);
    }

    @Test
    public void testAddAndPermutation() {
        builder.beginChange();

        builder.nextAdd(1, 2); // as-if "b" was added
        builder.nextPermutation(0, 3, new int[]{2, 0, 1}); // new order is "b", "c", "a", "d"

        builder.endChange();
        // "c", "a", "d" before "b" was added
        observer.checkPermutation(0, observableList, 0, 3, new int[]{1, 0, 2});

        observer.checkAddRemove(1, observableList, Collections.emptyList(), 0, 1);
    }

    @Test
    public void testRemoveAndPermutation() {
        builder.beginChange();

        List<String> removed = Arrays.asList("bb", "bbb");

        builder.nextRemove(2, removed);
        builder.nextPermutation(0, 3, new int[]{2, 0, 1});

        builder.endChange();

        observer.checkPermutation(0, observableList, 0, 6, new int[]{4, 0, 2, 3, 1, 5});
        observer.checkAddRemove(1, observableList, removed, 1, 1);
    }

    @Test
    public void testAddRemoveAndPermutation() {
        builder.beginChange();

        // Expect list to be "b", "c1", "c2", "d"
        List<String> removed = Arrays.asList("c1", "c2");
        // After add: "a", "b", "c1", "c2", "d"
        builder.nextAdd(0, 1);
        // After replace: "a", "b", "c", "d"
        builder.nextReplace(2, 3, removed);
        builder.nextPermutation(1, 4, new int[]{3, 1, 2});

        builder.endChange();

        observer.checkPermutation(0, observableList, 0, 4, new int[]{3, 1, 2, 0});
        observer.checkAddRemove(1, observableList, removed, 0, 2);
    }

    @Test
    public void testPermutationAndAddRemove() {
        builder.beginChange();

        // Expect list to be "b", "c1", "c2", "d"
        // After perm: "b", "c2", "d", "c1"
        builder.nextPermutation(1, 4, new int[]{3, 1, 2});
        // After add: "a", "b", "c2", "d", "c1"
        builder.nextAdd(0, 1);
        builder.nextReplace(2, 3, Collections.singletonList("c2"));
        builder.nextRemove(4, Collections.singletonList("c1"));

        builder.endChange();

        observer.checkPermutation(0, observableList, 1, 4, new int[]{3, 1, 2});
        observer.checkAddRemove(1, observableList, Collections.emptyList(), 0, 1);
        observer.checkAddRemove(2, observableList, Collections.singletonList("c2"), 2, 3);
        observer.checkAddRemove(3, observableList, Collections.singletonList("c1"), 4, 4);
    }

    @Test
    public void testPermutationAddRemoveAndPermutation() {
        builder.beginChange();
        // Expect list to be "b", "c1", "d"
        List<String> removed = Collections.singletonList("c1");
        // After perm: "c1", "b", "d"
        builder.nextPermutation(0, 2, new int[]{1, 0});
        // After add: "a", "c1", "b", "d"
        builder.nextAdd(0, 1);
        // After remove/add: "a", "b", "c", "d"
        builder.nextRemove(1, removed);
        builder.nextAdd(2, 3);
        // After perm: "a", "c", "d", "b"
        builder.nextPermutation(1, 4, new int[]{3, 1, 2});

        builder.endChange();

        // When combined, it's from the expected list:
        // permutation to "c1", "d", "b"
        observer.checkPermutation(0, observableList, 0, 3, new int[]{2, 0, 1});
        // add remove to "a", "c", "d", "b"
        observer.checkAddRemove(1, observableList, removed, 0, 2);
    }

    @Test(expected = IllegalStateException.class)
    public void testNextAddWithoutBegin() {
        builder.nextAdd(0, 1);
    }

    @Test(expected = IllegalStateException.class)
    public void testNextRemoveWithoutBegin() {
        builder.nextRemove(0, (String) null);
    }

    @Test(expected = IllegalStateException.class)
    public void testNextRemove2WithoutBegin() {
        builder.nextRemove(0, Collections.emptyList());
    }

    @Test(expected = IllegalStateException.class)
    public void testNextUpdateWithoutBegin() {
        builder.nextUpdate(0);
    }

    @Test(expected = IllegalStateException.class)
    public void testNextSetWithoutBegin() {
        builder.nextSet(0, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testNextReplaceWithoutBegin() {
        builder.nextReplace(0, 1, Collections.emptyList());
    }

    @Test(expected = IllegalStateException.class)
    public void testNextPermutationWithoutBegin() {
        builder.nextPermutation(0, 2, new int[]{1, 0});
    }

    @Test
    public void testEmpty() {
        builder.beginChange();
        builder.endChange();

        observer.check0();
    }

    @Test
    public void testToString_Update() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextUpdate(0);

        builder.endChange();
    }

    @Test
    public void testToString_Add() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextAdd(0, 1);

        builder.endChange();
    }

    @Test
    public void testToString_Remove() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextRemove(0, "");

        builder.endChange();
    }

    @Test
    public void testToString_Remove2() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextRemove(0, Collections.singletonList("a"));

        builder.endChange();
    }

    @Test
    public void testToString_Set() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextSet(0, "aa");

        builder.endChange();
    }

    @Test
    public void testToString_Replace() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextReplace(0, 2, Arrays.asList("aa", "aaa"));

        builder.endChange();
    }

    @Test
    public void testToString_Composed() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextUpdate(0);

        builder.nextAdd(0, 3);

        builder.endChange();
    }

    @Test
    public void testToString_Permutation() {
        observableList.removeListener(observer);
        observableList.addListener((Change<? extends String> change) -> assertNotNull(change.toString()));
        builder.beginChange();

        builder.nextPermutation(0, 2, new int[]{1, 0});

        builder.endChange();
    }

}
