package net.liplum.api.weapon;

import net.liplum.I18ns;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.WeaponRegistry;
import net.liplum.attributes.Attribute;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.Utils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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

import static net.liplum.Attributes.Generic.*;

public abstract class WeaponBaseItem<CoreType extends WeaponCore> extends FawItem {
    @Nonnull
    protected final List<Attribute> allAttributes;

    public WeaponBaseItem() {
        super();
        WeaponRegistry.register(this.getWeaponType(), this);
        this.allAttributes = initAllAttributes();
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return super.showDurabilityBar(stack);
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @Nonnull
    protected List<Attribute> initAllAttributes() {
        LinkedList<Attribute> list = new LinkedList<>();
        list.add(Strength);
        list.add(AbilityPower);
        list.add(AttackReach);
        list.add(CoolDown);
        return list;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        boolean vanillaAdvanced = flagIn.isAdvanced();
        boolean shiftPressed = Utils.isShiftDown();
        boolean altPressed = Utils.isAltDown();
        TooltipOption tooltipOption = new TooltipOption(shiftPressed, altPressed, vanillaAdvanced);
        CoreType core = getCore();
        IGemstone gemstone = GemUtil.getGemstoneFrom(stack);
        Modifier<?> modifier = null;
        IPassiveSkill<?>[] passiveSkills = null;
        if (gemstone != null) {
            passiveSkills = gemstone.getPassiveSkillsOf(core);
            modifier = gemstone.getModifierOf(core);
        }
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        LinkedList<String> gemstoneTooltip = new LinkedList<>();
        LinkedList<String> attributesTooltip = new LinkedList<>();
        LinkedList<String> passiveSkillsTooltip = new LinkedList<>();

        boolean gemstoneShown = addGemstoneTooltip(stack, gemstone, gemstoneTooltip, tooltipOption)
                && gemstoneTooltip.size() != 0;

        boolean attributesShown = addAttributesTooltip(stack, modifier, player, tooltipOption, attributesTooltip)
                && attributesTooltip.size() != 0;

        boolean passiveSkillsShown = addPassiveSkillsTooltip(stack, passiveSkills, passiveSkillsTooltip, tooltipOption)
                && passiveSkillsTooltip.size() != 0;

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
        }

        if (tooltipOption.isVanillaAdvanced()) {
            tooltip.add("");
        }
    }

    /**
     * @return whether it was added something.
     */
    @SideOnly(Side.CLIENT)
    public boolean addGemstoneTooltip(@Nonnull ItemStack stack, @Nullable IGemstone gemstone, @Nonnull List<String> gemstoneTooltip, TooltipOption option) {
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
    public boolean addAttributesTooltip(@Nonnull ItemStack stack, @Nullable Modifier<?> modifier, @Nullable EntityPlayer player, TooltipOption option, @Nonnull List<String> attributesTooltip) {
        boolean added = false;
        CoreType core = getCore();
        for (Attribute attribute : allAttributes) {
            if ((!attribute.needMoreDetailsToShown()) ||
                    (attribute.needMoreDetailsToShown() && option.isMoreDetailsShown())
            ) {
                FinalAttrValue finalValue = FawItemUtil.calcuAttribute(attribute, core, modifier, player);
                if (attribute.canTooltipShow(finalValue.getNumber())) {
                    FawItemUtil.addAttributeTooltip(
                            attributesTooltip, attribute.getI18nKey(),
                            attribute.getTooltipShownValue(finalValue.getNumber()),
                            attribute.getFormat(),
                            attribute.isStripTrailingZero(),
                            ((attribute.hasUnit() && option.isUnitShown()) ?
                                    attribute.getUnit() : null)
                    );
                    added = true;
                }
            }
        }
        return added;
    }

    /**
     * @return whether it was added something.
     */
    @SideOnly(Side.CLIENT)
    public boolean addPassiveSkillsTooltip(@Nonnull ItemStack stack, @Nullable IPassiveSkill<?>[] passiveSkills, @Nonnull List<String> passiveSkillsTooltip, TooltipOption option) {
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
        Modifier<?> modifier = GemUtil.getModifierFrom(stack);
        FinalAttrValue finalAttackReach = FawItemUtil.calcuAttribute(AttackReach, getCore(), modifier, player);
        if (AttackReach.isDefaultValue(finalAttackReach)) {
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

    @Nonnull
    public abstract WeaponType getWeaponType();

}
