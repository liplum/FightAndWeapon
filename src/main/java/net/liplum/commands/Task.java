package net.liplum.commands;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public abstract class Task implements ITask {
    private final int argsCount;
    private final boolean hasTabCompletion;
    private final boolean isMutex;

    public Task(int argsCount, boolean hasTabCompletion, boolean isMutex) {
        this.argsCount = argsCount;
        this.hasTabCompletion = hasTabCompletion;
        this.isMutex = isMutex;
    }

    @Override
    public int getArgsCount() {
        return argsCount;
    }

    @Override
    public boolean hasTabCompletion(int index) {
        return hasTabCompletion;
    }

    @Nonnull
    @Override
    public List<String> getCompletions(int needBeCompetedIndex) {
        return Collections.emptyList();
    }

    @Override
    public boolean isMutex() {
        return isMutex;
    }
}
