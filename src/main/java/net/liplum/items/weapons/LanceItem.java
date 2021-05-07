package net.liplum.items.weapons;

import net.liplum.coroutine.WaitForTicks;
import net.liplum.enumerator.Yield;
import net.liplum.lib.coroutine.CoroutineSystem;
import net.liplum.lib.math.MathTool;
import net.liplum.lib.math.Point;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.tools.ItemTool;
import net.liplum.lib.tools.PhysicsTool;
import net.liplum.lib.weapons.ILongReachWeapon;
import net.liplum.lib.weapons.ISkillableWeapon;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class LanceItem extends WeaponBaseItem implements ILongReachWeapon, ISkillableWeapon {
    public LanceItem() {
        super(ToolMaterial.IRON);
    }

    private int coolDown = 20 * 6;

    /**
     * The double value of this is the true length of a sprint.
     *
     * @return
     */
    public float getSprintLength() {
        return sprintLength;
    }

    /**
     * The double value of this is the true length of a sprint.
     *
     * @param dashLength
     */
    public void setSprintLength(float dashLength) {
        this.sprintLength = dashLength;
    }

    /**
     * It means you can dash 4 units.
     */
    private float sprintLength = 2f;

    @Override
    public double getReach() {
        return 5;
    }

    @Override
    public int getCoolDown() {
        return coolDown;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        int coolDownTime = getCoolDown();
        EnumActionResult result = EnumActionResult.PASS;
        //Player can't sprint in the sky.
        if (playerIn.onGround && playerIn.isSneaking()) {
            float length = getSprintLength();
            Vec3d playerFace = playerIn.getLookVec();
            Vec3d sprintForce = playerFace.scale(length);
            Point playerP = PhysicsTool.get2DPosition(playerIn);
            Vector2D playerFace2D = MathTool.toV2D(playerFace);
            PhysicsTool.setMotion(playerIn, sprintForce.x, 0.32, sprintForce.z);
            /*playerIn.velocityChanged = true;
            playerIn.setPositionNonDirty();*/
            /*AxisAlignedBB playerBox = playerIn.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = worldIn
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(length, 0.25D, length));
            for (EntityLivingBase e : allInRange) {
                Point ep = PhysicsTool.get2DPosition(e);
                if (e != playerIn &&
                        MathTool.isInside(playerFace2D, playerP, ep, 2, length*2)
                ) {
                    e.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), 5);
                }
            }*/
            ItemTool.HeatWeaponIfSurvival(playerIn, held.getItem(), coolDownTime);
            if (!worldIn.isRemote) {
                CoroutineSystem.Instance().attachCoroutineToPlayer(playerIn, new Yield() {
                    @Override
                    protected void task() {
                        AxisAlignedBB playerBox = playerIn.getEntityBoundingBox();
                        List<EntityLivingBase> allInRange = worldIn
                                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(0.25D, 0.25D, 0.25D));
                        for (EntityLivingBase e : allInRange) {
                            if (e != playerIn) {
                                e.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), 5);
                            }
                        }
                        yieldReturn(new WaitForTicks(3));
                    }
                }, 80);
            }
            result = EnumActionResult.SUCCESS;
        }
        return ActionResult.newResult(result, held);
    }

    /*    *//**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for continuously
     *//*
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        EntityPlayer p = (EntityPlayer) player;
        AxisAlignedBB playerBox = p.getEntityBoundingBox();
        World world = p.world;
        List<EntityLivingBase> allInRange = world
                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(1, 0.25D, 1));
        for (EntityLivingBase e : allInRange) {
            e.attackEntityFrom(DamageSource.causePlayerDamage(p),1
                    //getAttackDamage()+getDamage(stack)
                    );
        }
    }*/

}
