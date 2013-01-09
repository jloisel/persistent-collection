package com.jloisel.persistent.map;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Test;
import junit.framework.TestCase;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.testing.MapTestSuiteBuilder;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestMapGenerator;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.MapFeature;
import com.google.common.io.Files;
import com.jloisel.collection.map.Persistent;
import com.jloisel.collection.map.PersistentMapBuilder;

public class PersistentMapTest extends TestCase {

	static final Path TMP = Files.createTempDir().toPath();
	
	public static Test suite() {
		return MapTestSuiteBuilder
			.using(new PersistentHashMapGenerator())
			.named("PersistentMapTest")
			.withFeatures(
					MapFeature.GENERAL_PURPOSE, 
					MapFeature.FAILS_FAST_ON_CONCURRENT_MODIFICATION, 
					MapFeature.SUPPORTS_PUT, 
					MapFeature.SUPPORTS_REMOVE,
					CollectionSize.ANY)
			.createTestSuite();
	}
	
	static final class PersistentHashMapGenerator implements TestMapGenerator<String, JunitP> {

		PersistentHashMapGenerator() {
			super();
		}
		
		@Override
		public SampleElements<Entry<String, JunitP>> samples() {
			final Iterator<Entry<String, JunitP>> it = ImmutableMap.of(
					"1", JunitP.of(1), 
					"2", JunitP.of(2),
					"3", JunitP.of(3), 
					"4", JunitP.of(4), 
					"5", JunitP.of(5)
			).entrySet().iterator();
			
			return new SampleElements<>(it.next(), it.next(), it.next(), it.next(), it.next());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Map<String, JunitP> create(final Object... entries) {
			try {
				clear(TMP);
			} catch (final IOException e) {
				Throwables.propagate(e);
			}
			Map<String, JunitP> map;
			try {
				map = new PersistentMapBuilder<JunitP>().build();
				for(final Object entry : entries) {
					final Entry<String, JunitP> persistent = (Entry<String, JunitP>)entry;
					map.put(persistent.getKey(), persistent.getValue());
				}
				return map;
			} catch (final IOException e) {
				fail(e.getMessage());
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Entry<String, JunitP>[] createArray(final int length) {
			final Map<String, JunitP> map = new LinkedHashMap<>();
			for(int i = 0; i < length; i++) {
				map.put(String.valueOf(i), JunitP.of(i));
			}
			return map.entrySet().toArray(new Entry[length]);
		}

		@Override
		public Iterable<Entry<String, JunitP>> order(final List<Entry<String, JunitP>> insertionOrder) {
			return insertionOrder;
		}

		@Override
		public String[] createKeyArray(final int length) {
			final Map<String, JunitP> map = new LinkedHashMap<>();
			for(int i = 0; i < length; i++) {
				map.put(String.valueOf(i), JunitP.of(i));
			}
			return map.keySet().toArray(new String[length]);
		}

		@Override
		public JunitP[] createValueArray(int length) {
			final Map<String, JunitP> map = new LinkedHashMap<>();
			for(int i = 0; i < length; i++) {
				map.put(String.valueOf(i), JunitP.of(i));
			}
			return map.values().toArray(new JunitP[length]);
		}
		
	}
	
	private static final class JunitP implements Persistent {
		private static final long serialVersionUID = 1579708251879152838L;
		private final int hash;
		
		JunitP(final int hash) {
			super();
			this.hash = hash;
		}
		
		@Override
		public boolean equals(final Object obj) {
			if(obj instanceof JunitP) {
				return hashCode() == obj.hashCode();
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return hash;
		}
		
		@Override
		public String toString() {
			return String.valueOf(hash);
		}
		
		static JunitP of(final int hash) {
			return new JunitP(hash);
		}
	}
	
	static void clear(final Path directory) throws IOException {
		java.nio.file.Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(
					final Path file,
					final BasicFileAttributes attrs) throws IOException {
				java.nio.file.Files.delete(checkNotNull(file));
				return super.visitFile(file, attrs);
			}
		});
	}
}
