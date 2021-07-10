package net.liplum.api.weapon;

import com.google.common.collect.Multimap;
import net.liplum.I18ns;
import net.liplum.Names;
import net.liplum.Vanilla;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.WeaponRegistry;
import net.liplum.attributes.Attribute;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.utils.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
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
        WeaponRegistry.register(this);
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
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) {
            return;
        }
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
        LinkedList<String> weaponTypeTooltip = new LinkedList<>();
        LinkedList<String> gemstoneTooltip = new LinkedList<>();
        LinkedList<String> attributesTooltip = new LinkedList<>();
        LinkedList<String> passiveSkillsTooltip = new LinkedList<>();

        addWeaponTypeTooltip(stack, gemstone, weaponTypeTooltip, tooltipOption);
        addGemstoneTooltip(stack, gemstone, gemstoneTooltip, tooltipOption);
        addAttributesTooltip(stack, modifier, player, tooltipOption, attributesTooltip);
        addPassiveSkillsTooltip(stack, passiveSkills, passiveSkillsTooltip, tooltipOption);

        boolean weaponTypeShown = weaponTypeTooltip.size() != 0;
        boolean gemstoneShown = gemstoneTooltip.size() != 0;
        boolean attributesShown = attributesTooltip.size() != 0;
        boolean passiveSkillsShown = passiveSkillsTooltip.size() != 0;

        if (weaponTypeShown) {
            tooltip.addAll(weaponTypeTooltip);
        }

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

    @SideOnly(Side.CLIENT)
    public void addWeaponTypeTooltip(@Nonnull ItemStack stack, @Nullable IGemstone gemstone, @Nonnull List<String> weaponTypeTooltip, TooltipOption option) {
        weaponTypeTooltip.add(I18n.format(FawI18n.getNameI18nKey(getWeaponType())));
    }

    @SideOnly(Side.CLIENT)
    public void addGemstoneTooltip(@Nonnull ItemStack stack, @Nullable IGemstone gemstone, @Nonnull List<String> gemstoneTooltip, TooltipOption option) {
        if (gemstone != null) {
            gemstoneTooltip.add(
                    I18n.format(I18ns.Tooltip.Inlaid) + " " +
                            TextFormatting.RED +
                            I18n.format(FawI18n.getNameI18nKey(gemstone)));
        } else {
            gemstoneTooltip.add(I18n.format(I18ns.Tooltip.NoGemstone));
        }
    }

    @SideOnly(Side.CLIENT)
    public void addAttributesTooltip(@Nonnull ItemStack stack, @Nullable Modifier<?> modifier, @Nullable EntityPlayer player, TooltipOption option, @Nonnull List<String> attributesTooltip) {
        CoreType core = getCore();
        for (Attribute attribute : allAttributes) {
            if(!attribute.isShownInTooltip()){
                continue;
            }
            if (attribute.needMoreDetailsToShown() && !option.isMoreDetailsShown()) {
                continue;
            }
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
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addPassiveSkillsTooltip(@Nonnull ItemStack stack, @Nullable IPassiveSkill<?>[] passiveSkills, @Nonnull List<String> passiveSkillsTooltip, TooltipOption option) {
        if (passiveSkills != null) {
            for (IPassiveSkill<?> passiveSkill : passiveSkills) {
                if (passiveSkill.isShownInTooltip()) {
                    FawItemUtil.addPassiveSkillTooltip(passiveSkillsTooltip, passiveSkill);
                }
            }
        }
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

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, @Nonnull ItemStack stack) {
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND) {
            Modifier<?> modifier = GemUtil.getModifierFrom(stack);
            FinalAttrValue finalAttackSpeed = FawItemUtil.calcuAttribute(AttackSpeed, getCore(), modifier);
            double attackSpeed = finalAttackSpeed.getFloat() - Vanilla.DefaultAttackSpeed;
            map.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    ItemTool.genAttrModifier(ATTACK_SPEED_MODIFIER, Vanilla.AttrModifierType.DeltaAddition, Names.WeaponAttributeModifier, attackSpeed));
        }
        return map;
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