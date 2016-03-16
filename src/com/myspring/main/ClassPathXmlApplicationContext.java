package com.myspring.main;

import com.myspring.config.Bean;
import com.myspring.config.Property;
import com.myspring.config.parse.ConfigManager;
import com.myspring.util.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AillsonWei on 2016/3/16.
 */
public class ClassPathXmlApplicationContext implements BeanFactory{
    private Map<String, Bean> config;
    //使用一个map来作为mySpring的容器 =>放置mySpring所管理的对象
    private Map<String, Object> context = new HashMap<String, Object>();

    //希望在ClassPathXmlApplicationContext类一创建就初始化mySpring容器（装载Bean实现类）

    public ClassPathXmlApplicationContext(String path) {
        //1.读取配置文件获得需要初始化的Bean的信息
         config = ConfigManager.getConfig(path);
        //2.遍历配置文件，初始化Bean
        if(config != null){
            for(Map.Entry<String,Bean> en : config.entrySet()){
                //获取配置中的Bean信息
                String beanId = en.getKey();
                Bean bean = en.getValue();

                Object existBean = context.get(beanId);

                //因为createBean方法中也会向Context中放置Bean
                //我们在初始化之前先要判断容器是否已经存在这个Bean，再去完成初始化的工作
                if(existBean == null) {
                    //根据bean配置创建bean对象
                    Object beanObj = createBean(bean);

                    //3.将初始化好的Bean放入容器
                    context.put(beanId, beanObj);
                }
            }
        }

    }

    //根据Bean配置创建Bean实例
    /*
    <bean id="A" class="com.myspring.bean.A">
		<!-- 将A的属性配置，使mySpring自动注入到A中 -->
		<property name="name" value="联想y480"/>
		<property name="mainboard" value="华硕"/>
	</bean>
    */
    private Object createBean(Bean bean) {
        //1.获得要创建的Bean的Class
        String className = bean.getClassName();
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("请检查Bean的配置是否正确" + className);
        }
        //获得class =》将class对应的对象创建出来
        Object beanObj = null;
        try {
            beanObj = clazz.newInstance();//调用空參构造
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("您的Bean没有空参构造！");
        }
        //2.获得Bean的属性，将其注入
        if(bean.getProperties()!= null){
            for(Property prop : bean.getProperties()){
                //注入分两种情况
                //获得要注入的属性名称
                String name = prop.getName();
                String value = prop.getValue();
                String ref = prop.getRef();
                //用于注入值类型属性
                //注入属性方式2；使用BeanUtils工具类完成属性注入
                if(value != null){
                    //说明有值类型的属性需要注入
                    Map<String,String[]> paramMap = new HashMap<String, String[]>();
                    paramMap.put(name,new String[]{value});
                    //调用BeanUtils方法可以将值类型的属性注入（自动完成类型转换）
                    try {
                        org.apache.commons.beanutils.BeanUtils.populate(beanObj, paramMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("请检查您的" + name + "属性！");
                    }
                }
                //用于注入bean类型属性
                if(prop.getRef() != null){
                    //根据属性名称获得注入属性对应的Set方法
                    Method setMethod = BeanUtils.getWriteMethod(beanObj,name);
                    //因为要注入其他bean到当前bean中，我们先从容器中查找当前要注入Bean是否已经创建，并放入容器
                    Object existBean = context.get(prop.getRef());

                    if(existBean == null){
                        //说明容器中还不存在我们要注入的Bean
                        //将Bean创建
                        existBean = createBean(config.get(prop.getRef()));
                        //将创建好的Bean放入容器中
                        context.put(prop.getRef(),existBean);
                    }
                    // 调用Set方法注入
                    try {
                        setMethod.invoke(beanObj,existBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("您的Bean属性" + name + "没有对应的set方法，或方法参数不正确" + className);
                    }
                }

                /*
                //根据属性名称获得注入属性对应的Set方法
                Method setMethod = BeanUtils.getWriteMethod(beanObj,name);
                //创建一个需要注入到Bean中的属性
                Object param = null;

                if(prop.getValue() != null){
                    //1.简单value属性注入
                    //获得要注入的实例值
                    String value = prop.getValue();
                    param = value;

                }
                if(prop.getRef() != null){
                    //2.复杂其他bean注入

                    //因为要注入其他bean到当前bean中，我们先从容器中查找当前要注入Bean是否已经创建，并放入容器
                    Object existBean = context.get(prop.getRef());

                    if(existBean == null){
                        //说明容器中还不存在我们要注入的Bean
                        //将Bean创建
                        existBean = createBean(config.get(prop.getRef()));
                        //将创建好的Bean放入容器中
                        context.put(prop.getRef(),existBean);
                    }
                    param = existBean;
                }
                // 调用Set方法注入
                try {
                    setMethod.invoke(beanObj,param);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("您的Bean属性" + name + "没有对应的set方法，或方法参数不正确" + className);
                }
                */
            }
        }
        return beanObj;
    }

    @Override
    //根据Bean的名称获得Bean实例
    public Object getBean(String beanId) {
        Object bean = context.get(beanId);
        return bean;
    }
}
