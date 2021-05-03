package net.liplum.items.weapons;

import net.liplum.entities.StraightDamageEntity;
import net.liplum.events.LanceSprintEvent;
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
import net.minecraftforge.common.MinecraftForge;

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
    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        boolean isCreative = playerIn.isCreative();
        int coolDownTime = getCoolDown();
        EnumActionResult result = EnumActionResult.PASS;
        //Player can't sprint over the sky.
        if (playerIn.onGround && playerIn.isSneaking()) {
            playerIn.motionY += 0.32;
            float length = getSprintLength();
            //float yawToRadian = playerIn.rotationYaw / 180.0F * (float) Math.PI;
            //float pitchToRadian = playerIn.rotationPitch / 180.0F * (float) Math.PI;
            //double toPitch=MathTool.toRadian(playerIn.rotationPitch);
            //double toYaw=MathTool.toRadian(playerIn.rotationYaw);
            //V3DHelper playerFace = MathTool.toEntityFace(MathTool.toV3D(toPitch,toYaw));
            //playerIn.motionX = -MathHelper.sin(yawToRadian) * MathHelper.cos(pitchToRadian) * length;
            //playerIn.motionZ = MathHelper.cos(yawToRadian) * MathHelper.cos(pitchToRadian) * length;
            //V3DHelper sprintForce = playerFace.multiply(length);
            Vec3d playerFace = playerIn.getLookVec();
            Vec3d sprintForce = playerFace.scale(length);
            playerIn.motionX = sprintForce.x;
            playerIn.motionZ = sprintForce.z;
            if (!isCreative) {
                playerIn.getCooldownTracker().setCooldown(held.getItem(), coolDownTime);
            }
            result = EnumActionResult.SUCCESS;
            //Following is some tests.
            //StraightDamageEntity dmg = new StraightDamageEntity(worldIn, playerIn, 5, 40);
            //dmg.shoot(playerIn,playerIn.rotationPitch,playerIn.rotationYaw,0,1.5F,1);
            //MinecraftForge.EVENT_BUS.post(new LanceSprintEvent(playerIn,this,sprintForce));
        }
        return new ActionResult<>(result, held);
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
