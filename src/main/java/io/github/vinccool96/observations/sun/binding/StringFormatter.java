package io.github.vinccool96.observations.sun.binding;

import io.github.vinccool96.observations.beans.binding.StringBinding;
import io.github.vinccool96.observations.beans.binding.StringExpression;
import io.github.vinccool96.observations.beans.value.ObservableValue;
import io.github.vinccool96.observations.collections.ObservableCollections;
import io.github.vinccool96.observations.collections.ObservableList;
import io.github.vinccool96.observations.sun.collections.annotations.ReturnsUnmodifiableCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class StringFormatter extends StringBinding {

    private static Object extractValue(Object obj) {
        return obj instanceof ObservableValue ? ((ObservableValue<?>) obj).getValue() : obj;
    }

    private static Object[] extractValues(Object[] objs) {
        final int n = objs.length;
        final Object[] values = new Object[n];
        for (int i = 0; i < n; i++) {
            values[i] = extractValue(objs[i]);
        }
        return values;
    }

    private static ObservableValue<?>[] extractDependencies(Object... args) {
        final List<ObservableValue<?>> dependencies = new ArrayList<>();
        for (final Object obj : args) {
            if (obj instanceof ObservableValue) {
                dependencies.add((ObservableValue<?>) obj);
            }
        }
        return dependencies.toArray(new ObservableValue[0]);
    }

    public static StringExpression convert(final ObservableValue<?> observableValue) {
        if (observableValue == null) {
            throw new NullPointerException("ObservableValue must be specified");
        }
        if (observableValue instanceof StringExpression) {
            return (StringExpression) observableValue;
        } else {
            return new StringBinding() {

                {
                    super.bind(observableValue);
                }

                @Override
                public void dispose() {
                    super.unbind(observableValue);
                }

                @Override
                protected String computeValue() {
                    final Object value = observableValue.getValue();
                    return (value == null) ? "null" : value.toString();
                }

                @Override
                @ReturnsUnmodifiableCollection
                public ObservableList<ObservableValue<?>> getDependencies() {
                    return ObservableCollections.singletonObservableList(observableValue);
                }
            };
        }
    }

    public static StringExpression concat(final Object... args) {
        if ((args == null) || (args.length == 0)) {
            return StringConstant.valueOf("");
        }
        if (args.length == 1) {
            final Object cur = args[0];
            return cur instanceof ObservableValue ? convert((ObservableValue<?>) cur)
                    : StringConstant.valueOf(cur.toString());
        }
        if (extractDependencies(args).length == 0) {
            final StringBuilder builder = new StringBuilder();
            for (final Object obj : args) {
                builder.append(obj);
            }
            return StringConstant.valueOf(builder.toString());
        }
        return new StringFormatter() {

            {
                super.bind(extractDependencies(args));
            }

            @Override
            public void dispose() {
                super.unbind(extractDependencies(args));
            }

            @Override
            protected String computeValue() {
                final StringBuilder builder = new StringBuilder();
                for (final Object obj : args) {
                    builder.append(extractValue(obj));
                }
                return builder.toString();
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<ObservableValue<?>> getDependencies() {
                return ObservableCollections.unmodifiableObservableList(
                        ObservableCollections.observableArrayList(extractDependencies(args)));
            }
        };
    }

    public static StringExpression format(final Locale locale, final String format, final Object... args) {
        if (format == null) {
            throw new NullPointerException("Format cannot be null.");
        }
        if (extractDependencies(args).length == 0) {
            return StringConstant.valueOf(String.format(locale, format, args));
        }
        final StringFormatter formatter = new StringFormatter() {

            {
                super.bind(extractDependencies(args));
            }

            @Override
            public void dispose() {
                super.unbind(extractDependencies(args));
            }

            @Override
            protected String computeValue() {
                final Object[] values = extractValues(args);
                return String.format(locale, format, values);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<ObservableValue<?>> getDependencies() {
                return ObservableCollections.unmodifiableObservableList(
                        ObservableCollections.observableArrayList(extractDependencies(args)));
            }
        };
        // Force calculation to check format
        formatter.get();
        return formatter;
    }

    public static StringExpression format(final String format, final Object... args) {
        if (format == null) {
            throw new NullPointerException("Format cannot be null.");
        }
        if (extractDependencies(args).length == 0) {
            return StringConstant.valueOf(String.format(format, args));
        }
        final StringFormatter formatter = new StringFormatter() {

            {
                super.bind(extractDependencies(args));
            }

            @Override
            public void dispose() {
                super.unbind(extractDependencies(args));
            }

            @Override
            protected String computeValue() {
                final Object[] values = extractValues(args);
                return String.format(format, values);
            }

            @Override
            @ReturnsUnmodifiableCollection
            public ObservableList<ObservableValue<?>> getDependencies() {
                return ObservableCollections.unmodifiableObservableList(
                        ObservableCollections.observableArrayList(extractDependencies(args)));
            }
        };
        // Force calculation to check format
        formatter.get();
        return formatter;
    }

}