package net.liplum;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.attributes.IAttribute;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.utils.FawI18n;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.tooltips.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static net.liplum.Attributes.Generic.AttackSpeed;

@SideOnly(Side.CLIENT)
public final class TooltipMiddlewares {
    public static final ITooltipMiddleware AutoAddSpaceLine = new ITooltipMiddleware() {
        @Nonnull
        @Override
        public TooltipPart through(@Nonnull TooltipContext context) {
            return TooltipPart.Empty;
        }

        @Override
        public void onFollowingMiddlewareThroughOut(@Nonnull TooltipEntry tooltipEntry, @Nonnull List<TooltipEntry> rest, @Nonnull TooltipContext context) {
            TooltipPart itsResult = tooltipEntry.result;
            if (context.hasNextMiddleware()) {
                if (TooltipUtil.followingHasTooltip(rest)) {
                    itsResult.add("");
                }
            } else {
                if (!TooltipUtil.followingHasTooltip(rest)) {
                    boolean vanillaAttackSpeedTipShown = !AttackSpeed.isDefaultValue(context.calculator.calcu(AttackSpeed));
                    if (context.tooltipOption.isVanillaAdvanced() && vanillaAttackSpeedTipShown) {
                        itsResult.add("");
                    }
                }
            }
        }
    };
    public static final ITooltipMiddleware ShowWeaponType = context ->
            new TooltipPart().add(I18n.format(FawI18n.getNameI18nKey(context.weapon.getWeaponType())));

    public static final ITooltipMiddleware ShowGemstone = context -> {
        IGemstone gemstone = context.gemstone;
        if (gemstone != null) {
            return new TooltipPart().add(I18n.format(I18ns.Tooltip.Inlaid) + " " +
                    TextFormatting.RED +
                    I18n.format(FawI18n.getNameI18nKey(gemstone)));
        } else {
            return new TooltipPart().add(I18n.format(I18ns.Tooltip.NoGemstone));
        }
    };

    public static final ITooltipMiddleware ShowAttributes = context -> {
        TooltipOption option = context.tooltipOption;
        LinkedList<String> tooltips = new LinkedList<>();
        List<IAttribute> sortedAttr = context.weaponCore.getAllAttributes().stream()
                .filter(IAttribute::isShownInTooltip)
                .sorted(Comparator.comparing(IAttribute::getDisplayPriority))
                .collect(Collectors.toList());

        for (IAttribute attribute : sortedAttr) {
            if (attribute.needMoreDetailsToShown() && !option.isMoreDetailsShown()) {
                continue;
            }
            FinalAttrValue finalValue = context.calculator.calcu(attribute);
            if (attribute.canTooltipShow(finalValue)) {
                FawItemUtil.addAttributeTooltip(
                        tooltips, attribute.getI18nKey(),
                        attribute.getTooltipShownValue(finalValue),
                        attribute.getFormat(),
                        attribute.isStripTrailingZero(),
                        ((attribute.hasUnit() && option.isUnitShown()) ?
                                attribute.getUnit() : null)
                );
            }
        }
        return new TooltipPart(tooltips);
    };

    public static final ITooltipMiddleware ShowPassiveSkills = context -> {
        Collection<IPassiveSkill<?>> passiveSkills = context.passiveSkills;
        LinkedList<String> tooltips = new LinkedList<>();
        if (passiveSkills != null) {
            for (IPassiveSkill<?> passiveSkill : passiveSkills) {
                if (passiveSkill.isShownInTooltip()) {
                    FawItemUtil.addPassiveSkillTooltip(tooltips, passiveSkill);
                }
            }
        }
        return new TooltipPart(tooltips);
    };
}
