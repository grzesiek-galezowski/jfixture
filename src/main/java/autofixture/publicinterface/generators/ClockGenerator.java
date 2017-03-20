package autofixture.publicinterface.generators;

import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;
import autofixture.interfaces.FixtureContract;

import java.time.Clock;
import java.time.ZoneId;

/**
 * Created by astral on 08.02.15.
 */
public class ClockGenerator implements InstanceGenerator {
  @Override
  public <T> boolean appliesTo(final InstanceType<T> instanceType) {
    return instanceType.isRawTypeAssignableFrom(Clock.class);
  }

  @Override
  public <T> T next(final InstanceType<T> instanceType, final FixtureContract fixture) {
    return (T) Clock.tickMinutes(fixture.create(ZoneId.class));
  }

  @Override
  public void setOmittingAutoProperties(final boolean isOn) {

  }
}
