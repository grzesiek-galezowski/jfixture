package autofixture.generators;

import autofixture.interfaces.FixtureContract;
import autofixture.interfaces.InlineGeneratorsFactory;
import autofixture.interfaces.InstanceGenerator;
import autofixture.interfaces.InstanceType;

import java.nio.file.Path;

public class PathGenerator implements InstanceGenerator {
    private InlineGeneratorsFactory generatorsFactory;

    public PathGenerator(InlineGeneratorsFactory generatorsFactory) {
        this.generatorsFactory = generatorsFactory;
    }

    @Override
    public <T> boolean appliesTo(InstanceType<T> instanceType) {
        return instanceType.isAssignableTo(Path.class);
    }

    @Override
    public <T> T next(InstanceType<T> instanceType, FixtureContract fixture) {
        return (T) generatorsFactory.relativePath().next(fixture);
    }

    @Override
    public <T> T nextEmpty(InstanceType<T> instanceType, FixtureContract fixture) {
        return next(instanceType, fixture);
    }

    @Override
    public void setOmittingAutoProperties(boolean isOn) {

    }
}
