package com.jloisel.collection.map.persistence;

import java.io.IOException;

public interface Persistence extends Iterable<String> {

	boolean canRead(final String key);
	
	int count();
	
	void write(final String key, final byte[] serialized) throws IOException;
	
	byte[] read(final String key) throws IOException;
	
	void remove(final String key) throws IOException;
	
	void clear() throws IOException;
}
