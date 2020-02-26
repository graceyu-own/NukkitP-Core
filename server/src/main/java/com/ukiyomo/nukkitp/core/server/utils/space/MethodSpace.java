package com.ukiyomo.nukkitp.core.server.utils.space;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class MethodSpace {

    protected String name;
    protected Method method;

    protected Map<String, Class<?>> paramTypeArray;
    protected Class<?> returnType;

    public MethodSpace(Method method) {
        this(method, null);
    }

    public MethodSpace(Method method, String name) {
        this.name = name;
        this.method = method;
        init();
    }

    public String getName() {
        return name;
    }

    public Method getMethod() {
        return method;
    }

    public void init() {
        if (this.name == null || "".equals(this.name)) {
            this.name = this.method.getName();
        }

        // 禁用安全检查
        this.getMethod().setAccessible(true);

        /* 这里一定要 JDK 8 以上 */
        Class<?>[] paramClassArray = this.getMethod().getParameterTypes();
        this.returnType = this.getMethod().getReturnType();

        this.paramTypeArray = new HashMap<>();

        for (Class<?> aClass : paramClassArray) {
            this.paramTypeArray.put(aClass.getName(), aClass);
        }
    }

    public Object run(Object instance, Object... args)
            throws InvocationTargetException, IllegalAccessException {

        return this.getMethod().invoke(instance, args);
    }
}
