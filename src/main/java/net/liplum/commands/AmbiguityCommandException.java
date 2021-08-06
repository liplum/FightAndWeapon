package net.liplum.commands;

import javax.annotation.Nonnull;

public class AmbiguityCommandException extends RuntimeException {
    public AmbiguityCommandException(@Nonnull String commandName, int allMatchedTasks) {
        super(commandName + " has " + allMatchedTasks + " matches");
    }
}
