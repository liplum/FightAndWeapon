package net.liplum.events.skill;

import net.liplum.api.weapon.WeaponSkillArgs;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * If you cancel it then the player can't sprint this time.
 */
@Cancelable
public class LanceSprintEvent extends Event {
    private final WeaponSkillArgs args;

    public LanceSprintEvent(WeaponSkillArgs args) {
        this.args = args;
    }

    public WeaponSkillArgs getArgs() {
        return args;
    }
}
