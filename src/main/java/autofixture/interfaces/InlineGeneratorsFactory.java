package autofixture.interfaces;

import com.google.common.reflect.TypeToken;

import java.nio.file.Path;

/**
 * Created by grzes on 20.03.2017.
 */
public interface InlineGeneratorsFactory {

  InlineInstanceGenerator<String> stringContaining(String str);

  InlineInstanceGenerator<String> alphaString(int length);

  InlineInstanceGenerator<String> uppercaseString();

  InlineInstanceGenerator<String> lowercaseString();

  InlineInstanceGenerator<String> uppercaseString(int length);

  InlineInstanceGenerator<String> lowercaseString(int length);

  InlineInstanceGenerator<String> identifierString();

  InlineInstanceGenerator<Character> digitChar();

  InlineInstanceGenerator<String> stringNotContaining(String... excludedSubstrings);

  InlineInstanceGenerator<Character> alphaChar();

  InlineInstanceGenerator<String> alphaString();

  InlineInstanceGenerator<String> stringOfLength(int charactersCount);

  <T> InlineInstanceGenerator<T> from(T[] possibleValues);

  <T> InlineInstanceGenerator<T> exploding(TypeToken<T> instance);

  <T> InlineConstrainedGenerator<T> otherThan(T... omittedValues);

  <T> InlineConstrainedGenerator<T> without(T... omittedValues);

  InlineInstanceGenerator<Integer> portNumber();

  InlineInstanceGenerator<String> seededString(String seed);

  InlineInstanceGenerator<Path> absolutePath();

  InlineInstanceGenerator<Path> relativePath();

  InlineInstanceGenerator<Path> rootPath();
}
