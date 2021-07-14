package net.liplum.items.weapons.battleaxe;

import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.Point;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

import static net.liplum.Attributes.BattleAxe.SweepRange;
import static net.liplum.Attributes.Generic.*;

public final class BattleAxeCoreTypes {
    public static final BattleAxeCore Empty = new BattleAxeCore() {

        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            return false;
        }

    };

    public static final BattleAxeCore Normal = new BattleAxeCore() {

        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();
            AttrCalculator calculator = args.getCalculator();

            float strength = calculator.calcu(Strength).getFloat();
            float sweepRange = calculator.calcu(SweepRange).getFloat();

            float knockBackAngleToX = MathHelper.sin((float) MathUtil.toRadian(player.rotationYaw));
            float knockBackAngleToY = -MathHelper.cos((float) MathUtil.toRadian(player.rotationYaw));
            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sweepRange, 0.25D, sweepRange));
            //Gets player's look vector and turn it to v2d.
            Vec3d pLook = player.getLookVec();
            Vector2D pLook2D = new Vector2D(pLook.x, pLook.z);
            Point pp = new Point(player.posX, player.posZ);
            int damagedEntityCount = 0;
            for (EntityLivingBase e : allInRange) {
                if (e != player &&//Without player self
                        (!e.isOnSameTeam(player) &&//The side entity is not on the same team with attacker
                                e.getDistanceSq(player) < sweepRange * sweepRange)) {
                    Point sp = new Point(e.posX, e.posZ);
                    Point spNew = sp.minus(pp);
                    Vector2D sv = spNew.toV2D();
                    if (MathUtil.belongToCO(0, 1, sv.cosAngle(pLook2D))) {
                        e.attackEntityFrom(DamageSource.causePlayerDamage(player), strength);
                        e.knockBack(player, 0.5f, knockBackAngleToX, knockBackAngleToY);
                        damagedEntityCount++;
                    }
                }
            }
            int weaponDamage = (int) MathUtil.castTo(1F, 5F, (float) damagedEntityCount / 3);
            FawItemUtil.damageWeapon(args.getWeapon(), args.getItemStack(), weaponDamage, player);

            //Some effects of attack
            player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f);
            player.spawnSweepParticles();
            return true;
        }

        @Override
        protected void build(WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    SweepRange, SweepRange.newBasicAttrValue(3F)
            ).set(
                    Strength, Strength.newBasicAttrValue(7F)
            ).set(
                    CoolDown, CoolDown.newBasicAttrValue(5 * 20)
            );
        }
    };

    public static final BattleAxeCore BerserkerAxe = new BattleAxeCore() {
        @Override
        public boolean releaseSkill(WeaponSkillArgs args) {
            EntityPlayer player = args.getPlayer();
            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 8 * 20, 1));
            FawItemUtil.damageWeapon(args.getWeapon(), args.getItemStack(), 3, player);
            return true;
        }

        @Override
        protected void build(WeaponCoreBuilder builder) {
            super.build(builder);
            builder.set(
                    Strength, Strength.newBasicAttrValue(7F)
            ).set(
                    CoolDown, CoolDown.newBasicAttrValue(350)
            ).set(
                    AttackSpeed, AttackSpeed.newBasicAttrValue(1F)
            );
        }
    };

}
