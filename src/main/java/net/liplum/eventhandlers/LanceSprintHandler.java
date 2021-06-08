package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.events.skill.LanceSprintEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class LanceSprintHandler {

    @SubscribeEvent
    public static void onLanceSprint(LanceSprintEvent e) {
        IPassiveSkill[] passiveSkills = SkillRegistry.getPassiveSkillsFromEvent(LanceSprintEvent.class);
        if (passiveSkills != null) {

            for (IPassiveSkill skill : passiveSkills) {
                PSkillResult res = skill.onTrigger(e);
                if (res == PSkillResult.CancelTrigger) {
                    break;
                }
            }
        }
    }
}
