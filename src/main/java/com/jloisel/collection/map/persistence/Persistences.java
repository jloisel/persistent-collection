package com.jloisel.collection.map.persistence;

import java.nio.file.Path;

public final class Persistences {

	private Persistences() {
		throw new IllegalAccessError();
	}
	
	public static Persistence none() {
		return None.INSTANCE;
	}
	
	public static Persistence toFilesystem(final Path directory) {
		return new FileSystem(directory);
	}
}
