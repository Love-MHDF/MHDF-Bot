package cn.ChengZhiYa.MHDFBot.api;

import cn.ChengZhiYa.MHDFBot.api.manager.Runnable;

public abstract class MHDFBotRunnable implements Runnable {
    @Override
    public void run() {
    }

    public void runTask() {
        MHDFBot.getScheduler().runTask(this::run);
    }

    public void runTaskLater(long delay) {
        MHDFBot.getScheduler().runTaskLater(this::run, delay);
    }

    public void runTaskAsynchronously() {
        MHDFBot.getScheduler().runTaskAsynchronously(this::run);
    }

    public void runTaskAsynchronouslyLater(long delay) {
        MHDFBot.getScheduler().runTaskAsynchronouslyLater(this::run, delay);
    }

    public void runTaskAsynchronouslyTimer(long delay, long period) {
        MHDFBot.getScheduler().runTaskAsynchronouslyTimer(this::run, delay, period);
    }
}
