package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefination;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.BeanCreatationException;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanfactory extends DefaultSingletonBeanRegistery
        implements ConfigurableBeanFactory, BeanDefinationRegistry {

    ClassLoader beanClassLoader;


    private final Map<String, BeanDefination> beanDefinationMap = new ConcurrentHashMap<String, BeanDefination>();


    public DefaultBeanfactory() {

    }

    public BeanDefination getBeanDefination(String beanId) {
        return this.beanDefinationMap.get(beanId);
    }

    public void registerBeanDefination(String id, BeanDefination bd) {
        this.beanDefinationMap.put(id, bd);

    }

    public Object getBean(String beanId) {
        BeanDefination bd = this.getBeanDefination(beanId);
        if (bd == null) {
            throw new BeanCreatationException("BeanDefination dees not exist!");
        }
        if (bd.isSingleton()) {
            Object bean = this.getSingleton(beanId);
            if (bean == null) {
                bean = createBean(bd);
                this.registerSingleton(beanId, bean);
            }
            return bean;
        }
        return createBean(bd);

    }

    private Object createBean(BeanDefination bd) {
        Object bean = instantiateBean(bd);
        //get the root bean(petStoreService)

        populateBean(bd, bean);
        //settter properties object(itemDao or accoutDao) into petStoreService object
        return bean;

    }

    private void populateBean(BeanDefination bd, Object bean) {

        List<PropertyValue> pvs = bd.getPropertyValues();//get all pvs

        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinationValueResolver valueResolver = new BeanDefinationValueResolver(this);
        //parse object
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue pv :
                    pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                //image that accoutDao has been gotten
                //how to invoke the set method to inject accoutDao to petStore?

                //use java Bean's Function to do that
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                //this type method can get a class object's info such as method or field
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                //this refers to petStoreService's two field object ,one is accoutDao next is itemDao
                for (PropertyDescriptor pd :
                        pds) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        //impl the converter and add it in petStoreService Object
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        //this write method refers to his set method and set the resolvedValue(accoutDao or itemDao) into bean(petStore) using refect way indeed
                        break;
                    }
                }

            }
        } catch (Exception ex) {
            new BeanCreatationException("Failed to  obtain BeanInfo for class[" + bd.getBeanClassName() + " ] ", ex);

        }
    }

    private Object instantiateBean(BeanDefination bd) {
        if (bd.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
            ClassLoader c1 = this.getClassLoader();
            String beanClassName = bd.getBeanClassName();

            try {
                Class<?> clz = c1.loadClass(beanClassName);
                //at this place,we default think there is a default constructor of a type
                return clz.newInstance();
            } catch (Exception e) {
                throw new BeanCreatationException("craete bean for" + beanClassName + " failed", e);
            }
        }
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;

    }

    public ClassLoader getClassLoader() {
        return (this.beanClassLoader != null) ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();

        for (BeanDefination bd :
                this.beanDefinationMap.values()) {
            resolveBeanClass(bd);
            Class<?> beanClass  = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(bd.getId());
            }

        }
        return null;
    }

    public void resolveBeanClass(BeanDefination bd) {

        if (bd.hasBeanClasss()) {
            return;
        } else {

            try {
                bd.resolveBeanClass(this.getClassLoader());
            } catch (Exception e) {
                throw new RuntimeException("can't load class:" + bd.getBeanClassName());
            }
        }

    }
}
