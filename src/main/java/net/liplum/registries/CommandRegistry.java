package net.liplum.registries;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.commands.Command;
import net.liplum.commands.CommandTree;
import net.liplum.commands.Mastery.MasteryExpCommand;
import net.liplum.commands.Mastery.MasteryResetCommand;
import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.function.Supplier;

import static net.liplum.CommandTasks.*;

public class CommandRegistry {
    private static final LinkedList<Supplier<ICommand>> AllCommandGetters = new LinkedList<>();

    public static final Supplier<ICommand> ClearCoolDownCommand = with(() ->
            new Command(Names.Command.ClearCD, 2, I18ns.Command.ClearCD)
                    .addTask(ClearOneWeaponTypeCoolDown)
                    .addTask(ClearAllWeaponTypeCoolDown));

    public static final Supplier<ICommand> InlayGemstoneCommand = with(() ->
            new Command(Names.Command.Inlay, 2, I18ns.Command.Inlay)
                    .addTask(InlayGemstone)
                    .addTask(RemoveGemstone)
    );

    public static final Supplier<ICommand> ShowMasteryInfoCommand = () ->
            new Command(Names.Command.MasterySub.MasteryShow, 4, I18ns.Command.MasteryShow)
                    .addTask(ShowOneMasteryInfo)
                    .addTask(ShowAllMasteryInfo);

    public static final Supplier<ICommand> ResetMasteryDataCommand = MasteryResetCommand::new;

    public static final Supplier<ICommand> AddMasteryExpCommand = MasteryExpCommand::new;

    public static final Supplier<ICommand> MasteryCommand = with(() ->
            new CommandTree(Names.Command.Mastery, I18ns.Command.Mastery)
                    .addSub(ShowMasteryInfoCommand)
                    .addSub(ResetMasteryDataCommand)
                    .addSub(AddMasteryExpCommand)
    );

    private static Supplier<ICommand> with(Supplier<ICommand> commandGetter) {
        AllCommandGetters.add(commandGetter);
        return commandGetter;
    }

    //I even don't know why we can't directly use a object instead of a Class.
    public static void load(FMLServerStartingEvent event) {
        for (Supplier<ICommand> commandGetter : AllCommandGetters) {
            event.registerServerCommand(commandGetter.get());
        }
    }
}
