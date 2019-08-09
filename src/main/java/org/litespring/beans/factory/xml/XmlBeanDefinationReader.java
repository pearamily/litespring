package org.litespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinationStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypeStringValue;
import org.litespring.beans.factory.support.BeanDefinationRegistry;
import org.litespring.beans.factory.support.GenericBeanDefination;
import org.litespring.core.io.Resource;
import org.litespring.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinationReader {
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String PROPERTY_ATTRIBUTE = "property";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";


    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String TYPE_ATTRIBUTE = "type";


    protected final Log logger = LogFactory.getLog(getClass());

    BeanDefinationRegistry registry;

    public XmlBeanDefinationReader(BeanDefinationRegistry registry) {
        this.registry = registry;
    }


    public void loadBeanDefination(Resource resource) {
        InputStream is = null;
        try {

            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator<Element> iterator = root.elementIterator();//root element  -->beans
            while (iterator.hasNext()) {
                Element ele = (Element) iterator.next();//bean
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefination bd = new GenericBeanDefination(id, beanClassName);
                if (ele.attributeValue(SCOPE_ATTRIBUTE) != null) {
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                parseConstructorArgElements(ele, bd);
                parsePropertyElement(ele, bd);
                this.registry.registerBeanDefination(id, bd);
            }
        } catch (Exception e) {
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

    private void parseConstructorArgElements(Element beanEle, BeanDefination bd) {
        Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iter.hasNext()) {
            Element ele = (Element) iter.next();
            parseConstructorArgElement(ele, bd);
        }
    }

    private void parseConstructorArgElement(Element ele, BeanDefination bd) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);

        Object value = paresPropertyValue(ele, bd, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);//store  runtimebeanreference of stringvalue

        if (StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);

        }
        if (StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);

        }
        bd.getConstructorArgument().addArgumentValue(valueHolder);


    }


    public Object paresPropertyValue(Element ele, BeanDefination bd, String propertyName) {
        String eleName = (propertyName != null) ? "<property> element for property '" + propertyName + "'" : "<constructor-arg> element";

        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (ele.attribute(VALUE_ATTRIBUTE) != null);


        // at this place  we only support two property : value or  ref
        if (hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                logger.error(eleName + " constrains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        } else if (hasValueAttribute) {
            TypeStringValue valueHolder = new TypeStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        } else {
            throw new RuntimeException(eleName + " must specify as a ref or value");
        }

    }

    public void parsePropertyElement(Element beanELem, BeanDefination bd) {
        Iterator iter = beanELem.elementIterator();
        while (iter.hasNext()) {
            Element propElem = (Element) iter.next();//property
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object val = paresPropertyValue(propElem, bd, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);
            bd.getPropertyValues().add(pv);
        }

    }


}
