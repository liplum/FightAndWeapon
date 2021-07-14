package net.liplum.api.weapon;

import com.google.common.collect.Multimap;
import net.liplum.I18ns;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.WeaponRegistry;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.attributes.IAttribute;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.FawI18n;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static net.liplum.Attributes.Generic.*;

public abstract class WeaponBaseItem extends FawItem {
    @Nonnull
    private final WeaponCore weaponCore;
    @Nonnull
    private final WeaponType weaponType;

    public WeaponBaseItem(@Nonnull WeaponCore weaponCore) {
        super();
        this.weaponCore = weaponCore;
        this.weaponType = weaponCore.getWeaponType();
        AttrCalculator calculator = new AttrCalculator(weaponCore);
        setMaxDamage(calculator.calcu(Durability).getInt());
        WeaponRegistry.register(this);
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
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) {
            return;
        }
        boolean vanillaAdvanced = flagIn.isAdvanced();
        boolean shiftPressed = Utils.isShiftDown();
        boolean altPressed = Utils.isAltDown();
        TooltipOption tooltipOption = new TooltipOption(shiftPressed, altPressed, vanillaAdvanced);
        WeaponCore core = getCore();
        IGemstone gemstone = GemUtil.getGemstoneFrom(stack);
        Modifier modifier = null;
        Collection<IPassiveSkill<?>> passiveSkills = null;
        if (gemstone != null) {
            passiveSkills = gemstone.getPassiveSkillsOf(core);
            modifier = gemstone.getModifierOf(core);
        }
        AttrCalculator calculator = new AttrCalculator()
                .setWeaponCore(core)
                .setModifier(modifier)
                .setPlayer(player);

        LinkedList<String> weaponTypeTooltip = new LinkedList<>();
        LinkedList<String> gemstoneTooltip = new LinkedList<>();
        LinkedList<String> attributesTooltip = new LinkedList<>();
        LinkedList<String> passiveSkillsTooltip = new LinkedList<>();

        addWeaponTypeTooltip(stack, gemstone, weaponTypeTooltip, tooltipOption);
        addGemstoneTooltip(stack, gemstone, gemstoneTooltip, tooltipOption);
        addAttributesTooltip(stack, calculator, tooltipOption, attributesTooltip);
        if (passiveSkills != null) {
            addPassiveSkillsTooltip(stack, passiveSkills, passiveSkillsTooltip, tooltipOption);
        }

        boolean weaponTypeShown = weaponTypeTooltip.size() > 0;
        boolean gemstoneShown = gemstoneTooltip.size() > 0;
        boolean attributesShown = attributesTooltip.size() > 0;
        boolean passiveSkillsShown = passiveSkillsTooltip.size() > 0;
        boolean vanillaAttackSpeedTipShown = !AttackSpeed.isDefaultValue(calculator.calcu(AttackSpeed));

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

        if (tooltipOption.isVanillaAdvanced() && !vanillaAttackSpeedTipShown) {
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
    public void addAttributesTooltip(@Nonnull ItemStack stack, AttrCalculator calculator, TooltipOption option, @Nonnull List<String> attributesTooltip) {
        WeaponCore core = getCore();
        List<IAttribute> sortedAttr = core.getAllAttributes().stream()
                .filter(IAttribute::isShownInTooltip)
                .sorted(Comparator.comparing(IAttribute::getDisplayPriority))
                .collect(Collectors.toList());

        for (IAttribute attribute : sortedAttr) {
            if (attribute.needMoreDetailsToShown() && !option.isMoreDetailsShown()) {
                continue;
            }
            FinalAttrValue finalValue = calculator.calcu(attribute);
            if (attribute.canTooltipShow(finalValue)) {
                FawItemUtil.addAttributeTooltip(
                        attributesTooltip, attribute.getI18nKey(),
                        attribute.getTooltipShownValue(finalValue),
                        attribute.getFormat(),
                        attribute.isStripTrailingZero(),
                        ((attribute.hasUnit() && option.isUnitShown()) ?
                                attribute.getUnit() : null)
                );
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addPassiveSkillsTooltip(@Nonnull ItemStack stack, @Nonnull Collection<IPassiveSkill<?>> passiveSkills, @Nonnull List<String> passiveSkillsTooltip, TooltipOption option) {
        for (IPassiveSkill<?> passiveSkill : passiveSkills) {
            if (passiveSkill.isShownInTooltip()) {
                FawItemUtil.addPassiveSkillTooltip(passiveSkillsTooltip, passiveSkill);
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
        Modifier modifier = GemUtil.getModifierFrom(stack);
        AttrCalculator calculator = new AttrCalculator()
                .setWeaponCore(getCore())
                .setModifier(modifier)
                .setPlayer(player);
        FinalAttrValue finalAttackReach = calculator.calcu(AttackReach);
        if (AttackReach.isDefaultValue(finalAttackReach)) {
            return attackEntity(stack, player, entity);
        } else {
            return true;
        }
    }

    public void reduceDurabilityOnHit(ItemStack stack, EntityPlayer player, float attackDamage) {
        float weaponDamage = MathUtil.fixMin(1f, attackDamage / 10f);
        FawItemUtil.damageWeapon(this, stack, (int) weaponDamage, player);
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
        Modifier modifier = GemUtil.getModifierFrom(stack);
        WeaponAttrModifierContext context = new WeaponAttrModifierContext(stack, new AttrCalculator()
                .setWeaponCore(getCore())
                .setModifier(modifier), slot, map);
        FawItemUtil.applyAttrModifier(weaponCore, modifier, context);
        return map;
    }

    /**
     * Gets the core.
     *
     * @return A core of this weapon.
     */
    @Nonnull
    public WeaponCore getCore() {
        return weaponCore;
    }

    @Nonnull
    public WeaponType getWeaponType() {
        return weaponType;
    }

}
