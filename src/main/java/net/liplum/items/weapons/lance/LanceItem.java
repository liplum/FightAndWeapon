package net.liplum.items.weapons.lance;

import net.liplum.lib.items.ILance;
import net.liplum.lib.items.ILongReachWeapon;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.util.ItemTool;
import net.liplum.lib.weaponcores.ILanceCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class LanceItem extends WeaponBaseItem implements ILongReachWeapon, ILance {
    private ILanceCore core;
    public LanceItem(@Nonnull ILanceCore core) {
        super();
        this.core=core;
    }

    /**
     * The double value of this is the true length of a sprint.
     *
     * @return
     */
    public float getSprintLength() {
        return core.getSprintLength();
    }

    @Override
    public double getReach() {
        return 5;
    }

    @Override
    public int getCoolDown() {
        return core.getCoolDown();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        int coolDownTime = getCoolDown();
        EnumActionResult result = EnumActionResult.PASS;
        //Player can't sprint in the sky.
        if (playerIn.onGround && playerIn.isSneaking()) {
            float length = getSprintLength();
            if(core.releaseLanceSkill(worldIn,playerIn,handIn,length)){
                ItemTool.HeatWeaponIfSurvival(playerIn, held.getItem(), coolDownTime);
                result = EnumActionResult.SUCCESS;
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Override
    public ILanceCore getLanceCore() {
        return core;
    }
}
