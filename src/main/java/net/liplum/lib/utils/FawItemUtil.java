package net.liplum.lib.utils;

import com.google.common.collect.Multimap;
import net.liplum.Vanilla;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.*;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.events.weapon.*;
import net.liplum.items.GemstoneItem;
import net.liplum.items.tools.InlayingToolItem;
import net.liplum.lib.FawDamage;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        EntityLivingBase targetLiving = null;
        EntityPlayer attackerPlayer = null;
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
                .setWeaponCore(core)
                .setPlayer(attackerPlayer)
                .setModifier(modifier);

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

        FawDamage damageSource = EntityUtil.genFawDamage(attacker, core, gemstone, modifier);

        DamageArgs initialDamage = new DamageArgs(finalDamage, damageSource, target);
        WeaponAttackingArgs attackingArgs = new WeaponAttackingArgs();
        attackingArgs.setWorld(world)
                .setAttacker(attacker)
                .setTarget(target)
                .setItemStack(itemStack)
                .setModifier(modifier)
                .setWeaponCore(core)
                .setInitialDamage(initialDamage)
                .setFullAttack(isFullAttack);

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
        //If attacker is not a player, it will be null.
        if (attackerPlayer != null &&
                //It actually made some sounds.
                sound != null
        ) {
            attackerPlayer.playSound(sound, 1, 1);
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
                .setTotalDamage(totalDamage)
                .setFullAttack(isFullAttack);

        WeaponAttackedEvent postAttackEvent = new WeaponAttackedEvent(postArgs);
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

    public static Iterable<ItemStack> getAllPossibleFawWeaponSlotsFormPlayerInventory(EntityPlayer player) {
        return () -> new Iterator<ItemStack>() {
            private int currentIndex = 0;
            private final InventoryPlayer inventory = player.inventory;

            @Override
            public boolean hasNext() {
                return currentIndex < Vanilla.PlayerAllInventorySlotCount;
            }

            @Override
            public ItemStack next() {
                ItemStack itemStack = inventory.getStackInSlot(currentIndex);
                currentIndex++;
                if (currentIndex == Vanilla.PlayerMainInventorySlotCount) {
                    currentIndex += Vanilla.PlayerArmorInventorySlotCount;
                }
                return itemStack;
            }
        };
    }

    public static boolean heatWeaponType(EntityPlayer player, WeaponType weaponType) {
        Map<WeaponBaseItem, Integer> weaponAndMinimumCoolDown = new HashMap<>();
        AttrCalculator calculator = new AttrCalculator()
                .setPlayer(player);
        for (ItemStack itemStack : getAllPossibleFawWeaponSlotsFormPlayerInventory(player)) {
            Item item = itemStack.getItem();
            if (item instanceof WeaponBaseItem) {
                WeaponBaseItem weapon = (WeaponBaseItem) item;
                if (weapon.getWeaponType() == weaponType) {
                    calculator.setWeaponCore(weapon.getCore())
                            .setModifier(GemUtil.getModifierFrom(itemStack));
                    int coolDown = calculator.calcu(CoolDown).getInt();
                    if (coolDown != 0) {
                        if (weaponAndMinimumCoolDown.containsKey(weapon)) {
                            Integer formerCoolDown = weaponAndMinimumCoolDown.get(weapon);
                            if (coolDown < formerCoolDown) {
                                weaponAndMinimumCoolDown.put(weapon, coolDown);
                            }
                        } else {
                            weaponAndMinimumCoolDown.put(weapon, coolDown);
                        }
                    }
                }
            }
        }
        boolean hasOneSucceed = false;
        if (weaponAndMinimumCoolDown.size() > 0) {
            for (Map.Entry<WeaponBaseItem, Integer> entry : weaponAndMinimumCoolDown.entrySet()) {
                hasOneSucceed |= ItemTool.heatItemIfSurvival(player, entry.getKey(), entry.getValue());
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

    public static void damageWeapon(WeaponBaseItem weapon, ItemStack itemStack, int amount, EntityLivingBase entity) {
        if (amount <= 0) {
            return;
        }
        WeaponDurabilityEvent.Damaged damagedEvent = new WeaponDurabilityEvent.Damaged(itemStack, weapon, entity, amount);
        MinecraftForge.EVENT_BUS.post(damagedEvent);
        int finalAmount = damagedEvent.getAmount();
        if (finalAmount > 0) {
            ItemTool.decreaseItemDurability(itemStack, finalAmount);
        }
    }

    public static void healWeapon(WeaponBaseItem weapon, ItemStack itemStack, int amount, EntityLivingBase entity) {
        if (amount <= 0) {
            return;
        }
        WeaponDurabilityEvent.Healed healedEvent = new WeaponDurabilityEvent.Healed(itemStack, weapon, entity, amount);
        MinecraftForge.EVENT_BUS.post(healedEvent);
        int finalAmount = healedEvent.getAmount();
        if (finalAmount > 0) {
            ItemTool.increaseItemDurability(itemStack, finalAmount);
        }
    }
}
