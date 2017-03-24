package autofixture.publicinterface.generators;

import autofixture.interfaces.FixtureContract;
import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;

public class FloatSequenceGenerator implements InstanceGenerator {
  public static final double ARBITRARY_FLOATING_POINT_VALUE = 0.6;
  private Float startingNumber = (float)ARBITRARY_FLOATING_POINT_VALUE;

  @Override
  public <T> boolean appliesTo(final InstanceType<T> typeToken) {
    return typeToken.isCompatibleWithAnyOf(Float.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T next(final InstanceType<T> typeToken, final FixtureContract fixture) {
    return (T) (startingNumber++);
  }

  @Override
  public void setOmittingAutoProperties(final boolean isOn) {
  }
}
