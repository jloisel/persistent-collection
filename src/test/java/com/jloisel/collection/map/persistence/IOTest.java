package com.jloisel.collection.map.persistence;

import static com.jloisel.unittest.UnitTests.assertNotInstantiable;

import org.junit.Test;

public class IOTest {

	@Test
	public void testNotInstantiable() {
		assertNotInstantiable(IO.class);
	}
}
