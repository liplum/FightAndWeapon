package net.liplum.skills.gemstone;

import net.liplum.Names;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.api.weapon.DamageArgs;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.lib.FawDamage;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

@Developing
public final class MagicPearlSkills {
    public static final IPassiveSkill<WeaponAttackEvent.Attacking> Magicize =
            new PassiveSkill<WeaponAttackEvent.Attacking>(Names.PassiveSkill.Magicize, WeaponAttackEvent.Attacking.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull WeaponAttackEvent.Attacking event) {
                    WeaponAttackEvent.Attacking.Args args = event.getArgs();
                    EntityLivingBase attacker = args.attacker();
                    ItemStack itemStack = args.itemStack();
                    List<DamageArgs> allDamages = args.getAllDamages();
                    DamageArgs initialDamage = args.initialDamage();
                    float initialDamageValue = initialDamage.getDamage();
                    Entity target = initialDamage.getTarget();

                    FawDamage normalDamage = EntityUtil.genFawDamage(attacker, itemStack);
                    FawDamage magicDamage = EntityUtil.genFawDamage(attacker, itemStack);

                    magicDamage.setMagicDamage().setDamageBypassesArmor();
                    args.initialDamage(new DamageArgs(initialDamageValue / 2, normalDamage, target));
                    allDamages.add(new DamageArgs(initialDamageValue / 2, magicDamage, target));

                    return PSkillResult.Complete;
                }
            };

}