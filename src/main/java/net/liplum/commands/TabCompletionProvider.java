package net.liplum.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public interface TabCompletionProvider {
    @NotNull
    List<String> getCompletions(@NotNull MinecraftServer server, @NotNull ICommandSender sender, int needBeCompetedIndex, @Nullable BlockPos targetPos);
}
