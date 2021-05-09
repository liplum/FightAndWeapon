package net.liplum.items.weapons.lance;

import net.liplum.coroutine.WaitForTicks;
import net.liplum.enumerator.Yield;
import net.liplum.lib.coroutine.CoroutineSystem;
import net.liplum.lib.tools.ItemTool;
import net.liplum.lib.tools.PhysicsTool;
import net.liplum.lib.items.ILongReachWeapon;
import net.liplum.lib.items.ISkillableWeapon;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.registeies.PotionRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LanceItem extends WeaponBaseItem implements ILongReachWeapon, ISkillableWeapon {
    public LanceItem() {
        super();
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
            Vec3d sprintForce = playerFace.scale(MathHelper.sqrt(length));
            PhysicsTool.setMotion(playerIn, sprintForce.x, 0.32, sprintForce.z);
            ItemTool.HeatWeaponIfSurvival(playerIn, held.getItem(), coolDownTime);
            if (!worldIn.isRemote) {
                //playerIn.addPotionEffect(new PotionEffect(PotionRegistry.Unstoppable_Potion,80,1));
                CoroutineSystem.Instance().attachCoroutineToPlayer(playerIn, new Yield() {
                    Set<EntityLivingBase> damaged = new HashSet<>();
                    @Override
                    protected void task() {
                        AxisAlignedBB playerBox = playerIn.getEntityBoundingBox();
                        List<EntityLivingBase> allInRange = worldIn
                                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(0.25D, 0.25D, 0.25D));
                        for (EntityLivingBase e : allInRange) {
                            if (e != playerIn && !damaged.contains(e)) {
                                e.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), 5);
                                damaged.add(e);
                            }
                        }
                        yieldReturn(new WaitForTicks(5));
                    }
                }, 50);
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
