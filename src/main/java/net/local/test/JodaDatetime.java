package net.local.test;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaDatetime extends AbstractTimestamp2Date {
    private DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT).withZoneUTC();

    public JodaDatetime(long c) {
        super(c);
    }

    @Override
    public String format(long timestamp) {
        return formatter.print(timestamp);
    }
}
