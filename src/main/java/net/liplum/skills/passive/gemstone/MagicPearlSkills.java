package net.liplum.skills.passive.gemstone;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.api.weapon.DamageArgs;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.events.attack.WeaponAttackingArgs;
import net.liplum.events.attack.WeaponAttackingEvent;
import net.liplum.lib.FawDamage;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nonnull;
import java.util.List;

public final class MagicPearlSkills {
    public static final IPassiveSkill<WeaponAttackingEvent> Magicize =
            SkillRegistry.registerPassiveSkill(
                    new IPassiveSkill<WeaponAttackingEvent>() {
                        @Nonnull
                        @Override
                        public Class<WeaponAttackingEvent> getEventType() {
                            return WeaponAttackingEvent.class;
                        }

                        @Nonnull
                        @Override
                        public PSkillResult onTrigger(@Nonnull WeaponAttackingEvent event) {
                            WeaponAttackingArgs args = event.getArgs();
                            EntityLivingBase attacker = args.getAttacker();
                            WeaponCore weaponCore = args.getWeaponCore();
                            IGemstone gemstone = args.getGemstone();
                            Modifier<?> modifier = args.getModifier();
                            List<DamageArgs> allDamages = args.getAllDamages();
                            DamageArgs initialDamage = args.getInitialDamage();
                            float initialDamageValue = initialDamage.getDamage();
                            Entity target = initialDamage.getTarget();

                            FawDamage normalDamage = EntityUtil.genFawDamage(attacker,
                                    weaponCore,
                                    gemstone, modifier);
                            FawDamage magicDamage = EntityUtil.genFawDamage(attacker,
                                    weaponCore,
                                    gemstone, modifier);

                            magicDamage.setMagicDamage().setDamageBypassesArmor();
                            args.setInitialDamage(new DamageArgs(initialDamageValue / 2, normalDamage, target));
                            allDamages.add(new DamageArgs(initialDamageValue / 2, magicDamage, target));

                            return PSkillResult.Complete;
                        }

                        @Nonnull
                        @Override
                        public String getRegisterName() {
                            return Names.PassiveSkill.Magicize;
                        }
                    });

}