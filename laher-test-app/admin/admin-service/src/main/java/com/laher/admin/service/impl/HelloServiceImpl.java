package com.laher.admin.service.impl;

import com.laher.admin.model.HelloRequest;
import com.laher.admin.service.HelloService;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/14
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Resource
    private KieSession kieSession;

    /**
     * 输出结果
     *
     * @param helloRequest 数据
     * @return 结果
     */
    @Override
    public String say(HelloRequest helloRequest) {
        System.out.println("开始执行规则");
        kieSession.insert(helloRequest);
        int count = kieSession.fireAllRules();
        System.out.println("执行规则数：" + count);
        return helloRequest.getResult();
    }
}
