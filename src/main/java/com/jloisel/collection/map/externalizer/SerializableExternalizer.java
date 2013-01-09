package com.jloisel.collection.map.externalizer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Converts object to/from bytes by using Java Serialization.
 * 
 * @author jerome
 *
 * @param <V> type of the object being persisted
 */
final class SerializableExternalizer<V extends Serializable> implements Externalizer<V> {
	
	SerializableExternalizer() {
		super();
	}
	
	@Override
	public byte[] serialize(final V instance) throws IOException {
		checkNotNull(instance);
		
		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try (final ObjectOutput out = new ObjectOutputStream(byteOut)) {
			out.writeObject(instance);
		}
		
		return byteOut.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public V unserialize(final byte[] bytes) throws IOException {
		checkNotNull(bytes);
		checkArgument(bytes.length > 0);
		
		final ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(byteIn);
			return (V) input.readObject();
		} catch (final ClassNotFoundException e) {
			throw new IOException(e);
		} finally {
			input.close();
		}
	}
}
