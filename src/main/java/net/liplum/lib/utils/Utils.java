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
import java.util.stream.Stream;

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

    public static String notNull(@Nullable String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    public static boolean notNull(@Nullable Boolean value) {
        if (value == null) {
            return false;
        }
        return value;
    }

    public static int notNull(@Nullable Integer value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static float notNull(@Nullable Float value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static double notNull(@Nullable Double value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static char notNull(@Nullable Character value) {
        if (value == null) {
            return '\0';
        }
        return value;
    }

    public static long notNull(@Nullable Long value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static short notNull(@Nullable Short value) {
        if (value == null) {
            return 0;
        }
        return value;
    }

    public static byte notNull(@Nullable Byte value) {
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

    @SafeVarargs
    @Nonnull
    public static <T> Stream<T> mergeStream(@Nonnull Stream<T>... streams) {
        if (streams.length == 0) {
            throw new IllegalArgumentException();
        }
        if (streams.length == 1) {
            return streams[0];
        }
        Stream<T> res = streams[0];
        for (int i = 1; i < streams.length; i++) {
            res = Stream.concat(res, streams[i]);
        }
        return res;
    }

    @SafeVarargs
    @Nonnull
    public static <T> Stream<T> mergeStream(@Nonnull Collection<T>... collections) {
        if (collections.length == 0) {
            throw new IllegalArgumentException();
        }
        if (collections.length == 1) {
            return collections[0].stream();
        }
        Stream<T> res = collections[0].stream();
        for (int i = 1; i < collections.length; i++) {
            res = Stream.concat(res, collections[i].stream());
        }
        return res;
    }
}
