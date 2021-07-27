package net.liplum.registeies;

import net.liplum.commands.InlayCommand;
import net.liplum.commands.MasteryExpCommand;
import net.minecraft.command.CommandBase;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class CommandRegistry {
    private static final Logger Logger = LogManager.getLogger();
    private static final LinkedList<Class<? extends CommandBase>> Commands = new LinkedList<>();

    //I even don't know why we can't directly use a object instead of a Class.
    static {
        Commands.add(InlayCommand.class);
        Commands.add(MasteryExpCommand.class);
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
    }
}
