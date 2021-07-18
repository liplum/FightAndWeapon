package net.liplum;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.attributes.IAttribute;
import net.liplum.lib.Delegate;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.utils.FawI18n;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.tooltips.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static net.liplum.Attributes.Generic.AttackSpeed;

@SideOnly(Side.CLIENT)
public final class TooltipMiddlewares {
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

    public static final IThroughable ShowWeaponType = pipe ->
            new TooltipPart(
                    (FawItemUtil.isBroken(pipe.getContext().itemStack) ?
                            TextFormatting.ITALIC + I18n.format(I18ns.Tooltip.Broken) + " " + TextFormatting.RESET
                            : "")
                            +
                            I18n.format(FawI18n.getNameI18nKey(pipe.getContext().weapon.getWeaponType()))
            );

    public static final IThroughable ShowGemstone = pipe ->
            new TooltipPart(pipe.getContext().gemstone != null ?
                    I18n.format(I18ns.Tooltip.Inlaid) + " " +
                            TextFormatting.RED +
                            I18n.format(FawI18n.getNameI18nKey(pipe.getContext().gemstone)) :
                    I18n.format(I18ns.Tooltip.NoGemstone)
            );

    public static final IThroughable ShownWeaponTypeAndGemstone =
            new AggregateThroughable(ShowWeaponType, ShowGemstone);

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

    public static final IThroughable ShowPassiveSkills = pipe -> {
        Collection<IPassiveSkill<?>> passiveSkills = pipe.getContext().passiveSkills;
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
