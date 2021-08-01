package net.liplum.api.annotations;

import java.lang.annotation.*;

/**
 * It means this thing can only be called or used when the mod initializes(pre,while,post).
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
public @interface OnlyWhenInitialization {
}
