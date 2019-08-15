package org.litespring.util;


import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ReflectUtils {
    private static final String CGLIB_RENAMED_METHOD_PREFIX = "CGLIB$";

    /**
     * Pattern for detecting CGLIB-renamed methods.
     * @see #isCglibRenamedMethod
     */
    private static final Pattern CGLIB_RENAMED_METHOD_PATTERN = Pattern.compile("(.+)\\$\\d+");


    private static final Map<Class<?>, Method[]> declaredMethodCache = new ConcurrentHashMap<Class<?>, Method[]>(256);

    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    private static Field findField(Class<?> clazz, String name, Class<?> type) {
        Assert.notNull(clazz, "Class must not be null");
        Class<?> searchType = clazz;

        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName()) && (type == null) || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;

    }

    public static void setField(Field field, Object target, Object value) {
        try {
            field.set(target, value);
        } catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException("Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }

    }


    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        }
        catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

    public static Method findMethod(Class<?> clazz, String name) {

        return findMethod(clazz, name, new Class<?>[0]);
    }

    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");


        Class<?> searchType = clazz;

        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType));
            for (Method method : methods) {
                if (name.equals(method.getName()) &&
                        (paramTypes==null&&Arrays.equals(paramTypes,method.getParameterTypes()))
                ) {
                    return method;

                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;

    }

    public static Method[] getDeclaredMethods(Class<?> clazz) {
        Method[] result = declaredMethodCache.get(clazz);
        if (result == null) {

            result
                    = clazz.getDeclaredMethods();
            declaredMethodCache.put(clazz, result);
        }
        return result;
    }

    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception ex
        ) {

            handleReflectionException(ex);
        }
        throw new IllegalStateException("should never get here");
    }

    private static void handleReflectionException(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Method not found: " + ex.getMessage());
        }
        if (ex instanceof IllegalAccessException) {
            throw new IllegalStateException("Could not access method: " + ex.getMessage());
        }
        if (ex instanceof InvocationTargetException) {
            handleInvocationTargetException((InvocationTargetException) ex);
        }
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        throw new UndeclaredThrowableException(ex);

    }
    public static void handleInvocationTargetException(InvocationTargetException ex) {
        rethrowRuntimeException(ex.getTargetException());
    }
    public static void rethrowRuntimeException(Throwable ex) {
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        }
        if (ex instanceof Error) {
            throw (Error) ex;
        }
        throw new UndeclaredThrowableException(ex);
    }

    public static void makeAccessible(Field field) { //set a thing accessible
        if ((!Modifier.isPublic(field.getModifiers())||!Modifier.isPublic(field.getDeclaringClass().getModifiers())||Modifier.isFinal(field.getModifiers())&&!field.isAccessible())
        ) {
            field.setAccessible(true);
        }
    }



}
