package io.starlight;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public abstract class AbstractHazelcastJWTTokenServiceImpl {
	
	private final Logger logger = LoggerFactory.getLogger(AbstractHazelcastJWTTokenServiceImpl.class);
	
	private IMap<Integer, SecretItem> scrKeyMap;
	
	private IMap<Integer, SecretItem> secretKeyMap(){
		if (scrKeyMap == null) {
			HazelcastInstance hzInstance = HazelcastContextHolder.currentInstance();
			scrKeyMap = hzInstance.getMap("starlight-jwt-secret-map");
		}
		return scrKeyMap;
	}

	protected static volatile long SECRET_TIMEOUT_SECONDS = 24l * 3600l; //1 day
	
	static {
		SECRET_TIMEOUT_SECONDS = Long.valueOf(System.getProperty("starlight.jwt.secret.timeout.seconds", "86400"));// 1 day
	}
	
	private boolean isExpired(SecretItem si) {
		if (si == null) {
			return true;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Check expiry SECRET_TIMEOUT_SECONDS = "+SECRET_TIMEOUT_SECONDS);
		}
		if (System.currentTimeMillis() - si.getCreated() > SECRET_TIMEOUT_SECONDS*1000l) {
			return true;
		}
		return false;
	}
	
	private static final int NEW_KEY = 0;
	private static final int OLD_KEY = 1;
	
	/**
	 * Get new secret {@link Key} for creating token
	 * @return {@link Key}
	 */
	protected Key getNewSecret() {
		SecretItem si =  secretKeyMap().get(NEW_KEY);
		if (si == null || isExpired(si)) {
			logger.debug("Secret item is expired --> "+si);
			if (secretKeyMap().tryLock(NEW_KEY)) {
				try {
					si = secretKeyMap().get(NEW_KEY);
					if (isExpired(si)) {
						if (si != null) {
							secretKeyMap().put(OLD_KEY, si);
						}
						si = new SecretItem(this.generateSecretKey());
						secretKeyMap().put(NEW_KEY, si);
					}
				} finally {
					try {
						secretKeyMap().unlock(NEW_KEY);
					} catch (Throwable t) {}
				}
			}
		}
		return si.getSecret();
	}
	
	/**
	 * Implementation of secret key generation
	 * @return secret {@link Key}
	 */
	protected abstract Key generateSecretKey();
	
	/**
	 * Get List of secret {@link Key}
	 * @return list of {@link Key}
	 */
	protected List<Key> getSecrets() {
		ArrayList<Key> secrets = new ArrayList<>(2);
		SecretItem siNew = secretKeyMap().get(NEW_KEY);
		if (siNew != null) {
			secrets.add(siNew.getSecret());
		}
		SecretItem siOld = secretKeyMap().get(OLD_KEY);
		if (siOld != null) {
			secrets.add(siOld.getSecret());
		}
		return secrets;
	}
	
	static class SecretItem implements Serializable{
		private static final long serialVersionUID = 1L;
		private final Key secret;
		private final long created = System.currentTimeMillis();
		
		SecretItem(Key secret){
			this.secret = secret;
		}

		public Key getSecret() {
			return secret;
		}

		public long getCreated() {
			return created;
		}

		@Override
		public String toString() {
			return "SecretItem [secret=" + secret + ", created=" + created + "]";
		}
		
		
	}
}
