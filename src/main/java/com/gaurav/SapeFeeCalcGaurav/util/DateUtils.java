package com.gaurav.SapeFeeCalcGaurav.util;

import com.gaurav.SapeFeeCalcGaurav.exception.DataProcessorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    public static String DATE_FORMAT = "dd/MM/yy";
    public static String DEFAULT_DATE_FORMAT = "MM/dd/yy";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    public static LocalDate getLocaldateForString(String date) throws DataProcessorException {
        try {
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new DataProcessorException("Unparsable date - " + date);
        }
    }
}
