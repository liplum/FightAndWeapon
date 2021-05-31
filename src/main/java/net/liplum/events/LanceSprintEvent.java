package net.liplum.events;

import net.liplum.lib.cores.lance.LanceArgs;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * If you cancel it then the player can't sprint anymore.
 */
@Cancelable
public class LanceSprintEvent extends Event {
    private final LanceArgs args;

    public LanceSprintEvent(LanceArgs args) {
        this.args = args;
    }

    public LanceArgs getArgs(){
        return args;
    }
}
