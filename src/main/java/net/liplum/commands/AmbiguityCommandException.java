package net.liplum.commands;

import org.jetbrains.annotations.NotNull;

public class AmbiguityCommandException extends RuntimeException {
    public AmbiguityCommandException(@NotNull String commandName, int allMatchedTasks) {
        super(commandName + " has " + allMatchedTasks + " matches");
    }
}
