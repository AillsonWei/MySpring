package com.myspring.config.parse;

import com.myspring.config.Bean;
import com.myspring.config.Property;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AillsonWei on 2016/3/16.
 */
public class ConfigManager {
    //读取mySpring的配置文件 => 并返回读取结果
    public static Map<String,Bean> getConfig(String path){
        //创建一个用于返回的Map对象
        Map<String,Bean> map = new HashMap<String, Bean>();
    //dom4j实现
        //1.创建解析器
        SAXReader reader = new SAXReader();
        //2.加载配置文件 =>document对象
        InputStream inStream = ConfigManager.class.getResourceAsStream(path);
        Document doc = null;
        try {
            doc = reader.read(inStream);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("请检查xml配置文件是否正确！");
        }
        //3.定义xpath表达式，取出所有bean元素
        String xpath = "//bean";
        //4.对bean元素进行遍历
        List<Element> list = doc.selectNodes(xpath);
        if(list != null){
            for(Element beanEle : list){
                Bean bean = new Bean();
                //将bean元素的id/class属性封装到Bean对象中
                String id = beanEle.attributeValue("id");
                String className = beanEle.attributeValue("class");

                bean.setId(id);
                bean.setClassName(className);
                //获得Bean元素下的所有的property子元素，将属性name/value/ref封装到Property对象中
                List<Element> children = beanEle.elements("property");

                if(children != null){
                    for(Element child : children){
                        Property prop = new Property();

                        String name = child.attributeValue("name");
                        String value = child.attributeValue("value");
                        String ref = child.attributeValue("ref");

                        prop.setName(name);
                        prop.setValue(value);
                        prop.setRef(ref);

                        //将Property对象封装到Bean对象中
                        bean.getProperties().add(prop);
                    }
                }
                //将Bean对象封装到Map中（用于返回map）
                map.put(id,bean);
            }
        }
        //5.返回Map结果
        return map;
    }
}
