package autofixture.publicinterface;

import autofixture.implementationdetails.ErrorMessages;
import autofixture.interfaces.InlineConstrainedGenerator;
import autofixture.interfaces.InlineInstanceGenerator;
import com.google.common.reflect.TypeToken;
import lombok.NonNull;

import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import static autofixture.generators.objects.implementationdetails.TypeAssertions.assertIsNotParameterized;
import static autofixture.implementationdetails.Boxing.boxed;
import static autofixture.publicinterface.InlineGenerators.identifierString;
import static autofixture.publicinterface.InlineGenerators.portNumber;
import static autofixture.publicinterface.InlineGenerators.without;

public class Any {

  @NonNull
  public static <T> T anonymous(final InstanceOf<T> instanceType) {
    return PrivateGenerate.FIXTURE.create(instanceType);
  }

  @NonNull
  public static <T> T anonymous(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("anonymous"));
    return PrivateGenerate.FIXTURE.create(clazz);
  }

  @NonNull
  public static <T> T instanceOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("instanceOf"));
    return PrivateGenerate.FIXTURE.create(clazz);
  }

  @NonNull
  public static <T> T instanceOf(final InlineInstanceGenerator<T> generator) {
    return PrivateGenerate.FIXTURE.create(generator);
  }

  @NonNull
  public static <T> T instanceOf(final Class<T> instanceType, final InlineConstrainedGenerator<T> generator) {
    assertIsNotParameterized(instanceType, ErrorMessages.msgInline("anonymous"));
    return PrivateGenerate.any(TypeToken.of(instanceType), generator);
  }


  @NonNull
  public static <T> T anonymous(final InlineInstanceGenerator<T> generator) {
    return PrivateGenerate.FIXTURE.create(generator);
  }

  @NonNull
  public static <T> T anonymous(final InstanceOf<T> type, final InlineConstrainedGenerator<T> generator) {
    return PrivateGenerate.FIXTURE.create(type, generator);
  }

  @NonNull
  public static <T> T anonymous(final Class<T> instanceType, final InlineConstrainedGenerator<T> generator) {
    assertIsNotParameterized(instanceType, ErrorMessages.msgInline("anonymous"));
    return PrivateGenerate.any(TypeToken.of(instanceType), generator);
  }

  @NonNull
  public static <T> T dummy(final InstanceOf<T> instanceType) {
    return PrivateGenerate.FIXTURE.createDummy(instanceType);
  }

  @NonNull
  public static <T> T dummy(final Class<T> clazz) {
    return PrivateGenerate.FIXTURE.createDummy(clazz);
  }

  public static String string() {
    return anonymous(String.class);
  }

  @NonNull
  public static String string(String seed) {
    return anonymous(InlineGenerators.seededString(seed));
  }

  public static String string(final int charactersCount) {
    return stringOfLength(charactersCount);
  }

  public static String stringOfLength(final int charactersCount) {
    return anonymous(InlineGenerators.stringOfLength(charactersCount));
  }

  @NonNull
  public static String stringNotContaining(final String... excludedSubstrings) {
    return anonymous(InlineGenerators.stringNotContaining(excludedSubstrings));
  }

  @NonNull
  public static String stringContaining(final String str) {
    return anonymous(InlineGenerators.stringContaining(str));
  }

  public static Character alphaChar() {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.alphaChar());
  }

  public static Character digitChar() {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.digitChar());
  }

  public static String alphaString() {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.alphaString());
  }

  public static String alphaString(final int length) {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.alphaString(length));
  }

  public static String lowercaseString() {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.lowercaseString());
  }

  public static String lowercaseString(final int length) {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.lowercaseString(length));
  }

  public static String uppercaseString() {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.uppercaseString());
  }

  public static String uppercaseString(final int length) {
    return PrivateGenerate.FIXTURE.create(InlineGenerators.uppercaseString(length));
  }


  public static String identifier() {
    return PrivateGenerate.FIXTURE.create(identifierString());
  }

  public static String legalXmlTagName() {
    return PrivateGenerate.FIXTURE.create(identifierString());
  }

  public static Integer intValue() {
    return PrivateGenerate.FIXTURE.create(int.class);
  }

  public static Short shortValue() {
    return PrivateGenerate.FIXTURE.create(short.class);
  }

  public static Double doubleValue() {
    return PrivateGenerate.FIXTURE.create(double.class);
  }

  public static Float floatValue() {
    return PrivateGenerate.FIXTURE.create(float.class);
  }

  public static Character charValue() {
    return PrivateGenerate.FIXTURE.create(char.class);
  }

  public static Long longValue() {
    return PrivateGenerate.FIXTURE.create(long.class);
  }

  @NonNull
  public static <T> T otherThan(final T... others) {
    assertHasElements(others);
    Class<?> requestedType = others[0].getClass();
    assertIsNotParameterized(requestedType, "otherThan() does not work for generics. Try Any.anonymous(new InstanceOf<MyType<GenericType>>() {}, otherThan(x,y,z))");
    return Any.anonymous((Class<T>) requestedType, InlineGenerators.otherThan(others));
  }

  @NonNull
  public static <T> T[] arrayWithout(final T... others) {
    assertHasElements(others);
    Class<T> elementClass = (Class<T>) (others[0].getClass());
    assertIsNotParameterized(elementClass, "arrayWithout() does not work for generics. Try Any.arrayOf(new InstanceOf<MyType<GenericType>>() {}, without(x))");

    return Any.arrayOf(elementClass, without(others));
  }

  @NonNull
  public static <T> T[] arrayOtherThan(final T[] otherArray) {
    assertHasElements(otherArray);
    Class<T> elementClass = (Class<T>) (otherArray[0].getClass());
    assertIsNotParameterized(elementClass, "arrayWithout() does not work for generics. Try Any.arrayOf(new InstanceOf<MyType<GenericType>>() {}, without(x))");

    return Any.arrayOf(elementClass, without(otherArray));
  }

  @NonNull
  public static Long longOtherThan(final long... others) {
    assertHasElements(boxed(others));
    return PrivateGenerate.FIXTURE.create(new InstanceOf<Long>() {
    }, InlineGenerators.otherThan(boxed(others)));
  }

  @NonNull
  public static String stringOtherThan(final String... others) {
    assertHasElements(others);
    return PrivateGenerate.FIXTURE.create(new InstanceOf<String>() {
    }, InlineGenerators.otherThan(others));
  }

  @NonNull
  public static Integer intOtherThan(final int... others) {
    assertHasElements(boxed(others));
    return PrivateGenerate.FIXTURE.create(new InstanceOf<Integer>() {
    }, InlineGenerators.otherThan(boxed(others)));
  }

  @NonNull
  public static Short shortOtherThan(final short... others) {
    assertHasElements(boxed(others));
    return PrivateGenerate.FIXTURE.create(new InstanceOf<Short>() {
    }, InlineGenerators.otherThan(boxed(others)));
  }

  @NonNull
  public static Double doubleOtherThan(final double... others) {
    assertHasElements(boxed(others));
    return PrivateGenerate.FIXTURE.create(new InstanceOf<Double>() {
    }, InlineGenerators.otherThan(boxed(others)));
  }

  @NonNull
  public static Float floatOtherThan(final float... others) {
    assertHasElements(boxed(others));
    return PrivateGenerate.FIXTURE.create(new InstanceOf<Float>() {
    }, InlineGenerators.otherThan(boxed(others)));
  }

  @NonNull
  public static <T> T of(final Class<T> enumClass) {
    return anonymous(enumClass);
  }

  public static Date date() {
    return anonymous(Date.class);
  }

  @NonNull
  public static <T> T exploding(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("exploding"));
    return PrivateGenerate.anyExploding(TypeToken.of(clazz));
  }

  @NonNull
  public static <T> T exploding(final InstanceOf<T> typeToken) {
    return PrivateGenerate.anyExploding(typeToken);
  }

  @NonNull
  public static <T> T exploding(final TypeToken<T> typeToken) {
    return PrivateGenerate.anyExploding(typeToken);
  }

  public static Exception exception() {
    return anonymous(RuntimeException.class);
  }

  public static RuntimeException runtimeException() {
    return anonymous(RuntimeException.class);
  }

  public static Exception checkedException() {
    return PrivateGenerate.FIXTURE.create(Exception.class);
  }

  public static Throwable throwable() {
    return PrivateGenerate.FIXTURE.create(Throwable.class);
  }

  public static Throwable uncheckedThrowable() {
    return runtimeException();
  }


  public static Error error() {
    return PrivateGenerate.FIXTURE.create(Error.class);
  }

  public static Boolean booleanValue() {
    return PrivateGenerate.FIXTURE.create(Boolean.class);
  }

  public static Object object() {
    return anonymous(Object.class);
  }

  public static URI uri() {
    return PrivateGenerate.FIXTURE.create(URI.class);
  }

  public static URL url() {
    return PrivateGenerate.FIXTURE.create(URL.class);
  }

  public static Path path() {
    return PrivateGenerate.FIXTURE.create(Path.class);
  }

  public static Path absolutePath() {
    return PrivateGenerate.FIXTURE.create(Path.class);
  }

  public static Path relativePath() {
    return anonymous(InlineGenerators.relativePath());
  }

  public static int port() {
    return PrivateGenerate.FIXTURE.create(portNumber());
  }

  public static InetAddress ip() {
    return PrivateGenerate.FIXTURE.create(InetAddress.class);
  }

  public static ChronoLocalDate chronoLocalDate() {
    return PrivateGenerate.FIXTURE.create(ChronoLocalDate.class);
  }

  public static ChronoLocalDateTime chronoLocalDateTime() {
    return PrivateGenerate.FIXTURE.create(ChronoLocalDateTime.class);
  }

  public static LocalDateTime localDateTime() {
    return PrivateGenerate.FIXTURE.create(LocalDateTime.class);
  }

  public static LocalDate localDate() {
    return PrivateGenerate.FIXTURE.create(LocalDate.class);
  }

  public static ZonedDateTime zonedDateTime() {
    return PrivateGenerate.FIXTURE.create(ZonedDateTime.class);
  }

  public static ZoneId zoneId() {
    return PrivateGenerate.FIXTURE.create(ZoneId.class);
  }

  public static OffsetTime offsetTime() {
    return PrivateGenerate.FIXTURE.create(OffsetTime.class);
  }

  public static Period period() {
    return PrivateGenerate.FIXTURE.create(Period.class);
  }

  public static Duration duration() {
    return PrivateGenerate.FIXTURE.create(Duration.class);
  }

  public static ZoneOffset zoneOffset() {
    return PrivateGenerate.FIXTURE.create(ZoneOffset.class);
  }

  public static Clock clock() {
    return PrivateGenerate.FIXTURE.create(Clock.class);
  }

  public static Instant instant() {
    return PrivateGenerate.FIXTURE.create(Instant.class);
  }

  @NonNull
  public static <T> T from(final T... possibleValues) {
    return InlineGenerators.from(possibleValues).next(PrivateGenerate.FIXTURE);
  }

  // ITERABLES - complete
  @NonNull
  public static <T> Iterable<T> iterableOf(final InstanceOf<T> type) {
    return PrivateGenerate.FIXTURE.createMany(type);
  }

  @NonNull
  public static <T> Iterable<T> iterableOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("iterableOf"));
    return PrivateGenerate.FIXTURE.createMany(TypeToken.of(clazz));
  }

  @NonNull
  public static <T> Iterable<T> iterableOf(
      final InstanceOf<T> typeToken, final InlineConstrainedGenerator<T> omittedValues) {
    return PrivateGenerate.manyAsIterableOf(typeToken, omittedValues);
  }

  @NonNull
  public static <T> Iterable<T> iterableOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("iterableOf"));
    return PrivateGenerate.manyAsIterableOf(TypeToken.of(type), omittedValues);
  }

  //ARRAYS - complete
  @NonNull
  public static <T> T[] arrayOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("arrayOf"));
    return PrivateGenerate.manyAsArrayOf(TypeToken.of(clazz));
  }

  @NonNull
  public static <T> T[] arrayOf(final InstanceOf<T> type) {
    return PrivateGenerate.manyAsArrayOf(type);
  }

  @NonNull
  public static <T> T[] arrayOf(final InstanceOf<T> typeToken, final InlineConstrainedGenerator<T> omittedValues) {
    return PrivateGenerate.manyAsArrayOf(typeToken, omittedValues);
  }

  @NonNull
  public static <T> T[] arrayOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("arrayOf"));
    return PrivateGenerate.manyAsArrayOf(TypeToken.of(type), omittedValues);
  }

  //LISTS - complete

  @NonNull
  public static <T> List<T> listOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("listOf"));
    return PrivateGenerate.manyAsListOf(TypeToken.of(clazz));
  }

  @NonNull
  public static <T> List<T> listOf(final InstanceOf<T> type) {
    return PrivateGenerate.manyAsListOf(type);
  }

  @NonNull
  public static <T> List<T> listOf(final InstanceOf<T> typeToken, final InlineConstrainedGenerator<T> generator) {
    return PrivateGenerate.manyAsListOf(typeToken, generator);
  }

  @NonNull
  public static <T> List<T> listOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("listOf"));
    return PrivateGenerate.manyAsListOf(TypeToken.of(type), omittedValues);
  }

  // COLLECTIONS - complete

  @NonNull
  public static <T> Collection<T> collectionOf(final InstanceOf<T> typeToken, final InlineConstrainedGenerator<T> omittedValues) {
    return PrivateGenerate.manyAsListOf(typeToken, omittedValues);
  }

  @NonNull
  public static <T> Collection<T> collectionOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("collectionOf"));
    return PrivateGenerate.FIXTURE.createMany(TypeToken.of(clazz));
  }

  @NonNull
  public static <T> Collection<T> collectionOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("collectionOf"));
    return PrivateGenerate.manyAsListOf(TypeToken.of(type), omittedValues);
  }

  @NonNull
  public static <T> Collection<T> collectionOf(final InstanceOf<T> instanceType) {
    return PrivateGenerate.FIXTURE.createMany(instanceType);
  }

  //SETS: incomplete

  //TODO variations
  @NonNull
  public static <T> Set<T> setOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("setOf"));
    return PrivateGenerate.manyAsSetOf(TypeToken.of(clazz));
  }

  @NonNull
  public static <T> Set<T> setOf(final InstanceOf<T> type) {
    return PrivateGenerate.manyAsSetOf(type);
  }

  //TODO UT
  @NonNull
  public static <T> Set<T> setOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("setOf"));
    return PrivateGenerate.manyAsSetOf(TypeToken.of(type), omittedValues);
  }

  //TODO UT
  @NonNull
  public static <T> Set<T> setOf(final InstanceOf<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    return PrivateGenerate.manyAsSetOf(type, omittedValues);
  }


  //queues: incomplete
  @NonNull
  public static <T> Queue<T> queueOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("queueOf"));
    return PrivateGenerate.manyAsQueueOf(TypeToken.of(clazz));
  }

  @NonNull
  public static <T> Queue<T> queueOf(final InstanceOf<T> type) {
    return PrivateGenerate.manyAsQueueOf(type);
  }

  //TODO UT

  @NonNull
  public static <T> Queue<T> queueOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("queueOf"));
    return PrivateGenerate.manyAsQueueOf(TypeToken.of(type), omittedValues);
  }
  //TODO UT

  @NonNull
  public static <T> Queue<T> queueOf(final InstanceOf<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    return PrivateGenerate.manyAsQueueOf(type, omittedValues);
  }
  //Deques: incomplete

  @NonNull
  public static <T> Deque<T> dequeOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("dequeOf"));
    return PrivateGenerate.manyAsDequeOf(TypeToken.of(clazz));
  }
  @NonNull
  public static <T> Deque<T> dequeOf(final InstanceOf<T> type) {
    return PrivateGenerate.manyAsDequeOf(type);
  }

  //TODO UT
  @NonNull
  public static <T> Deque<T> dequeOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("dequeOf"));
    return PrivateGenerate.manyAsDequeOf(TypeToken.of(type), omittedValues);
  }

  //TODO UT

  @NonNull
  public static <T> Deque<T> dequeOf(final InstanceOf<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    return PrivateGenerate.manyAsDequeOf(type, omittedValues);
  }
  //sorted sets: incomplete

  @NonNull
  public static <T> SortedSet<T> sortedSetOf(final Class<T> clazz) {
    assertIsNotParameterized(clazz, ErrorMessages.msg("sortedSetOf"));
    return PrivateGenerate.manyAsSortedSetOf(TypeToken.of(clazz));
  }
  @NonNull
  public static <T> SortedSet<T> sortedSetOf(final InstanceOf<T> type) {
    return PrivateGenerate.manyAsSortedSetOf(type);
  }

  //TODO UT

  @NonNull
  public static <T> SortedSet<T> sortedSetOf(final Class<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    assertIsNotParameterized(type, ErrorMessages.msgInline("sortedSetOf"));
    return PrivateGenerate.manyAsSortedSetOf(TypeToken.of(type), omittedValues);
  }
  //TODO UT

  @NonNull
  public static <T> SortedSet<T> sortedSetOf(final InstanceOf<T> type, final InlineConstrainedGenerator<T> omittedValues) {
    return PrivateGenerate.manyAsSortedSetOf(type, omittedValues);
  }

  //sorted maps
  //TODO variations and UT

  @NonNull
  public static <T, V> SortedMap<T, V> sortedMapBetween(final Class<T> keyClass, final Class<V> valueClass) {
    assertIsNotParameterized(keyClass, "generic key types are not allowed for this method.");
    assertIsNotParameterized(valueClass, "generic value types are not allowed for this method.");
    return PrivateGenerate.manyAsSortedMapBetween(TypeToken.of(keyClass), TypeToken.of(valueClass));
  }

  @NonNull
  public static <T, V> SortedMap<T, V> sortedMapBetween(final InstanceOf<T> key, final InstanceOf<V> value) {
    return PrivateGenerate.manyAsSortedMapBetween(key, value);
  }

  //maps

  //TODO variations
  @NonNull
  public static <T, V> Map<T, V> mapBetween(final Class<T> keyClass, final Class<V> valueClass) {
    assertIsNotParameterized(keyClass, "generic key types are not allowed for this method.");
    assertIsNotParameterized(valueClass, "generic value types are not allowed for this method.");
    return PrivateGenerate.manyAsMapBetween(TypeToken.of(keyClass), TypeToken.of(valueClass));
  }

  @NonNull
  public static <T, V> Map<T, V> mapBetween(final InstanceOf<T> keyType, final InstanceOf<V> valueType) {
    return PrivateGenerate.manyAsMapBetween(keyType, valueType);
  }

  /// optionals

  @NonNull
  public static <T> Optional<T> optional(Class<T> type) {
    return Optional.of(Any.anonymous(type));
  }

  @NonNull
  public static <T> Optional<T> optional(InstanceOf<T> type) {
    return Optional.of(Any.anonymous(type));
  }

  @NonNull
  public static <T> Optional<T> optionalOtherThan(Optional<T>... others) {
    T[] others1 = (T[])Arrays.stream(others)
        .map(Optional::get)
        .toArray(Object[]::new);
    T o = Any.otherThan(others1);
    return Optional.of(o);
  }

  private static <T> void assertHasElements(T[] otherArray) {
    if(otherArray.length == 0) {
      throw new RuntimeException(
          "expected the input array to contain at least one element, but is empty");
    }
  }


}
