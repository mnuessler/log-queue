/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging.writer;

/**
 * Writes all log messages to stdout.
 */
class EgymConsoleLogWriter implements EgymLogWriter {
	@Override
	public void write(String msg) {
		System.out.println(msg);
	}
}
