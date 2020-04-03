package net.local.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JdkDatetime extends AbstractTimestamp2Date {
    public JdkDatetime(long c) {
        super(c);
    }

    @Override
    public String format(long timestamp) {
        Date date = new Date(timestamp - OFFSET);
        // Use UTC timezone
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone(UTC));
        return formatter.format(date);
    }
}
