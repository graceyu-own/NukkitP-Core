package com.ukiyomo.nukkitp.core.client.rpc.proxy;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RPCFunc {
    String name() default "";
}
