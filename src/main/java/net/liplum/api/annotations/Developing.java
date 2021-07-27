package net.liplum.api.annotations;

import java.lang.annotation.*;

/**
 * It means this API is been developing and will probably be removed or changed in the future.
 */
@Documented
@LongSupport
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.TYPE})
public @interface Developing {
}
