/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging;

import javax.servlet.http.HttpServletRequest;

/**
 * Implements per-request level logging by queuing all log records until the request ends and then formatting an atomic log block for the
 * entire request. Also allows to define a threshold which, if exceeded at any given time during a request, leads to an increased log level,
 * thus providing more details when they are really needed.
 */
public interface EgymLogQueue {
	/**
	 * Must be called when a request starts.
	 *
	 * @param requestHeadline
	 *            a custom request headline which is printed right after the request timestamp. Must not be null.
	 */
	void startRequest(String requestHeadline);

	/**
	 * Must be called when a request ends.
	 */
	void endRequest();

	/**
	 * Used to log a log record.
	 *
	 * @param logRecord
	 *            the record to log. Must not be null.
	 */
	void log(EgymLogRecord logRecord);
}
