package net.liplum.items.weapons.magickwand;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.IAttribute;
import net.liplum.events.AttributeAccessEvent;
import net.liplum.lib.utils.FawUtil;

import javax.annotation.Nonnull;

import static net.liplum.Attributes.Generic.AbilityPower;
import static net.liplum.Attributes.Generic.Strength;

public class PSkills {
    public static final IPassiveSkill<AttributeAccessEvent> AP2Strength =
            new PassiveSkill<AttributeAccessEvent>(Names.PassiveSkill.AP2Strength, AttributeAccessEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull AttributeAccessEvent event) {
                    IAttribute attr = event.getAttribute();
                    if (attr == Strength) {
                        AttrCalculator calculator = FawUtil.toCalculator(event);
                        float ap = calculator.calcu(AbilityPower).getFloat();
                        float formerStrength = event.getFinalAttrValue().getFloat();
                        event.setFinalAttrValue(AbilityPower.newFinalAttrValue(formerStrength + ap / 2));
                        return PSkillResult.Complete;
                    }
                    return PSkillResult.Fail;
                }
            };
}
