package com.ukiyomo.nukkitp.core.server.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Utils {

    public static void main(String[] s) {
        //System.out.println(Arrays.toString(shearArray(new String[]{"1", "2", "3", "4", "5", "6"}, 0, 5)));
    }

    public static <A extends Annotation> Map<Class<?>, A> searchTypeAll(Class<A> annotation)
            throws NoSuchFieldException, IllegalAccessException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> cla = classLoader.getClass();
        while (cla != ClassLoader.class) {
            cla = cla.getSuperclass();
        }
        Field field = cla.getDeclaredField("classes");
        field.setAccessible(true);
        Vector<?> v = (Vector<?>) field.get(classLoader);

        Map<Class<?>, A> map = new HashMap<>();

        for (Object o : v) {
            Class<?> clazz = (Class<?>) o;

            A ann = clazz.getAnnotation(annotation);
            if (ann == null) {
                continue;
            }

            map.put(clazz, ann);
        }

        return map;
    }

    public static <A extends Annotation> Map<Method, A> searchFuncAll(Class<?> clazz, Class<A> annotation) {
        Method[] methods = clazz.getMethods();

        Map<Method, A> map = new HashMap<>();

        for (Method method : methods) {
            A ann = method.getAnnotation(annotation);
            if (ann == null) {
                continue;
            }
            map.put(method, ann);
        }
        return map;
    }

    public static String[] shearArray(String[] oldArray, int start, int end) {
        String[] newArray = new String[end - start];
        System.arraycopy(oldArray, start, newArray, 0, newArray.length);
        /*
        for (int i = start, j = 0; i < end; i++, j++) {
            newArray[j] = oldArray[i];
        }
         */
        return newArray;
    }
}
