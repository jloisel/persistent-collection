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

public final class PersistentMapBuilder<V extends Persistent> {
	Map<String, V> delegate = Maps.newHashMap();
	Persistence persistence = Persistences.none();
	Externalizer<V> externalizer = Externalizers.serializable();
	
	public PersistentMapBuilder() {
		super();
	}
	
	public PersistentMapBuilder<V> backWith(final Map<String, V> map) {
		checkState(map.isEmpty(), "backing map must be empty");
		this.delegate = checkNotNull(map);
		return this;
	}
	
	public PersistentMapBuilder<V> persistOn(final Persistence persistence) {
		this.persistence = checkNotNull(persistence);
		return this;
	}
	
	public PersistentMapBuilder<V> externalizeUsing(final Externalizer<V> externalizer) {
		this.externalizer = checkNotNull(externalizer);
		return this;
	}
	
	public Map<String, V> build() throws IOException {
		return new PersistentMap<>(this);
	}
}
