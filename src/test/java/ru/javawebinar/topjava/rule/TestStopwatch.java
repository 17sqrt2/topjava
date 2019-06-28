package ru.javawebinar.topjava.rule;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class TestStopwatch extends Stopwatch {
	private static Logger log = getLogger("duration");

	private Map<String, Long> testDurationMap;

	public TestStopwatch(Map<String, Long> testDurationMap) {
		this.testDurationMap = testDurationMap;
	}

	@Override
	protected void finished(long nanos, Description description) {
		String test = description.getMethodName();
		long duration = TimeUnit.NANOSECONDS.toMillis(nanos);
		String message = String.format("\n%-30s - %10d milliseconds", test, duration);
		log.info(message);
		testDurationMap.put(test, duration);
	}
}
