package autofixture.publicinterface.generators;

import java.util.Calendar;
import java.util.Date;

import autofixture.publicinterface.FixtureContract;
import autofixture.publicinterface.InstanceGenerator;
import autofixture.publicinterface.InstanceType;

public class DateGenerator implements InstanceGenerator {
	
	Calendar calendar = Calendar.getInstance();
	
	@Override
	public <T> boolean appliesTo(InstanceType<T> typeToken) {
		return typeToken.isRawTypeAssignableFrom(Date.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T next(InstanceType<T> typeToken, FixtureContract fixture) {
		calendar.add(Calendar.SECOND, 1);
		return (T)calendar.getTime();
	}
}