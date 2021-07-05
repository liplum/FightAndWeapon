package net.liplum.items.weapons.battleaxe;

import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.Point;
import net.liplum.lib.math.Vector2D;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public final class BattleAxeCoreTypes {
    public static final BattleAxeCore Empty = new BattleAxeCore() {

        @Override
        public boolean releaseSkill(BattleAxeArgs args) {
            return false;
        }

        @Override
        public float getSweepRange() {
            return 0;
        }

        @Override
        public int getCoolDown() {
            return 0;
        }
    };

    public static final BattleAxeCore Normal = new BattleAxeCore() {
        private final int coolDown = 20 * 5;

        @Override
        public boolean releaseSkill(BattleAxeArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();
            float strength = args.getStrength();

            float sweepRange = args.getSweepRange();

            float knockBackAngleToX = MathHelper.sin((float) MathUtil.toRadian(player.rotationYaw));
            float knockBackAngleToY = -MathHelper.cos((float) MathUtil.toRadian(player.rotationYaw));
            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sweepRange, 0.25D, sweepRange));
            //Gets player's look vector and turn it to v2d.
            Vec3d pLook = player.getLookVec();
            Vector2D pLook2D = new Vector2D(pLook.x, pLook.z);
            Point pp = new Point(player.posX, player.posZ);
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
                    }
                }
            }
            //Some effects of attack
            player.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f);
            player.spawnSweepParticles();
            return true;
        }

        @Override
        public float getSweepRange() {
            return 3;
        }

        @Override
        public int getCoolDown() {
            return coolDown;
        }

        @Override
        public float getStrength() {
            return 7;
        }
    };

}
