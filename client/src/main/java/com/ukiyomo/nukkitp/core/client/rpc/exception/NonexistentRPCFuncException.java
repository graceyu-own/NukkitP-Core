package com.ukiyomo.nukkitp.core.client.rpc.exception;

/**
 * 不存在的RPC函数错误
 */
public class NonexistentRPCFuncException extends RPCException {

    private static final long serialVersionUID = 1949349029025L;

    public NonexistentRPCFuncException() {
    }

    public NonexistentRPCFuncException(String message) {
        super(message);
    }

    public NonexistentRPCFuncException(String message,  Object... args) {
        super(String.format(message, args));
    }

    public NonexistentRPCFuncException(String service, String func) {
        super(String.format("RPC函数不存在错误, 函数所属服务:%s, 函数名:%s.", service, func));
    }

}
