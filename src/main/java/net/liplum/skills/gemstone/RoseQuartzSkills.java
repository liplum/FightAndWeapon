package net.liplum.skills.gemstone;

import net.liplum.Names;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.api.weapon.DamageArgs;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;
import java.util.List;

@Developing
public final class RoseQuartzSkills {
    public static final IPassiveSkill<WeaponAttackEvent.Attacking> MagicAttach =
            new PassiveSkill<WeaponAttackEvent.Attacking>(Names.PassiveSkill.MagicAttach, WeaponAttackEvent.Attacking.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull WeaponAttackEvent.Attacking event) {
                    WeaponAttackEvent.Attacking.Args args = event.getArgs();
                    List<DamageArgs> allDamages = args.getAllDamages();
                    EntityLivingBase attacker = args.attacker();
                    DamageSource extraDamageSource = EntityUtil.genDamageSource(attacker).
                            setMagicDamage().setDamageBypassesArmor();
                    allDamages.add(new DamageArgs(1F, extraDamageSource, args.target()));
                    return PSkillResult.Complete;
                }
            };
}