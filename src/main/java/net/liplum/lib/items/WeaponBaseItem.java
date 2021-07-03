package net.liplum.lib.items;

import net.liplum.AttributeDefault;
import net.liplum.I18ns;
import net.liplum.Vanilla;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IMagicToolCore;
import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public abstract class WeaponBaseItem<CoreType extends IWeaponCore> extends FawItem {
    public WeaponBaseItem() {
        super();
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return super.showDurabilityBar(stack);
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        boolean isAdvanced = flagIn.isAdvanced() || Utils.isShiftDown();
        CoreType core = getCore();
        IGemstone gemstone = GemUtil.getGemstoneFrom(stack);
        IPassiveSkill<?>[] passiveSkills = null;
        if (gemstone != null) {
            passiveSkills = gemstone.getPassiveSkillsOf(core);
        }
        LinkedList<String> gemstoneTooltip = new LinkedList<>();
        LinkedList<String> attributesTooltip = new LinkedList<>();
        LinkedList<String> passiveSkillsTooltip = new LinkedList<>();

        boolean gemstoneShown = addGemstoneTooltip(stack, gemstone, gemstoneTooltip, isAdvanced)
                && gemstoneTooltip.size() != 0;

        boolean attributesShown = addAttributesTooltip(stack, attributesTooltip, isAdvanced)
                && attributesTooltip.size() != 0;

        boolean passiveSkillsShown = addPassiveSkillsTooltip(stack, passiveSkills, passiveSkillsTooltip, isAdvanced)
                && attributesTooltip.size() != 0;

        if (gemstoneShown) {
            tooltip.addAll(gemstoneTooltip);
            if (attributesShown || passiveSkillsShown) {
                tooltip.add("");
            }
        }
        if (attributesShown) {
            tooltip.addAll(attributesTooltip);
            if (passiveSkillsShown) {
                tooltip.add("");
            }
        }
        if (passiveSkillsShown) {
            tooltip.addAll(passiveSkillsTooltip);
            if (flagIn.isAdvanced()) {
                tooltip.add("");
            }
        }
    }

    /**
     * @return whether it was added something.
     */
    @SideOnly(Side.CLIENT)
    public boolean addGemstoneTooltip(@Nonnull ItemStack stack, @Nullable IGemstone gemstone, @Nonnull List<String> gemstoneTooltip, boolean isAdvanced) {
        if (gemstone != null) {
            gemstoneTooltip.add(
                    I18n.format(I18ns.Tooltip.Inlaid) + " " +
                            TextFormatting.RED +
                            I18n.format(GemUtil.getNameI18nKey(gemstone)));
        } else {
            gemstoneTooltip.add(I18n.format(I18ns.Tooltip.NoGemstone));
        }
        return true;
    }

    /**
     * @return whether it was added something.
     */
    @SideOnly(Side.CLIENT)
    public boolean addAttributesTooltip(@Nonnull ItemStack stack, @Nonnull List<String> attributesTooltip, boolean isAdvanced) {
        boolean added = false;
        CoreType core = getCore();
        float strength = core.getStrength();
        if (strength > 0 && strength != AttributeDefault.Generic.Strength) {
            FawItemUtil.addAttributeTooltip(attributesTooltip, I18ns.Attribute.Generic.Strength, strength,null,null);
            added = true;
        }
        if (core instanceof IMagicToolCore) {
            IMagicToolCore magicCore = (IMagicToolCore) core;
            float ap = magicCore.getAbilityPower();
            if (ap > 0) {
                FawItemUtil.addAttributeTooltip(attributesTooltip, I18ns.Attribute.Generic.AbilityPower, ap,null,null);
                added = true;
            }
        }
        if (isAdvanced) {
            int coolDown = core.getCoolDown();
            if (coolDown > 0) {
                float coolDownSecond = (float) coolDown / Vanilla.TPS;
                FawItemUtil.addAttributeTooltip(attributesTooltip, I18ns.Attribute.Generic.CoolDown, coolDownSecond,
                        "%.1f",I18ns.Tooltip.Unit.Second);
                added = true;
            }

            double attackReach = core.getAttackReach();
            if (attackReach > 0 && attackReach != AttributeDefault.Generic.AttackReach) {
                FawItemUtil.addAttributeTooltip(attributesTooltip, I18ns.Attribute.Generic.AttackReach, attackReach,
                        "%.1f",I18ns.Tooltip.Unit.Unit);
                added = true;
            }
        }
        return added;
    }

    /**
     * @return whether it was added something.
     */
    @SideOnly(Side.CLIENT)
    public boolean addPassiveSkillsTooltip(@Nonnull ItemStack stack, @Nullable IPassiveSkill<?>[] passiveSkills, @Nonnull List<String> passiveSkillsTooltip, boolean isAdvanced) {
        if (passiveSkills != null) {
            for (IPassiveSkill<?> passiveSkill : passiveSkills) {
                FawItemUtil.addPassiveSkillTooltip(passiveSkillsTooltip, passiveSkill);
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the basic attack speed and it can be changed by modifier.
     * Range : (0,no limit]. A greater value stands for a slower speed.(Normal items are 4)
     *
     * @return the basic attack speed
     */
    public double getBaseAttackSpeed() {
        return 4;
    }

    /**
     * Deals damage to a target form the attacker.
     * It's called by {@link FawItemUtil#attackEntity(ItemStack, WeaponBaseItem, EntityLivingBase, Entity)}
     *
     * @param stack
     * @param attacker
     * @param target
     * @param damage
     * @return true if the target was hit.
     */
    public boolean dealDamage(@Nonnull ItemStack stack, @Nonnull EntityLivingBase attacker, @Nonnull Entity target, @Nonnull DamageSource damageSource, float damage) {
        return target.attackEntityFrom(damageSource, damage);
    }

    public boolean attackEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return FawItemUtil.attackEntity(stack, this, player, entity);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        double reach = getCore().getAttackReach();
        IModifier<?> modifier = GemUtil.getModifierFrom(stack);
        if (modifier != null) {
            reach = FawItemUtil.calcuAttribute(reach, modifier.getAttackReachDelta(), modifier.getAttackReachRate());
        }
        if (reach == AttributeDefault.Generic.AttackReach) {
            return attackEntity(stack, player, entity);
        } else {
            return true;
        }
    }

    public void reduceDurabilityOnHit(ItemStack stack, EntityPlayer player, float damage) {
        damage = Math.max(1f, damage / 10f);
        //TODO:This
        //ToolHelper.damageTool(stack, (int) damage, player);
    }

    /**
     * Whether player mines the block effectively
     *
     * @param state the block player is mining
     * @return false but you can override it
     */
    public boolean isEffective(IBlockState state) {
        return false;
    }

    /**
     * Gets the core.
     *
     * @return A core of this weapon.
     */
    @Nonnull
    public abstract CoreType getCore();

}
