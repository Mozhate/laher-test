package com.laher.test.tested.config;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试配置
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/17
 */
@RunWith(value = SpringRunner.class)
public class TestedConfig {


    @Before
    public void before() {
        System.out.println("测试前置输出");
    }

    @After
    public void after() {
        System.out.println("测试后置输出");
    }
}
