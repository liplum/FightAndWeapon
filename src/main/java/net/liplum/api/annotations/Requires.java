package net.liplum.api.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Documented
@LongSupport
public @interface Requires {
    Require[] value();
}
