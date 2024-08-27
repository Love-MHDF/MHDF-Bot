package cn.ChengZhiYa.MHDFBot.api;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Scheduler {
    private final ScheduledExecutorService executorService;

    public Scheduler() {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void runTask(Runnable task) {
        task.run();
        executorService.shutdown();
    }

    public void runTaskLater(Runnable task, long delay) {
        try {
            Thread.sleep(delay * 1000);
            task.run();
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void runTaskAsynchronously(Runnable task) {
        executorService.submit(task);
        executorService.shutdown();
    }

    public void runTaskAsynchronouslyLater(Runnable task, long delay) {
        executorService.schedule(task, delay, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    public void runTaskAsynchronouslyTimer(Runnable task, long delay, long period) {
        executorService.scheduleAtFixedRate(task, delay, period, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
