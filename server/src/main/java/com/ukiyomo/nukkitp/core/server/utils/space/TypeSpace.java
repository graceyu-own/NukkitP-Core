package com.ukiyomo.nukkitp.core.server.utils.space;

import java.lang.annotation.Annotation;
import java.util.HashMap;

public abstract class TypeSpace<A extends MethodSpace, T extends Annotation> extends HashMap<String, A> {

    protected String name;

    protected Class<?> aClass;
    protected Object instance;

    protected Class<T> annotationClass;

    public TypeSpace(Class<?> aClass, Class<T> annotationClass) {
        this(aClass, annotationClass, null);
    }

    public TypeSpace(Class<?> aClass, Class<T> annotationClass, String name) {
        this.name = name;
        this.aClass = aClass;
        this.annotationClass = annotationClass;

        init();
    }

    public void init() {
        if (this.getName() == null || "".equals(this.getName())) {
            this.name = this.aClass.getSimpleName();
        }
        try {
            this.instance = this.aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO: throw;
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Object getInstance() {
        return instance;
    }

    public boolean run(String MethodName, Object... args) throws Exception {
        if (!this.containsKey(MethodName)) {
            // TODO: throw ;
            return false;
        }

        this.get(MethodName).run(this.getInstance(), args);

        return true;
    }
}
