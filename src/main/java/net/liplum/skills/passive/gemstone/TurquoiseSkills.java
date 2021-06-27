package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nonnull;

public final class TurquoiseSkills {
    public final static IPassiveSkill<LivingHurtEvent> GentlyLand =
            SkillRegistry.registerPassiveSkill(
                    new IPassiveSkill<LivingHurtEvent>() {
                        @Nonnull
                        @Override
                        public Class<LivingHurtEvent> getEventType() {
                            return LivingHurtEvent.class;
                        }

                        @Nonnull
                        @Override
                        public PSkillResult onTrigger(@Nonnull LivingHurtEvent event) {
                            DamageSource source = event.getSource();
                            if (source.getDamageType().equals(DamageSource.FALL.getDamageType())) {
                                float dmg = event.getAmount();
                                event.setAmount(dmg - dmg * 0.3F);
                            }
                            return PSkillResult.Complete;
                        }

                        @Nonnull
                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.GentlyLand;
                        }
                    });
}