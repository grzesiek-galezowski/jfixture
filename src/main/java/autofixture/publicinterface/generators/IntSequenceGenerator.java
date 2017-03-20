package autofixture.publicinterface.generators;

import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;
import autofixture.interfaces.FixtureContract;

public class IntSequenceGenerator implements InstanceGenerator {

  private Integer startingInteger = 1;

  @Override
  public <T> boolean appliesTo(final InstanceType<T> typeToken) {
    return typeToken.isCompatibleWithAnyOf(
        Integer.class,
        Short.class,
        Long.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T next(final InstanceType<T> typeToken, final FixtureContract fixture) {
    return (T) (startingInteger++);
  }

  @Override
  public void setOmittingAutoProperties(final boolean isOn) {
  }

}
