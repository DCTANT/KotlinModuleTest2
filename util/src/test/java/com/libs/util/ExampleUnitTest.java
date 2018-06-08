package com.libs.util;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void daysBetween(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2017,6,31);
		int days = TimeUtil.daysBetween(Calendar.getInstance().getTime(), calendar.getTime());
		System.out.println(days);
	}
}