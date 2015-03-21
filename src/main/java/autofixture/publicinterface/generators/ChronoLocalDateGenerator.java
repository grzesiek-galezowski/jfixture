package autofixture.publicinterface.generators;

import autofixture.publicinterface.FixtureContract;
import autofixture.publicinterface.InstanceGenerator;
import autofixture.publicinterface.InstanceType;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;

/**
 * Created by astral on 08.02.15.
 */
public class ChronoLocalDateGenerator implements InstanceGenerator {
  @Override
  public <T> boolean appliesTo(InstanceType<T> instanceType) {
    return instanceType.isRawTypeAssignableFrom(ChronoLocalDate.class);
  }

  @Override
  public <T> T next(InstanceType<T> instanceType, FixtureContract fixture) {
    return (T) LocalDate.now().plus(Period.ofWeeks(fixture.create(Integer.class)));
  }

  @Override
  public void setOmittingAutoProperties(boolean isOn) {

  }
}