package com.ukiyomo.nukkitp.core.network.exception;

public class DataPacketException extends Exception {

    public DataPacketException(String message) {
        super(message);
    }

    public DataPacketException(String message, Throwable cause) {
        super(message, cause);
    }
}
