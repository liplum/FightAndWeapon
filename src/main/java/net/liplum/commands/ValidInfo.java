package net.liplum.commands;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ValidInfo {
    @Nonnull
    public final static ValidInfo FalseAndNull = new ValidInfo(false, null);

    private final boolean isValid;
    @Nullable
    private final Object transformedArg;

    public ValidInfo(boolean isValid, @Nullable Object transformedArg) {
        this.isValid = isValid;
        this.transformedArg = transformedArg;
    }

    @Nonnull
    public static ValidInfo invalidWhenNull(@Nullable Object transformedArg) {
        return new ValidInfo(transformedArg != null, transformedArg);
    }

    @Nonnull
    public static ValidInfo nullTransformedArg(boolean isValid) {
        return new ValidInfo(isValid, null);
    }

    public boolean isValid() {
        return isValid;
    }

    @Nullable
    public Object transformedArg() {
        return transformedArg;
    }
}
