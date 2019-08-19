package org.litespring.aop.config;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.BeanFactoryAware;
import org.litespring.util.StringUtils;

public class AspectInstanceFactory implements BeanFactoryAware {

    private String aspectBeanName;
    private BeanFactory beanFactory;

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    public void setBeanFactory(BeanFactory factory) {

        this.beanFactory = factory;
        if (!StringUtils.hasText(this.aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }
    public Object getAspectInstance() throws Exception {

        return this.beanFactory.getBean(this.aspectBeanName);
    }
}
