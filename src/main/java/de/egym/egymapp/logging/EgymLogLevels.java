/**
 * This file is part of the source code and related artifacts for eGym Application.
 *
 * Copyright © 2013 eGym GmbH
 */
package de.egym.egymapp.logging;

class EgymLogLevels {
	private EgymLogLevels() {
		throw new AssertionError("Do not instantiate");
	}

	/**
	 * Tests whether the given log record is equal to or exceeds the specified log threshold.
	 *
	 * @param logRecord
	 *            the log record to test. Must not be null.
	 * @param thresholdLogLevel
	 *            the threshold. Must not be null.
	 * @return True if the record's log level is equal to or exceeds the threshold.
	 */
	static boolean hasSufficientLogLevel(EgymLogRecord logRecord, EgymLogLevel thresholdLogLevel) {
		if (logRecord == null) {
			throw new IllegalArgumentException("logRecord");
		}
		if (logRecord.getLogLevel() == null) {
			throw new IllegalArgumentException("logRecord.logLevel");
		}

		return isSufficientLogLevel(logRecord.getLogLevel(), thresholdLogLevel);
	}

	/**
	 * Tests whether the given log level is equal to or exceeds the specified log threshold.
	 *
	 * @param logLevel
	 *            the log level to test. Must not be null.
	 * @param thresholdLogLevel
	 *            the threshold. Must not be null.
	 * @return True if the record's log level is equal to or exceeds the threshold.
	 */
	static boolean isSufficientLogLevel(EgymLogLevel logLevel, EgymLogLevel thresholdLogLevel) {
		if (logLevel == null) {
			throw new IllegalArgumentException("logLevel");
		}
		if (thresholdLogLevel == null) {
			throw new IllegalArgumentException("thresholdLogLevel");
		}

		return logLevel.ordinal() >= thresholdLogLevel.ordinal();
	}
}
