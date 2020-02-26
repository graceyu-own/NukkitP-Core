package com.ukiyomo.nukkitp.core.server.command.space;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandType {
    String name() default "";
}
