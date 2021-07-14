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

import java.util.Set;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PassiveSkillHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = e.player;
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(TickEvent.PlayerTickEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityHurt(LivingHurtEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(LivingHurtEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityDamage(LivingDamageEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(LivingDamageEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityDeath(LivingDeathEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(LivingDeathEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityHeal(LivingHealEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(LivingHealEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingEntityFall(LivingFallEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(LivingFallEvent.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
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
        EntityPlayer player = e.getArgs().getPlayer();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(LanceSprintEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    private static void onWeaponAttack(WeaponAttackEvent<?> e, Class<? extends WeaponAttackEvent<?>> eventType) {
        WeaponAttackArgs<?> args = e.getArgs();
        EntityLivingBase attacker = args.getAttacker();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(eventType, attacker);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFawWeaponLeftClick(FawWeaponLeftClickEvent e) {
        EntityPlayer player = e.getPlayer();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(FawWeaponLeftClickEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWeaponDurabilityDamaged(WeaponDurabilityEvent.Damaged e) {
        EntityLivingBase entity = e.entity;
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(WeaponDurabilityEvent.Damaged.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWeaponDurabilityHealed(WeaponDurabilityEvent.Healed e) {
        EntityLivingBase entity = e.entity;
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(WeaponDurabilityEvent.Healed.class, entity);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerCollision(PlayerCollisionEvent e) {
        EntityPlayer player = e.player;
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(PlayerCollisionEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupXp(PlayerPickupXpEvent e) {
        EntityPlayer player = e.getEntityPlayer();
        Set<IPassiveSkill<Event>> skills =
                SkillUtil.getPassiveSkills(PlayerPickupXpEvent.class, player);
        for (IPassiveSkill<Event> skill : skills) {
            PSkillResult res = skill.onTrigger(e);
            if (res == PSkillResult.CancelTrigger) {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttributeAccess(AttributeAccessEvent e) {
        EntityPlayer player = e.getPlayer();
        if (player != null) {
            Set<IPassiveSkill<Event>> skills =
                    SkillUtil.getPassiveSkills(AttributeAccessEvent.class, player);
            for (IPassiveSkill<Event> skill : skills) {
                PSkillResult res = skill.onTrigger(e);
                if (res == PSkillResult.CancelTrigger) {
                    break;
                }
            }
        }
    }
}
