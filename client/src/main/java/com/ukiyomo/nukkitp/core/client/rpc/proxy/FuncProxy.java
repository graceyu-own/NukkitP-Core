package com.ukiyomo.nukkitp.core.client.rpc.proxy;

import com.ukiyomo.nukkitp.core.client.rpc.exception.IllegalRPCFuncException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FuncProxy implements Func {

    protected String name;
    protected Method method;

    protected Class<?>[] paramTypeArray;
    protected Class<?> returnType;
    protected String[] paramTypeNameArray;

    public FuncProxy(Method method) throws IllegalRPCFuncException {
        RPCFunc func = method.getAnnotation(RPCFunc.class);
        if (func == null) {
            throw new IllegalRPCFuncException("非法类型，RPC函数创建失败");
        }
        this.method = method;
        this.name = func.name();

        this.init();
    }


    @Override
    public boolean init() {

        if ("".equals(this.name)) {
            this.name = this.method.getName();
        }

        /* 这里一定要 JDK 8 以上 */
        this.paramTypeArray = this.getMethod().getParameterTypes();
        this.returnType = this.getMethod().getReturnType();

        this.paramTypeNameArray = new String[this.paramTypeArray.length];
        for (int i = 0; i < this.paramTypeArray.length; i++) {
            this.paramTypeNameArray[i] = this.paramTypeArray[i].getName();
        }

        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Class<?>[] getParamType() {
        return this.paramTypeArray;
    }

    @Override
    public String[] getParamTypeName() {
        return this.paramTypeNameArray;
    }

    @Override
    public boolean run(Object instance, Object... args)
            throws InvocationTargetException, IllegalAccessException {

        // 禁用安全检查
        this.getMethod().setAccessible(true);
        //this.getMethod().invoke(instance, param);
        this.getMethod().invoke(instance, args);

        return true;
    }


}
