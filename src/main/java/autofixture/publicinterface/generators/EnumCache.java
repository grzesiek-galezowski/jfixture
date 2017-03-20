package autofixture.publicinterface.generators;

import autofixture.interfaces.InstanceType;

public interface EnumCache {

  <T> void registerIfNotPresent(InstanceType<T> instanceType);

  <T> T retrieveNextValueOf(InstanceType<T> instanceType);

}
