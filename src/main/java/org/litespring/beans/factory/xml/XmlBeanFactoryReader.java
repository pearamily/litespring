package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.factory.BeanDefinationStoreException;
import org.litespring.beans.factory.support.GenericBeanDefination;
import org.litespring.util.ClassUtils;
import org.litespring.beans.factory.support.BeanDefinationRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanFactoryReader {
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";

    BeanDefinationRegistry registry;

    public XmlBeanFactoryReader(BeanDefinationRegistry registry) {
        this.registry = registry;
    }


    public void loadBeanDefination(String configFile) {
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
                registry.registerBeanDefination(id,bd);
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

}