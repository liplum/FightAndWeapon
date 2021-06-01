package net.liplum.lib.skills.passive;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.events.LanceSprintEvent;
import net.liplum.lib.cores.lance.LanceArgs;
import net.liplum.registeies.PotionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

/**
 * There are all the passive skills of master in FAW mod.
 */
public final class MasterPassiveSkills {

    /**
     * It can make the player unstoppable when sprinting with a lance.
     */
    public final static IPassiveSkill<LanceSprintEvent> UnstoppableLanceSprint = new IPassiveSkill<LanceSprintEvent>() {
        @Override
        public Class<LanceSprintEvent> getEventType() {
            return LanceSprintEvent.class;
        }

        @Override
        public PSkillResult onTrigger(LanceSprintEvent event) {
            LanceArgs args = event.getArgs();
            EntityPlayer player = args.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionRegistry.Unstoppable_Potion, 15, 0, false, false));
            return PSkillResult.Complete;
        }
    };
}
