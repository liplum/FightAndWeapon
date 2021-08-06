package net.liplum.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;

public interface ITask {
    int getArgsCount();

    ValidInfo isValid(@Nonnull String parameter, int index);


    void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender,@Nonnull Object[] transformedArgs) throws CommandException;
}
