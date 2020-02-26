package com.ukiyomo.nukkitp.core.server.command.space;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMethod {

    String value() default "";

    // 默认0，为-1时表示无限接受参数
    int length() default 0;

    String root() default "";
}
