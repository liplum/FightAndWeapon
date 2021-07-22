package net.liplum.items.weapons.rangeweapon;

import net.liplum.WeaponTypes;
import net.liplum.api.fight.AggregatePassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.BoolAttribute;
import net.liplum.events.weapon.FawWeaponLeftClickEvent;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.Point2D;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.utils.GemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;
import java.util.List;

import static net.liplum.Attributes.Generic.*;

public abstract class RangedWeaponCore extends WeaponCore {
    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.RangedWeapon;
    }

    @Override
    protected void build(WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                Strength, Strength.newBasicAttrValue(5)
        ).set(
                SpecialAttackReachJudgment, BoolAttribute.FalseBasicAttrValue
        ).addPassiveSkills(new AggregatePassiveSkill("RangedWeaponPS").add(FawWeaponLeftClickEvent.class,
                event -> {
                    ItemStack itemStack = event.getItemStack();
                    EntityPlayer attacker = event.getPlayer();
                    Modifier modifier = GemUtil.getModifierFrom(itemStack);
                    AttrCalculator calculator = new AttrCalculator()
                            .setWeaponCore(this)
                            .setModifier(modifier)
                            .setPlayer(attacker);
                    float attackReach = calculator.calcu(AttackReach).getFloat();
                    float strength = calculator.calcu(Strength).getFloat();
                    AxisAlignedBB playerBox = attacker.getEntityBoundingBox();
                    List<EntityLivingBase> allInRange = attacker.world
                            .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(attackReach, 0.25D, attackReach));
                    //Gets player's look vector and turn it to v2d.
                    Vec3d pLook = attacker.getLookVec();
                    Vector2D pLook2D = new Vector2D(pLook.x, pLook.z);
                    Point2D pp = new Point2D(attacker.posX, attacker.posZ);
                    for (EntityLivingBase e : allInRange) {
                        if (e != attacker &&//Without player self
                                (!e.isOnSameTeam(attacker) &&//The side entity is not on the same team with attacker
                                        e.getDistanceSq(attacker) < attackReach * attackReach)) {
                            Point2D sp = new Point2D(e.posX, e.posZ);
                            Point2D spNew = sp.minus(pp);
                            Vector2D sv = spNew.toV2D();
                            if (MathUtil.belongToCO(0, 1, sv.cosAngle(pLook2D))) {
                                e.attackEntityFrom(DamageSource.causePlayerDamage(attacker), strength);
                            }
                        }
                    }
                    return PSkillResult.Complete;
                }
                )
        ).setLeftClickEntityBehavior((weaponItem, stack, attacker, target) -> true);
    }

    public boolean rangedAttack() {
        return false;
    }
}
