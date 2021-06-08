package net.liplum.lib.utils;

import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.liplum.events.attack.WeaponPostAttackedEvent;
import net.liplum.events.attack.WeaponPreAttackEvent;
import net.liplum.lib.items.Category;
import net.liplum.lib.items.FawItem;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.util.List;

public final class FawItemUtil {
    private FawItemUtil() {

    }

    public static boolean hasCategory(ItemStack itemStack, Category category) {
        Item item = itemStack.getItem();
        if (item instanceof FawItem) {
            return ((FawItem) item).isA(category);
        }
        return false;
    }

    /**
     * It calls {@link WeaponBaseItem#dealDamage(ItemStack, EntityLivingBase, Entity, DamageSource, float)} to deal damage to the target.
     * It's called by {@link WeaponBaseItem#onLeftClickEntity(ItemStack, EntityPlayer, Entity)}.
     *
     * @param itemStack
     * @param weapon
     * @param attacker  the attacker
     * @param target    the target
     * @return whether this attack is successful
     */
    public static boolean attackEntity(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem<?> weapon, EntityLivingBase attacker, Entity target) {
        //Nobody did and nobody was hit.
        if (attacker == null || target == null ||
                //the target can't be hit by item
                !target.canBeAttackedWithItem() ||
                //the target has already hit by attacker
                target.hitByEntity(attacker) ||
                //the item has no nbt tag(basically impossible)
                !itemStack.hasTagCompound()) {
            return false;
        }
        World world = attacker.world;
        EntityLivingBase targetLiving = null;
        EntityPlayer attackPlayer = null;
        if (target instanceof EntityLivingBase) {
            targetLiving = (EntityLivingBase) target;
        }

        if (attacker instanceof EntityPlayer) {
            attackPlayer = (EntityPlayer) attacker;
            if (target instanceof EntityPlayer) {
                EntityPlayer targetPlayer = (EntityPlayer) target;
                if (!attackPlayer.canAttackPlayer(targetPlayer)) {
                    return false;
                }
            }
        }

        //The damage from attacker's hand not about any item held in hand.
        //It doesn't take some potion effects into account --like Strength.
        //But some of them go into effect by the attack event. So we don't have to think about it.
        float baseDamage = (float) attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        float finalDamage = baseDamage;

        IWeaponCore core = weapon.getCore();
        float strengthBase = core.getStrength();
        IModifier<?> modifier = FawGemUtil.getModifierFrom(itemStack);
        if (modifier != null) {
            finalDamage += calcuAttribute(strengthBase, modifier.getStrengthDelta(), modifier.getStrengthRate());
        } else {
            finalDamage += strengthBase;
        }
        SoundEvent sound = null;

        if (attackPlayer != null) {
            float cooldown = ((EntityPlayer) attacker).getCooledAttackStrength(0.5F);
            sound = cooldown > 0.9f ? SoundEvents.ENTITY_PLAYER_ATTACK_STRONG : SoundEvents.ENTITY_PLAYER_ATTACK_WEAK;
            finalDamage *= (0.2F + cooldown * cooldown * 0.8F);
        }
        DamageSource damageSource = EntityUtil.genDamageSource(attacker);

        WeaponPreAttackEvent preAttackEvent = new WeaponPreAttackEvent(world, attacker, target, core, modifier, itemStack,damageSource, finalDamage);
        MinecraftForge.EVENT_BUS.post(preAttackEvent);
        DamageSource newDamageSource = preAttackEvent.getDamageSource();
        finalDamage = preAttackEvent.getDamage();

        boolean isHitSuccessfully = weapon.dealDamage(itemStack, attacker, target, newDamageSource, finalDamage);

        if (!isHitSuccessfully) {
            sound = SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE;
        } else {
            //Post Weapon Attacking Event
            WeaponAttackingEvent attackingEvent = new WeaponAttackingEvent(world, attacker, target, core, modifier, itemStack);
            MinecraftForge.EVENT_BUS.post(attackingEvent);
            //Deal extra damages
            List<Tuple<DamageSource, Float>> extraDamages = attackingEvent.getExtraDamages();
            for (Tuple<DamageSource, Float> extraDamage : extraDamages) {
                DamageSource extraDamageSource = extraDamage.getFirst();
                Float extraDamageValue = extraDamage.getSecond();
                weapon.dealDamage(itemStack, attacker, target, extraDamageSource, extraDamageValue);
            }
            attacker.setLastAttackedEntity(target);

            //calcu enemy breaking time
            int enemyBreakingTime = core.getEnemyBreakingTime();
            if (modifier != null) {
                enemyBreakingTime = calcuAttribute(enemyBreakingTime, modifier.getEnemyBreakingTimeDelta(), modifier.getEnemyBreakingTimeRate());
            }

            if (enemyBreakingTime >= 0) {
                target.hurtResistantTime = enemyBreakingTime;
            }

            //TODO:Something here!
        }
        //If attacker is not a player, it will be null.
        if (attackPlayer != null &&
                //It actually made some sounds.
                sound != null
        ) {
            attackPlayer.playSound(sound, 1, 1);
        }

        //TODO:More!!!
        WeaponPostAttackedEvent postAttackEvent = new WeaponPostAttackedEvent(world, attacker, target, core, modifier, itemStack, isHitSuccessfully);
        MinecraftForge.EVENT_BUS.post(postAttackEvent);

        return isHitSuccessfully;
    }

