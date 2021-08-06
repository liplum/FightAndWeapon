package net.liplum.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;

public class Task implements ITask {
    @Override
    public int getArgsCount() {
        return 0;
    }

    @Override
    public ValidInfo isValid(@Nonnull String parameter, int index) {
        return null;
    }

    @Override
    public void run(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull Object[] transformedArgs) throws CommandException {

    }
}
