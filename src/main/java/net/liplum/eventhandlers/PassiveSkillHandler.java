package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.lib.utils.SkillUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.Set;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PassiveSkillHandler {
    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = e.player;
        Set<IPassiveSkill<TickEvent.PlayerTickEvent>> skills =
                SkillUtil.getPassiveSkillsFromPlayer(TickEvent.PlayerTickEvent.class, player);
        for (IPassiveSkill<TickEvent.PlayerTickEvent> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

}
