package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Tuple;

import java.util.List;

public final class RoseQuartzSkills {
    public static final IPassiveSkill<WeaponAttackingEvent> MagicAttach =
            SkillRegistry.registerPassiveSkill(Names.PassiveSkill.MagicAttach,
                    new IPassiveSkill<WeaponAttackingEvent>() {
                        @Override
                        public Class<WeaponAttackingEvent> getEventType() {
                            return WeaponAttackingEvent.class;
                        }

                        @Override
                        public PSkillResult onTrigger(WeaponAttackingEvent event) {
                            List<Tuple<DamageSource, Float>> extraDamages = event.getExtraDamages();
                            EntityLivingBase attacker = event.getAttacker();
                            DamageSource extraDamageSource = EntityUtil.genDamageSource(attacker).setMagicDamage();
                            extraDamages.add(new Tuple<>(extraDamageSource, 1F));
                            return PSkillResult.Complete;
                        }
                    });
}