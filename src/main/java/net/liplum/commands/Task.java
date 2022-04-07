package net.liplum.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Task implements ITask {
    @NotNull
    private final Map<Integer, IValidation> validChecks = new HashMap<>();
    @NotNull
    private final Map<Integer, TabCompletionProvider> tabCompletionProviders = new HashMap<>();

    public Task() {
    }

    @NotNull
    public Task addValidRequirement(int index, @NotNull IValidation isValid) {
        validChecks.put(index, isValid);
        return this;
    }

    @NotNull
    public Task addTabCompletion(int needBeCompetedIndex, @NotNull TabCompletionProvider provider) {
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

    @NotNull
    @Override
    public List<String> getCompletions(@NotNull MinecraftServer server, @NotNull ICommandSender sender, int needBeCompetedIndex, @Nullable BlockPos targetPos) {
        TabCompletionProvider provider = tabCompletionProviders.get(needBeCompetedIndex);
        if (provider != null) {
            return provider.getCompletions(server, sender, needBeCompetedIndex, targetPos);
        }
        return Collections.emptyList();
    }

    @NotNull
    @Override
    public ValidInfo isValid(@NotNull String parameter, int index) {
        IValidation isValid = validChecks.get(index);
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
