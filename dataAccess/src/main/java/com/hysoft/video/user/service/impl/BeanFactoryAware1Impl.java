package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.service.BeanFactoryAware1;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by yulifan on 2017/3/6.
 */
@Component
public class BeanFactoryAware1Impl implements BeanFactoryAware1,BeanNameAware,BeanPostProcessor,
        ApplicationContextAware,InitializingBean,DisposableBean{

    private String beanId;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setBeanName(String s) {
      this.beanId = s;
    }

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception{

    }

    public void destroy() throws Exception {
        System.out.println("destroy Bean:" + this.beanId);
    }
}
