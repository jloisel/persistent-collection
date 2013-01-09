package com.jloisel.collection.map;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ForwardingSet;
import com.jloisel.collection.map.persistence.Persistence;


final class KeySet<E> extends ForwardingSet<E> {
	private final Persistence persistence;
	private final Set<E> delegate;
	
	public KeySet(final Set<E> delegate, final Persistence persistence) {
		super();
		this.delegate = delegate;
		this.persistence = persistence;
	}
	
	@Override
	public boolean remove(final Object key) {
		try {
			return super.remove(key);
		} finally {
			try {
				persistence.remove(String.valueOf(key));
			} catch (final IOException e) {
				
			}
		}
	}
	
	@Override
	public boolean removeAll(final Collection<?> collection) {
		// TODO Auto-generated method stub
		return super.removeAll(collection);
	}
	
	@Override
	public void clear() {
		try {
			super.clear();
		} finally {
			try {
				persistence.clear();
			} catch (final IOException e) {
				
			}
		}
	}
	
	@Override
	protected Set<E> delegate() {
		return delegate;
	}

}
