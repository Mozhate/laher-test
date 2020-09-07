package com.laher.test;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 测试桶算法
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/7/16
 */
public class TestTrafficShaper {

    public static final ConcurrentMap<String, Double> resourceMap = Maps.newConcurrentMap();

    public static final ConcurrentMap<String, RateLimiter> userResourceLimiterMap = Maps.newConcurrentMap();

    static {
        resourceMap.put("aaa", 50d);
    }

    public static void updateResourceMap(String key, Double value) {
        resourceMap.put(key, value);
    }

    public static void remoteResourceMap(String key) {
        resourceMap.remove(key);
    }

    public static int enter(String resource, String userKey) throws InterruptedException {
        long t1 = System.currentTimeMillis();

        String key = resource + userKey;
        RateLimiter rateLimiter = userResourceLimiterMap.get(key);
        if (rateLimiter == null) {
            double qps = resourceMap.get(resource);

            if (qps == 0d) {
                return 0;
            }

//            rateLimiter = RateLimiter.create(qps);
            rateLimiter = RateLimiter.create(qps, 1000, TimeUnit.MILLISECONDS);
            RateLimiter threadLimiter = userResourceLimiterMap.putIfAbsent(key, rateLimiter);
            if (threadLimiter != null) {
                rateLimiter = threadLimiter;
            }
            // System.out.println(rateLimiter.getRate());
            // rateLimiter.setRate(qps);
            // System.out.println(rateLimiter.getRate());
        }

//        System.out.println("111111111-" + rateLimiter.acquire());

         if (!rateLimiter.tryAcquire()) {
//        if (rateLimiter.acquire() > 0) {
            System.out.println("use:" + (System.currentTimeMillis() - t1) + "ms;" + resource
                + " visited too frequently by key:" + userKey);
            return 99;
        } else {
            System.out.println("use:" + (System.currentTimeMillis() - t1) + "ms;");
            return 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while (true) {
            i++;
            long t2 = System.currentTimeMillis();
            // Thread.sleep(25);
            System.out.println(t2 + ":qq:" + i);

            int result = TestTrafficShaper.enter("aaa", "qq");
            System.out.println((System.currentTimeMillis() - t2) + ":qq:" + i + "\n");
            if (result == 99) {
                i = 0;
                // Thread.sleep(1000);
                return;
            }
        }
    }

}
