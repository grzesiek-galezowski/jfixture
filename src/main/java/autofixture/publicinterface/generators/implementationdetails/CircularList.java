package autofixture.publicinterface.generators.implementationdetails;

import autofixture.publicinterface.InstanceType;

import java.util.Random;

public class CircularList<T> {

	private T[] enumConstants;
    private Random random = new Random();
	private int currentIndex = 0;

	public static <TListElement> CircularList<TListElement> createFromEnum(InstanceType<TListElement> type) {
		TListElement[] enumConstants = type.getEnumConstants();
		
		return new CircularList<>(enumConstants);
	}
	
	public CircularList(T[] values) {
		this.enumConstants = values;
		currentIndex = random.nextInt(values.length);
	}
	
	public T next() {
		if(currentIndex >= enumConstants.length) {
			currentIndex = 0;
		}
		
		T nextElement = enumConstants[currentIndex];
		currentIndex++;
		return nextElement;
	}

	public static CircularList<Character> fromCharactersIn(String string) {
		Character[] characters = toCharacterArray(string); 
		return new CircularList<>(characters);
	}

	
	private static Character[] toCharacterArray(String s) {
		   if (s == null) {
		     return null;
		   }
		   Character[] array = new Character[s.length()];
		   for (int i = 0; i < s.length(); i++) {
		      array[i] = s.charAt(i);
		   }

		   return array;
		}
}