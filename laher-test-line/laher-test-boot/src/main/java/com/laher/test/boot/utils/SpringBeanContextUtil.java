package com.laher.test.boot.utils;

import java.util.function.Supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * @author laher
 * @version 1.0.0
 * @date 2020/9/15
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringBeanContextUtil implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {
    private static ApplicationContext applicationContext;
    private static BeanDefinitionRegistry registry;

    public SpringBeanContextUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        try {
            return applicationContext.getBean(name);
        } catch (Exception var2) {

            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception var2) {
            return null;
        }
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        try {
            return applicationContext.getBean(name, clazz);
        } catch (Exception var3) {

            return null;
        }
    }

    public static void registryBean(String name, final Object object) {
        registry.registerBeanDefinition(name, BeanDefinitionBuilder.genericBeanDefinition(object.getClass(), (Supplier) () -> object).getBeanDefinition());
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        SpringBeanContextUtil.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
