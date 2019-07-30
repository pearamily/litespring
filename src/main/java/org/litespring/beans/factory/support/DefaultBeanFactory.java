package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.BeanCreatationException;
import org.litespring.beans.factory.BeanDefinationStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";

    private final Map<String, BeanDefination> beanDefinationMap = new ConcurrentHashMap<String, BeanDefination>();


    public DefaultBeanFactory(String configFile) {

        loadBeanDefination(configFile);
    }

    private void loadBeanDefination(String configFile) {
        InputStream is = null;
        try {
            ClassLoader c1 = ClassUtils.getDefaultClassLoader();
            is = c1.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element ele = (Element) iterator.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefination bd = new GenericBeanDefination(id, beanClassName);
                this.beanDefinationMap.put(id, bd);
            }
        } catch (DocumentException e) {
            throw new BeanDefinationStoreException("IOException is created  parseing XML document", e);

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public BeanDefination getBeanDefination(String beanId) {
        return this.beanDefinationMap.get(beanId);
    }

    public Object getBean(String beanId)  {
        BeanDefination bd = this.getBeanDefination(beanId);
        if (bd == null) {
            throw new BeanCreatationException("BeanDefination dees not exist!");
        }
        ClassLoader c1 = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();

        try {
            Class<?> clz = c1.loadClass(beanClassName);//at this place,we default think there is a default constructor of a type
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreatationException("craete bean for"+beanClassName+" failed",e);
        }

    }
}
