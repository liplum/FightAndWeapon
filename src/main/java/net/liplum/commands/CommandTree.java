package net.liplum.commands;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class CommandTree extends CommandTreeBase {
    @Nonnull
    private final String name;
    @Nonnull
    private final Function<ICommandSender, String> usageGetter;
    @Nonnull
    private final List<String> aliases = new LinkedList<>();

    public CommandTree(@Nonnull String name, @Nonnull Function<ICommandSender, String> usageGetter) {
        this.name = name;
        this.usageGetter = usageGetter;
    }

    public CommandTree(@Nonnull String name, @Nonnull String usage) {
        this(name, (s) -> usage);
    }

    @Nonnull
    public CommandTree addSub(ICommand subCommand) {
        this.addSubcommand(subCommand);
        return this;
    }

    @Nonnull
    public CommandTree addSub(Supplier<ICommand> subCommandGetter) {
        return this.addSub(subCommandGetter.get());
    }

    @Nonnull
    public CommandTree addAlias(@Nonnull String alias) {
        this.aliases.add(alias);
        return this;
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return usageGetter.apply(sender);
    }
}
