package net.liplum.items.weapons.lance;

import net.liplum.coroutine.WaitForNextTick;
import net.liplum.coroutine.WaitForTicks;
import net.liplum.enumerator.Yield;
import net.liplum.lib.coroutine.CoroutineSystem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.utils.PhysicsTool;
import net.liplum.lib.weaponcores.ILanceCore;
import net.liplum.registeies.PotionRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class LanceCoreTypes {
    public static final ILanceCore Empty = new ILanceCore() {
        @Override
        public boolean releaseSkill(World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, float strength, float sprintLength) {
            return false;
        }

        @Override
        public int getCoolDown() {
            return 0;
        }

        @Override
        public float getSprintLength() {
            return 0;
        }
    };

    public static final ILanceCore Normal = new ILanceCore() {
        @Override
        public boolean releaseSkill(World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, float strength, float sprintLength) {
            Vec3d playerFace = player.getLookVec();
            Vec3d sprintForce = playerFace.scale(MathHelper.sqrt(sprintLength));
            PhysicsTool.setMotion(player, sprintForce.x, 0.32, sprintForce.z);
            if (!world.isRemote) {
                player.addPotionEffect(new PotionEffect(PotionRegistry.Unstoppable_Potion, 15, 0, false, false));
                CoroutineSystem.Instance().attachCoroutineToPlayer(player, new Yield() {
                    Set<EntityLivingBase> damaged = new HashSet<>();

                    @Override
                    protected void runTask() {
                        AxisAlignedBB playerBox = player.getEntityBoundingBox();
                        List<EntityLivingBase> allInRange = world
                                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(0.25D, 0.25D, 0.25D));
                        for (EntityLivingBase e : allInRange) {
                            if (e != player && !damaged.contains(e)) {
                                e.attackEntityFrom(DamageSource.causePlayerDamage(player), strength);
                                damaged.add(e);
                            }
                        }
                        yieldReturn(new WaitForNextTick());
                    }
                }, 25);
            }
            return true;
        }

        @Override
        public int getCoolDown() {
            return 20 * 6;
        }

        /**
         * It means you can dash 4 units.
         */
        @Override
        public float getSprintLength() {
            return 2F;
        }

        @Override
        public float getStrength() {
            return 5;
        }
    };
    public static final ILanceCore KnightLance = new ILanceCore() {
        @Override
        public boolean releaseSkill(World world, EntityPlayer player, ItemStack itemStack, EnumHand hand, float strength, float sprintLength) {
            AxisAlignedBB playerBox = player.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = world
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(sprintLength, 0.25D, sprintLength));
            Vector2D look = MathUtil.toV2D(player.getLookVec());
            for (EntityLivingBase e : allInRange) {
                if (player != e && MathUtil.isInside(look, PhysicsTool.get2DPosition(player), PhysicsTool.get2DPosition(e), 1.5, sprintLength)) {
                    e.attackEntityFrom(DamageSource.causePlayerDamage(player), 1.5F * strength);
                }
            }
            return true;
        }

        @Override
        public int getCoolDown() {
            return 10 * 20;
        }

        @Override
        public float getSprintLength() {
            return 4;
        }

        @Override
        public float getStrength() {
            return 6;
        }
    };
}
