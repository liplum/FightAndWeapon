package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PassiveSkillResult;
import net.liplum.events.LanceSprintEvent;
import net.liplum.lib.registeies.SkillManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class LanceSprintHandler {

    @SubscribeEvent
    public static void onLanceSprint(LanceSprintEvent e) {
        SkillManager skillManager = SkillManager.Instance();
        IPassiveSkill[] passiveSkills = skillManager.getPassiveSkills(LanceSprintEvent.class);
        for (IPassiveSkill skill : passiveSkills) {
            PassiveSkillResult res = skill.onTrigger(e);
            if (res == PassiveSkillResult.CancelTrigger) {
                break;
            }
        }
    }
}