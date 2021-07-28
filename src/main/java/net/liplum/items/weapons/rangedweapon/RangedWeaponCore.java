package net.liplum.items.weapons.rangedweapon;

import net.liplum.Names;
import net.liplum.WeaponTypes;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.BoolAttribute;
import net.liplum.events.weapon.FawWeaponLeftClickEvent;
import net.liplum.lib.math.Angle;
import net.liplum.lib.math.AxisAlignedCube;
import net.liplum.lib.math.P3D;
import net.liplum.lib.math.Point3D;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.liplum.Attributes.Generic.*;

public abstract class RangedWeaponCore extends WeaponCore {

    private static final IPassiveSkill<FawWeaponLeftClickEvent> RangedWeaponPS =
            new PassiveSkill<FawWeaponLeftClickEvent>(Names.PassiveSkill.WeaponCore.RangedWeaponPS, FawWeaponLeftClickEvent.class) {
        @Nonnull
        @Override
        public PSkillResult onTrigger(@Nonnull FawWeaponLeftClickEvent event) {
            EntityLivingBase attacker = event.entity();
            AttrCalculator calculator = FawUtil.toCalculator(event);
            float attackReach = calculator.calcu(AttackReach).getFloat();
            float strength = calculator.calcu(Strength).getFloat();

            AxisAlignedBB playerBox = attacker.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = attacker.world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(attackReach, attackReach, attackReach));
            //Gets player's look vector and turn it to v2d.
            Point3D p = new Point3D(attacker.posX, attacker.posY, attacker.posZ);
            AxisAlignedCube range = new AxisAlignedCube(0, attackReach, 0, 2, -1.5, 1.5);
            float pitch = Angle.toRadian(Angle.toNormalDegreeAngle(attacker.rotationPitch));
            float yaw = Angle.toRadian(Angle.toNormalDegreeAngle(attacker.rotationYaw));
            List<EntityLivingBase> allInCube = allInRange.stream()
                    .filter(e ->
                            EntityUtil.canAttack(attacker, e) &&
                                    P3D.isInside(p, new Point3D(e.posX, e.posY, e.posZ)
                                            , pitch, yaw,
                                            range)).collect(Collectors.toList());
            Optional<EntityLivingBase> nearTarget = allInCube.stream()
                    .min(Comparator.comparing(
                            e -> e.getDistanceSq(attacker) < attackReach * attackReach));

            if (nearTarget.isPresent()) {
                EntityLivingBase target = nearTarget.get();
                float distance = target.getDistance(attacker);
                AxisAlignedCube nextToTargetCube = new AxisAlignedCube(distance, distance + 1, 0, 2, -1.5, 1.5);
                List<EntityLivingBase> allInAttackRange = allInCube.stream().filter(e ->
                        P3D.isInside(p, new Point3D(e.posX, e.posY, e.posZ),
                                pitch, yaw, nextToTargetCube)
                ).collect(Collectors.toList());
                for (EntityLivingBase e : allInAttackRange) {
                    e.attackEntityFrom(EntityUtil.genDamageSource(attacker), strength);
                }
            }
            return PSkillResult.Complete;
        }
    }.setShownInTooltip(false).setBanedWhenBroken(false);

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.RangedWeapon;
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                Strength, Strength.newBasicAttrValue(5)
        ).set(
                SpecialAttackReachJudgment, BoolAttribute.FalseBasicAttrValue
        ).set(
                EnumAction.BLOCK
        ).add(
                RangedWeaponPS
        ).set(
                (weaponItem, stack, attacker, target) -> true
        );
    }
}
