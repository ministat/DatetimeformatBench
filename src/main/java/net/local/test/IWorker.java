package net.local.test;

public interface IWorker {
    public long benchRun();
    public void warmup();
    public String name();
}
