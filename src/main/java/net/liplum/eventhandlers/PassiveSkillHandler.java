package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.weapon.WeaponAttackArgs;
import net.liplum.events.AttributeAccessEvent;
import net.liplum.events.PlayerCollisionEvent;
import net.liplum.events.skill.LanceSprintEvent;
import net.liplum.events.weapon.FawWeaponLeftClickEvent;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.events.weapon.WeaponDurabilityEvent;
import net.liplum.lib.utils.SkillUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PassiveSkillHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = e.player;
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(TickEvent.PlayerTickEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(player, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityHurt(LivingHurtEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(LivingHurtEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityDamage(LivingDamageEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(LivingDamageEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityDeath(LivingDeathEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(LivingDeathEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityHeal(LivingHealEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(LivingHealEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityFall(LivingFallEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(LivingFallEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWeaponAttacking(WeaponAttackEvent.Attacking e) {
        onWeaponAttack(e, WeaponAttackEvent.Attacking.class);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onWeaponAttacked(WeaponAttackEvent.Attacked e) {
        onWeaponAttack(e, WeaponAttackEvent.Attacked.class);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLanceSprint(LanceSprintEvent e) {
        EntityLivingBase entity = e.getArgs().entity();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(LanceSprintEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    private static void onWeaponAttack(WeaponAttackEvent<?> e, Class<? extends WeaponAttackEvent<?>> eventType) {
        WeaponAttackArgs<?> args = e.getArgs();
        EntityLivingBase attacker = args.attacker();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(eventType, attacker);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(attacker, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFawWeaponLeftClick(FawWeaponLeftClickEvent e) {
        EntityPlayer player = e.player();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(FawWeaponLeftClickEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(player, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWeaponDurabilityDamaged(WeaponDurabilityEvent.Damaged e) {
        EntityLivingBase entity = e.entity;
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(WeaponDurabilityEvent.Damaged.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWeaponDurabilityHealed(WeaponDurabilityEvent.Healed e) {
        EntityLivingBase entity = e.entity;
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(WeaponDurabilityEvent.Healed.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(entity, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerCollision(PlayerCollisionEvent e) {
        EntityPlayer player = e.player;
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(PlayerCollisionEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(player, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupXp(PlayerPickupXpEvent e) {
        EntityPlayer player = e.getEntityPlayer();
        Collection<IPassiveSkill<Event>> skills =
                SkillUtil.getAvailablePassiveSkills(PlayerPickupXpEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            SkillUtil.onTrigger(player, skill, res);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttributeAccess(AttributeAccessEvent e) {
        EntityLivingBase entity = e.entity();
        if (entity != null) {
            Collection<IPassiveSkill<Event>> skills =
                    SkillUtil.getAvailablePassiveSkills(AttributeAccessEvent.class, entity);
            for (IPassiveSkill<Event> skill : skills) {
                PSkillResult res = skill.onTrigger(e);
                SkillUtil.onTrigger(entity, skill, res);
                if (res == PSkillResult.CancelTrigger) {
                    break;
                }
            }
        }
    }
}
