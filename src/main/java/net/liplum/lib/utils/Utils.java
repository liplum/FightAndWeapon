package net.liplum.lib.utils;

import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Consumer;

public class Utils {
    private static Random RANDOM = new Random();

    public static void refreshRandom() {
        RANDOM = new Random();
    }

    public static Random getRandom() {
        return RANDOM;
    }

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

    public static boolean isShiftDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
}
