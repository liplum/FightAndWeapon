package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.events.attack.WeaponAttackBaseEvent;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.liplum.events.attack.WeaponPostAttackedEvent;
import net.liplum.events.attack.WeaponPreAttackEvent;
import net.liplum.lib.utils.SkillUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Set;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PassiveSkillHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
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

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWeaponPreAttackEvent(WeaponPreAttackEvent e) {
        onWeaponAttack(e, WeaponPreAttackEvent.class);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWeaponAttackingEvent(WeaponAttackingEvent e) {
        onWeaponAttack(e, WeaponAttackingEvent.class);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWeaponPostAttackEvent(WeaponPostAttackedEvent e) {
        onWeaponAttack(e, WeaponPostAttackedEvent.class);
    }

    private static void onWeaponAttack(WeaponAttackBaseEvent e, Class<? extends WeaponAttackBaseEvent> eventType) {
        EntityLivingBase attacker = e.getAttacker();
        Set<IPassiveSkill<Event>> skills;
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            skills = SkillUtil.getPassiveSkillsFromPlayer(eventType, player);
        } else {
            skills = SkillUtil.getPassiveSkillsFromMob(eventType, attacker);
        }
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }
}
