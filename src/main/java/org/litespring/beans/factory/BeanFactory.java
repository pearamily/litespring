package org.litespring.beans.factory;

        import org.litespring.beans.BeanDefination;

public interface BeanFactory {
    BeanDefination getBeanDefination(String beanId);

    Object getBean(String beanId);
}
