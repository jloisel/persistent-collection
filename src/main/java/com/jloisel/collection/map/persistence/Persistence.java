package com.jloisel.collection.map.persistence;

import java.io.IOException;

/**
 * Persistence reads and writes serialized objects 
 * from and to persistence.
 * 
 * @author jerome
 *
 */
public interface Persistence extends Iterable<String> {
	
	/**
	 * Persists serialized bytes with associated key.
	 * 
	 * @param key key associated to this serialized object
	 * @param serialized serialized object to persist
	 * @throws IOException if any error occurs while persisting
	 */
	void persist(final String key, final byte[] serialized) throws IOException;
	
	/**
	 * Reads serialized object.
	 * 
	 * @param key key associated to the serialized persisted object to read
	 * @return serialized object
	 * @throws IOException if any error occurs while reading
	 */
	byte[] read(final String key) throws IOException;
	
	/**
	 * Removes the persisted serialized object associated to 
	 * the passed {@code key}.
	 * 
	 * @param key key associated to the serialized persisted object to read
	 * @throws IOException if any error occurs while removing
	 */
	void remove(final String key) throws IOException;
	
	/**
	 * Removes all the persisted serialized objects.
	 * 
	 * @throws IOException if any error occurs while removing
	 */
	void clear() throws IOException;
}
