package net.liplum.skills.passive.gemstone;

import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.events.attack.WeaponPreAttackEvent;
import net.minecraft.util.DamageSource;

public final class MagicPearlSkills {
    public static final IPassiveSkill<WeaponPreAttackEvent> Magicize = new IPassiveSkill<WeaponPreAttackEvent>() {
        @Override
        public Class<WeaponPreAttackEvent> getEventType() {
            return WeaponPreAttackEvent.class;
        }

        @Override
        public PSkillResult onTrigger(WeaponPreAttackEvent event) {
            DamageSource damageSource = event.getDamageSource();
            damageSource.setMagicDamage();
            return PSkillResult.Complete;
        }
    };

}