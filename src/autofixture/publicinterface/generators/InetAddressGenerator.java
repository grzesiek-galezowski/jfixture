package autofixture.publicinterface.generators;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import autofixture.publicinterface.FixtureContract;
import autofixture.publicinterface.InstanceGenerator;
import autofixture.publicinterface.InstanceType;
import autofixture.publicinterface.ObjectCreationException;

import com.google.common.net.InetAddresses;

public class InetAddressGenerator implements InstanceGenerator {
	Random random = new Random();
	
	@Override
	public <T> boolean appliesTo(InstanceType<T> instanceType) {
		return instanceType.isRawTypeAssignableFrom(InetAddress.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T next(InstanceType<T> instanceType, FixtureContract fixture) {
		byte[] bytes = new byte[4];
		random.nextBytes(bytes);
		InetAddress address;
		try {
			address = Inet4Address.getByAddress(bytes);
		} catch (UnknownHostException e) {
			throw new ObjectCreationException(instanceType, e);
		}
		return (T) address;
	}

}