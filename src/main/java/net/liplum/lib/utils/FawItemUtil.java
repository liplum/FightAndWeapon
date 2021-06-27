package net.liplum.lib.utils;

import net.liplum.I18ns;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.DamageArgs;
import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.api.weapon.WeaponPart;
import net.liplum.events.attack.WeaponAttackedArgs;
import net.liplum.events.attack.WeaponAttackedEvent;
import net.liplum.events.attack.WeaponAttackingArgs;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.liplum.items.gemstones.GemstoneItem;
import net.liplum.items.tools.InlayingToolItem;
import net.liplum.lib.items.Category;
import net.liplum.lib.items.FawItem;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
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

    public static boolean isFawWeapon(@Nonnull ItemStack itemStack) {
        return isFawWeapon(itemStack.getItem());
    }

    public static boolean isGemstone(@Nonnull ItemStack itemStack) {
        return isGemstone(itemStack.getItem());
    }

    public static boolean isFawWeapon(@Nonnull Item item) {
        return item instanceof WeaponBaseItem<?>;
    }

    public static boolean isGemstone(@Nonnull Item item) {
        return item instanceof GemstoneItem;
    }

    public static boolean isInlayingTool(ItemStack itemStack) {
        return isInlayingTool(itemStack.getItem());
    }

    public static boolean isInlayingTool(Item item) {
        return item instanceof InlayingToolItem;
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
        IModifier<?> modifier = GemUtil.getModifierFrom(itemStack);
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

        DamageArgs initialDamage = new DamageArgs(finalDamage, damageSource, target);
        WeaponAttackingArgs attackingArgs = new WeaponAttackingArgs();
        attackingArgs.setWorld(world)
                .setAttacker(attacker)
                .setTarget(target)
                .setItemStack(itemStack)
                .setModifier(modifier)
                .setWeaponCore(core)
                .setInitialDamage(initialDamage);

        WeaponAttackingEvent attackingEvent = new WeaponAttackingEvent(attackingArgs);
        MinecraftForge.EVENT_BUS.post(attackingEvent);

        float totalDamage = 0;
        boolean isHitSuccessfully = false;
        List<DamageArgs> allDamages = attackingArgs.getAllDamages();
        for (DamageArgs damageArgs : allDamages) {
            float singleDamage = damageArgs.getDamage();
            boolean currentHitSuccessfully = weapon.dealDamage(itemStack, attacker, damageArgs.getTarget(), damageArgs.getDamageSource(), singleDamage);
            if (currentHitSuccessfully) {
                totalDamage += singleDamage;
                isHitSuccessfully = true;
            }
        }
        attacker.setLastAttackedEntity(target);

        if (!isHitSuccessfully) {
            sound = SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE;
        } else {
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
        WeaponAttackedArgs postArgs = new WeaponAttackedArgs();
        postArgs.setWorld(world)
                .setAttacker(attacker)
                .setTarget(target)
                .setItemStack(itemStack)
                .setModifier(modifier)
                .setInitialDamage(initialDamage)
                .setWeaponCore(core)
                .setHitSuccessfully(isHitSuccessfully)
                .setTotalDamage(totalDamage);

        WeaponAttackedEvent postAttackEvent = new WeaponAttackedEvent(postArgs);
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

    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, double value) {
        tooltip.add(TextFormatting.YELLOW +
                I18n.format(attrTranslateKey) + " : " +
                Math.round(value));
    }

    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, float value) {
        tooltip.add(TextFormatting.YELLOW +
                I18n.format(attrTranslateKey) + " : " +
                Math.round(value));
    }

    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, float value, String format) {
        tooltip.add(TextFormatting.YELLOW +
                I18n.format(attrTranslateKey) + " : " +
                String.format(format, value));
    }

    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, double value, String format) {
        tooltip.add(TextFormatting.YELLOW +
                I18n.format(attrTranslateKey) + " : " +
                String.format(format, value));
    }

    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, int value) {
        tooltip.add(TextFormatting.YELLOW +
                I18n.format(attrTranslateKey) + " : " +
                value);
    }

    public static void addPassiveSkillTooltip(@Nonnull List<String> tooltip, @Nonnull IPassiveSkill<?> passiveSkill) {
        tooltip.add(TextFormatting.BLUE +
                I18n.format(SkillUtil.getNameI18nKey(passiveSkill)));
    }

    public static String getNameI18nKey(WeaponPart weaponPart) {
        return I18ns.endWithName(I18ns.prefixItem(weaponPart.getRegisterName()));
    }
}
