package com.laher.test.boot.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程创建工厂
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/7/30
 */
public class LaherThreadFactory implements ThreadFactory {

    private final ThreadGroup threadGroup;

    private final String namePrefix;

    private final static AtomicInteger THREAD_POOL_NUM = new AtomicInteger(1);

    private final static AtomicInteger THREAD_NUM = new AtomicInteger(1);

    public LaherThreadFactory() {
        SecurityManager security = System.getSecurityManager();
        threadGroup = security == null ? Thread.currentThread().getThreadGroup() : security.getThreadGroup();

        namePrefix = "Laher-" + THREAD_POOL_NUM.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(threadGroup, r, namePrefix + THREAD_NUM.getAndIncrement());

        // StackSize 默认0 当前线程栈请求堆栈大小，0由VM决定执行
        // State 状态 NEW(新建)，RUNNABLE(运行)，BLOCKED(阻塞)，WAITING(等待)，TIMED_WAITING(有时间的等待)，TERMINATED(终止)

        // 如果当前为守护线程则改为及时线程
        if (thread.isDaemon()) {
            // 守护线程：随主线程关闭而中断执行
            // 及时线程：不随主线程关闭所影响
            thread.setDaemon(Boolean.FALSE);
        }

        // 线程优先级1~10，默认5，级别越高CPU资源获取越高
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        return thread;
    }

    public static void main(String[] args) {
        // 通常是指处理器数：1-4-2 结果为8，1插槽 4内核 2超线程
        // 任务管理器-性能-CPU-（图像数量为超线程数、内核、插槽）
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
