package net.liplum.items.weapons;

import net.liplum.lib.math.MathTool;
import net.liplum.lib.tools.ItemTool;
import net.liplum.lib.tools.JavaTool;
import net.liplum.lib.weapons.IHarp;
import net.liplum.lib.weapons.IHarpCore;
import net.liplum.lib.weapons.IHarpModifier;
import net.liplum.lib.weapons.WeaponBaseItem;
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

public class HarpItem extends WeaponBaseItem implements IHarp {
    //private int coolDown = 20 * 20;//Unit:tick
    //private double radius = 8;
    private IHarpCore core;
    private IHarpModifier modifier;

    public HarpItem(@Nonnull IHarpCore core) {
        super(ToolMaterial.WOOD);
        this.core = core;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        if (playerIn.isSneaking()) {
            ItemStack offHeld = playerIn.getHeldItemOffhand();
            int originCoolDown = getCoolDown(), deltaCoolDownTime = modifier != null ? modifier.getCoolDownModifier() : 0;
            double originR = core.getRadius(), deltaR = modifier != null ? modifier.getSkillRadiusModifier() : 0;
            double r = MathTool.fixMin(originR + deltaR, 0);
            int coolDown =MathTool.fixMin( originCoolDown + deltaCoolDownTime,0);

            AxisAlignedBB playerBox = playerIn.getEntityBoundingBox();
            List<EntityLivingBase> allInRange = worldIn
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(r, 0.25D, r));

            for (EntityLivingBase e : allInRange) {
                JavaTool.notNullThenDo(core, (c) -> c.releaseSkill(worldIn, playerIn, handIn, e));
                JavaTool.notNullThenDo(modifier, (m) -> m.doExtraSkillEffect(worldIn, playerIn, handIn, e));
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
    public IHarpCore getHarpCore() {
        return core;
    }

    @Override
    public IHarpModifier getHarpModifier() {
        return modifier;
    }

    @Override
    public void setHarpModifier(IHarpModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public int getCoolDown() {
        return core.getCoolDown();
    }
}
