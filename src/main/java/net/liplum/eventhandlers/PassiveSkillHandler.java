package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.events.attack.WeaponAttackBaseEvent;
import net.liplum.lib.utils.SkillUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Set;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PassiveSkillHandler {
    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = e.player;
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkillsFromPlayer(TickEvent.PlayerTickEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHurtEvent(LivingHurtEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            Set<IPassiveSkill<Event>> skills =
                    SkillUtil.getPassiveSkillsFromPlayer(LivingHurtEvent.class, player);
            for (IPassiveSkill<Event> skill : skills) {
                PSkillResult res = skill.onTrigger(e);
                if (res == PSkillResult.CancelTrigger) {
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerFallEvent(LivingFallEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            Set<IPassiveSkill<Event>> skills =
                    SkillUtil.getPassiveSkillsFromPlayer(LivingFallEvent.class, player);
            for (IPassiveSkill<Event> skill : skills) {
                PSkillResult res = skill.onTrigger(e);
                if (res == PSkillResult.CancelTrigger) {
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWeaponAttackEvent(WeaponAttackBaseEvent e) {
        EntityLivingBase attacker = e.getAttacker();
        Set<IPassiveSkill<Event>> skills;
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            skills = SkillUtil.getPassiveSkillsFromPlayer(LivingFallEvent.class, player);
        } else {
            skills = SkillUtil.getPassiveSkillsFromMob(WeaponAttackBaseEvent.class, attacker);
        }
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }
}