    /**
     * Calculate the final value of attribute without gemstone's amplification.
     *
     * @param base
     * @param deltaMaster
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static float calcuAttribute(float base, float deltaMaster) {
        return calcuAttribute(base, deltaMaster, 0, 0);

    }

    /**
     * Calculate the final value of attribute without master's amplification.
     *
     * @param base
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static float calcuAttribute(float base, float deltaGem, float rateGem) {
        return calcuAttribute(base, 0, deltaGem, rateGem);
    }

    /**
     * @param base
     * @param deltaMaster
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static float calcuAttribute(float base, float deltaMaster, float deltaGem, float rateGem) {
        if (base < 0) {
            return -1;
        }
        float res = ((base + deltaMaster) + deltaGem) * (1 + rateGem);
        return MathUtil.fixMin(res, 0);
    }

    /**
     * Calculate the final value of attribute without gemstone's amplification.
     *
     * @param base
     * @param deltaMaster
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static double calcuAttribute(double base, double deltaMaster) {
        return calcuAttribute(base, deltaMaster, 0, 0);

    }

    /**
     * Calculate the final value of attribute without master's amplification.
     *
     * @param base
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static double calcuAttribute(double base, double deltaGem, float rateGem) {
        return calcuAttribute(base, 0, deltaGem, rateGem);
    }


    /**
     * @param base
     * @param deltaMaster
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static double calcuAttribute(double base, double deltaMaster, double deltaGem, float rateGem) {
        if (base < 0) {
            return -1;
        }
        double res = ((base + deltaMaster) + deltaGem) * (1 + rateGem);
        return MathUtil.fixMin(res, 0);
    }

    /**
     * Calculate the final value of attribute without gemstone's amplification.
     *
     * @param base
     * @param deltaMaster
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static int calcuAttribute(int base, int deltaMaster) {
        return calcuAttribute(base, deltaMaster, 0, 0);

    }

    /**
     * Calculate the final value of attribute without master's amplification.
     *
     * @param base
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static int calcuAttribute(int base, int deltaGem, float rateGem) {
        return calcuAttribute(base, 0, deltaGem, rateGem);
    }


    /**
     * @param base
     * @param deltaMaster
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static int calcuAttribute(int base, int deltaMaster, int deltaGem, float rateGem) {
        if (base < 0) {
            return -1;
        }
        if (deltaMaster == 0 && deltaGem == 0 && rateGem == 0) {
            return base;
        }
        int res = (int) (((base + deltaMaster) + deltaGem) * (1 + rateGem));
        return MathUtil.fixMin(res, 0);
    }

    /**
     * @param base
     * @param rate
     * @return
     */
    public static int calcuAttributeInRate(int base, float rate) {
        if (base < 0) {
            return -1;
        } else if (rate == 0) {
            return base;
        }
        int res = (int) (base * (1 + rate));
        return res;
    }

}
