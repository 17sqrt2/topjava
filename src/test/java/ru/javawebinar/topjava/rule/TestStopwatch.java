package ru.javawebinar.topjava.rule;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class TestStopwatch extends Stopwatch {
	private static Logger log = getLogger(TestStopwatch.class);

	private Map<String, Long> testDurationMap;

	public TestStopwatch(Map<String, Long> testDurationMap) {
		this.testDurationMap = testDurationMap;
	}

	@Override
	protected void finished(long nanos, Description description) {
		String test = description.getMethodName();
		long duration = TimeUnit.NANOSECONDS.toMicros(nanos);
		log.info("Test {} took {} milliseconds", test, duration);
		testDurationMap.put(test, duration);
	}
}
