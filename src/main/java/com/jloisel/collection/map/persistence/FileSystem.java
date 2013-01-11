package com.jloisel.collection.map.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

final class FileSystem implements Persistence {
	private final Path directory;
	
	public FileSystem(final Path directory) {
		super();
		this.directory = checkNotNull(directory);
	}

	@Override
	public void write(final String key, final byte[] serialized) throws IOException {
		final Path path = toPath(key);
		if(!IO.canRead(path)) {
			Files.createFile(path);
		}
		IO.write(path, serialized);
	}

	@Override
	public byte[] read(final String key) throws IOException {
		if(!IO.canRead(toPath(key))) {
			return IO.EMPTY_BYTES;
		}
		return IO.read(toPath(key));
	}

	@Override
	public boolean canRead(final String key) {
		return IO.canRead(toPath(key));
	}

	@Override
	public void remove(final String key) throws IOException {
		IO.delete(toPath(key));
	}
	
	private Path toPath(final String key) {
		return directory.resolve(checkNotNull(key));
	}

	@Override
	public void clear() throws IOException {
		IO.clear(directory);
	}

	@Override
	public Iterator<String> iterator() {
		try {
			return Iterables.transform(IO.iterable(directory), new ToKeyFunction(directory)).iterator();
		} catch (final IOException e) {
			return Iterators.emptyIterator();
		}
	}
	
	static final class ToKeyFunction implements Function<Path, String> {
		private final Path directory;		
		
		ToKeyFunction(final Path directory) {
			super();
			this.directory = directory;
		}
		
		public String apply(final Path path) {
			return directory.relativize(path).toString();
		}
	}
}