package com.jloisel.collection.map;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ForwardingMap;
import com.jloisel.collection.map.externalizer.Externalizer;
import com.jloisel.collection.map.persistence.Persistence;

/**
 * Persistent {@link Map}.
 * {@code null} keys or values are not supported.
 * 
 * Not Thread-safe.
 * 
 * @author jerome
 *
 * @param <V> type of objects being maintained
 */
final class PersistentMap<V extends Persistent> extends ForwardingMap<String, V> {
	private final Map<String, V> delegate;
	private final Externalizer<V> externalizer;
	private final Persistence persistence;

	PersistentMap(
			final Map<String, V> delegate, 
			final Externalizer<V> externalizer, 
			final Persistence persistence) throws IOException {
		super();
		this.delegate = checkNotNull(delegate);
		this.externalizer = checkNotNull(externalizer);
		this.persistence = checkNotNull(persistence);
		
		for(final String key : persistence) {
			put(key, externalizer.unserialize(persistence.read(key)));
		}
	}

	@Override
	protected Map<String, V> delegate() {
		return delegate;
	}

	@Override
	public V remove(final Object key) {
		try {
			return super.remove(checkNotNull(key));
		} finally {
			try {
				persistence.remove(String.valueOf(key));
			} catch (final IOException e) {
				// cannot be re thrown
			}
		}
	}

	@Override
	public void putAll(final Map<? extends String, ? extends V> map) {
		for (final Entry<? extends String, ? extends V> entry : checkNotNull(map).entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public V put(final String key, final V value) {
		try {
			return super.put(checkNotNull(key), checkNotNull(value));
		} finally {
			try {
				persistence.write(key, externalizer.serialize(value));
			} catch (final IOException e) {
				// cannot be rethrown
			}
		}
	}
	
	@Override
	public Set<String> keySet() {
		return new KeySet<>(super.keySet(), persistence);
	}
	
	@Override
	public Set<Entry<String, V>> entrySet() {
		return new EntrySet<>(super.entrySet(), persistence);
	}
	
	@Override
	public void clear() {
		try {
			delegate.clear();
		} finally {
			try {
				persistence.clear();
			} catch (final IOException e) {
				// cannot be rethrown
			}
		}
	}
}
