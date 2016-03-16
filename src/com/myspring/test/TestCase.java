package com.myspring.test;

import com.myspring.bean.Computer;
import com.myspring.bean.Student;
import com.myspring.config.Bean;
import com.myspring.config.parse.ConfigManager;
import com.myspring.main.BeanFactory;
import com.myspring.main.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.util.Map;

/**
 * Created by AillsonWei on 2016/3/16.
 */
public class TestCase {
    //1.测试读取配置文件的ConfigManager类是否正确
    @Test
    public void test01(){
        Map<String,Bean> config = ConfigManager.getConfig("/mySpringContext.xml");
        System.out.println(config);
    }

    //2.测试ClassPathXmlApplicationContext方法是否正确
    @Test
    public void test02(){
        BeanFactory bf = new ClassPathXmlApplicationContext("/mySpringContext.xml");
        //值类型属性注入
        Computer computer = (Computer) bf.getBean("computer");
        System.out.println(computer);//Computer{name='联想y480', mainboard='华硕', cpu='Intel'}
        //引用类型属性注入
        Student student = (Student) bf.getBean("student");
        System.out.println(student);//Student{name='AillsonWei', computer=Computer{name='联想y480', mainboard='华硕', cpu='Intel'}}
    }
}
