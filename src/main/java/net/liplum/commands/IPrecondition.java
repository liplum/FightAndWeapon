package net.liplum.commands;

import javax.annotation.Nonnull;

public interface IPrecondition {
    int getIndex();

    void onEntered(int index);

    ValidInfo isValid(@Nonnull String parameter);
}
