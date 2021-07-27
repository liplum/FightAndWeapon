package net.liplum.skills.gemstone;

import net.liplum.Names;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.lib.utils.PotionUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;

@Developing
public final class FlamegemSkills {
    public final static IPassiveSkill<TickEvent.PlayerTickEvent> FireProof =
            new PassiveSkill<TickEvent.PlayerTickEvent>(Names.PassiveSkill.Fireproof, TickEvent.PlayerTickEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull TickEvent.PlayerTickEvent event) {
                    if (event.phase == TickEvent.Phase.START) {
                        EntityPlayer player = event.player;
                        PotionUtil.addFireResistance(player, 5, false);
                        return PSkillResult.Complete;
                    }
                    return PSkillResult.Fail;
                }
            }.setBanedWhenBroken(false);

    public final static IPassiveSkill<WeaponAttackEvent.Attacking> ScorchingTouch =
            new PassiveSkill<WeaponAttackEvent.Attacking>(Names.PassiveSkill.ScorchingTouch, WeaponAttackEvent.Attacking.class, 100) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull WeaponAttackEvent.Attacking event) {
                    Entity target = event.getArgs().target();
                    target.setFire(3);
                    return PSkillResult.Complete;
                }
            };
}