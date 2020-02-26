package com.ukiyomo.nukkitp.core.client.rpc.proxy;

import java.lang.reflect.Method;

public interface Func {

    public boolean init() throws Exception;

    public String getName();

    public Method getMethod();

    public Class<?>[] getParamType();

    public String[] getParamTypeName();

    public boolean run(Object instance, Object... args) throws Exception;

}
