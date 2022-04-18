package io.starlight;

import com.hazelcast.core.HazelcastInstance;

public class HazelcastContextHolder {

	private static volatile HazelcastInstance _currentInstance;
	
	public static void setCurrentInstance(HazelcastInstance hzInstance){
		_currentInstance = hzInstance;
	}
	
	public static HazelcastInstance currentInstance() {
		return _currentInstance;
	}
}
