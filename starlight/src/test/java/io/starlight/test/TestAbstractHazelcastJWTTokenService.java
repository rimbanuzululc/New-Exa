package io.starlight.test;
import java.security.Key;
import java.util.UUID;

import org.apache.log4j.BasicConfigurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hazelcast.core.Hazelcast;

import io.starlight.AbstractHazelcastJWTTokenServiceImpl;
import io.starlight.HazelcastContextHolder;

public class TestAbstractHazelcastJWTTokenService extends AbstractHazelcastJWTTokenServiceImpl{
	
	//@BeforeClass
	public static void pre() {
		BasicConfigurator.configure();

		HazelcastContextHolder.setCurrentInstance(Hazelcast.newHazelcastInstance());
	}

	//@Test
	public void testGetSecret() throws InterruptedException {
		System.out.println("current secret = "+this.getNewSecret());
		Thread.sleep(500);
		System.out.println("current secret = "+this.getNewSecret());
		Thread.sleep(500);
		System.out.println("current secret = "+this.getNewSecret());
		Thread.sleep(500);

		System.out.println("Secrets ===========>");
		for (Key s : this.getSecrets()) {
			System.out.println(s);
		}

		System.out.println("current secret = "+this.getNewSecret());
		Thread.sleep(500);
		System.out.println("current secret = "+this.getNewSecret());
		Thread.sleep(500);
		System.out.println("current secret = "+this.getNewSecret());
		Thread.sleep(500);

		System.out.println("Secrets ===========>");
		for (Key s : this.getSecrets()) {
			System.out.println(s);
		}
	}

	//@AfterClass
	public static void post() {
		HazelcastContextHolder.currentInstance().shutdown();
	}
	
	public static class KeyImpl implements Key {
		private static final long serialVersionUID = 1L;
		String uuid = UUID.randomUUID().toString();
		
		@Override
		public String getFormat() {
			return "UUID";
		}
		
		@Override
		public byte[] getEncoded() {
			return uuid.getBytes();
		}
		
		@Override
		public String getAlgorithm() {
			return "UUID";
		}

		@Override
		public String toString() {
			return uuid.toString();
		}
		
		
	}

	@Override
	protected Key generateSecretKey() {
		Key k = new KeyImpl();
		return k;
	}
}
