package com.jloisel.collection.map.externalizer;

import java.io.IOException;

/**
 * Serialize / unserialize instances to/from byte arrays.
 * 
 * @author jerome
 *
 * @param <V> type of object being serialized
 */
public interface Externalizer<V> {

	/**
	 * Converts the passed {@code instance} to bytes.
	 * 
	 * @param instance to serialize
	 * @return serialized instance as bytes
	 * @throws IOException if any error occurs while serializing
	 */
	byte[] serialize(final V instance) throws IOException;
	
	/**
	 * Converts the passed {@code bytes} to an instance.
	 * 
	 * @param bytes to unserialize
	 * @return unserialized instance
	 * @throws IOException if any error occurs while unserializing
	 */
	V unserialize(final byte[] bytes) throws IOException;
}
