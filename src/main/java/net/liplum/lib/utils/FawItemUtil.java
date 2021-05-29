package net.liplum.lib.utils;

import net.liplum.lib.items.Category;
import net.liplum.lib.items.FawItem;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.modifiers.Modifier;
import net.liplum.lib.weaponcores.IWeaponCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

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
     * It calls {@link WeaponBaseItem#dealDamage(ItemStack, EntityLivingBase, Entity, float)} to deal damage to the target.
     * It's called by {@link WeaponBaseItem#onLeftClickEntity(ItemStack, EntityPlayer, Entity)}.
     *
     * @param itemStack
     * @param weapon
     * @param attacker  the attacker
     * @param target    the target
     * @return
     */
    public static boolean attackEntity(ItemStack itemStack, WeaponBaseItem weapon, EntityLivingBase attacker, Entity target) {
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
        Modifier modifier = FawGemUtil.getModifierFrom(itemStack);
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

        boolean successfullyHit = weapon.dealDamage(itemStack, attacker, target, finalDamage);
        target.hurtResistantTime = 0;
        if (!successfullyHit) {
            sound = SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE;
        } else {
            attacker.setLastAttackedEntity(target);
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
        return successfullyHit;
    }
    /**
     * Calculate the final value of attribute without gemstone's amplification.
     *
     * @param base
     * @param deltaMaster
     * @return
     */
    public static float calcuAttribute(float base, float deltaMaster) {
        float res = base + deltaMaster;
        return MathUtil.fixMin(res,0);
    }

    /**
     * Calculate the final value of attribute without master's amplification.
     *
     * @param base
     * @param rateGem
     * @param deltaGem
     * @return
     */
    public static float calcuAttribute(float base, float deltaGem, float rateGem) {
        float res = (base + deltaGem) * (1 + rateGem);
        return MathUtil.fixMin(res,0);
    }

    /**
     * @param base
     * @param deltaMaster
     * @param rateGem
     * @param deltaGem
     * @return
     */
    public static float calcuAttribute(float base, float deltaMaster, float deltaGem, float rateGem) {
        float res = ((base + deltaMaster) + deltaGem) * (1 + rateGem);
        return MathUtil.fixMin(res,0);
    }

}
