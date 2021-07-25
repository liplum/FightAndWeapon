package net.liplum.api.annotations;

import java.lang.annotation.*;

/**
 * It means this API is stable and never changed(only return value and parameter) or removed in the future.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.TYPE})
public @interface LongSupport {
}
