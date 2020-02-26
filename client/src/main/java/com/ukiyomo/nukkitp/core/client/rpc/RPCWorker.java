package com.ukiyomo.nukkitp.core.client.rpc;

import com.alibaba.fastjson.JSON;
import com.ukiyomo.nukkitp.core.client.rpc.data.FuncData;
import com.ukiyomo.nukkitp.core.client.rpc.data.ServiceData;
import com.ukiyomo.nukkitp.core.client.rpc.exception.RPCException;
import com.ukiyomo.nukkitp.core.client.rpc.proxy.RPCService;
import com.ukiyomo.nukkitp.core.client.rpc.proxy.Service;
import com.ukiyomo.nukkitp.core.client.rpc.proxy.ServiceProxy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RPCWorker {

    public Map<String, Service> serviceMap = new HashMap<>();

    public static ServiceData toRPC(String serviceName, String funcName, Object... args) {
        ServiceData serviceData = new ServiceData();
        serviceData.service = serviceName;
        serviceData.func = new FuncData();
        serviceData.func.name = funcName;

        serviceData.func.param = args;

        return serviceData;
    }

    public static String toRPCString(String serviceName, String funcName, Object... args) {
        return toJSONServiceData(toRPC(serviceName, funcName, args));
    }

    public static String toJSONServiceData(ServiceData serviceData) {
        return JSON.toJSONString(serviceData, false);
    }

    public static ServiceData parseServiceData(String json) {
        return JSON.parseObject(json, ServiceData.class);
    }

    public static ServiceData parseServiceData(byte[] bytes) {
        return JSON.parseObject(bytes, ServiceData.class);
    }



    public void searchAll()
            throws ReflectiveOperationException, RuntimeException, RPCException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> cla = classLoader.getClass();
        while (cla != ClassLoader.class) {
            cla = cla.getSuperclass();
        }
        Field field = cla.getDeclaredField("classes");
        field.setAccessible(true);
        Vector<?> v = (Vector<?>) field.get(classLoader);
        for (int i = 0; i < v.size(); i++) {
            Class<?> aClass = (Class<?>) v.get(i);

            this.search(aClass);
        }
    }

    /**
     * 扫描服务
     */
    public boolean search(Class<?> cls)
            throws InstantiationException, RPCException, IllegalAccessException {

        RPCService ser = cls.getAnnotation(RPCService.class);
        if (ser == null) {
            return false;
        }

        //System.out.println("扫描到RPCService类 " + cls.getName());

        ServiceProxy service = new ServiceProxy(cls);
        this.serviceMap.put(service.getName(), service);

        return true;
    }

    public void runFunc(String serviceName, String funcName, Object... args) throws Exception {

        if (!this.serviceMap.containsKey(serviceName)) {
            return;
        }

        this.serviceMap.get(serviceName).runFunc(funcName, args);
    }

    public void runFuncByJSON(String json) throws Exception {

        ServiceData data = RPCWorker.parseServiceData(json);

        //System.out.println(data.func.param.size());

        this.runFunc(data.getName(), data.func.getName(), data.func.param);

    }
}
