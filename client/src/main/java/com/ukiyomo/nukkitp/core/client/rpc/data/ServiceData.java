package com.ukiyomo.nukkitp.core.client.rpc.data;

import com.alibaba.fastjson.annotation.JSONField;

public class ServiceData {

    public String service = "";
    public FuncData func;

    @JSONField(serialize = false)
    public String getName() {
        return this.service;
    }
}
