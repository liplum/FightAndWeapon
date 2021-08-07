package net.liplum.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface TabCompletionProvider {
    @Nonnull
    List<String> getCompletions(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, int needBeCompetedIndex, @Nullable BlockPos targetPos);
}
