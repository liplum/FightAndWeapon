package net.liplum.items.weapons.battleaxe;

import net.liplum.Names;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.Point2D;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

import static net.liplum.Attributes.BattleAxe.SweepRange;
import static net.liplum.Attributes.Generic.*;
import static net.liplum.items.weapons.battleaxe.PSkills.Combo;

@LongSupport
public final class BattleAxeCores {
    public static final BattleAxeCore Empty = new BattleAxeCore(Names.Item.EmptyCore) {

        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.hasWeaponSkill(
                    false
            );
        }

    };

    public static final BattleAxeCore Normal = new BattleAxeCore(Names.Item.BattleAxe.BattleAxeItem) {

        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            World world = args.world();
            EntityLivingBase player = args.entity();
            WeaponBaseItem weapon = args.weapon();
            ItemStack itemStack = args.itemStack();
            AttrCalculator calculator = args.calculator();

            float strength = calculator.calcu(Strength).getFloat();
            float sweepRange = calculator.calcu(SweepRange).getFloat();

            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sweepRange, 0.25D, sweepRange));
            //Gets player's look vector and turn it to v2d.
            Vec3d pLook = player.getLookVec();
            Vector2D pLook2D = new Vector2D(pLook.x, pLook.z);
            Point2D pp = new Point2D(player.posX, player.posZ);
            int damagedEntityCount = 0;
            for (EntityLivingBase e : allInRange) {
                if (e != player &&//Without player self
                        (!e.isOnSameTeam(player) &&//The side entity is not on the same team with attacker
                                e.getDistanceSq(player) < sweepRange * sweepRange)) {
                    Point2D sp = new Point2D(e.posX, e.posZ);
                    Point2D spNew = sp.minus(pp);
                    Vector2D sv = spNew.toV2D();
                    if (MathUtil.belongToCO(0, 1, sv.cosAngle(pLook2D))) {
                        weapon.dealDamage(EntityUtil.genFawDamage(player, itemStack), e, strength);
                        EntityUtil.knockBack(player, e, 0.5F);
                        damagedEntityCount++;
                    }
                }
            }
            int weaponDamage = (int) MathUtil.castTo(1F, 5F, (float) damagedEntityCount / 3);
            FawItemUtil.damageWeapon(weapon, itemStack, weaponDamage, player);

            //Some effects of attack
            player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f);
            EntityUtil.spawnSweepParticles(player);
            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    SweepRange, 3F
            ).set(
                    Strength, 7F
            ).set(
                    CoolDown, 5 * 20
            );
        }
    };

    public static final BattleAxeCore BerserkerAxe = new BattleAxeCore(Names.Item.BattleAxe.BerserkerAxeItem) {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            EntityLivingBase player = args.entity();
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 8 * 20, 1));
            FawItemUtil.damageWeapon(args.weapon(), args.itemStack(), 3, player);
            return true;
        }

        @Override
        protected void build(@Nonnull WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    Strength, Strength.newBasicAttrValue(7F)
            ).set(
                    CoolDown, CoolDown.newBasicAttrValue(350)
            ).set(
                    AttackSpeed, AttackSpeed.newBasicAttrValue(1F)
            ).add(
                    1, Combo
            );
        }
    };

}
