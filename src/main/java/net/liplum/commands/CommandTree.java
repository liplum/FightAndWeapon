package net.liplum.commands;

import net.liplum.api.annotations.LongSupport;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@LongSupport
public class CommandTree extends CommandTreeBase {
    @NotNull
    private final String name;
    @NotNull
    private final Function<ICommandSender, String> usageGetter;
    @NotNull
    private final List<String> aliases = new LinkedList<>();

    public CommandTree(@NotNull String name, @NotNull Function<ICommandSender, String> usageGetter) {
        this.name = name;
        this.usageGetter = usageGetter;
    }

    public CommandTree(@NotNull String name, @NotNull String usage) {
        this(name, (s) -> usage);
    }

    @NotNull
    public CommandTree addSub(ICommand subCommand) {
        this.addSubcommand(subCommand);
        return this;
    }

    @NotNull
    public CommandTree addSub(Supplier<ICommand> subCommandGetter) {
        return this.addSub(subCommandGetter.get());
    }

    @NotNull
    public CommandTree addAlias(@NotNull String alias) {
        this.aliases.add(alias);
        return this;
    }

    @NotNull
    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public String getUsage(@NotNull ICommandSender sender) {
        return usageGetter.apply(sender);
    }
}
