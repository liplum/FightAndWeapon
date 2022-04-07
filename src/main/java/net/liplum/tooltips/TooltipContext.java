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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

public class TooltipContext {
    @NotNull
    public final ItemStack itemStack;
    @NotNull
    public final WeaponBaseItem weapon;
    @NotNull
    public final WeaponCore weaponCore;
    @NotNull
    public final WeaponType weaponType;
    @NotNull
    public final AttrCalculator calculator;
    @NotNull
    public final TooltipOption tooltipOption;
    @Nullable
    public final IGemstone gemstone;
    @Nullable
    public final Modifier modifier;
    @NotNull
    public final Collection<IPassiveSkill<?>> gemstonePSkills;
    @NotNull
    public final Collection<IPassiveSkill<?>> unlockedPSkills;
    @NotNull
    public final Collection<IPassiveSkill<?>> masteryPSkills;
    @NotNull
    public final EntityPlayer player;
    @NotNull
    public final IMasteryDetail masteryDetails;
    @NotNull
    public final IMastery mastery;

    public TooltipContext(@NotNull ItemStack itemStack, @NotNull WeaponBaseItem weapon, @NotNull AttrCalculator calculator, @NotNull TooltipOption tooltipOption, @NotNull EntityPlayer player) {
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
