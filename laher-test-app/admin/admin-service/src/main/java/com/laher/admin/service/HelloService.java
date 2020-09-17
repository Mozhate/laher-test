package com.laher.admin.service;

import com.laher.admin.model.HelloRequest;

/**
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/14
 */
public interface HelloService {
    /**
     * 输出结果
     * 
     * @param helloRequest 数据
     * @return 结果
     */
    String say(HelloRequest helloRequest);
}
