package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.events.attack.WeaponAttackedArgs;
import net.liplum.events.attack.WeaponAttackedEvent;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nonnull;

public final class ForestgemSkills {
    public static final IPassiveSkill<WeaponAttackedEvent> NutrientAbsorption =
            SkillRegistry.registerPassiveSkill(
                    new IPassiveSkill<WeaponAttackedEvent>() {
                        @Nonnull
                        @Override
                        public Class<WeaponAttackedEvent> getEventType() {
                            return WeaponAttackedEvent.class;
                        }

                        @Nonnull
                        @Override
                        public PSkillResult onTrigger(@Nonnull WeaponAttackedEvent event) {
                            WeaponAttackedArgs args = event.getArgs();
                            if (args.isHitSuccessfully()) {
                                EntityLivingBase attacker = args.getAttacker();
                                float dmg = args.getInitialDamage().getDamage();
                                attacker.heal(dmg * 0.3F);
                            }
                            return PSkillResult.Complete;
                        }

                        @Nonnull
                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.NutrientAbsorption;
                        }
                    }
            );
}
