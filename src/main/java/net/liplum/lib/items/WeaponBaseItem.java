package net.liplum.lib.items;

import net.liplum.I18ns;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class WeaponBaseItem<CoreType extends IWeaponCore> extends FawItem {
    public WeaponBaseItem() {
        super();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return super.showDurabilityBar(stack);
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        boolean isAdvanced = flagIn.isAdvanced() || Utils.isShiftDown();
        CoreType core = getCore();
        IGemstone gemstone = GemUtil.getGemstoneFrom(stack);
        IPassiveSkill<?>[] passiveSkills = null;
        if (gemstone != null) {
            passiveSkills = gemstone.getPassiveSkillsOf(core);
        }

        if (addGemstoneTooltip(stack, gemstone, tooltip, isAdvanced)) {
            tooltip.add("");
        }
        if (addAttributesTooltip(stack, tooltip, isAdvanced)) {
            tooltip.add("");
        }
        addPassiveSkillsTooltip(stack, passiveSkills, tooltip, isAdvanced);
    }

    /**
     * @return whether it was added something.
     */
    public boolean addGemstoneTooltip(@Nonnull ItemStack stack, @Nullable IGemstone gemstone, @Nonnull List<String> tooltip, boolean isAdvanced) {
        if (gemstone != null) {
            tooltip.add(
                    I18n.format(I18ns.Tooltip.Inlaid) + " " +
                            TextFormatting.RED +
                            I18n.format(GemUtil.getNameI18nKey(gemstone)));
        } else {
            tooltip.add(I18n.format(I18ns.Tooltip.NoGemstone));
        }
        return true;
    }

    /**
     * @return whether it was added something.
     */
    public boolean addAttributesTooltip(@Nonnull ItemStack stack, @Nonnull List<String> tooltip, boolean isAdvanced) {
        boolean added = false;
        CoreType core = getCore();
        float strength = core.getStrength();
        int coolDown = core.getCoolDown();
        if (strength > 0) {
            FawItemUtil.addAttributeTooltip(tooltip, I18ns.Attribute.Generic.Strength, strength);
            added = true;
        }
        if (coolDown > 0) {
            FawItemUtil.addAttributeTooltip(tooltip, I18ns.Attribute.Generic.CoolDown, coolDown);
            added = true;
        }
        return added;
    }

    /**
     * @return whether it was added something.
     */
    public boolean addPassiveSkillsTooltip(@Nonnull ItemStack stack, @Nullable IPassiveSkill<?>[] passiveSkills, @Nonnull List<String> tooltip, boolean isAdvanced) {
        if (passiveSkills != null) {
            for (IPassiveSkill<?> passiveSkill : passiveSkills) {
                FawItemUtil.addPassiveSkillTooltip(tooltip, passiveSkill);
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

    public double getAttackDistance(){
        return getCore().getAttackDistance();
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
    public boolean dealDamage(ItemStack stack, EntityLivingBase attacker, Entity target, DamageSource damageSource, float damage) {
        return target.attackEntityFrom(damageSource, damage);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return FawItemUtil.attackEntity(stack, this, player, entity);
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
    public abstract CoreType getCore();

}
