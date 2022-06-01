package com.sourcygen.kata.bank.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateHelper {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

	public static Date parse(String dateInString) throws ParseException {
		return DATE_FORMATTER.parse(dateInString);
	}

	public static String format(Date date) {
		return DATE_FORMATTER.format(date);
	}

}
