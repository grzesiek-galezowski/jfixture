package autofixture.publicinterface;

import autofixture.generators.composition.DefaultGeneratorsFactory;
import autofixture.generators.objects.implementationdetails.ConcreteInstanceType;
import autofixture.implementationdetails.CollectionFactory;
import autofixture.implementationdetails.MapBasedRecursionGuard;
import autofixture.interfaces.*;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static autofixture.generators.objects.implementationdetails.TypeAssertions.assertIsNotParameterized;

public class Fixture implements FixtureContract {
  public static final int MINIMUM_VALUE_THAT_COULD_MEAN_MANY = 3;
  private final DefaultGeneratorsFactory generatorsFactory;
  private final RecursionGuard recursionGuard;
  private final GeneratorsPipeline instanceGenerators;
  private final int arbitraryRecursionDepth = 5;
  private int repeatCount = MINIMUM_VALUE_THAT_COULD_MEAN_MANY;

  public Fixture() {
    recursionGuard = new MapBasedRecursionGuard(arbitraryRecursionDepth);
    generatorsFactory = new DefaultGeneratorsFactory(new DefaultInlineGeneratorsFactory());
    instanceGenerators = generatorsFactory.createBuiltinGenerators(recursionGuard);
  }

  @Override
  public <T> T create(final Class<T> clazz) {
    assertIsNotParameterized(clazz,
        getParameterizedClassAssertionMessage(clazz, "create"));
    return this.create(TypeToken.of(Primitives.wrap(clazz)));
  }

  @Override
  public <T> T create(final TypeToken<T> typeToken) {
    return create(new ConcreteInstanceType<>(typeToken));
  }

  public <T> T create(final InstanceOf<T> typeToken) {
      return create((TypeToken<T>) (typeToken));
  }

  public <T> T createDummy(final Class<T> clazz) {
    assertIsNotParameterized(clazz,
        getParameterizedClassAssertionMessage(clazz, "createDummy"));
    return this.createDummy(TypeToken.of(Primitives.wrap(clazz)));
  }

  public <T> T createDummy(final TypeToken<T> typeToken) {
    return createDummy(new ConcreteInstanceType<>(typeToken));
  }

  public <T> T createDummy(final InstanceOf<T> typeToken) {
    return createDummy((TypeToken<T>)(typeToken));
  }

  public <T> T freeze(final TypeToken<T> clazz) {
    final T value = create(clazz);
    inject(value);
    return value;
  }

  public <T> T freeze(final InstanceOf<T> clazz) {
    return freeze((TypeToken<T>)clazz);
  }

  public <T> T freeze(final Class<T> clazz) {
    assertIsNotParameterized(clazz,
        getParameterizedClassAssertionMessage(clazz, "freeze"));
    return freeze(TypeToken.of(Primitives.wrap(clazz)));
  }

  public void register(final InstanceGenerator instanceGenerator) {
    instanceGenerators.registerCustomization(instanceGenerator);
  }

  public void clearCustomizations() {
    instanceGenerators.clearCustomizations();
  }

  @Override
  public <T> T create(final InstanceType<T> instanceType) {
    return instanceGenerators.generateInstanceOf(instanceType, this);
  }

  @Override
  public <T> T createDummy(final InstanceType<T> instanceType) {
    return instanceGenerators.generateEmptyInstanceOf(instanceType, this);
  }

  @Override
  public <T> Collection<T> createMany(final TypeToken<T> type) {
    final List<T> manyObjects = Arrays.asList(createArray(type));

    return manyObjects;
  }

  public <T> Collection<T> createMany(final InstanceOf<T> type) {
    return createMany((TypeToken<T>)type);
  }

  @Override
  public <T> T[] createArray(final TypeToken<T> type) {
    final T[] array = CollectionFactory.createArray(type, repeatCount);
    for (int i = 0; i < repeatCount; ++i) {
      Array.set(array, i, create(type));
    }
    return array;
  }

  public <T> T[] createArray(final InstanceOf<T> type) {
    return createArray((TypeToken<T>)type);
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
      public <T2> T2 nextEmpty(final InstanceType<T2> instanceType, final FixtureContract fixture) {
        return (T2) injectedValue;
      }

      @Override
      public void setOmittingAutoProperties(final boolean isOn) {

      }
    });
  }

  public <T> T create(final TypeToken<T> type, final InlineConstrainedGenerator<T> generator) {
    return generator.next(type, this);
  }

  public <T> T create(final InstanceOf<T> type, final InlineConstrainedGenerator<T> generator) {
    return create((TypeToken<T>)type, generator);
  }

  @Override
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

  private <T> String getParameterizedClassAssertionMessage(final Class<T> clazz, final String methodName) {
    return clazz.getSimpleName() + " is a generic class, " +
        "which cannot be instantiated using " + methodName + "(Class<T>) method. " +
        "It should be created using "+ methodName + "(InstanceOf<T>) method like this (example for List<T> class): " +
        methodName + "(new InstanceOf<List<Integer>>() {}); " +
        "(note the {} brackets - they are mandatory!)";
  }
}
