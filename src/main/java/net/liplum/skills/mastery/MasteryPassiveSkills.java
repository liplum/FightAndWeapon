package net.liplum.skills.mastery;

import net.liplum.Names;
import net.liplum.WeaponTypes;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.AttrCalculator;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.NotNull;

import static net.liplum.Attributes.Generic.Strength;
import static net.liplum.Attributes.Sword.Sweep;

/**
 * There are all the passive skills of master in FAW mod.
 */
@Developing
public final class MasteryPassiveSkills {
    public final static IPassiveSkill<WeaponAttackEvent.Attacked> Sweeping =
            new PassiveSkill<WeaponAttackEvent.Attacked>(Names.PassiveSkill.Sweeping, WeaponAttackEvent.Attacked.class) {
                @NotNull
                @Override
                public PSkillResult onTrigger(@NotNull WeaponAttackEvent.Attacked event) {
                    WeaponAttackEvent.Attacked.Args args = event.getArgs();
                    if (args.isHitSuccessfully() && args.isFullAttack()) {
                        WeaponBaseItem weapon = args.weapon();
                        if (weapon.getWeaponType() == WeaponTypes.Sword) {
                            AttrCalculator calculator = args.calculator();
                            float sweep = calculator.calcu(Sweep).getFloat();
                            float strength = calculator.calcu(Strength).getFloat();
                            float dmg = MathUtil.fixMax(sweep, strength);
                            Entity target = args.target();
                            World world = args.world();
                            EntityLivingBase attacker = args.attacker();
                            boolean atLeastSweepOne = false;
                            for (EntityLivingBase entity :
                                    world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
                                if (EntityUtil.canAttack(attacker, entity) &&
                                        attacker.getDistanceSq(entity) < 9.0D) {
                                    EntityUtil.knockBack(attacker, entity, 0.4F);
                                    entity.attackEntityFrom(EntityUtil.genDamageSource(attacker), dmg);
                                    atLeastSweepOne = true;
                                }
                            }
                            world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, attacker.getSoundCategory(), 1.0F, 1.0F);
                            EntityUtil.spawnSweepParticles(attacker);
                            //I like that but someone haven't, so I have to comment it and wait for unlocking it in the future. ;(
                           /* if (atLeastSweepOne) {
                                weapon.reduceDurabilityOnHit(args.getItemStack(), attacker, dmg);
                            }*/
                            return PSkillResult.Complete;
                        }
                    }
                    return PSkillResult.Fail;
                }
            };

    //TODO:The KnockBack didn't work.
    public final static IPassiveSkill<LivingHurtEvent> ManaBarrier =
            new PassiveSkill<LivingHurtEvent>(Names.PassiveSkill.ManaBarrier, LivingHurtEvent.class, 100 * 20) {
                @NotNull
                @Override
                public PSkillResult onTrigger(@NotNull LivingHurtEvent event) {
                    EntityLivingBase entity = event.getEntityLiving();
                    if (entity instanceof EntityPlayer) {
                        event.setAmount(0);
                        World world = entity.world;
                        for (EntityLivingBase e :
                                world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().grow(1.5D, 0.25D, 1.5D))) {
                            EntityUtil.knockBackForward(entity, e, 0.5F);
                        }
                        return PSkillResult.Complete;
                    }
                    return PSkillResult.Fail;
                }
            };

    //You must call it to load this class and all the static fields.
    public static void load() {
    }
}
