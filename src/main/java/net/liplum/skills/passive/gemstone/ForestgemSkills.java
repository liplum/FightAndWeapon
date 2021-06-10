package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.events.attack.WeaponPostAttackedEvent;
import net.minecraft.entity.EntityLivingBase;

public final class ForestgemSkills {
    public static final IPassiveSkill<WeaponPostAttackedEvent> NutrientAbsorption =
            SkillRegistry.registerPassiveSkill(
                    new IPassiveSkill<WeaponPostAttackedEvent>() {
                        @Override
                        public Class<WeaponPostAttackedEvent> getEventType() {
                            return WeaponPostAttackedEvent.class;
                        }

                        @Override
                        public PSkillResult onTrigger(WeaponPostAttackedEvent event) {
                            if (event.isHitSuccessfully()) {
                                EntityLivingBase attacker = event.getAttacker();
                                float dmg = event.getDamage();
                                attacker.heal(dmg * 0.3F);
                            }
                            return PSkillResult.Complete;
                        }

                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.NutrientAbsorption;
                        }
                    }
            );
}
