package com.jloisel.collection.map;

import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.jloisel.collection.map.persistence.Persistence;

final class EntrySet<E> extends PersistentMapSet<Entry<String, E>> {

	EntrySet(final Set<Entry<String, E>> delegate, final Persistence persistence) {
		super(delegate, persistence, new ToKey<E>());
	}
	
	static final class ToKey<E> implements Function<Entry<String, E>, String> {
		@Override
		public String apply(@Nullable final Entry<String, E> entry) {
			return entry == null ? null : String.valueOf(entry.getKey());
		}	
	}
}
