package net.liplum.tooltips;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.utils.GemUtil;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public class TooltipContext {
    @Nonnull
    public final ItemStack itemStack;
    @Nonnull
    public final WeaponBaseItem weapon;
    @Nonnull
    public final WeaponCore weaponCore;
    @Nonnull
    public final AttrCalculator calculator;
    @Nonnull
    public final TooltipOption tooltipOption;
    @Nullable
    public final IGemstone gemstone;
    @Nullable
    public final Modifier modifier;
    @Nullable
    public final Collection<IPassiveSkill<?>> passiveSkills;

    public TooltipContext(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weapon, @Nonnull AttrCalculator calculator, @Nonnull TooltipOption tooltipOption) {
        this.itemStack = itemStack;
        this.weapon = weapon;
        this.calculator = calculator;
        this.tooltipOption = tooltipOption;
        this.weaponCore = weapon.getCore();
        this.gemstone = GemUtil.getGemstoneFrom(itemStack);
        if (this.gemstone != null) {
            this.modifier = calculator.getModifier();
            this.passiveSkills = gemstone.getPassiveSkillsOf(weaponCore);
        } else {
            this.modifier = null;
            this.passiveSkills = null;
        }
    }
}
