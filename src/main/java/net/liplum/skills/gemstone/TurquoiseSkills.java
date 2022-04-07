package net.liplum.skills.gemstone;

import net.liplum.Names;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

@Developing
public final class TurquoiseSkills {
    public final static IPassiveSkill<LivingHurtEvent> GentlyLand =
            new PassiveSkill<LivingHurtEvent>(Names.PassiveSkill.GentlyLand, LivingHurtEvent.class) {
                @NotNull
                @Override
                public PSkillResult onTrigger(@NotNull LivingHurtEvent event) {
                    DamageSource source = event.getSource();
                    if (source.getDamageType().equals(DamageSource.FALL.getDamageType())) {
                        float dmg = event.getAmount();
                        event.setAmount(dmg - dmg * 0.3F);
                        return PSkillResult.Complete;
                    }
                    return PSkillResult.Fail;
                }
            };
}