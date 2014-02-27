package jfixture.publicinterface.generators;

import jfixture.publicinterface.FixtureContract;
import jfixture.publicinterface.InstanceGenerator;
import jfixture.publicinterface.InstanceType;

public class DoubleGenerator implements InstanceGenerator {
	private Double startingNumber = 0.3;

	@Override
	public <T> boolean appliesTo(InstanceType<T> typeToken) {
		return typeToken.isRawTypeAssignableFrom(double.class)
				|| typeToken.isRawTypeAssignableFrom(Double.class)
				|| typeToken.isRawTypeAssignableFrom(float.class)
				|| typeToken.isRawTypeAssignableFrom(Float.class); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T next(InstanceType<T> typeToken, FixtureContract fixture) {
		return (T)(startingNumber++);
	}
}
