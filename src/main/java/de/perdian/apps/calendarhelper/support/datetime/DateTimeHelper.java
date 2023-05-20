package de.perdian.apps.calendarhelper.support.datetime;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

public class DateTimeHelper {

    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyyMMdd"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy")
    );

    private static final List<DateTimeFormatter> TIME_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("HHmmss"),
            DateTimeFormatter.ofPattern("HHmm"),
            DateTimeFormatter.ofPattern("HH:mm:ss"),
            DateTimeFormatter.ofPattern("HH:mm")
    );

    public static LocalDate parseDate(String input) {
        return DateTimeHelper.parse(input, LocalDate::parse, DATE_FORMATTERS);
    }

    public static LocalTime parseTime(String input) {
        return DateTimeHelper.parse(input, LocalTime::parse, TIME_FORMATTERS);
    }

    private static <T extends TemporalAccessor> T parse(String input, BiFunction<String, DateTimeFormatter, T> parserFunction, Collection<DateTimeFormatter> formatters) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return parserFunction.apply(input, formatter);
            } catch (DateTimeParseException e) {
                // Ignore here and move on to next formatter
            }
        }
        throw new DateTimeException("Invalid date time value: " + input);
    }

}
