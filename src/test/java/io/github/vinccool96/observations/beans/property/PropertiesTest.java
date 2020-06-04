package io.github.vinccool96.observations.beans.property;//package io.github.vinccool96.observations.beans.property;
//
//import static org.junit.Assert.assertEquals;
//import io.github.vinccool96.observations.binding.expression.BooleanExpression;
//import io.github.vinccool96.observations.binding.expression.DoubleExpression;
//import io.github.vinccool96.observations.binding.expression.FloatExpression;
//import io.github.vinccool96.observations.binding.expression.IntegerExpression;
//import io.github.vinccool96.observations.binding.expression.LongExpression;
//import io.github.vinccool96.observations.binding.expression.ObjectExpression;
//import io.github.vinccool96.observations.binding.expression.StringExpression;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class PropertiesTest {
//
//  private static final double EPSILON_DOUBLE = 1e-12;
//  private static final float EPSILON_FLOAT = 1e-6f;
//
//  @Before
//  public void setUp() throws Exception {
//  }
//
//  @Test
//  public void testUnmodifiablePropertyBooleanProperty() {
//      final boolean value1 = true;
//      final boolean value2 = false;
//      final BooleanProperty source = new SimpleBooleanProperty(value1);
//      final BooleanExpression target = Properties.unmodifiableProperty(source);
//
//      assertEquals(value1, target.get());
//      source.set(value2);
//      assertEquals(value2, target.get());
//      source.set(value1);
//
//      assertEquals(value1, target.getValue());
//      source.set(value2);
//      assertEquals(value2, target.getValue());
//      source.set(value1);
//  }
//
//  @Test
//  public void testUnmodifiablePropertyDoubleProperty() {
//      final double value1 = Math.PI;
//      final double value2 = -Math.E;
//      final DoubleProperty source = new SimpleDoubleProperty(value1);
//      final DoubleExpression target = Properties.unmodifiableProperty(source);
//
//      assertEquals(value1, target.get(), EPSILON_DOUBLE);
//      source.set(value2);
//      assertEquals(value2, target.get(), EPSILON_DOUBLE);
//      source.set(value1);
//
//      assertEquals(value1, target.getValue(), EPSILON_DOUBLE);
//      source.set(value2);
//      assertEquals(value2, target.getValue(), EPSILON_DOUBLE);
//      source.set(value1);
//  }
//
//  @Test
//  public void testUnmodifiablePropertyFloatProperty() {
//      final float value1 = (float)Math.PI;
//      final float value2 = (float)-Math.E;
//      final FloatProperty source = new SimpleFloatProperty(value1);
//      final FloatExpression target = Properties.unmodifiableProperty(source);
//
//      assertEquals(value1, target.get(), EPSILON_FLOAT);
//      source.set(value2);
//      assertEquals(value2, target.get(), EPSILON_FLOAT);
//      source.set(value1);
//
//      assertEquals(value1, target.getValue(), EPSILON_FLOAT);
//      source.set(value2);
//      assertEquals(value2, target.getValue(), EPSILON_FLOAT);
//      source.set(value1);
//  }
//
//  @Test
//  public void testUnmodifiablePropertyIntegerProperty() {
//      final int value1 = 42;
//      final int value2 = 12345;
//      final IntegerProperty source = new SimpleIntegerProperty(value1);
//      final IntegerExpression target = Properties.unmodifiableProperty(source);
//
//      assertEquals(value1, target.get());
//      source.set(value2);
//      assertEquals(value2, target.get());
//      source.set(value1);
//
//      assertEquals(Integer.valueOf(value1), target.getValue());
//      source.set(value2);
//      assertEquals(Integer.valueOf(value2), target.getValue());
//      source.set(value1);
//  }
//
//  @Test
//  public void testUnmodifiablePropertyLongProperty() {
//      final long value1 = 98765432123456789L;
//      final long value2 = -1234567890987654321L;
//      final LongProperty source = new SimpleLongProperty(value1);
//      final LongExpression target = Properties.unmodifiableProperty(source);
//
//      assertEquals(value1, target.get());
//      source.set(value2);
//      assertEquals(value2, target.get());
//      source.set(value1);
//
//      assertEquals(Long.valueOf(value1), target.getValue());
//      source.set(value2);
//      assertEquals(Long.valueOf(value2), target.getValue());
//      source.set(value1);
//  }
//
//  @Test
//  public void testUnmodifiablePropertyObjectPropertyOfT() {
//      final Object value1 = new Object();
//      final Object value2 = new Object();
//      final ObjectProperty<Object> source = new SimpleObjectProperty<Object>(value1);
//      final ObjectExpression<Object> target = Properties.unmodifiableProperty(source);
//
//      assertEquals(value1, target.get());
//      source.set(value2);
//      assertEquals(value2, target.get());
//      source.set(value1);
//
//      assertEquals(value1, target.getValue());
//      source.set(value2);
//      assertEquals(value2, target.getValue());
//      source.set(value1);
//  }
//
//  @Test
//  public void testUnmodifiablePropertyStringProperty() {
//      final String value1 = "Hello World";
//      final String value2 = "Goodbye";
//      final StringProperty source = new SimpleStringProperty(value1);
//      final StringExpression target = Properties.unmodifiableProperty(source);
//
//      assertEquals(value1, target.get());
//      source.set(value2);
//      assertEquals(value2, target.get());
//      source.set(value1);
//
//      assertEquals(value1, target.getValue());
//      source.set(value2);
//      assertEquals(value2, target.getValue());
//      source.set(value1);
//  }
//
//}
