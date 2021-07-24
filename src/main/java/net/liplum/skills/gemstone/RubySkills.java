package net.liplum.skills.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nonnull;

public final class RubySkills {
    public final static IPassiveSkill<LivingHurtEvent> FireResistance =
            new PassiveSkill<LivingHurtEvent>(Names.PassiveSkill.FireResistance, LivingHurtEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull LivingHurtEvent event) {
                    DamageSource source = event.getSource();
                    if (source.isFireDamage()) {
                        float dmg = event.getAmount();
                        event.setAmount(dmg / 2);
                        return PSkillResult.Complete;
                    }
                    return PSkillResult.Fail;
                }
            };

}