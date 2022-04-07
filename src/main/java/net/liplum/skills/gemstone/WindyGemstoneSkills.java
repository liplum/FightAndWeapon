package net.liplum.skills.gemstone;

import net.liplum.Names;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.jetbrains.annotations.NotNull;

@Developing
public final class WindyGemstoneSkills {
    public final static IPassiveSkill<WeaponAttackEvent.Attacked> Levitation =
            new PassiveSkill<WeaponAttackEvent.Attacked>(Names.PassiveSkill.Levitation, WeaponAttackEvent.Attacked.class) {
                @NotNull
                @Override
                public PSkillResult onTrigger(@NotNull WeaponAttackEvent.Attacked event) {
                    WeaponAttackEvent.Attacked.Args args = event.getArgs();
                    if (args.attacker().isServerWorld() && args.isHitSuccessfully() && args.isFullAttack()) {
                        EntityUtil.ifLivingThenDO(args.target(), e ->
                                e.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 8, 0, false, false)));
                        return PSkillResult.Complete;
                    }
                    return PSkillResult.Fail;
                }
            };

    public final static IPassiveSkill<LivingFallEvent> Feather =
            new PassiveSkill<LivingFallEvent>(Names.PassiveSkill.Feather, LivingFallEvent.class) {

                @NotNull
                @Override
                public PSkillResult onTrigger(@NotNull LivingFallEvent event) {
                    event.setCanceled(true);
                    return PSkillResult.CancelTrigger;
                }
            }.setTriggerPriority(Integer.MIN_VALUE);
}