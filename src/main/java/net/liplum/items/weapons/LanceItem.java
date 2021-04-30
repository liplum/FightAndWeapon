package net.liplum.items.weapons;

import net.liplum.lib.weapons.ILongReachWeapon;
import net.liplum.lib.weapons.ISkillableWeapon;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LanceItem extends WeaponBaseItem implements ILongReachWeapon, ISkillableWeapon {
    public LanceItem() {
        super(ToolMaterial.IRON);
    }

    private int coolDown = 20 * 6;

    /**
     * The double value of this is the true length of a sprint.
     * @return
     */
    public float getSprintLength() {
        return sprintLength;
    }

    /**
     * The double value of this is the true length of a sprint.
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
        //playerIn.motionY += 0.32;
        float length = getSprintLength();
        playerIn.motionX = -MathHelper.sin(playerIn.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(playerIn.rotationPitch / 180.0F * (float) Math.PI) * length;
        //playerIn.motionX=1;
        //playerIn.motionZ=1;
        //playerIn.rotationPitch += 90;
        playerIn.motionZ = MathHelper.cos(playerIn.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(playerIn.rotationPitch / 180.0F * (float) Math.PI) * length;
        if (!playerIn.isCreative()) {
            playerIn.getCooldownTracker().setCooldown(held.getItem(), getCoolDown());
        }
        return new ActionResult<>(EnumActionResult.PASS, held);
    }
}
