package com.jloisel.collection.map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jloisel.collection.map.externalizer.Externalizer;
import com.jloisel.collection.map.externalizer.Externalizers;
import com.jloisel.collection.map.persistence.Persistence;
import com.jloisel.collection.map.persistence.Persistences;

/**
 * Builds a persistent {@link Map}.
 * 
 * By default, no persistence is configured. See {@link Persistences} 
 * for predefined ones.
 * 
 * key / value pairs are persisted by being converted to/from bytes. 
 * By default, Java Serialization mechanism is used.
 * 
 * {@code null} keys or values are not supported.
 * 
 * @author jloisel
 *
 * @param <V> type of value being stored
 */
public final class PersistentMapBuilder<V extends Persistent> {
	Map<String, V> delegate = Maps.newHashMap();
	Persistence persistence = Persistences.none();
	Externalizer<V> externalizer = Externalizers.serializable();
	
	/**
	 * New persistent map builder.
	 */
	public PersistentMapBuilder() {
		super();
	}
	
	/**
	 * By default, persistent {@link Map} is backed with an 
	 * {@code HashMap}.
	 * Persistent {@link Map} can be backed by any {@link Map}.
	 * The backing map keeps stored objects copies in memory.
	 * 
	 * @param map backing map
	 * @return
	 */
	public PersistentMapBuilder<V> backWith(final Map<String, V> map) {
		checkState(map.isEmpty(), "backing map must be empty");
		this.delegate = checkNotNull(map);
		return this;
	}
	
	/**
	 * By default, there is no persistence.
	 * See {@link Persistences} for existing persistence, including 
	 * storage on file system.
	 * 
	 * @param persistence
	 * @return
	 */
	public PersistentMapBuilder<V> persistOn(final Persistence persistence) {
		this.persistence = checkNotNull(persistence);
		return this;
	}
	
	/**
	 * By default, 
	 * @param externalizer
	 * @return
	 */
	public PersistentMapBuilder<V> externalizeUsing(final Externalizer<V> externalizer) {
		this.externalizer = checkNotNull(externalizer);
		return this;
	}
	
	/**
	 * Returns a new persistent map.
	 * 
	 * @return persistent map
	 * @throws IOException if any 
	 */
	public Map<String, V> build() throws IOException {
		return new PersistentMap<>(this);
	}
}
