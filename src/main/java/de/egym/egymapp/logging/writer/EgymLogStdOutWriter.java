/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging.writer;

import com.google.inject.Singleton;
import net.jcip.annotations.ThreadSafe;

/**
 * Writes all log messages to stdout.
 */
@Singleton
@ThreadSafe
public class EgymLogStdOutWriter implements EgymLogWriter<String> {
	@Override
	public void write(String msg) {
		if (msg != null) {
			System.out.println(msg);
		}
	}
}
