package site.kycer.project.ktool.cache.scheduled;

import java.util.List;
import java.util.concurrent.*;

/**
 * 计划清理任务
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public enum ScheduledClean {
    /**
     * 单例对象
     */
    INSTANCE;

    private ScheduledExecutorService executorService;

    ScheduledClean() {
        create();
    }


    /**
     * 启动定时任务
     *
     * @param task  执行任务
     * @param delay 执行周期
     * @return {@link ScheduledFuture} 可手动取消此任务
     */
    public ScheduledFuture<?> schedule(Runnable task, long delay) {
        return this.executorService.scheduleWithFixedDelay(task, delay, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建定时器
     */
    public void create() {
        if (null != executorService) {
            shutdownNow();
        }
        this.executorService = new ScheduledThreadPoolExecutor(16, r -> new Thread("clean-cache"));
    }

    /**
     * 销毁清理任务
     */
    public void shutdown() {
        if (null != executorService) {
            executorService.shutdown();
        }
    }

    /**
     * 销毁清理任务
     *
     * @return 销毁时未被执行的任务列表
     */
    public List<Runnable> shutdownNow() {
        if (null != executorService) {
            return executorService.shutdownNow();
        }
        return null;
    }
}
