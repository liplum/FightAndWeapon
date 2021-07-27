package net.liplum.api.annotations;

import java.lang.annotation.*;

/**
 * It means this API is stable and never changed or removed in the future.<br/>
 * If it annotate a method, it stands for only not changing return value and parameter.<br/>
 * If it annotate a class, it stands for only being able to add more superclass.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD,ElementType.TYPE})
public @interface LongSupport {
}
