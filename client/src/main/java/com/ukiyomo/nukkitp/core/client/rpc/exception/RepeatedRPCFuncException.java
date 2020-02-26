package com.ukiyomo.nukkitp.core.client.rpc.exception;

/**
 * 重复RPC函数错误
 * 可能原因为@RPCFunc标签参数重名
 */
public class RepeatedRPCFuncException extends RPCException {

    private static final long serialVersionUID = 1949083483280L;

    public RepeatedRPCFuncException() {
    }

    public RepeatedRPCFuncException(String message) {
        super(message);
    }

    public RepeatedRPCFuncException(String message,  Object... args) {
        super(String.format(message, args));
    }

    public RepeatedRPCFuncException(String service, String func) {
        super(String.format("重复RPC函数错误, 重复函数所属服务:%s, 重复函数名:%s.", service, func));
    }

}
