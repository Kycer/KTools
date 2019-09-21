package site.kycer.project.ktool.cache.scheduled;

import java.util.List;
import java.util.concurrent.*;

/**
 * 全局定时任务
 *
 * @author Kycer
 * @version 1.0
 * @date 2019-09-20
 */
public class ScheduledExecutor {


    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(8, r -> new Thread(r, "removal-cache"));


    private ScheduledExecutor() {

    }


    public static ScheduledExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ScheduledExecutor INSTANCE = new ScheduledExecutor();
    }


    /**
     * 启动定时任务
     *
     * @param task  执行任务
     * @param delay 执行周期
     * @return {@link ScheduledFuture} 可手动取消此任务
     */
    public ScheduledFuture<?> schedule(Runnable task, long delay) {
        return this.executorService.scheduleAtFixedRate(task, 0, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * 销毁清理任务
     */
    public void shutdown() {
        executorService.shutdown();
    }

    /**
     * 销毁清理任务
     *
     * @return 销毁时未被执行的任务列表
     */
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }


}
