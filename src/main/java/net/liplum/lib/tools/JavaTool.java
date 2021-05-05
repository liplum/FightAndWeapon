package net.liplum.lib.tools;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class JavaTool {
    public static <T> void notNullThenDo(@Nullable T a, Consumer<T> delegate) {
        if (a != null) {
            delegate.accept(a);
        }
    }
}
