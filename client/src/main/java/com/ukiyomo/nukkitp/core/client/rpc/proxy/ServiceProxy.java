package com.ukiyomo.nukkitp.core.client.rpc.proxy;


import com.ukiyomo.nukkitp.core.client.rpc.exception.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceProxy implements Service {

    protected String name;
    protected Object instance;
    protected Class<?> aClass;

    protected Map<String, Func> funcProxyMap = new HashMap<>();
    protected String[] funcProxyNameArray;

    public ServiceProxy(Class<?> cls) throws IllegalAccessException,
            InstantiationException, IllegalRPCClassException,
            IllegalRPCFuncException, RepeatedRPCFuncException {

        RPCService service = cls.getAnnotation(RPCService.class);
        if (service == null) {
            throw new IllegalRPCClassException("非法类型，RPC服务创建失败");
        }
        this.name = service.name();
        this.aClass = cls;

        this.init();
    }

    @Override
    public boolean init() throws IllegalAccessException,
            InstantiationException, IllegalRPCFuncException, RepeatedRPCFuncException {
        if ("".equals(this.getName())) {
            this.name = this.aClass.getSimpleName();
        }
        this.instance = this.aClass.newInstance();

        Method[] methods = this.aClass.getMethods();
        for (Method method : methods) {
            RPCFunc func = method.getAnnotation(RPCFunc.class);
            if (func == null) {
            } else {
                FuncProxy proxy = new FuncProxy(method);
                if (this.funcProxyMap.containsKey(proxy.getName())) {
                    throw new RepeatedRPCFuncException(this.getName(), proxy.getName());
                } else {
                    this.funcProxyMap.put(proxy.getName(), proxy);
                }
            }
        }
        List<String> funcNameList = new ArrayList<>();
        this.funcProxyMap.forEach((name, v) -> funcNameList.add(name));
        this.funcProxyNameArray = funcNameList.toArray(new String[0]);
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getInstance() {
        return this.instance;
    }

    @Override
    public String[] getFunc() {
        return this.funcProxyNameArray;
    }

    @Override
    public Func getFunc(String name) {
        if (!this.funcProxyMap.containsKey(name)) {
            return null;
        }
        return this.funcProxyMap.get(name);
    }

    @Override
    public boolean runFunc(String funcName, Object... args) throws Exception {
        if (!this.funcProxyMap.containsKey(funcName)) {
            throw new NonexistentRPCFuncException(this.getName(), funcName);
        }

        //System.out.println(this.getFunc(funcName).getParamTypeName().length);
        //System.out.println(args.length);

        if (this.getFunc(funcName).getParamTypeName().length != args.length) {
            throw new IncorrectPRCFuncParamException(
                    this.getName(), funcName, this.getFunc(funcName).getParamTypeName());
        }

        this.funcProxyMap.get(funcName).run(this.getInstance(), args);

        return true;
    }
}
