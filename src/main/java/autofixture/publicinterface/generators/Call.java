package autofixture.publicinterface.generators;

import autofixture.publicinterface.FixtureContract;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.Parameter;

import java.util.ArrayList;

public interface Call<TOwnerType, TReturnType> {

	ImmutableList<Parameter> getParameters();
	TReturnType invoke(TOwnerType ownerType, ArrayList<Object> arguments);
	TReturnType invokeWithArgumentsCreatedUsing(
			FixtureContract fixture,
			TOwnerType returnType);
	TReturnType invokeWithArgumentsCreatedUsing(
			FixtureContract fixture);

	
}
