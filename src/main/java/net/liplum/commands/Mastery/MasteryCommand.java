package net.liplum.commands.Mastery;

import net.liplum.I18ns;
import net.liplum.Names;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

import javax.annotation.Nonnull;

public class MasteryCommand extends CommandTreeBase {
    public MasteryCommand() {
        this.addSubcommand(new MasteryExpCommand());
        this.addSubcommand(new MasteryShowCommand());
    }

    @Nonnull
    @Override
    public String getName() {
        return Names.Command.Mastery;
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return I18ns.Command.Mastery;
    }
}
