package net.liplum.commands;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface IsValid {
    ValidInfo isValid(@Nonnull String parameter);
}
