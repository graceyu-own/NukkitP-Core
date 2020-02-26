package com.ukiyomo.nukkitp.core.client.rpc.proxy;

public interface Service {

    public boolean init() throws Exception;

    /**
     * 获取服务名字
     */
    public String getName();

    /**
     * 获取服务实例类
     */
    public Object getInstance();

    /**
     * 获取函数（方法）名
     */
    public String[] getFunc();

    public Func getFunc(String name);

    public boolean runFunc(String funcName, Object... args) throws Exception;
}
