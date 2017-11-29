package autofixture.generators.composition;

import autofixture.generators.*;
import autofixture.generators.collections.*;
import autofixture.generators.enums.EnumSequenceGenerator;
import autofixture.generators.enums.InMemoryEnumCache;
import autofixture.generators.numbers.*;
import autofixture.generators.objects.ConcreteObjectGenerator;
import autofixture.generators.objects.InterfaceImplementationGenerator;
import autofixture.generators.objects.ObjenesisGenerator;
import autofixture.generators.objects.PlainObjectGenerator;
import autofixture.generators.throwables.ErrorGenerator;
import autofixture.generators.throwables.ExceptionGenerator;
import autofixture.generators.throwables.ThrowableGenerator;
import autofixture.generators.time.*;
import autofixture.generators.vavr.*;
import autofixture.interfaces.GeneratorsPipeline;
import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.RecursionGuard;
import com.google.common.collect.Lists;
import io.vavr.collection.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DefaultGeneratorsFactory {

  public GeneratorsPipeline createBuiltinGenerators(final RecursionGuard recursionGuard) {
    return
        protectedBy(recursionGuard,
            pipelineOfGeneratorsForTypes(
                matchedInTheFollowingOrder(
                    objects(),
                    integers(),
                    enums(),
                    throwables(),
                    exceptions(),
                    errors(),
                    strings(),
                    doubles(),
                    floats(),
                    bigIntegers(),
                    bigDecimals(),
                    dates(),
                    calendars(),
                    chronoLocalDates(),
                    chronoLocalDateTimes(),
                    localDateTimes(),
                    localDates(),
                    zonedDateTimes(),
                    zoneIds(),
                    offsetTimes(),
                    periods(),
                    durations(),
                    zoneOffsets(),
                    clocks(),
                    instants(),
                    booleans(),
                    urls(),
                    arrays(),
                    optionals(),
                    vavrTypes(),
                    builtInCollections(),
                    builtInMaps(),
                    inetAddresses(),
                    interfaceImplementations(),
                    colorSpaces(),
                    concreteObjects()))
        );
  }

  private InstanceGenerator vavrTypes() {
    return new VavrGenerator(
        new DefaultGeneratorsPipeline(
            Lists.newArrayList(
                new VavrCollectionGenerator<>(
                    io.vavr.collection.List.class,
                    io.vavr.collection.List::ofAll),
                new VavrCollectionGenerator<>(
                    io.vavr.collection.Array.class,
                    io.vavr.collection.Array::ofAll),
                new VavrMapGenerator<>(
                    HashMap.class,
                    HashMap::ofAll),
                new VavrCollectionGenerator<>(
                    io.vavr.collection.HashSet.class,
                    io.vavr.collection.HashSet::ofAll),
                new VavrTreeSetGenerator(),
                new VavrMapGenerator<>(
                    TreeMap.class,
                    TreeMap::ofAll),
                new VavrNodeGenerator(),
                new VavrMapGenerator<>(
                    LinkedHashMap.class,
                    LinkedHashMap::ofAll),
                new VavrCollectionGenerator<>(
                    io.vavr.collection.LinkedHashSet.class,
                    io.vavr.collection.LinkedHashSet::ofAll),
                new VavrCollectionGenerator<>(
                    io.vavr.collection.Vector.class,
                    io.vavr.collection.Vector::ofAll),
                new VavrPriorityQueueGenerator(),
                new VavrCollectionGenerator<>(
                    io.vavr.collection.Queue.class,
                    io.vavr.collection.Queue::ofAll),
                new VavrCharSeqGenerator(),
                new VavrMapGenerator<>(
                    TreeMultimap.class,
                    TreeMultimap.withSeq()::ofAll),
                new VavrMapGenerator<>(
                    HashMultimap.class,
                    HashMultimap.withSeq()::ofAll),
                new VavrMapGenerator<>(
                    LinkedHashMultimap.class,
                    LinkedHashMultimap.withSeq()::ofAll)
            )));
  }

  private InstanceGenerator throwables() {
    return new ThrowableGenerator();
  }

  public DefaultGeneratorsPipeline createDummyGenerators() {
    return pipelineOfGeneratorsForTypes(
            matchedInTheFollowingOrder(
                    integers(),
                    enums(),
                    exceptions(),
                    errors(),
                    strings(),
                    doubles(),
                    floats(),
                    bigIntegers(),
                    bigDecimals(),
                    dates(),
                    calendars(),
                    chronoLocalDates(),
                    chronoLocalDateTimes(),
                    localDateTimes(),
                    localDates(),
                    zonedDateTimes(),
                    zoneIds(),
                    offsetTimes(),
                    periods(),
                    durations(),
                    zoneOffsets(),
                    clocks(),
                    instants(),
                    objects(),
                    booleans(),
                    urls(),
                    emptyArrays(),
                    optionals(),
                    emptyCollections(),
                    emptyMaps(),
                    inetAddresses(),
                    interfaceImplementations(),
                    colorSpaces(),
                    emptyObjects()));
  }

  private InstanceGenerator emptyMaps() {
    return new EmptyMapGenerator();
  }

  private InstanceGenerator builtInMaps() {
    return new BuiltInMapGenerator();
  }

  private ObjenesisGenerator emptyObjects() {
    return new ObjenesisGenerator();
  }

  private EmptyArraysGenerator emptyArrays() {
    return new EmptyArraysGenerator();
  }

  private EmptyCollectionsGenerator emptyCollections() {
    return new EmptyCollectionsGenerator();
  }

  private InstanceGenerator instants() {
    return new InstantGenerator();
  }

  private InstanceGenerator clocks() {
    return clockGenerator();
  }

  private InstanceGenerator clockGenerator() {
    return new ClockGenerator();
  }

  private InstanceGenerator zoneOffsets() {
    return new ZoneOffsetGenerator();
  }

  private InstanceGenerator chronoLocalDateTimes() {
    return new ChronoLocalDateTimeGenerator();
  }

  private InstanceGenerator chronoLocalDates() {
    return new ChronoLocalDateGenerator();
  }

  private InstanceGenerator durations() {
    return new DurationGenerator();
  }

  private InstanceGenerator periods() {
    return new PeriodGenerator();
  }

  private InstanceGenerator offsetTimes() {
    return new OffsetTimeGenerator();
  }

  private InstanceGenerator zoneIds() {
    return new ZoneIdGenerator();
  }

  private InstanceGenerator zonedDateTimes() {
    return new ZonedDateTimeGenerator();
  }

  private InstanceGenerator localDates() {
    return new LocalDateGenerator();
  }

  private InstanceGenerator localDateTimes() {
    return new LocalDateTimeGenerator();
  }

  /*
  @DataPoint public static InstanceOf<LocalDateTime> localDateTime = new InstanceOf<LocalDateTime>() {  };
  @DataPoint public static InstanceOf<LocalDate> localDate = new InstanceOf<LocalDate>() {  };
  @DataPoint public static InstanceOf<Month> month = new InstanceOf<Month>() {  };
  @DataPoint public static InstanceOf<ZonedDateTime> zonedDateTime = new InstanceOf<ZonedDateTime>() {  };
  @DataPoint public static InstanceOf<ZoneId> zoneId = new InstanceOf<ZoneId>() {  };
  @DataPoint public static InstanceOf<OffsetTime> offsetTime = new InstanceOf<OffsetTime>() {  };
  @DataPoint public static InstanceOf<Period> period = new InstanceOf<Period>() {  };
  @DataPoint public static InstanceOf<Duration> duration = new InstanceOf<Duration>() {  };
  @DataPoint public static InstanceOf<Chronology> chronology = new InstanceOf<Chronology>() {  };
  @DataPoint public static InstanceOf<ChronoLocalDate> chronoLocalDate = new InstanceOf<ChronoLocalDate>() {  };
  @DataPoint public static InstanceOf<ChronoLocalDateTime> chronoLocalDateTime = new InstanceOf<ChronoLocalDateTime>() {  };
  @DataPoint public static InstanceOf<ChronoZonedDateTime> chronoZonedDateTime = new InstanceOf<ChronoZonedDateTime>() {  };
  @DataPoint public static InstanceOf<ZoneOffset> zoneOffset = new InstanceOf<ZoneOffset>() {  };

  */


  private InstanceGenerator colorSpaces() {
    return new ColorSpaceGenerator();
  }

  private InstanceGenerator optionals() {
    return new OptionalsGenerator();
  }

  private DefaultGeneratorsPipeline pipelineOfGeneratorsForTypes(final List<InstanceGenerator> generators) {
    return new DefaultGeneratorsPipeline(generators);
  }

  private java.util.List<InstanceGenerator> matchedInTheFollowingOrder(final InstanceGenerator... ts) {
    return new LinkedList<>(Arrays.asList(ts));
  }

  private RandomNumberGenerator integers() {
    return new RandomNumberGenerator();
  }

  private GeneratorsPipeline protectedBy(
      final RecursionGuard recursionGuard, final DefaultGeneratorsPipeline defaultGeneratorsPipeline) {
    return new RecursionGuarded(defaultGeneratorsPipeline, recursionGuard);
  }

  private ConcreteObjectGenerator concreteObjects() {
    return new ConcreteObjectGenerator();
  }

  private InterfaceImplementationGenerator interfaceImplementations() {
    return new InterfaceImplementationGenerator();
  }

  private InetAddressGenerator inetAddresses() {
    return new InetAddressGenerator();
  }

  private BuiltInCollectionGenerator builtInCollections() {
    return new BuiltInCollectionGenerator();
  }

  private ArrayGenerator arrays() {
    return new ArrayGenerator();
  }

  private UrlGenerator urls() {
    return new UrlGenerator();
  }

  private BooleanGenerator booleans() {
    return new BooleanGenerator();
  }

  private ByteAndCharSequenceGenerator bytesAndChars() {
    return new ByteAndCharSequenceGenerator();
  }

  private PlainObjectGenerator objects() {
    return new PlainObjectGenerator();
  }

  private CalendarGenerator calendars() {
    return new CalendarGenerator();
  }

  private DateGenerator dates() {
    return new DateGenerator();
  }

  private BigDecimalGenerator bigDecimals() {
    return new BigDecimalGenerator();
  }

  private BigIntGenerator bigIntegers() {
    return new BigIntGenerator();
  }

  private DoubleSequenceGenerator doubles() {
    return new DoubleSequenceGenerator();
  }

  private FloatSequenceGenerator floats() {
    return new FloatSequenceGenerator();
  }

  private StringGenerator strings() {
    return new StringGenerator();
  }

  private ErrorGenerator errors() {
    return new ErrorGenerator();
  }

  private ExceptionGenerator exceptions() {
    return new ExceptionGenerator();
  }

  private EnumSequenceGenerator enums() {
    return new EnumSequenceGenerator(new InMemoryEnumCache());
  }

}