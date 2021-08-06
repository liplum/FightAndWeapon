package net.liplum.registries;

import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.commands.Command;
import net.liplum.commands.Mastery.MasteryCommand;
import net.liplum.commands.weapon.InlayCommand;
import net.minecraft.command.CommandBase;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.function.Supplier;

import static net.liplum.CommandTasks.ClearAllWeaponTypeCoolDown;
import static net.liplum.CommandTasks.ClearOneWeaponTypeCoolDown;

public class CommandRegistry {
    private static final Logger Logger = LogManager.getLogger();
    private static final LinkedList<Class<? extends CommandBase>> Commands = new LinkedList<>();
    private static final LinkedList<Supplier<CommandBase>> AllCommandGetters = new LinkedList<>();

    public static final Supplier<CommandBase> ClearCoolDown = with(() ->
            new Command(Names.Command.ClearCD, 2, I18ns.Command.ClearCD)
                    .addTask(ClearOneWeaponTypeCoolDown)
                    .addTask(ClearAllWeaponTypeCoolDown));

    private static Supplier<CommandBase> with(Supplier<CommandBase> commandGetter) {
        AllCommandGetters.add(commandGetter);
        return commandGetter;
    }

    //I even don't know why we can't directly use a object instead of a Class.
    static {
        Commands.add(InlayCommand.class);
        Commands.add(MasteryCommand.class);
        //Commands.add(ClearCoolDownCommand.class);
    }

    public static void load(FMLServerStartingEvent event) {
        for (Class<? extends CommandBase> commandClz : Commands) {
            try {
                CommandBase command = commandClz.newInstance();
                event.registerServerCommand(command);
            } catch (InstantiationException | IllegalAccessException e) {
                Logger.error("Can't load command" + commandClz.getName());
            }
        }

        for (Supplier<CommandBase> commandGetter : AllCommandGetters) {
            event.registerServerCommand(commandGetter.get());
        }
    }
}
