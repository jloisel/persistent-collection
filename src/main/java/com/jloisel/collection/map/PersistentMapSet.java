package com.jloisel.collection.map;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.ForwardingSet;
import com.jloisel.collection.map.persistence.Persistence;


abstract class PersistentMapSet<E> extends ForwardingSet<E> {
	private final Persistence persistence;
	private final Set<E> delegate;
	private final Function<E, String> toKey;

	PersistentMapSet(final Set<E> delegate, final Persistence persistence, final Function<E, String> toKey) {
		super();
		this.delegate = delegate;
		this.persistence = persistence;
		this.toKey = toKey;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(final Collection<?> collection) {
		for(final Object obj : this) {
			if(collection.contains(obj)) {
				continue;
			}

			try {
				persistence.remove(toKey.apply((E) obj));
			} catch (final IOException e) {
				// ignored
			}
		}
		return super.retainAll(collection);
	}

	@Override
	public boolean removeAll(final Collection<?> collection) {
		boolean modified = false;
		for(final Object obj : collection) {
			modified |= remove(obj);
		}
		return modified;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(final Object obj) {
		if(super.remove(obj)) {
			try {
				persistence.remove(toKey.apply((E) obj));
			} catch (final IOException e) {
				// ignored
			}
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		try {
			super.clear();
		} finally {
			try {
				persistence.clear();
			} catch (final IOException e) {
				// ignored
			}
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new PersistentMapSetIterator<>(super.iterator(), persistence, toKey);
	}

	@Override
	protected Set<E> delegate() {
		return delegate;
	}
}