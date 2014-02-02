/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package org.slf4j.impl;

import de.egym.egymapp.logging.EgymLoggerFactory;
import org.slf4j.ILoggerFactory;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

/**
 * Tests {@link StaticLoggerBinder}.
 */
@Test
public class StaticLoggerBinderTest {
	@Test
	public void testRequestedApiVersion() {
		assertNotNull(StaticLoggerBinder.REQUESTED_API_VERSION);
	}

	@Test
	public void testGetSingleton() {
		final StaticLoggerBinder loggerBinder1 = StaticLoggerBinder.getSingleton();
		final StaticLoggerBinder loggerBinder2 = StaticLoggerBinder.getSingleton();

		assertNotNull(loggerBinder1);
		assertNotNull(loggerBinder2);
		assertTrue(loggerBinder1 == loggerBinder2);
	}

	@Test
	public void testGetLoggerFactory() {
		final ILoggerFactory loggerFactory1 = StaticLoggerBinder.getSingleton().getLoggerFactory();
		final ILoggerFactory loggerFactory2 = StaticLoggerBinder.getSingleton().getLoggerFactory();

		assertNotNull(loggerFactory1);
		assertTrue(loggerFactory1 == loggerFactory2);
		assertTrue(loggerFactory1 instanceof EgymLoggerFactory);
	}

	@Test
	public void testGetLoggerFactoryClassStr() {
		final String classStr = StaticLoggerBinder.getSingleton().getLoggerFactoryClassStr();
		assertEquals(classStr, EgymLoggerFactory.class.getName());
	}
}
