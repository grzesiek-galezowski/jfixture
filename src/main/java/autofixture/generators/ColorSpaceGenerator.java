package autofixture.generators;

import autofixture.implementationdetails.CircularList;
import autofixture.interfaces.FixtureContract;
import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;

import java.awt.color.ColorSpace;

/**
 * Created by astral on 11.10.14.
 */
public class ColorSpaceGenerator implements InstanceGenerator {

  private final CircularList<Integer> codes = new CircularList<>(new Integer[]{
      ColorSpace.CS_CIEXYZ,
      ColorSpace.CS_GRAY,
      ColorSpace.CS_LINEAR_RGB,
      ColorSpace.CS_PYCC,
      ColorSpace.CS_sRGB
  });

  @Override
  public <T> boolean appliesTo(final InstanceType<T> instanceType) {
    return instanceType.isRawTypeAssignableFrom(ColorSpace.class);
  }

  @Override
  public <T> T next(final InstanceType<T> instanceType, final FixtureContract fixture) {
    return (T) ColorSpace.getInstance(codes.next());
  }

  @Override
  public <T> T nextEmpty(final InstanceType<T> instanceType, final FixtureContract fixture) {
    return next(instanceType, fixture);
  }

  @Override
  public void setOmittingAutoProperties(final boolean isOn) {

  }
}
