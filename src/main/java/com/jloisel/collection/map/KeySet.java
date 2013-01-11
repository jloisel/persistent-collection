package com.jloisel.collection.map;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.jloisel.collection.map.persistence.Persistence;

final class KeySet<E> extends PersistentMapSet<E>{

	KeySet(final Set<E> delegate, final Persistence persistence) {
		super(delegate, persistence, new ToKey<E>());
	}
	
	static final class ToKey<E> implements Function<E, String> {
		@Override
		public String apply(@Nullable final E key) {
			return key == null ? null : String.valueOf(key);
		}	
	}
}
