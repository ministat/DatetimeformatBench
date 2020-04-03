package net.local.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultipleThreadingEvaluator {
    private int _threadNo = 4;
    private IWorker _worker;

    public MultipleThreadingEvaluator(int thd, IWorker w) {
        _threadNo = thd;
        _worker = w;
    }

    public void run() {
        ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(_threadNo);
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < _threadNo; i++) {
            Task task = new Task(_worker);
            taskList.add(task);
        }
        //Execute all tasks and get reference to Future objects
        List<Future<IResult>> resultList = null;

        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        System.out.println("\n========Printing the results of " + _worker.name() + " ======");
        long totalSz = 0;
        long totalDur = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Future<IResult> future = resultList.get(i);
            try {
                IResult result = future.get();
                long sz = result.iterCount();
                long dur = result.durationMs();
                totalDur += dur;
                totalSz += sz;
                System.out.println("\t" + i +  "thd throughput: " + (double)(sz / dur) * 1000 + " bytes/s");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Overall throughput: " + (double)(totalSz / totalDur) * 1000 + " bytes/s");
        System.out.println("Overall duration: " + totalDur + "(ms) total strings " + totalSz + " bytes");
    }
}

class Task implements Callable<IResult> {
    private IWorker _worker;

    public Task(IWorker w) {
        _worker = w;
    }

    @Override
    public IResult call() throws Exception {
        _worker.warmup();
        long start = System.currentTimeMillis();
        long sz = _worker.benchRun();
        long end = System.currentTimeMillis();
        long dur = end - start;
        Result r = new Result(start, end, sz, dur);
        return r;
    }

}

class Result implements IResult {
    private long _startIndex;
    private long _endIndex;
    private long _iterCount;
    private long _durationMs;
    Result(long s, long e, long itCount, long durMs) {
        _startIndex = s;
        _endIndex = e;
        _iterCount = itCount;
        _durationMs = durMs;
    }

    @Override
    public long startIndex() {
        return _startIndex;
    }

    @Override
    public long endIndex() {
        return _endIndex;
    }

    @Override
    public long iterCount() {
        return _iterCount;
    }

    @Override
    public long durationMs() {
        return _durationMs;
    }
}

interface IResult {
    public long startIndex();
    public long endIndex();
    public long iterCount();
    public long durationMs();
}
