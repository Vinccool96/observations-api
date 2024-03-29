/**
 * The different bindings.
 * <h1>Characteristics of Bindings</h1>
 * Bindings are assembled from one or more sources, usually called their dependencies. A binding observes its
 * dependencies for changes and updates its own value according to changes in the dependencies.
 * <p>
 * Almost all bindings defined in this library require implementations of {@link io.github.vinccool96.observations.beans.Observable}
 * for their dependencies. There are two types of implementations already provided, the properties in the package {@code
 * io.github.vinccool96.observations.beans.property} and the observable collections ({@link
 * io.github.vinccool96.observations.collections.ObservableList} and {@link io.github.vinccool96.observations.collections.ObservableMap}).
 * Bindings also implement {@code Observable} and can again serve as sources for other bindings allowing to construct
 * very complex bindings from simple ones.
 * <p>
 * Bindings in our implementation are always calculated lazily. That means, if a dependency changes, the result of a
 * binding is not immediately recalculated, but it is marked as invalid. Next time the value of an invalid binding is
 * requested, it is recalculated.
 * <h1>High Level API and Low Level API</h1>
 * The Binding API is roughly divided in two parts, the High Level Binding API and the Low Level Binding API. The High
 * Level Binding API allows to construct simple bindings in an easy to use fashion. Defining a binding with the High
 * Level API should be straightforward, especially when used in an IDE that provides code completion. Unfortunately it
 * has its limitation and at that point the Low Level API comes into play. Experienced Java developers can use the Low
 * Level API to define bindings, if the functionality of the High Level API is not sufficient or to improve the
 * performance. The main goals of the Low Level API are fast execution and small memory footprint.
 * <p>
 * Following is an example of how both APIs can be used. Assuming we have four instances of {@link
 * io.github.vinccool96.observations.beans.property.DoubleProperty} a, b, c , and d, we can define a binding that
 * calculates a*b + c*d with the High Level API for example like this:
 * <p>
 * NumberBinding result = Bindings.add (a.multiply(b), c.multiply(d));
 * <p>
 * Defining the same binding using the Low Level API could be done like this:
 *
 * <pre>
 * <code>
 * DoubleBinding foo = new DoubleBinding() {
 *
 *     {
 *         super.bind(a, b, c, d);
 *     }
 *
 *     &#x40;Override
 *     protected double computeValue() {
 *         return a.getValue() * b.getValue() + c.getValue() * d.getValue();
 *     }
 *
 * };
 * </code>
 * </pre>
 */
package io.github.vinccool96.observations.beans.binding;