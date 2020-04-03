package net.local.test;

public abstract class AbstractTimestamp2Date implements IWorker{
    public static final long OFFSET = ((long) 25567)*24*3600*1000;
    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
    public static final String UTC = "UTC";

    protected long _count;
    private long _timestamp;

    public AbstractTimestamp2Date(long c) {
        _count = c;
        _timestamp = System.currentTimeMillis();
    }

    @Override
    public long benchRun() {
        long i = 0;
        for (i = 0 ;i < _count; i++) {
            format(_timestamp);
        }
        return i;
    }

    @Override
    public void warmup() {
        for (int i = 0 ;i < 10; i++) {
            format(_timestamp);
        }
    }

    @Override
    public String name() {
        return this.getClass().getName();
    }

    public abstract String format(long timestamp);
}
