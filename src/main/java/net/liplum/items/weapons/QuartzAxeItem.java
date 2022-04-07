package net.liplum.items.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class QuartzAxeItem extends ItemAxe {
    public QuartzAxeItem(ToolMaterial material) {
        super(material);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull EntityPlayer playerIn, @NotNull EnumHand handIn) {
        float cameraPitch = playerIn.cameraPitch;
        float cameraYaw = playerIn.cameraYaw;
        float rotationPitch = playerIn.rotationPitch;
        float rotationYaw = playerIn.rotationYaw;

        System.out.println("cameraPitch " + cameraPitch);
        System.out.println("cameraYaw " + cameraYaw);
        System.out.println("rotationPitch " + rotationPitch);
        System.out.println("rotationYaw " + rotationYaw);

        Vec2f pitchYaw = playerIn.getPitchYaw();
        System.out.println("pitchYaw pitch:" + pitchYaw.x + " yaw:" + pitchYaw.y);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
