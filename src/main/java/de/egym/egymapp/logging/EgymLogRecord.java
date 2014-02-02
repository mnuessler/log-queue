/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging;

import net.jcip.annotations.NotThreadSafe;
import org.joda.time.DateTime;

/**
 * Contains all the information about one log record as it is created from each slf4j log call.
 */
@NotThreadSafe
class EgymLogRecord {
	/** The point in time the log record was created. */
	private final DateTime timestamp;

	/** The logger which created this record. */
	private final EgymLogger source;

	/** The log level. */
	private final EgymLogLevel logLevel;

	/** The log message, if provided. */
	private final String message;

	/** The throwable, if provided. */
	private final Throwable throwable;

	EgymLogRecord(DateTime timestamp, EgymLogger source, EgymLogLevel logLevel, String message, Throwable throwable) {
		this.timestamp = timestamp;
		this.source = source;
		this.logLevel = logLevel;
		this.message = message;
		this.throwable = throwable;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public EgymLogger getSource() {
		return source;
	}

	public EgymLogLevel getLogLevel() {
		return logLevel;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}
}
