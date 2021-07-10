package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.api.weapon.DamageArgs;
import net.liplum.events.attack.WeaponAttackingArgs;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;
import java.util.List;

public final class RoseQuartzSkills {
    public static final IPassiveSkill<WeaponAttackingEvent> MagicAttach =
            new PassiveSkill<WeaponAttackingEvent>(Names.PassiveSkill.MagicAttach, WeaponAttackingEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull WeaponAttackingEvent event) {
                    WeaponAttackingArgs args = event.getArgs();
                    List<DamageArgs> allDamages = args.getAllDamages();
                    EntityLivingBase attacker = args.getAttacker();
                    DamageSource extraDamageSource = EntityUtil.genDamageSource(attacker).
                            setMagicDamage().setDamageBypassesArmor();
                    allDamages.add(new DamageArgs(1F, extraDamageSource, args.getTarget()));
                    return PSkillResult.Complete;
                }
            };
}