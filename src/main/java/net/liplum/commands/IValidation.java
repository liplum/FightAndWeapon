package net.liplum.commands;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IValidation {
    ValidInfo isValid(@NotNull String parameter);
}
