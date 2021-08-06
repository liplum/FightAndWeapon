package net.liplum.commands;

import net.liplum.api.annotations.Require;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import java.util.List;

public interface ITask {
    int getArgsCount();

    @Nonnull
    ValidInfo isValid(@Nonnull String parameter, int index);


    void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException;

    boolean hasTabCompletion(int index);

    @Nonnull
    @Require(func = "hasTabCompletion(int)", is = "true")
    List<String> getCompletions(int needBeCompetedIndex);

    boolean isMutex();
}
