package com.jloisel.collection.map.externalizer;

import java.io.Serializable;

/**
 * Utility methods pertaining to the {@link Externalizer} interface.
 * 
 * @author jerome
 *
 */
public final class Externalizers {
	private Externalizers() {
		throw new IllegalAccessError();
	}
	
	/**
	 * Serialize instances using Java Serialization mecanism.
	 * 
	 * @return
	 */
	public static <V extends Serializable> Externalizer<V> serializable() {
		return new SerializableExternalizer<>();
	}
}
