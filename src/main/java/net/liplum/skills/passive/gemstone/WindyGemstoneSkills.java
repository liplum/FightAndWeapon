package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.events.attack.WeaponPostAttackedEvent;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public final class WindyGemstoneSkills {
    public final static IPassiveSkill<WeaponPostAttackedEvent> Levitation =
            SkillRegistry.registerPassiveSkill(
                    new IPassiveSkill<WeaponPostAttackedEvent>() {
                        @Override
                        public Class<WeaponPostAttackedEvent> getEventType() {
                            return WeaponPostAttackedEvent.class;
                        }

                        @Override
                        public PSkillResult onTrigger(WeaponPostAttackedEvent event) {
                            if (event.isHitSuccessfully()) {
                                EntityUtil.ifLivingThenDO(event.getTarget(), e ->
                                        e.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 8, 0, false, false)));
                            }
                            return PSkillResult.Complete;
                        }

                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.Levitation;
                        }
                    });
    public final static IPassiveSkill<LivingFallEvent> Feather =
            SkillRegistry.registerPassiveSkill(
                    new IPassiveSkill<LivingFallEvent>() {
                        @Override
                        public Class<LivingFallEvent> getEventType() {
                            return LivingFallEvent.class;
                        }

                        @Override
                        public PSkillResult onTrigger(LivingFallEvent event) {
                            event.setCanceled(true);
                            return PSkillResult.CancelTrigger;
                        }

                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.Feather;
                        }
                    });
}