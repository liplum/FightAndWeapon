package net.liplum.lib.utils;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.WeaponRegistry;
import net.liplum.api.registeies.WeaponTypeRegistry;
import net.liplum.api.weapon.*;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.events.weapon.FoundAmmoEvent;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.events.weapon.WeaponDurabilityEvent;
import net.liplum.items.GemstoneItem;
import net.liplum.items.tools.InlayingToolItem;
import net.liplum.items.weapons.bow.BowCore;
import net.liplum.items.weapons.bow.BowItem;
import net.liplum.lib.FawDamage;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static net.liplum.Attributes.Generic.*;

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
        return item instanceof WeaponBaseItem;
    }

    public static boolean isGemstone(@Nonnull Item item) {
        return item instanceof IGemstoneItem;
    }

    public static boolean isInlayingTool(ItemStack itemStack) {
        return isInlayingTool(itemStack.getItem());
    }

    public static boolean isInlayingTool(Item item) {
        return item instanceof InlayingToolItem;
    }


    /**
     * It calls {@link WeaponBaseItem#dealDamage(FawDamage, Entity, float)} to deal damage to the target.
     * It's called by {@link WeaponBaseItem#onLeftClickEntity(ItemStack, EntityPlayer, Entity)}.
     *
     * @param itemStack
     * @param weapon
     * @param attacker  the attacker
     * @param target    the target
     * @return whether this attack is successful
     */
    @LongSupport
    public static boolean attackEntity(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weapon, EntityLivingBase attacker, Entity target) {
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
        @Nullable EntityLivingBase targetLiving = null;
        @Nullable EntityPlayer attackerPlayer = null;
        if (target instanceof EntityLivingBase) {
            targetLiving = (EntityLivingBase) target;
        }

        if (attacker instanceof EntityPlayer) {
            attackerPlayer = (EntityPlayer) attacker;
            if (target instanceof EntityPlayer) {
                EntityPlayer targetPlayer = (EntityPlayer) target;
                if (!attackerPlayer.canAttackPlayer(targetPlayer)) {
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
        Modifier modifier = GemUtil.getModifierFrom(itemStack);
        AttrCalculator calculator = new AttrCalculator()
                .weapon(weapon)
                .entity(attacker)
                .modifier(modifier)
                .itemStack(itemStack);

        FinalAttrValue finalStrength = calculator.calcu(Strength);

        finalDamage += finalStrength.getFloat();
        SoundEvent sound = null;

        boolean isFullAttack = true;
        if (attackerPlayer != null) {
            float cooldown = ((EntityPlayer) attacker).getCooledAttackStrength(0.5F);
            sound = cooldown > 0.9f ? SoundEvents.ENTITY_PLAYER_ATTACK_STRONG : SoundEvents.ENTITY_PLAYER_ATTACK_WEAK;
            finalDamage *= (0.2F + cooldown * cooldown * 0.8F);
            isFullAttack = cooldown >= 1F;
        }

        FawDamage damageSource = EntityUtil.genFawDamage(attacker, itemStack);

        DamageArgs initialDamage = new DamageArgs(finalDamage, damageSource, target);
        WeaponAttackEvent.Attacking.Args attackingArgs = new WeaponAttackEvent.Attacking.Args();
        attackingArgs.world(world)
                .attacker(attacker)
                .target(target)
                .itemStack(itemStack)
                .modifier(modifier)
                .weapon(weapon)
                .initialDamage(initialDamage)
                .setFullAttack(isFullAttack)
                .calculator(calculator);

        WeaponAttackEvent.Attacking attackingEvent = new WeaponAttackEvent.Attacking(attackingArgs);
        MinecraftForge.EVENT_BUS.post(attackingEvent);

        float totalDamage = 0;
        boolean isHitSuccessfully = false;
        List<DamageArgs> allDamages = attackingArgs.getAllDamages();
        for (DamageArgs damageArgs : allDamages) {
            float singleDamage = damageArgs.getDamage();
            boolean currentHitSuccessfully =
                    weapon.dealDamage(itemStack, attacker, damageArgs.getTarget(), damageArgs.getDamageSource(), singleDamage);
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
            FinalAttrValue finalEnemyBreakingTime = calculator.calcu(EnemyBreakingTime);
            int enemyBreakingTime = finalEnemyBreakingTime.getInt();
            if (enemyBreakingTime >= 0) {
                target.hurtResistantTime = enemyBreakingTime;
            }

            //TODO:Something here!
            if (attackerPlayer != null && targetLiving != null) {
                itemStack.hitEntity(targetLiving, attackerPlayer);
                if (!attackerPlayer.isCreative()) {
                    weapon.reduceDurabilityOnHit(itemStack, attackerPlayer, totalDamage);
                }
            }
        }
        if (isWeaponBroken(itemStack)) {
            sound = SoundEvents.ENTITY_ITEM_BREAK;
        }
        //If attacker is not a player, it will be null.
        if (attackerPlayer != null &&
                //It actually made some sounds.
                sound != null
        ) {
            attackerPlayer.playSound(sound, 1, 1);
        }

        //TODO:More!!!
        WeaponAttackEvent.Attacked.Args postArgs = new WeaponAttackEvent.Attacked.Args();
        postArgs.world(world)
                .attacker(attacker)
                .target(target)
                .itemStack(itemStack)
                .modifier(modifier)
                .initialDamage(initialDamage)
                .weapon(weapon)
                .setHitSuccessfully(isHitSuccessfully)
                .setTotalDamage(totalDamage)
                .setFullAttack(isFullAttack)
                .calculator(calculator);

        WeaponAttackEvent.Attacked postAttackEvent = new WeaponAttackEvent.Attacked(postArgs);
        MinecraftForge.EVENT_BUS.post(postAttackEvent);

        return isHitSuccessfully;
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
    @LongSupport
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
                I18n.format(FawI18n.getNameI18nKey(passiveSkill)));
    }

    @Nonnull
    public static Iterable<ItemStack> getAllFawWeaponSlotsFormPlayerInventory(@Nonnull EntityPlayer player) {
        return ItemUtil.getMainAndOffHandSlots(player, FawItemUtil::isFawWeapon);
    }

    @LongSupport
    public static boolean heatWeaponType(@Nonnull EntityLivingBase player, @Nonnull WeaponType weaponType) {
        if (player instanceof EntityPlayer) {
            return heatWeaponType((EntityPlayer) player, weaponType);
        }
        return false;
    }

    @LongSupport
    public static boolean heatWeaponType(@Nonnull EntityPlayer player, @Nonnull WeaponType weaponType) {
        if (player.isCreative()) {
            return false;
        }
        boolean hasOneSucceed = false;
        AttrCalculator calculator = new AttrCalculator()
                .entity(player);
        for (ItemStack itemStack : getAllFawWeaponSlotsFormPlayerInventory(player)) {
            Item item = itemStack.getItem();
            WeaponBaseItem weapon = (WeaponBaseItem) item;
            if (weapon.getWeaponType() == weaponType) {
                calculator.weapon(weapon)
                        .modifier(GemUtil.getModifierFrom(itemStack));
                int coolDown = calculator.calcu(CoolDown).getInt();
                if (coolDown != 0) {
                    hasOneSucceed |= ItemUtil.heatItem(player, weapon, coolDown);
                }
            }
        }
        return hasOneSucceed;
    }

    public static boolean releaseWeaponSkill(@Nonnull WeaponCore core, @Nullable Modifier modifier, @Nonnull WeaponSkillArgs args) {
        if (modifier != null) {
            return modifier.releaseSkill(core, args);
        } else {
            return core.releaseSkill(args);
        }
    }

    public static void applyAttrModifier(@Nonnull WeaponCore core, @Nullable Modifier modifier,
                                         @Nonnull WeaponAttrModifierContext context) {
        if (modifier != null) {
            modifier.applyAttrModifier(core, context);
        } else {
            core.applyAttrModifier(context);
        }
    }

    public static void damageWeapon(WeaponBaseItem weapon, ItemStack itemStack, @Nonnegative int amount, @Nonnull EntityLivingBase entity) {
        if (amount == 0) {
            return;
        }
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isCreative()) {
                return;
            }
        }
        WeaponDurabilityEvent.Damaged damagedEvent = new WeaponDurabilityEvent.Damaged(itemStack, weapon, entity, amount);
        MinecraftForge.EVENT_BUS.post(damagedEvent);
        int finalAmount = damagedEvent.getAmount();
        if (finalAmount > 0) {
            ItemUtil.decreaseItemDurability(itemStack, finalAmount);
        }
    }

    public static void healWeapon(WeaponBaseItem weapon, ItemStack itemStack, @Nonnegative int amount, EntityLivingBase entity) {
        if (amount == 0) {
            return;
        }
        WeaponDurabilityEvent.Healed healedEvent = new WeaponDurabilityEvent.Healed(itemStack, weapon, entity, amount);
        MinecraftForge.EVENT_BUS.post(healedEvent);
        int finalAmount = healedEvent.getAmount();
        if (finalAmount > 0) {
            ItemUtil.increaseItemDurability(itemStack, finalAmount);
        }
    }

    public static boolean isWeaponBroken(@Nonnull ItemStack itemStack) {
        return (itemStack.getItem() instanceof WeaponBaseItem) && itemStack.getItemDamage() == itemStack.getMaxDamage();
    }

    public static boolean hasAmmo(@Nonnull EntityPlayer player, @Nonnull BowCore bowCore) {
        return ItemUtil.hasAmmo(player, bowCore::isAmmo);
    }

    public static boolean hasAmmo(@Nonnull EntityLivingBase entity, @Nonnull BowCore bowCore) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            return ItemUtil.hasAmmo(player, bowCore::isAmmo);
        }
        return true;
    }

    @Nonnull
    public static ItemStack findAmmo(@Nonnull EntityPlayer player, @Nonnull BowItem bow, @Nonnull ItemStack itemStack, @Nullable Modifier modifier) {
        BowCore core = bow.getConcreteCore();
        ItemStack res = ItemUtil.findAmmo(player, core::isAmmo);
        if (res.isEmpty() && player.isCreative()) {
            res = core.getDefaultAmmo(player, itemStack);
        }
        FoundAmmoEvent event = new FoundAmmoEvent(bow, modifier, itemStack, player, res);
        MinecraftForge.EVENT_BUS.post(event);
        return event.ammo();
    }

    @Nonnull
    public static ItemStack findAmmo(@Nonnull EntityLivingBase entity, @Nonnull BowItem bow, @Nonnull ItemStack itemStack, @Nullable Modifier modifier) {
        BowCore core = bow.getConcreteCore();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            return findAmmo(player, bow, itemStack, modifier);
        }
        return core.getDefaultAmmo(entity, itemStack);
    }

    public static void clearWeaponCoolDown(@Nonnull EntityPlayer player, @Nonnull WeaponType weaponType) {
        Set<WeaponBaseItem> weapons = WeaponRegistry.getWeaponsOf(weaponType);
        CooldownTracker cooldownTracker = player.getCooldownTracker();
        for (WeaponBaseItem weapon : weapons) {
            cooldownTracker.removeCooldown(weapon);
        }
    }

    public static void clearAllWeaponsCoolDown(@Nonnull EntityPlayer player) {
        CooldownTracker cooldownTracker = player.getCooldownTracker();
        for (WeaponType weaponType : WeaponTypeRegistry.getAllWeaponTypes()) {
            Set<WeaponBaseItem> weapons = WeaponRegistry.getWeaponsOf(weaponType);
            for (WeaponBaseItem weapon : weapons) {
                cooldownTracker.removeCooldown(weapon);
            }
        }
    }
}
