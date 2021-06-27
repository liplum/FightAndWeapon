package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.events.attack.WeaponAttackedArgs;
import net.liplum.events.attack.WeaponAttackedEvent;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import javax.annotation.Nonnull;

public final class WindyGemstoneSkills {
    public final static IPassiveSkill<WeaponAttackedEvent> Levitation =
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
                                EntityUtil.ifLivingThenDO(args.getTarget(), e ->
                                        e.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 8, 0, false, false)));
                            }
                            return PSkillResult.Complete;
                        }

                        @Nonnull
                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.Levitation;
                        }
                    });
    public final static IPassiveSkill<LivingFallEvent> Feather =
            SkillRegistry.registerPassiveSkill(
                    new IPassiveSkill<LivingFallEvent>() {
                        @Nonnull
                        @Override
                        public Class<LivingFallEvent> getEventType() {
                            return LivingFallEvent.class;
                        }

                        @Nonnull
                        @Override
                        public PSkillResult onTrigger(@Nonnull LivingFallEvent event) {
                            event.setCanceled(true);
                            return PSkillResult.CancelTrigger;
                        }

                        @Nonnull
                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.Feather;
                        }
                    });
}