package com.laher.test;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 *
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/7/17
 */
public class TestRateLimiter {
    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));
        // 指定每秒放1个令牌
        RateLimiter limiter = RateLimiter.create(1);
        for (int i = 1; i < 50; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            // acquire(int permits)函数主要用于获取permits个令牌，并计算需要等待多长时间，进而挂起等待，并将该值返回

            // 每秒生产1个令牌，第一个acquire获取一个令牌花了0毫秒
            // 因为还在1秒以内，此时Rate中没有多余令牌，此时第二个acquire消费10令牌来了，
            // rate是可超限领取令牌机制，下次则挂起等待。
            // 所以第二个acquire需要等第一个1秒结束才能执行，
            // 以此类推，第三个acquire 等待 第二个(1*permits消耗令牌数)秒结束
            // 最终目的，根据服务端实际生产令牌数量，控制请求的qps
            // 和抢票一个道理，每1秒钟放10个 那么最多1秒钟10个请求
            // 想抢第十一个只能等到下一个一秒
            Double acquire = null;
            if (i == 1) {
                acquire = limiter.acquire(1);
            } else if (i == 2) {
                acquire = limiter.acquire(10);
            } else if (i == 3) {
                acquire = limiter.acquire(2);
            } else if (i == 4) {
                acquire = limiter.acquire(20);
            } else {
                acquire = limiter.acquire(2);
            }
            executorService.submit(new Task("获取令牌成功，获取耗：" + acquire + " 第 " + i + " 个任务执行"));
        }
    }
}

class Task implements Runnable {
    String str;

    public Task(String str) {
        this.str = str;
    }

    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf.format(new Date()) + " | " + Thread.currentThread().getName() + str);
    }
}