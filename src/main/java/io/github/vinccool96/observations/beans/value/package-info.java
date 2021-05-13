/**
 * The package {@code io.github.vinccool96.observations.beans.value} contains the two fundamental interfaces {@link
 * io.github.vinccool96.observations.beans.value.ObservableValue} and {@link io.github.vinccool96.observations.beans.value.WritableValue}
 * and all of its sub-interfaces.
 *
 * <h1>ObservableValue</h1>
 * An {@code ObservableValue} wraps a value that can be read and observed for invalidations and changes. Listeners have
 * to implement either {@link io.github.vinccool96.observations.beans.InvalidationListener} or {@link
 * io.github.vinccool96.observations.beans.value.ChangeListener}. To allow working with primitive types directly a
 * number of sub-interfaces are defined.
 * <table summary="ObservableValue">
 *     <tr><th style="text-align:center"><b>Type</b></th><th><b>Sub-interface of ObservableValue</b></th></tr>
 *     <tr>
 *         <th>{@code boolean}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableBooleanValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code double}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableDoubleValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code float}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableFloatValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code int}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableIntegerValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code long}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableLongValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code double}, {@code float}, {@code int}, {@code long}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableNumberValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code Object}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableObjectValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code String}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.ObservableStringValue}</th>
 *     </tr>
 * </table>
 * <h1>WritableValue</h1>
 * A {@code WritableValue} wraps a value that can be read and set. As with {@code ObservableValues}, a number of
 * sub-interfaces are defined to work with primitive types directly.
 * <table summary="WritableValue">
 *     <tr><th style="text-align:center"><b>Type</b></th><th><b>Sub-interface of WritableValue</b></th></tr>
 *     <tr>
 *         <th>{@code boolean}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableBooleanValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code double}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableDoubleValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code float}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableFloatValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code int}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableIntegerValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code long}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableLongValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code double}, {@code float}, {@code int}, {@code long}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableNumberValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code Object}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableObjectValue}</th>
 *     </tr>
 *     <tr>
 *         <th>{@code String}</th>
 *         <th>{@link io.github.vinccool96.observations.beans.value.WritableStringValue}</th>
 *     </tr>
 * </table>
 */
package io.github.vinccool96.observations.beans.value;