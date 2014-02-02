/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import de.egym.egymapp.logging.writer.EgymLogWriterModule;

public class EgymLogModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new EgymLogWriterModule());
		bind(EgymLogQueue.class).to(EgymLogQueueImpl.class).in(Singleton.class);
		requestStaticInjection(EgymLogger.class);
	}
}
