package ru.itpark.app;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements BeanPostProcessor {
    private final Map<Method, Object> cache = new HashMap<>();
    private final Map<String, Class> names = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Cached.class)) {
            names.put(beanName, bean.getClass());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(!names.containsKey(beanName)) {
            return bean;
        }

        var enhancer = new Enhancer();
        enhancer.setSuperclass(names.get(beanName));
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if(!cache.containsKey(method)) {
                    System.out.println("--> cache is emty");
                    var result = methodProxy.invoke(bean, args);
                    cache.put(method, result);
                    return result;
                }
                else {
                    System.out.println("--> cache request");
                    return cache.get(method);
                }
            }
        });

        return enhancer.create();
    }
}
