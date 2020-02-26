package com.ukiyomo.nukkitp.core.network;


import com.ukiyomo.nukkitp.core.network.anno.DataPacketEventHandler;
import com.ukiyomo.nukkitp.core.network.event.RegisteredListener;
import com.ukiyomo.nukkitp.core.network.event.executors.MethodDataPacketEventExecutor;
import com.ukiyomo.nukkitp.core.network.event.listener.DataPacketEventListener;
import com.ukiyomo.nukkitp.core.network.exception.DataPacketException;
import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;
import com.ukiyomo.nukkitp.core.network.protocol.TextPacket;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPacketManager implements Cloneable {

    // 已注册的数据包 -> Map<数据包, 数据包Class>
    private Map<Short, Class<? extends DataPacket>> dataPackets = new HashMap<>();

    // 已注册的数据包监听器 -> Map<监听的数据包ID, List<方法监听器列表>>
    private Map<Short, List<RegisteredListener>> listeners = new HashMap<>();

    public DataPacketManager() {
        this.init();
    }

    private void init() {
        this.dataPackets.put(TextPacket.DATA_PACKET_ID, TextPacket.class);
    }

    public Class<? extends DataPacket> getDataPacket(short pid) {
        return this.dataPackets.get(pid);
    }

    public List<RegisteredListener> getRegisteredListener(short pid) {
        return this.listeners.get(pid);
    }

    /**
     * 注册数据包, 每个端的Builder有自己的一个DataPacketManager, 必要的情况下，需要统一注册
     *
     * @param clazz 数据包Class
     * @throws DataPacketException except
     */
    public void registerDataPacket(Class<? extends DataPacket> clazz) throws DataPacketException {

        try {
            short pid = clazz.getDeclaredField("DATA_PACKET_ID").getShort(null);

            if(!this.dataPackets.containsKey(pid)) {
                this.dataPackets.put(pid, clazz);
            } else {
                throw new DataPacketException("this dataPacket already registered.");
            }

        } catch (NoSuchFieldException e) {
            throw new DataPacketException("Invalid dataPacket.", e.getCause());
        } catch (Exception e) {
            //throw new DataPacketException("Unknown register exception.", e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * 注册事件监听器, 每个端的Builder有自己的一个DataPacketManager, 必要的情况下，需要统一注册
     *
     * @param listener 监听器实例
     */
    public void registerDataPacketEventListener(DataPacketEventListener listener) {

        this.getValidDataPacketEvent(listener.getClass()).forEach((pid, method) -> {
            if(this.listeners.containsKey(pid)) {
                this.listeners.get(pid).add(new RegisteredListener(listener, new MethodDataPacketEventExecutor(method)));
            } else {
                List<RegisteredListener> list = new ArrayList<>();
                list.add(new RegisteredListener(listener, new MethodDataPacketEventExecutor(method)));
                this.listeners.put(pid, list);
            }
        });
    }

    /**
     * 获得监听器内有效的方法
     * @param clazz 监听器Class
     * @return Map<监听的数据包ID, 方法>
     */
    private Map<Short, Method> getValidDataPacketEvent(Class<? extends DataPacketEventListener> clazz) {

        Map<Short, Method> methods = new HashMap<>();

        try {
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                DataPacketEventHandler dataPacketEventHandler = declaredMethod.getDeclaredAnnotation(DataPacketEventHandler.class);
                if(null != dataPacketEventHandler) {
                    short pid = dataPacketEventHandler.value().getDeclaredField("DATA_PACKET_ID").getShort(null);
                    if(this.dataPackets.containsKey(pid)) {
                        methods.put(pid, declaredMethod);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return methods;
    }

    public Object clone() throws CloneNotSupportedException {

        DataPacketManager o = (DataPacketManager) super.clone();

        o.dataPackets = this.dataPackets;
        o.listeners = this.listeners;

        return o;
    }

}
