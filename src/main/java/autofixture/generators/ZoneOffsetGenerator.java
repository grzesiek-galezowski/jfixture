package autofixture.generators;

import autofixture.interfaces.FixtureContract;
import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;

import java.time.ZoneOffset;

/**
 * Created by astral on 08.02.15.
 */
public class ZoneOffsetGenerator implements InstanceGenerator {
  @Override
  public <T> boolean appliesTo(final InstanceType<T> instanceType) {
    return instanceType.isRawTypeAssignableFrom(ZoneOffset.class);
  }

  @Override
  public <T> T next(final InstanceType<T> instanceType, final FixtureContract fixture) {
    return (T) ZoneOffset.ofTotalSeconds(fixture.create(Integer.class));
  }

  @Override
  public void setOmittingAutoProperties(final boolean isOn) {

  }
}