package com.myspring.main;

/**
 * Created by AillsonWei on 2016/3/16.
 */
public interface BeanFactory {
    //根据Bean的id获得Bean对象的方法
    Object getBean(String beanName);
}
