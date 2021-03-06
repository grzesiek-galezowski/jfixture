package autofixture.generators.time;

import autofixture.interfaces.FixtureContract;
import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;

import java.time.Duration;
import java.time.OffsetTime;

/**
 * Created by astral on 08.02.15.
 */
public class OffsetTimeGenerator implements InstanceGenerator {
  @Override
  public <T> boolean appliesTo(final InstanceType<T> instanceType) {
    return instanceType.isRawTypeAssignableFrom(OffsetTime.class);
  }

  @Override
  public <T> T next(final InstanceType<T> instanceType, final FixtureContract fixture) {
    return (T) OffsetTime.now().plus(Duration.ofSeconds(fixture.create(Integer.class)));
  }

  @Override
  public <T> T nextEmpty(final InstanceType<T> instanceType, final FixtureContract fixture) {
    return next(instanceType, fixture);
  }

  @Override
  public void setOmittingAutoProperties(final boolean isOn) {

  }
}
