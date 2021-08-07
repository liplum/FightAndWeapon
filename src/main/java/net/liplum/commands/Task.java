package net.liplum.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Task implements ITask {
    @Nonnull
    private final Map<Integer, IsValid> validChecks = new HashMap<>();
    @Nonnull
    private final Map<Integer, TabCompletionProvider> tabCompletionProviders = new HashMap<>();

    public Task() {
    }

    @Nonnull
    public Task addValidRequirement(int index, @Nonnull IsValid isValid) {
        validChecks.put(index, isValid);
        return this;
    }

    @Nonnull
    public Task addTabCompletion(int needBeCompetedIndex, @Nonnull TabCompletionProvider provider) {
        tabCompletionProviders.put(needBeCompetedIndex, provider);
        return this;
    }

    @Override
    public int getArgsCount() {
        return validChecks.size();
    }

    @Override
    public boolean hasTabCompletion(int index) {
        return tabCompletionProviders.size() > 0;
    }

    @Nonnull
    @Override
    public List<String> getCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, int needBeCompetedIndex, @Nullable BlockPos targetPos) {
        TabCompletionProvider provider = tabCompletionProviders.get(needBeCompetedIndex);
        if (provider != null) {
            return provider.getCompletions(server, sender, needBeCompetedIndex, targetPos);
        }
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public ValidInfo isValid(@Nonnull String parameter, int index) {
        IsValid isValid = validChecks.get(index);
        if (isValid != null) {
            return isValid.isValid(parameter);
        }
        return ValidInfo.FalseAndNull;
    }

    @Override
    public boolean isTabCompletionMutex(int index) {
        return false;
    }
}
