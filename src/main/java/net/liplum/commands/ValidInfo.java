package net.liplum.commands;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ValidInfo {
    @NotNull
    public final static ValidInfo FalseAndNull = new ValidInfo(false, null);

    private final boolean isValid;
    @Nullable
    private final Object transformedArg;

    public ValidInfo(boolean isValid, @Nullable Object transformedArg) {
        this.isValid = isValid;
        this.transformedArg = transformedArg;
    }

    @NotNull
    public static ValidInfo invalidWhenNull(@Nullable Object transformedArg) {
        return new ValidInfo(transformedArg != null, transformedArg);
    }

    @NotNull
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
