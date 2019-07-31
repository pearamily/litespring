package org.litespring.beans.factory;

        import org.litespring.beans.BeanDefination;

public interface BeanFactory {

    Object getBean(String beanId);
}
