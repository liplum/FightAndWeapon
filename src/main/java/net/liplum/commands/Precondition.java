package net.liplum.commands;

import javax.annotation.Nonnull;

public class Precondition implements IPrecondition {
    private int index;
    @Nonnull
    private final IsValid isValid;

    public Precondition() {
        this.isValid = str -> ValidInfo.AlwaysFalseAndNull;
    }

    public Precondition(@Nonnull IsValid isValid) {
        this.isValid = isValid;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void onEntered(int index) {
        this.index = index;
    }

    @Override
    public ValidInfo isValid(@Nonnull String parameter) {
        return  isValid.isValid(parameter);
    }
}
