package net.liplum.items.weapons;

import net.liplum.lib.weapons.IMeleeWeapon;
import net.liplum.lib.weapons.ISkillableWeapon;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        boolean isCreative = playerIn.isCreative();
        int coolDownTime = getCoolDown();
        float knockBackAngleToX = MathHelper.sin(playerIn.rotationYaw * ((float) Math.PI / 180F));
        float knockBackAngleToY = -MathHelper.cos(playerIn.rotationYaw * ((float) Math.PI / 180F));
        //Default is PASS
        EnumActionResult result = EnumActionResult.PASS;
        //If play were not sneaking, didn't detect.
        if (!playerIn.isSneaking()) {
            //The MAIN HAND MODE:
            if (handIn == EnumHand.MAIN_HAND) {
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
                        sideEntity.knockBack(playerIn, 0.5f, knockBackAngleToX, knockBackAngleToY);
                    }
                }
                //Some effects of attack
                playerIn.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f);
                playerIn.spawnSweepParticles();
                if (!isCreative) {
                    playerIn.getCooldownTracker().setCooldown(held.getItem(), coolDownTime);
                    //When you release the skill, it will make your shield hot.
                    //Don't worry about the EMPTY, if that it'll return Items.AIR (no exception).
                    if (offHeld.getItem() == Items.SHIELD) {
                        playerIn.getCooldownTracker().setCooldown(offHeld.getItem(), coolDownTime / 5 * 2);
                    }
                }
                result = EnumActionResult.SUCCESS;
            }
            //The OFF HAND MODE
            else {
                //nothing
            }
        }
        return new ActionResult<>(result, held);
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
