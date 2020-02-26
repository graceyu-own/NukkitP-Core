package com.ukiyomo.nukkitp.core.client.rpc.exception;

import java.util.Arrays;

/**
 * 对RPC函数传参错误
 */
public class IncorrectPRCFuncParamException extends RPCException {

    private static final long serialVersionUID = 1949023402481L;

    public IncorrectPRCFuncParamException() {
    }

    public IncorrectPRCFuncParamException(String message) {
        super(message);
    }

    public IncorrectPRCFuncParamException(String message,  Object... args) {
        super(String.format(message, args));
    }

    public IncorrectPRCFuncParamException(String service, String func) {
        super(String.format("RPC函数传入参数错误, 函数所属服务:%s, 函数名:%s.", service, func));
    }

    public IncorrectPRCFuncParamException(String service, String func, String... args) {
        super(String.format("RPC函数传入参数错误, 函数所属服务:%s, 函数名:%s, 参数类型:%s.",
                service, func, Arrays.toString(args)));
    }
}
