package com.ukiyomo.nukkitp.core.client.rpc.proxy;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RPCService {
    String name() default "";
}
