package com.laher.admin.controller;

import com.laher.admin.model.HelloRequest;
import com.laher.admin.service.HelloService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/14
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    @Resource
    private HelloService helloService;

    /**
     * 基本用法
     * 
     * @param helloRequest 入参
     * @return 结果
     */
    @RequestMapping("say")
    public String say(@RequestBody HelloRequest helloRequest) {
        return helloService.say(helloRequest);
    }

    /**
     * 动态数据结果
     * 
     * @param helloRequest 入参
     * @return 结果
     */
    @RequestMapping("dynamicSay")
    public String dynamicSay(@RequestBody HelloRequest helloRequest) {
        return helloService.dynamicSay(helloRequest);
    }

    /**
     * 返回动态规则
     * 
     * @return 结果
     */
    @RequestMapping("getDynamicRule")
    public String getDynamicRule() {
        return helloService.dynamicRule();
    }

    /**
     * 设置动态规则
     * 
     * @return 结果
     */
    @RequestMapping("setDynamicRule")
    public String setDynamicRule(@RequestParam String rule) {
        return helloService.dynamicRule(rule);
    }
}
