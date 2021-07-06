package net.liplum.lib.utils;

import net.liplum.I18ns;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.*;
import net.liplum.attributes.*;
import net.liplum.capabilities.MasterCapability;
import net.liplum.events.attack.WeaponAttackedArgs;
import net.liplum.events.attack.WeaponAttackedEvent;
import net.liplum.events.attack.WeaponAttackingArgs;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.liplum.items.GemstoneItem;
import net.liplum.items.tools.InlayingToolItem;
import net.liplum.lib.FawDamage;
import net.liplum.api.weapon.Category;
import net.liplum.api.weapon.FawItem;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.liplum.registeies.CapabilityRegistry;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.liplum.Attributes.Generic.EnemyBreakingTime;
import static net.liplum.Attributes.Generic.Strength;

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

        WeaponCore core = weapon.getCore();
        IGemstone gemstone = GemUtil.getGemstoneFrom(itemStack);
        Modifier<?> modifier = GemUtil.getModifierFrom(itemStack);
        FinalAttrValue finalStrength = FawItemUtil.calcuAttribute(Strength, core, modifier, attackPlayer);
        finalDamage += finalStrength.getFloat();
        SoundEvent sound = null;

        if (attackPlayer != null) {
            float cooldown = ((EntityPlayer) attacker).getCooledAttackStrength(0.5F);
            sound = cooldown > 0.9f ? SoundEvents.ENTITY_PLAYER_ATTACK_STRONG : SoundEvents.ENTITY_PLAYER_ATTACK_WEAK;
            finalDamage *= (0.2F + cooldown * cooldown * 0.8F);
        }

        FawDamage damageSource = EntityUtil.genFawDamage(attacker, core, gemstone, modifier);

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
            FinalAttrValue finalEnemyBreakingTime = FawItemUtil.calcuAttribute(EnemyBreakingTime, core, modifier, attackPlayer);
            int enemyBreakingTime = finalEnemyBreakingTime.getInt();

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
        return calcuAttribute(base, 0, 0, deltaMaster);

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
        return calcuAttribute(base, deltaGem, rateGem, 0);
    }

    /**
     * @param base
     * @param deltaGem
     * @param rateGem
     * @param deltaMaster
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static float calcuAttribute(float base, float deltaGem, float rateGem, float deltaMaster) {
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
    public static int calcuAttribute(int base, int deltaMaster) {
        return calcuAttribute(base, 0, 0, deltaMaster);

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
        return calcuAttribute(base, deltaGem, rateGem, 0);
    }


    /**
     * @param base
     * @param deltaGem
     * @param rateGem
     * @param deltaMaster
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    public static int calcuAttribute(int base, int deltaGem, float rateGem, int deltaMaster) {
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
        return (int) (base * (1 + rate));
    }

    /**
     * @param base
     * @param rate
     * @return
     */
    public static float calcuAttributeInRate(float base, float rate) {
        if (base < 0) {
            return -1;
        } else if (rate == 0) {
            return base;
        }
        return base * (1 + rate);
    }

    @SideOnly(Side.CLIENT)
    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, Number value,
                                           @Nullable String format, @Nullable String unit) {
        addAttributeTooltip(tooltip, attrTranslateKey, value, format, true, unit);
    }

    @SideOnly(Side.CLIENT)
    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, Number value,
                                           @Nullable String format) {
        addAttributeTooltip(tooltip, attrTranslateKey, value, format, null);
    }

    @SideOnly(Side.CLIENT)
    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, Number value) {
        addAttributeTooltip(tooltip, attrTranslateKey, value, null);
    }

    @SideOnly(Side.CLIENT)
    public static void addAttributeTooltip(@Nonnull List<String> tooltip, @Nonnull String attrTranslateKey, Number value,
                                           @Nullable String format, boolean isStripTrailingZero, @Nullable String unit) {
        String valueContent = format != null ? String.format(format, value) : value.toString();
        if (isStripTrailingZero) {
            valueContent = StringUtil.stripTrailingZero(valueContent);
        }
        String unitContent = unit != null ? " " + TextFormatting.WHITE + I18n.format(unit) : "";
        String content = TextFormatting.YELLOW +
                I18n.format(attrTranslateKey) + " : " +
                valueContent +
                unitContent;
        tooltip.add(content);
    }

    @SideOnly(Side.CLIENT)
    public static void addPassiveSkillTooltip(@Nonnull List<String> tooltip, @Nonnull IPassiveSkill<?> passiveSkill) {
        tooltip.add(TextFormatting.BLUE +
                I18n.format(SkillUtil.getNameI18nKey(passiveSkill)));
    }

    public static String getNameI18nKey(WeaponPart weaponPart) {
        return I18ns.endWithName(I18ns.prefixItem(weaponPart.getRegisterName()));
    }

    public static FinalAttrValue calcuAttribute(@Nonnull Attribute attribute, @Nonnull WeaponCore core) {
        return calcuAttribute(attribute, core, null, null);
    }

    public static FinalAttrValue calcuAttribute(@Nonnull Attribute attribute, @Nonnull WeaponCore core, @Nullable Modifier<?> modifier) {
        return calcuAttribute(attribute, core, modifier, null);
    }

    public static FinalAttrValue calcuAttribute(@Nonnull Attribute attribute, @Nonnull WeaponCore core, @Nullable Modifier<?> modifier, @Nullable EntityPlayer player) {
        BasicAttrValue baseAttrValue = core.getValue(attribute);
        //Master
        MasterCapability master = null;
        if (player != null) {
            master = player.getCapability(CapabilityRegistry.Master_Capability, null);
        }
        AttrDelta masterValue = null;
        if (master != null) {
            masterValue = MasterUtil.getAttributeValue(master, core.getWeaponType(), attribute);
        }

        //Modifier
        AttrModifier attrModifier = null;
        if (modifier != null) {
            attrModifier = modifier.getValue(attribute);
        }

        return attribute.compute(baseAttrValue, attrModifier, masterValue);
    }
}
