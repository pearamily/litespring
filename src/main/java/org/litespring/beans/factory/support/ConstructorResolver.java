package org.litespring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefination;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.BeanCreatationException;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorResolver {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private final ConfigurableBeanFactory beanFactory;


    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    public Object autowireConstructor(final BeanDefination bd) {
        Constructor<?> constructorToUse = null;//find a constructor can be used

        Object[] argsToUse = null;

        Class<?> beanClass = null;

        try {
            beanClass = this.beanFactory.getClassLoader().loadClass(bd.getBeanClassName());
            //this method  is not high-effi
            //TODO a good way is to cache the class ,while now  we load every times
        } catch (ClassNotFoundException e) {
            throw new BeanCreatationException(bd.getId()+" instantiation of bean failed ,can't resolve class", e);
        }


        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinationValueResolver valueResolver = new BeanDefinationValueResolver(this.beanFactory);


        ConstructorArgument cargs = bd.getConstructorArgument();

        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();

            if (parameterTypes.length != cargs.getArgumentCount()) {
                continue;

            }
            argsToUse = new Object[parameterTypes.length];

            boolean result = this.valueMatchTypes(parameterTypes, cargs.getArgumentValues(), argsToUse, valueResolver, typeConverter
            );// now we  will check weither bd generation type can match real object type..
            if (result) {
                constructorToUse = candidates[i];
                break;

            }

        }

        if (constructorToUse == null) {
            throw new BeanCreatationException(bd.getId(), "can't find apporiate constructor");
        }
        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreatationException( "can't find create instance using ", e,bd.getId());
        }

    }

    private boolean valueMatchTypes(Class<?>[] parameterTypes,
                                    List<ConstructorArgument.ValueHolder> valueHolders,
                                    Object[] argsToUse, BeanDefinationValueResolver valueResolver,
                                    SimpleTypeConverter typeConverter) {
        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            //get value of args maybe  it can be  runtimeference or typestringvalue

            Object originalValue = valueHolder.getValue();


            try {
                //get real value
                Object resolveValue
                        = valueResolver.resolveValueIfNecessary(originalValue);
                //if value type is int while value is Stirng type ,example "3" need to be converted
                //if converting failed ,then throw a exception show this errror
                Object convertValue = typeConverter.convertIfNecessary(resolveValue, parameterTypes[i]);
                //convertion success ,then record it
                argsToUse[i] = convertValue;
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }


}
