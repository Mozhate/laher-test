package com.laher.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动项
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/14
 */
@SpringBootApplication(scanBasePackages = "com.laher")
@ComponentScan(value = "com.laher")
public class AdminBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBackendApplication.class,args);
    }
}
