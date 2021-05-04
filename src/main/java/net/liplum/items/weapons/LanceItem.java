package net.liplum.items.weapons;

import net.liplum.lib.weapons.ILongReachWeapon;
import net.liplum.lib.weapons.ISkillableWeapon;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class LanceItem extends WeaponBaseItem implements ILongReachWeapon, ISkillableWeapon {
    public LanceItem() {
        super(new Properties().group());
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        boolean isCreative = playerIn.isCreative();
        int coolDownTime = getCoolDown();
        ActionResultType result = ActionResultType.PASS;
        //Player can't sprint over the sky.
        if (playerIn.isOnGround() && playerIn.isSneaking()) {
            float length = getSprintLength();
            Vector3d playerFace = playerIn.getLookVec();
            Vector3d sprintForce = playerFace.scale(length);
            playerIn.setMotion(new Vector3d(sprintForce.x,0.32,sprintForce.z));
            if (!isCreative) {
                playerIn.getCooldownTracker().setCooldown(held.getItem(), coolDownTime);
            }
            result = ActionResultType.SUCCESS;
        }
        return new ActionResult(result, held);
    }
}
