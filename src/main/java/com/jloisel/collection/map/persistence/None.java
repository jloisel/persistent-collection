package com.jloisel.collection.map.persistence;

import java.io.IOException;
import java.util.Iterator;

import com.google.common.collect.Iterators;

final class None implements Persistence {

	static final Persistence INSTANCE = new None();
	
	private None() {
		super();
	}
	
	@Override
	public Iterator<String> iterator() {
		return Iterators.emptyIterator();
	}

	@Override
	public void persist(final String key, final byte[] serialized) throws IOException {
		
	}

	@Override
	public byte[] read(final String key) throws IOException {
		return IO.EMPTY_BYTES;
	}

	@Override
	public void remove(final String key) throws IOException {
		
	}

	@Override
	public void clear() throws IOException {
		
	}
}
