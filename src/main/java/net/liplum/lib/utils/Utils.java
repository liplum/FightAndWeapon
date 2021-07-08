package net.liplum.lib.utils;

import net.liplum.attributes.DataType;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

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

    @Nonnull
    public static <T> int[] toIntArray(@Nonnull Collection<T> list, @Nonnull ToIntFunction<T> mapping) {
        int len = list.size();
        if (len == 0) {
            return new int[0];
        }
        int[] res = new int[len];

        Iterator<T> it = list.iterator();
        int index = 0;
        while (it.hasNext()) {
            res[index] = mapping.applyAsInt(it.next());
            index++;
        }
        return res;
    }

    public static boolean isShiftDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static boolean isCtrlDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
    }

    public static boolean isAltDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU);
    }

    public static Number intAdd(Number summand, Number addend) {
        return summand.intValue() + addend.intValue();
    }

    public static Number floatAdd(Number summand, Number addend) {
        return summand.floatValue() + addend.floatValue();
    }

    public static Number doubleAdd(Number summand, Number addend) {
        return summand.doubleValue() + addend.doubleValue();
    }

    public static boolean notNull(Boolean value) {
        if (value == null) {
            return false;
        }
        return value;
    }

    public static int notNull(Integer value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static float notNull(Float value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static double notNull(Double value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static char notNull(Character value) {
        if (value == null) {
            return '\0';
        }
        return value;
    }

    public static long notNull(Long value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static short notNull(Short value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static byte notNull(Byte value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static Number add(DataType dataType, Number summand, Number addend) {
        if (dataType == DataType.Int) {
            return intAdd(summand, addend);
        }
        return floatAdd(summand, addend);
    }
}
