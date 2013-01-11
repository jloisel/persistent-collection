package com.jloisel.collection.map.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

final class IO {
	static final byte[] EMPTY_BYTES = new byte[0];

	private IO() {
		throw new IllegalAccessError();
	}

	static byte[] read(final Path file) throws IOException {
		return Files.readAllBytes(checkNotNull(file));
	}

	static void write(final Path file, final byte[] bytes) throws IOException {
		Files.write(checkNotNull(file), checkNotNull(bytes), StandardOpenOption.WRITE);
	}

	static void delete(final Path file) throws IOException {
		Files.deleteIfExists(checkNotNull(file));
	}

	static boolean canRead(final Path file) {
		return Files.isReadable(file) && Files.isRegularFile(file);
	}

	static Iterable<Path> iterable(final Path directory) throws IOException {
		return Files.newDirectoryStream(checkNotNull(directory));
	}

	static void clear(final Path directory) throws IOException {
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(
					final Path file,
					final BasicFileAttributes attrs) throws IOException {
				Files.delete(checkNotNull(file));
				return super.visitFile(file, attrs);
			}
		});
	}
}
