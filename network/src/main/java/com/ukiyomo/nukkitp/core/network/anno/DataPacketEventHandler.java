package com.ukiyomo.nukkitp.core.network.anno;


import com.ukiyomo.nukkitp.core.network.protocol.DataPacket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPacketEventHandler {

    Class<? extends DataPacket> value();

}
