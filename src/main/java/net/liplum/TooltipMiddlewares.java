package net.liplum;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.attributes.IAttribute;
import net.liplum.lib.Delegate;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.utils.FawI18n;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.Utils;
import net.liplum.tooltips.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static net.liplum.Attributes.Generic.AttackSpeed;

public final class TooltipMiddlewares {
    @Nonnull
    public static final IMiddleware AutoAddSpaceLine = new IMiddlewareQualifier() {
        @Override
        public void onSubscribe(Delegate<MiddlewareThroughInArgs> onMiddlewareThroughIn, Delegate<MiddlewareThroughOutArgs> onMiddlewareThroughOut) {
            onMiddlewareThroughOut.add(
                    args -> {
                        IPipeContext pipe = args.pipeContext;
                        TooltipPart middlewareResult = args.middlewareResult;
                        if (pipe.isFirstMiddleware()) {
                            return;
                        } else {
                            if (middlewareResult.hasAnyTooltip()) {
                                middlewareResult.addHead("");
                            }
                            if (pipe.isLastMiddleware()) {
                                TooltipContext tooltipContext = pipe.getContext();
                                boolean vanillaAttackSpeedTipShown = !AttackSpeed.isDefaultValue(tooltipContext.calculator.calcu(AttackSpeed));
                                if (tooltipContext.tooltipOption.isVanillaAdvanced() && !vanillaAttackSpeedTipShown) {
                                    middlewareResult.add("");
                                }
                            }
                        }
                    }
            );
        }
    };

    @Nonnull
    public static final IThroughable ShowWeaponType = pipe ->
            new TooltipPart(
                    (FawItemUtil.isWeaponBroken(pipe.getContext().itemStack) ?
                            TextFormatting.ITALIC + I18n.format(I18ns.Tooltip.Broken) + " " + TextFormatting.RESET
                            : "")
                            +
                            I18n.format(FawI18n.getNameI18nKey(pipe.getContext().weapon.getWeaponType()))
            );

    @Nonnull
    public static final IThroughable ShowGemstone = pipe ->
            new TooltipPart(pipe.getContext().gemstone != null ?
                    I18n.format(I18ns.Tooltip.Inlaid) + " " +
                            TextFormatting.RED +
                            I18n.format(FawI18n.getNameI18nKey(pipe.getContext().gemstone)) :
                    I18n.format(I18ns.Tooltip.NoGemstone)
            );

    @Nonnull
    public static final IThroughable ShowWeaponSkillTip = pipe -> {
        TooltipPart res = new TooltipPart();
        TooltipContext context = pipe.getContext();
        if (context.tooltipOption.isWeaponSkillTipShown()) {
            WeaponCore core = context.weaponCore;
            IGemstone gemstone = context.gemstone;
            res.add(
                    I18n.format(FawI18n.getWeaponSkillTipI18nKey(core))
            );
            if (gemstone != null) {
                res.add(
                        I18n.format(FawI18n.getGemstoneSkillTipI18nKey(core, gemstone))
                );
            }
        }
        return res;
    };

    @Nonnull
    public static final IThroughable ShowAttributes = pipe -> {
        TooltipContext context = pipe.getContext();
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

    @Nonnull
    public static final IThroughable ShowPassiveSkills = pipe -> {
        TooltipContext context = pipe.getContext();
        Collection<IPassiveSkill<?>> passiveSkills = context.gemstonePSkills;
        Collection<IPassiveSkill<?>> unlockedPSkills = context.unlockedPSkills;
        LinkedList<String> tooltips = new LinkedList<>();
        for (IPassiveSkill<?> passiveSkill : Utils.mergeIterator(passiveSkills, unlockedPSkills)) {
            if (passiveSkill.isShownInTooltip()) {
                FawItemUtil.addPassiveSkillTooltip(tooltips, passiveSkill);
            }
        }
        return new TooltipPart(tooltips);
    };
}
