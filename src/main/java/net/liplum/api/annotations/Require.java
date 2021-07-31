package net.liplum.api.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Repeatable(Requires.class)
@LongSupport
public @interface Require {
    String func();

    String is();
}
