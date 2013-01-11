package com.jloisel.collection.map.persistence;

import java.nio.file.Path;

/**
 * Predefined {@link Persistence} implementations.
 * 
 * @author jloisel
 *
 */
public final class Persistences {
	/**
	 * Utility classes are not intended to be instantiated.
	 */
	private Persistences() {
		throw new IllegalAccessError();
	}
	
	/**
	 * Do not perform any persistence.
	 * @return persistence that does not persist anything
	 */
	public static Persistence none() {
		return None.INSTANCE;
	}
	
	/**
	 * Persists entries to file system.
	 * 
	 * @param directory directory where serialized objects are stored
	 * @return persistence on file system implementation
	 */
	public static Persistence fileSystem(final Path directory) {
		return new FileSystem(directory);
	}
}
