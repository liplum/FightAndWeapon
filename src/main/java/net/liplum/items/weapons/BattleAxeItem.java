package net.liplum.items.weapons;

import net.liplum.lib.weapons.IMeleeWeapon;
import net.liplum.lib.weapons.ISkillableWeapon;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class BattleAxeItem extends WeaponBaseItem implements IMeleeWeapon, ISkillableWeapon {
    public BattleAxeItem() {
        super(ToolMaterial.IRON);
    }

    private double sweepRange = 3;
    private int coolDown = 5 * 20;
    private float skillDamage = 8f;

    public void setSweepRange(double sweepRange) {
        this.sweepRange = sweepRange;
    }

    public double getSweepRange() {
        return sweepRange;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        if (!playerIn.isSneaking()) {
            List<EntityLivingBase> allEntitiesInRange = worldIn
                    .getEntitiesWithinAABB(EntityLivingBase.class, playerIn.getEntityBoundingBox()
                            .grow(getSweepRange(), 0.25D, getSweepRange()));
            for (EntityLivingBase sideEntity : allEntitiesInRange) {
                //Without player self
                if (sideEntity != playerIn &&
                        //The side entity is not on the same team with attacker
                        (!sideEntity.isOnSameTeam(playerIn))
                ) {
                    sideEntity.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), skillDamage);
                    sideEntity.knockBack(playerIn, 0.5f,
                            MathHelper.sin(playerIn.rotationYaw * ((float) Math.PI / 180F)),
                            -MathHelper.cos(playerIn.rotationYaw * ((float) Math.PI / 180F)));
                }
            }
            playerIn.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f);
            playerIn.spawnSweepParticles();
            if (!playerIn.isCreative()) {
                playerIn.getCooldownTracker().setCooldown(held.getItem(), getCoolDown());
            }
            if (handIn == EnumHand.MAIN_HAND) {
                ItemStack offHand = playerIn.getHeldItemOffhand();
                if (!offHand.isEmpty() &&
                        offHand.getItem().isShield(offHand, playerIn)
                ) {
                    playerIn.getCooldownTracker().setCooldown(offHand.getItem(),getCoolDown() / 5 * 2);
                    return new ActionResult<>(EnumActionResult.SUCCESS, held);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, held);
    }

    @Override
    public int getCoolDown() {
        return coolDown;
    }

    @Override
    public void setCoolDown(int tick) {
        coolDown = tick;
    }
}
