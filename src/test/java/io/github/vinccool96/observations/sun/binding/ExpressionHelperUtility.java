package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.InvalidationListener;
import io.github.vinccool96.observations.beans.Observable;
import io.github.vinccool96.observations.beans.value.ChangeListener;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.collections.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExpressionHelperUtility {

    private static final String EXPRESSION_HELPER_SINGLE_INVALIDATION =
            "io.github.vinccool96.observations.sun.binding.ExpressionHelper$SingleInvalidation";

    private static final String EXPRESSION_HELPER_SINGLE_CHANGE =
            "io.github.vinccool96.observations.sun.binding.ExpressionHelper$SingleChange";

    private static final String EXPRESSION_HELPER_GENERIC =
            "io.github.vinccool96.observations.sun.binding.ExpressionHelper$Generic";

    private static final String LIST_EXPRESSION_HELPER_SINGLE_INVALIDATION =
            "io.github.vinccool96.observations.sun.binding.ListExpressionHelper$SingleInvalidation";

    private static final String LIST_EXPRESSION_HELPER_SINGLE_CHANGE =
            "io.github.vinccool96.observations.sun.binding.ListExpressionHelper$SingleChange";

    private static final String LIST_EXPRESSION_HELPER_SINGLE_LIST_CHANGE =
            "io.github.vinccool96.observations.sun.binding.ListExpressionHelper$SingleListChange";

    private static final String LIST_EXPRESSION_HELPER_GENERIC =
            "io.github.vinccool96.observations.sun.binding.ListExpressionHelper$Generic";

    private static final String MAP_EXPRESSION_HELPER_SINGLE_INVALIDATION =
            "io.github.vinccool96.observations.sun.binding.MapExpressionHelper$SingleInvalidation";

    private static final String MAP_EXPRESSION_HELPER_SINGLE_CHANGE =
            "io.github.vinccool96.observations.sun.binding.MapExpressionHelper$SingleChange";

    private static final String MAP_EXPRESSION_HELPER_SINGLE_MAP_CHANGE =
            "io.github.vinccool96.observations.sun.binding.MapExpressionHelper$SingleMapChange";

    private static final String MAP_EXPRESSION_HELPER_GENERIC =
            "io.github.vinccool96.observations.sun.binding.MapExpressionHelper$Generic";

    private static final String SET_EXPRESSION_HELPER_SINGLE_INVALIDATION =
            "io.github.vinccool96.observations.sun.binding.SetExpressionHelper$SingleInvalidation";

    private static final String SET_EXPRESSION_HELPER_SINGLE_CHANGE =
            "io.github.vinccool96.observations.sun.binding.SetExpressionHelper$SingleChange";

    private static final String SET_EXPRESSION_HELPER_SINGLE_SET_CHANGE =
            "io.github.vinccool96.observations.sun.binding.SetExpressionHelper$SingleSetChange";

    private static final String SET_EXPRESSION_HELPER_GENERIC =
            "io.github.vinccool96.observations.sun.binding.SetExpressionHelper$Generic";

    private ExpressionHelperUtility() {
    }

    public static List<InvalidationListener> getInvalidationListeners(Observable observable) {
        final Object helper = getExpressionHelper(observable);
        if (helper == null) {
            return Collections.emptyList();
        }
        final Class helperClass = helper.getClass();

        try {
            final Class clazz = Class.forName(EXPRESSION_HELPER_SINGLE_INVALIDATION);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromSingleInvalidationClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(LIST_EXPRESSION_HELPER_SINGLE_INVALIDATION);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromSingleInvalidationClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(MAP_EXPRESSION_HELPER_SINGLE_INVALIDATION);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromSingleInvalidationClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(SET_EXPRESSION_HELPER_SINGLE_INVALIDATION);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromSingleInvalidationClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(LIST_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(MAP_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(SET_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getInvalidationListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        return Collections.emptyList();
    }

    public static <T> List<ChangeListener<? super T>> getChangeListeners(ObservableValue<T> observable) {
        final Object helper = getExpressionHelper(observable);
        if (helper == null) {
            return Collections.emptyList();
        }
        final Class helperClass = helper.getClass();

        try {
            final Class clazz = Class.forName(EXPRESSION_HELPER_SINGLE_CHANGE);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromSingleChangeClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(LIST_EXPRESSION_HELPER_SINGLE_CHANGE);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromSingleChangeClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(MAP_EXPRESSION_HELPER_SINGLE_CHANGE);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromSingleChangeClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(SET_EXPRESSION_HELPER_SINGLE_CHANGE);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromSingleChangeClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(LIST_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(MAP_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(SET_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                return getChangeListenerFromGenericClass(clazz, helper);
            }
        } catch (ClassNotFoundException ex) {
        }

        return Collections.emptyList();
    }

    public static <E> List<ListChangeListener<? super E>> getListChangeListeners(ObservableList<E> observable) {
        final Object helper = getExpressionHelper(observable);
        if (helper == null) {
            return Collections.emptyList();
        }
        final Class helperClass = helper.getClass();

        try {
            final Class clazz = Class.forName(LIST_EXPRESSION_HELPER_SINGLE_LIST_CHANGE);
            if (clazz.isAssignableFrom(helperClass)) {
                try {
                    final Field field = clazz.getDeclaredField("listener");
                    field.setAccessible(true);
                    final ListChangeListener<? super E> listener = (ListChangeListener) field.get(helper);
                    return Arrays.<ListChangeListener<? super E>>asList(listener);
                } catch (Exception ex) {
                }
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(LIST_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                try {
                    final Field field = clazz.getDeclaredField("listChangeListeners");
                    field.setAccessible(true);
                    final ListChangeListener<? super E>[] listeners = (ListChangeListener[]) field.get(helper);
                    if (listeners != null) {
                        final Field sizeField = clazz.getDeclaredField("listChangeSize");
                        sizeField.setAccessible(true);
                        final int size = sizeField.getInt(helper);
                        return Arrays.asList(Arrays.copyOf(listeners, size));
                    }
                } catch (Exception ex) {
                }
            }
        } catch (ClassNotFoundException ex) {
        }

        return Collections.emptyList();
    }

    public static <K, V> List<MapChangeListener<? super K, ? super V>> getMapChangeListeners(
            ObservableMap<K, V> observable) {
        final Object helper = getExpressionHelper(observable);
        if (helper == null) {
            return Collections.emptyList();
        }
        final Class helperClass = helper.getClass();

        try {
            final Class clazz = Class.forName(MAP_EXPRESSION_HELPER_SINGLE_MAP_CHANGE);
            if (clazz.isAssignableFrom(helperClass)) {
                try {
                    final Field field = clazz.getDeclaredField("listener");
                    field.setAccessible(true);
                    final MapChangeListener<? super K, ? super V> listener = (MapChangeListener) field.get(helper);
                    return Arrays.<MapChangeListener<? super K, ? super V>>asList(listener);
                } catch (Exception ex) {
                }
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(MAP_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                try {
                    final Field field = clazz.getDeclaredField("mapChangeListeners");
                    field.setAccessible(true);
                    final MapChangeListener<? super K, ? super V>[] listeners = (MapChangeListener[]) field.get(helper);
                    if (listeners != null) {
                        final Field sizeField = clazz.getDeclaredField("mapChangeSize");
                        sizeField.setAccessible(true);
                        final int size = sizeField.getInt(helper);
                        return Arrays.asList(Arrays.copyOf(listeners, size));
                    }
                } catch (Exception ex) {
                }
            }
        } catch (ClassNotFoundException ex) {
        }

        return Collections.emptyList();
    }

    public static <E> List<SetChangeListener<? super E>> getSetChangeListeners(ObservableSet<E> observable) {
        final Object helper = getExpressionHelper(observable);
        if (helper == null) {
            return Collections.emptyList();
        }
        final Class helperClass = helper.getClass();

        try {
            final Class clazz = Class.forName(SET_EXPRESSION_HELPER_SINGLE_SET_CHANGE);
            if (clazz.isAssignableFrom(helperClass)) {
                try {
                    final Field field = clazz.getDeclaredField("listener");
                    field.setAccessible(true);
                    final SetChangeListener<? super E> listener = (SetChangeListener) field.get(helper);
                    return Arrays.<SetChangeListener<? super E>>asList(listener);
                } catch (Exception ex) {
                }
            }
        } catch (ClassNotFoundException ex) {
        }

        try {
            final Class clazz = Class.forName(SET_EXPRESSION_HELPER_GENERIC);
            if (clazz.isAssignableFrom(helperClass)) {
                try {
                    final Field field = clazz.getDeclaredField("setChangeListeners");
                    field.setAccessible(true);
                    final SetChangeListener<? super E>[] listeners = (SetChangeListener[]) field.get(helper);
                    if (listeners != null) {
                        final Field sizeField = clazz.getDeclaredField("setChangeSize");
                        sizeField.setAccessible(true);
                        final int size = sizeField.getInt(helper);
                        return Arrays.asList(Arrays.copyOf(listeners, size));
                    }
                } catch (Exception ex) {
                }
            }
        } catch (ClassNotFoundException ex) {
        }

        return Collections.emptyList();
    }

    private static Object getExpressionHelper(Object bean) {
        Class clazz = bean.getClass();
        while (clazz != Object.class) {
            try {
                final Field field = clazz.getDeclaredField("helper");
                field.setAccessible(true);
                return field.get(bean);
            } catch (Exception ex) {
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static List<InvalidationListener> getInvalidationListenerFromSingleInvalidationClass(Class clazz,
            Object helper) {
        try {
            final Field field = clazz.getDeclaredField("listener");
            field.setAccessible(true);
            final InvalidationListener listener = (InvalidationListener) field.get(helper);
            return Arrays.asList(listener);
        } catch (Exception ex) {
        }
        return Collections.emptyList();
    }

    private static List<InvalidationListener> getInvalidationListenerFromGenericClass(Class clazz, Object helper) {
        try {
            final Field field = clazz.getDeclaredField("invalidationListeners");
            field.setAccessible(true);
            final InvalidationListener[] listeners = (InvalidationListener[]) field.get(helper);
            if (listeners != null) {
                final Field sizeField = clazz.getDeclaredField("invalidationSize");
                sizeField.setAccessible(true);
                final int size = sizeField.getInt(helper);
                return Arrays.asList(Arrays.copyOf(listeners, size));
            }
        } catch (Exception ex) {
        }
        return Collections.emptyList();
    }

    private static <T> List<ChangeListener<? super T>> getChangeListenerFromSingleChangeClass(Class clazz,
            Object helper) {
        try {
            final Field field = clazz.getDeclaredField("listener");
            field.setAccessible(true);
            final ChangeListener<? super T> listener = (ChangeListener) field.get(helper);
            return Arrays.<ChangeListener<? super T>>asList(listener);
        } catch (Exception ex) {
        }
        return Collections.emptyList();
    }

    private static <T> List<ChangeListener<? super T>> getChangeListenerFromGenericClass(Class clazz, Object helper) {
        try {
            final Field field = clazz.getDeclaredField("changeListeners");
            field.setAccessible(true);
            final ChangeListener<? super T>[] listeners = (ChangeListener[]) field.get(helper);
            if (listeners != null) {
                final Field sizeField = clazz.getDeclaredField("changeSize");
                sizeField.setAccessible(true);
                final int size = sizeField.getInt(helper);
                return Arrays.asList(Arrays.copyOf(listeners, size));
            }
        } catch (Exception ex) {
        }
        return Collections.emptyList();
    }

}