package com.jloisel.collection.map.externalizer;

import java.io.IOException;

/**
 * Serialize / unserialize instances to/from byte arrays.
 * @author jerome
 *
 * @param <V>
 */
public interface Externalizer<V> {

	byte[] serialize(final V instance) throws IOException;
	
	V unserialize(final byte[] bytes) throws IOException;
}
