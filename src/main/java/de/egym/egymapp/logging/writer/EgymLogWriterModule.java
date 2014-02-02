/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging.writer;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class EgymLogWriterModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(EgymLogWriter.class).to(EgymConsoleLogWriter.class).in(Singleton.class);
	}
}
