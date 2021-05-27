package net.liplum.items.weapons.harp;

import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.ItemTool;
import net.liplum.lib.weaponcores.IHarpCore;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class HarpItem extends WeaponBaseItem<IHarpCore> {
    private IHarpCore core;

    public HarpItem(@Nonnull IHarpCore core) {
        super();
        this.core = core;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking()) {
            ItemStack offHeld = playerIn.getHeldItemOffhand();
            int originCoolDown = getCoolDown();
            double originR = core.getRadius();
            double r = MathUtil.fixMin(originR , 0);
            int coolDown = MathUtil.fixMin( originCoolDown,0);

            AxisAlignedBB playerBox = playerIn.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = worldIn
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(r, 0.25D, r));

            for (EntityLivingBase e : allInRange) {
                core.releaseHarpSkill(worldIn, playerIn, handIn, e);
            }
            double px = playerIn.posX, py = playerIn.posY, pz = playerIn.posZ;

            int rangeInt = MathHelper.ceil(r);
            for (int i = -rangeInt; i <= rangeInt; i++) {
                for (int j = -rangeInt; j <= rangeInt; j++) {
                    worldIn.spawnParticle(EnumParticleTypes.NOTE, px + i, py, pz + j, 1, 1, 1);
                }
            }
            ItemTool.HeatWeaponIfSurvival(playerIn,held.getItem(),coolDown);
            result = EnumActionResult.SUCCESS;
        }
        return ActionResult.newResult(result, held);
    }

    @Override
    public IHarpCore getCore() {
        return core;
    }
}
