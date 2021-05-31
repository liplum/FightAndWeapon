package net.liplum.api.fight;

import net.minecraftforge.fml.common.eventhandler.Event;

public interface IPassiveSkill<EventType extends Event> extends ISkill{
    Class<EventType> getEventType();

    PassiveSkillResult onTrigger(EventType event);
}
