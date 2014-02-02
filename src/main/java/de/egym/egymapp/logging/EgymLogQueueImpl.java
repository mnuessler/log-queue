/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.egym.egymapp.logging.writer.EgymLogWriter;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Singleton
@ThreadSafe
class EgymLogQueueImpl implements EgymLogQueue {
	/**
	 * Describes a request and contains the request specific log queue.
	 */
	@NotThreadSafe
	private static class RequestDescriptor {
		private final DateTime timestamp;
		private final String requestHeadline;
		private final List<EgymLogRecord> logRecords;

		private RequestDescriptor(DateTime timestamp, String requestHeadline) {
			this.timestamp = timestamp;
			this.requestHeadline = requestHeadline;
			this.logRecords = new ArrayList<EgymLogRecord>();
		}

		public DateTime getTimestamp() {
			return timestamp;
		}

		public String getRequestHeadline() {
			return requestHeadline;
		}

		public List<EgymLogRecord> getLogRecords() {
			return logRecords;
		}
	}

	/** The default log level threshold for everything happening <em>outside of a request</em>. */
	private static final EgymLogLevel THRESHOLD_DEFAULT = EgymLogLevel.INFO;

	/** The default log level threshold for everything happening <em>in a request</em>. */
	private static final EgymLogLevel THRESHOLD_REQUEST = EgymLogLevel.INFO;

	/** The threshold within a request which triggers the <em>audit mode</em>. */
	private static final EgymLogLevel THRESHOLD_REQUEST_AUDIT = EgymLogLevel.WARN;

	/** Keeps track of the per-thread request descriptors. This is possible because each thread processes only one request at a time. */
	private static final ThreadLocal<RequestDescriptor> threadRequestDescriptor = new ThreadLocal<RequestDescriptor>();

	private final EgymLogWriter logWriter;

	@Inject
	EgymLogQueueImpl(EgymLogWriter logWriter) {
		this.logWriter = logWriter;
	}

	@Override
	public void startRequest(String requestHeadline) {
		if (requestHeadline == null) {
			throw new IllegalArgumentException("requestHeadline must not be null");
		}

		final RequestDescriptor requestDescriptor = new RequestDescriptor(new DateTime(), requestHeadline);
		threadRequestDescriptor.set(requestDescriptor);
	}

	@Override
	public void endRequest() {
		printLogQueue(threadRequestDescriptor.get());
		threadRequestDescriptor.remove();
	}

	@Override
	public void log(EgymLogRecord logRecord) {
		if (logRecord == null) {
			throw new IllegalArgumentException("logRecord must not be null");
		}

		final RequestDescriptor requestDescriptor = threadRequestDescriptor.get();
		if (requestDescriptor == null) {
			// Print directly if not in a request.
			printLogRecord(logRecord);
		} else {
			// Otherwise the log record is added to the request specific queue.
			requestDescriptor.getLogRecords().add(logRecord);
		}
	}

	/**
	 * Formats and prints the specified log record if its log level is sufficiently high.
	 *
	 * @param logRecord
	 *            the record to print. Must not be null.
	 */
	private void printLogRecord(EgymLogRecord logRecord) {
		if (logRecord == null) {
			throw new IllegalArgumentException("logRecord must not be null");
		}
		if (EgymLogLevels.hasSufficientLogLevel(logRecord, THRESHOLD_DEFAULT)) {
			write(EgymLogFormatter.formatLogRecord(logRecord, ""));
		}
	}

	/**
	 * Formats and prints the entire log queue of the specified request descriptor.
	 *
	 * @param requestDescriptor
	 *            the request descriptor to print. Must not be null.
	 */
	private void printLogQueue(RequestDescriptor requestDescriptor) {
		if (requestDescriptor == null) {
			throw new IllegalArgumentException("requestDescriptor must not be null");
		}

		final StringBuilder str = new StringBuilder();

		final EgymLogLevel maxLogLevel = calcMaxLogLevel(requestDescriptor);
		final boolean audit = EgymLogLevels.isSufficientLogLevel(maxLogLevel, THRESHOLD_REQUEST_AUDIT);

		str.append("Request ");
		str.append(EgymLogFormatter.formatTimestamp(requestDescriptor.getTimestamp()));
		str.append(' ');
		str.append(requestDescriptor.getRequestHeadline());
		str.append('\n');

		if (requestDescriptor.getLogRecords() != null) {
			for (EgymLogRecord logRecord : requestDescriptor.getLogRecords()) {
				// Everything is logged in audit mode!
				if (audit || EgymLogLevels.hasSufficientLogLevel(logRecord, THRESHOLD_REQUEST)) {
					str.append(EgymLogFormatter.formatLogRecord(logRecord, "\t"));
					str.append("\n");
				}
			}
		}

		write(str.toString());
	}

	/**
	 * Calculates the maximum log level which appears in the log messages of the specified request descriptor. If the descriptor contains no
	 * log message, the lowest log level (TRACE) is returned.
	 *
	 * @param requestDescriptor
	 *            the request descriptor to analyze. Must not be null.
	 * @return the maximum log level discovered. Never null.
	 */
	private EgymLogLevel calcMaxLogLevel(RequestDescriptor requestDescriptor) {
		int maxLogLevelOrdinal = 0;

		if (requestDescriptor.getLogRecords() != null) {
			for (EgymLogRecord logRecord : requestDescriptor.getLogRecords()) {
				final EgymLogLevel logLevel = logRecord.getLogLevel();
				final int logLevelOrdinal = logLevel.ordinal();
				maxLogLevelOrdinal = Math.max(maxLogLevelOrdinal, logLevelOrdinal);
			}
		}

		return EgymLogLevel.values()[maxLogLevelOrdinal];
	}

	/**
	 * Writes the specified log message.
	 *
	 * @param msg
	 *            the log message to write. Must not be null.
	 */
	private void write(String msg) {
		logWriter.write(msg);
	}
}
