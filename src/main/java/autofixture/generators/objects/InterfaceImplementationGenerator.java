package autofixture.generators.objects;

import autofixture.generators.objects.implementationdetails.InterfaceHandler;
import autofixture.interfaces.FixtureContract;
import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;
import com.google.common.reflect.Reflection;

public class InterfaceImplementationGenerator implements InstanceGenerator {

  @Override
  public <T> boolean appliesTo(final InstanceType<T> instanceType) {
    final boolean isInterface = instanceType.isInterface();
    return isInterface;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T next(final InstanceType<T> instanceType, final FixtureContract fixture) {
    final Object proxy = Reflection.newProxy(instanceType.getRawType(), new InterfaceHandler(
        fixture, instanceType));
    return (T) proxy;
  }

  @Override
  public <T> T nextEmpty(final InstanceType<T> instanceType, final FixtureContract fixture) {
    return next(instanceType, fixture);
  }

  @Override
  public void setOmittingAutoProperties(final boolean isOn) {
  }

}
