package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nonnull;

public final class ForestgemSkills {
    public static final IPassiveSkill<WeaponAttackEvent.Attacked> NutrientAbsorption =
            new PassiveSkill<WeaponAttackEvent.Attacked>(Names.PassiveSkill.NutrientAbsorption, WeaponAttackEvent.Attacked.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull WeaponAttackEvent.Attacked event) {
                    WeaponAttackEvent.Attacked.Args args = event.getArgs();
                    if (args.isHitSuccessfully() && args.isFullAttack()) {
                        EntityLivingBase attacker = args.getAttacker();
                        float dmg = args.getInitialDamage().getDamage();
                        attacker.heal(dmg * 0.3F);
                    }
                    return PSkillResult.Complete;
                }
            };
}
