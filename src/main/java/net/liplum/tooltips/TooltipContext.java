package net.liplum.tooltips;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.api.weapon.*;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.utils.GemUtil;
import net.liplum.masteries.IMasteryDetail;
import net.liplum.masteries.MasteryDetail;
import net.liplum.masteries.MasteryNotFoundByWeaponTypeException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

public class TooltipContext {
    @Nonnull
    public final ItemStack itemStack;
    @Nonnull
    public final WeaponBaseItem weapon;
    @Nonnull
    public final WeaponCore weaponCore;
    @Nonnull
    public final WeaponType weaponType;
    @Nonnull
    public final AttrCalculator calculator;
    @Nonnull
    public final TooltipOption tooltipOption;
    @Nullable
    public final IGemstone gemstone;
    @Nullable
    public final Modifier modifier;
    @Nonnull
    public final Collection<IPassiveSkill<?>> gemstonePSkills;
    @Nonnull
    public final Collection<IPassiveSkill<?>> unlockedPSkills;
    @Nonnull
    public final Collection<IPassiveSkill<?>> masteryPSkills;
    @Nonnull
    public final EntityPlayer player;
    @Nonnull
    public final IMasteryDetail masteryDetails;
    @Nonnull
    public final IMastery mastery;

    public TooltipContext(@Nonnull ItemStack itemStack, @Nonnull WeaponBaseItem weapon, @Nonnull AttrCalculator calculator, @Nonnull TooltipOption tooltipOption, @Nonnull EntityPlayer player) {
        this.itemStack = itemStack;
        this.weapon = weapon;
        this.calculator = calculator;
        this.tooltipOption = tooltipOption;
        this.player = player;
        this.weaponCore = weapon.getCore();
        this.weaponType = weaponCore.getWeaponType();
        this.masteryDetails = MasteryDetail.create(player);
        this.gemstone = GemUtil.getGemstoneFrom(itemStack);
        IMastery maybeMastery = MasteryRegistry.getMasteryOf(weaponType);
        if (maybeMastery == null) {
            throw new MasteryNotFoundByWeaponTypeException(weaponType.getRegisterName());
        }
        this.mastery = maybeMastery;
        UnlockedPSkillList unlock = masteryDetails.getUnlockedPSkills(mastery);
        this.unlockedPSkills = weaponCore.unlockPassiveSkills(unlock);
        this.masteryPSkills = masteryDetails.getPassiveSkills(mastery);
        if (this.gemstone != null) {
            this.modifier = calculator.modifier();
            this.gemstonePSkills = gemstone.getPassiveSkillsOf(weaponCore);
        } else {
            this.modifier = null;
            this.gemstonePSkills = Collections.emptyList();
        }
    }
}
