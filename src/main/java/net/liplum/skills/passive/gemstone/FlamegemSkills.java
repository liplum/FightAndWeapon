package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;

public final class FlamegemSkills {
    public final static IPassiveSkill<TickEvent.PlayerTickEvent> FireProof =
            SkillRegistry.register(
                    new IPassiveSkill<TickEvent.PlayerTickEvent>() {
                        @Nonnull
                        @Override
                        public Class<TickEvent.PlayerTickEvent> getEventType() {
                            return TickEvent.PlayerTickEvent.class;
                        }

                        @Nonnull
                        @Override
                        public PSkillResult onTrigger(@Nonnull TickEvent.PlayerTickEvent event) {
                            if (event.phase == TickEvent.Phase.START) {
                                EntityPlayer player = event.player;
                                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 5, 0, false, false));
                            }
                            return PSkillResult.Complete;
                        }

                        @Nonnull
                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.Fireproof;
                        }
                    });

    public final static IPassiveSkill<WeaponAttackingEvent> ScorchingTouch =
            SkillRegistry.register(
                    new IPassiveSkill<WeaponAttackingEvent>() {
                        @Nonnull
                        @Override
                        public Class<WeaponAttackingEvent> getEventType() {
                            return WeaponAttackingEvent.class;
                        }

                        @Nonnull
                        @Override
                        public PSkillResult onTrigger(@Nonnull WeaponAttackingEvent event) {
                            Entity target = event.getArgs().getTarget();
                            target.setFire(3);
                            return PSkillResult.Complete;
                        }

                        @Nonnull
                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.ScorchingTouch;
                        }
                    }

            );
}