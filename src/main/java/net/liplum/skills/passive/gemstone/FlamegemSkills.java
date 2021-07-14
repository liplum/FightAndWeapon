package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;

public final class FlamegemSkills {
    public final static IPassiveSkill<TickEvent.PlayerTickEvent> FireProof =
            new PassiveSkill<TickEvent.PlayerTickEvent>(Names.PassiveSkill.Fireproof, TickEvent.PlayerTickEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull TickEvent.PlayerTickEvent event) {
                    if (event.phase == TickEvent.Phase.START) {
                        EntityPlayer player = event.player;
                        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 5, 0, false, false));
                    }
                    return PSkillResult.Complete;
                }
            };

    public final static IPassiveSkill<WeaponAttackEvent.Attacking> ScorchingTouch =
            new PassiveSkill<WeaponAttackEvent.Attacking>(Names.PassiveSkill.ScorchingTouch, WeaponAttackEvent.Attacking.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull WeaponAttackEvent.Attacking event) {
                    Entity target = event.getArgs().getTarget();
                    target.setFire(3);
                    return PSkillResult.Complete;
                }
            };
}