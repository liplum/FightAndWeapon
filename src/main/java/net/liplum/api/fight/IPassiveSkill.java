package net.liplum.api.fight;

import net.liplum.I18ns;
import net.minecraftforge.fml.common.eventhandler.Event;

public interface IPassiveSkill<EventType extends Event> extends ISkill {
    Class<EventType> getEventType();

    PSkillResult onTrigger(EventType event);

    String getRegisterName();

    default String getNameI18nKey() {
        return I18ns.endWithName(I18ns.prefixPSkill(getRegisterName()));
    }

    default String getDescriptionI18nKey() {
        return I18ns.endWithDescription(I18ns.prefixPSkill(getRegisterName()));
    }
}
