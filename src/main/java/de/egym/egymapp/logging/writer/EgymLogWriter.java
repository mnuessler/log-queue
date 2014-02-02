/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging.writer;

/**
 * Used to write log messages.
 */
public interface EgymLogWriter {
	/**
	 * Invoked for each log message.
	 *
	 * @param msg
	 *            the log message. Must not be null.
	 */
	void write(String msg);
}
