package net.liplum.commands;

import net.liplum.api.annotations.Require;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public interface ITask {
    int getArgsCount();

    @NotNull
    ValidInfo isValid(@NotNull String parameter, int index);


    void run(@NotNull MinecraftServer server, @NotNull ICommandSender sender, @NotNull Object[] transformedArgs) throws CommandException;

    boolean hasTabCompletion(int index);

    @NotNull
    @Require(func = "hasTabCompletion(int)", is = "true")
    List<String> getCompletions(@NotNull MinecraftServer server, @NotNull ICommandSender sender, int needBeCompetedIndex, @Nullable BlockPos targetPos);

    boolean isTabCompletionMutex(int index);
}
