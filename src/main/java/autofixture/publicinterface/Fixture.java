package autofixture.publicinterface;

import autofixture.interfaces.*;
import autofixture.publicinterface.generators.DefaultGeneratorsFactory;
import autofixture.interfaces.GeneratorsFactory;
import autofixture.interfaces.GeneratorsPipeline;
import autofixture.implementationdetails.RecursionGuard;
import autofixture.publicinterface.generators.implementationdetails.CollectionFactory;
import autofixture.publicinterface.generators.implementationdetails.ConcreteInstanceType;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Fixture implements FixtureContract {
  public static final int MINIMUM_VALUE_THAT_COULD_MEAN_MANY = 3;
  private final GeneratorsFactory generatorsFactory = new DefaultGeneratorsFactory();
  private final RecursionGuard recursionGuard = new RecursionGuard(20);
  private final GeneratorsPipeline instanceGenerators = generatorsFactory.createBuiltinGenerators(recursionGuard);
  private int repeatCount = MINIMUM_VALUE_THAT_COULD_MEAN_MANY;

  public <T> T create(final Class<T> clazz) {
    return this.create(TypeToken.of(Primitives.wrap(clazz)));
  }

  public <T> T create(final TypeToken<T> typeToken) {
    return create(new ConcreteInstanceType<>(typeToken));
  }

  public <T> T freeze(final TypeToken<T> clazz) {
    final T value = create(clazz);
    inject(value);
    return value;
  }

  public <T> T freeze(final Class<T> clazz) {
    return freeze(TypeToken.of(Primitives.wrap(clazz)));
  }

  public void register(final InstanceGenerator instanceGenerator) {
    instanceGenerators.registerCustomization(instanceGenerator);
  }

  public void clearCustomizations() {
    instanceGenerators.clearCustomizations();
  }

  public <T> T create(final InstanceType<T> instanceType) {
    return instanceGenerators.executeFor(instanceType, this);
  }

  @Override
  public <T> Collection<T> createMany(final TypeToken<T> type) {
    final List<T> manyObjects = Arrays.asList(createArray(type));

    return manyObjects;
  }

  @Override
  public <T> T[] createArray(final TypeToken<T> type) {
    final T[] array = CollectionFactory.createArray(type, repeatCount);
    for (int i = 0; i < repeatCount; ++i) {
      Array.set(array, i, create(type));
    }
    return array;
  }


  @Override
  public <T> Collection<? super T> createMany(final InstanceType<T> type) {
    return createMany(type.getToken());
  }

  @Override
  public int getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(final int repeatCount) {
    this.repeatCount = repeatCount;
  }

  @Override
  public <T> void inject(final T injectedValue) {
    register(new InstanceGenerator() {
      @Override
      public <T2> boolean appliesTo(final InstanceType<T2> instanceType) {
        return instanceType.isSameAsThatOf(injectedValue);
      }

      @Override
      public <T2> T2 next(final InstanceType<T2> instanceType, final FixtureContract fixture) {
        return (T2) injectedValue;
      }

      @Override
      public void setOmittingAutoProperties(final boolean isOn) {

      }
    });
  }

  @Override
  public <T> T create(final TypeToken<T> type, final InlineConstrainedGenerator<T> generator) {
    return generator.next(type, this);
  }

  public <T> T create(final InlineInstanceGenerator<T> inlineGenerator) {
    return inlineGenerator.next(this);
  }

  public String create(final String prefix) {
    return prefix + create(String.class);
  }

  public void setOmittingAutoProperties(final boolean isOn) {
    instanceGenerators.setOmittingAutoProperties(isOn);

  }

  public void setRecursionDepth(final int depth) {
    this.recursionGuard.setMaxDepth(depth);
  }
}
