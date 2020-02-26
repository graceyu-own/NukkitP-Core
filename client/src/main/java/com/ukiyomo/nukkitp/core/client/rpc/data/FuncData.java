package com.ukiyomo.nukkitp.core.client.rpc.data;

import com.alibaba.fastjson.annotation.JSONField;

public class FuncData {

    public String name = "";

    public Object[] param;

    @JSONField(serialize = false)
    public String getName() {
        return this.name;
    }
}
