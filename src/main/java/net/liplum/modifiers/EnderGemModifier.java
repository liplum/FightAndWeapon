package net.liplum.modifiers;

import net.liplum.items.weapons.lance.LanceCoreTypes;
import net.liplum.lib.cores.lance.ILanceCore;
import net.liplum.lib.cores.lance.LanceArgs;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.modifiers.LanceModifier;
import net.liplum.lib.utils.EntityUtil;
import net.liplum.lib.utils.PhysicsTool;
import net.liplum.lib.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public final class EnderGemModifier {
    public final static LanceModifier Normal_Lance = new LanceModifier() {
        @Override
        public ILanceCore getCoreType() {
            return LanceCoreTypes.Normal;
        }

        @Override
        public float getSprintLengthDelta() {
            return 2;
        }

        @Override
        public float getSprintLengthRate() {
            return 0;
        }

        @Override
        public boolean releaseSkill(ILanceCore core,  LanceArgs args) {
            World world = args.getWorld();
            EntityPlayer player = args.getPlayer();
            float strength = args.getStrength();

            float sprintLength = args.getSprintLength();

            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sprintLength, 0.25D, sprintLength));
            Vector2D look = MathUtil.toV2D(player.getLookVec());
            for (EntityLivingBase e : allInRange) {
                if (EntityUtil.canAttack(player,e) && MathUtil.isInside(look, PhysicsTool.get2DPosition(player), PhysicsTool.get2DPosition(e), 2, sprintLength)) {
                    e.attackEntityFrom(DamageSource.causePlayerDamage(player), strength);
                }
            }
            Vec3d originPos = player.getPositionVector();
            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(sprintLength);
            Vec3d nowPos = new Vec3d(sprintForce.x + originPos.x, originPos.y, sprintForce.z + originPos.z);
            PhysicsTool.setPosition(player, nowPos.x, nowPos.y + 0.5, nowPos.z);
            if (world.isRemote) {
                for (int i = 0; i < 32; ++i) {
                    world.spawnParticle(EnumParticleTypes.PORTAL,
                            nowPos.x,
                            nowPos.y + Utils.getRandom().nextDouble() * 2.0D,
                            nowPos.z,
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D,
                            -Utils.getRandom().nextDouble(),
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D);
                    /*world.spawnParticle(EnumParticleTypes.PORTAL,
                            nowPos.x + (Utils.getRandom().nextDouble() - 0.5D) * 2,
                            nowPos.y + Utils.getRandom().nextDouble() * 2 - 0.25D,
                            nowPos.z + (Utils.getRandom().nextDouble() - 0.5D) * 2,
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D,
                            -Utils.getRandom().nextDouble(),
                            (Utils.getRandom().nextDouble() - 0.5D) * 2.0D);*/
                }
            }
            player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1F, 1F);
            return true;
        }
    };
}
