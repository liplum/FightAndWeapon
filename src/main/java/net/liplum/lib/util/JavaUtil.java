package net.liplum.lib.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class JavaUtil {
    public static <T> void notNullThenDo(@Nullable T a, Consumer<T> delegate) {
        if (a != null) {
            delegate.accept(a);
        }
    }

    /**
     * Gets the first object which is instance of this class. if there were no consistent one, return null.
     *
     * @param clz
     * @param objs
     * @param <T>
     * @return the first object which is instance of this class. if there were no consistent one, return null.
     */
    @Nullable
    public static <T> T getObjectWhichInstanceOf(@Nonnull Class<T> clz, Object... objs) {
        for (Object obj : objs) {
            if (clz.isInstance(obj)) {
                return (T) obj;
            }
        }
        return null;
    }
}
