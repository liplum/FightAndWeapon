package net.liplum.items.weapons;

import net.liplum.lib.math.MathTool;
import net.liplum.lib.math.Point;
import net.liplum.lib.math.V3DHelper;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.weapons.IMeleeWeapon;
import net.liplum.lib.weapons.ISkillableWeapon;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class BattleAxeItem extends WeaponBaseItem implements IMeleeWeapon, ISkillableWeapon {
    public BattleAxeItem(Properties properties) {
        super(properties);
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
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack held = playerIn.getHeldItem(handIn);
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        boolean isCreative = playerIn.isCreative();
        int coolDownTime = getCoolDown();
        float knockBackAngleToX = MathHelper.sin(playerIn.rotationYaw * ((float) Math.PI / 180F));
        float knockBackAngleToY = -MathHelper.cos(playerIn.rotationYaw * ((float) Math.PI / 180F));
        //Default is PASS
        ActionResultType result = ActionResultType.PASS;
        //If play were not sneaking, didn't detect.
        if (!playerIn.isSneaking()) {
            //The MAIN HAND MODE:
            if (handIn == handIn.MAIN_HAND) {
                double r = getSweepRange();
                AxisAlignedBB playerBox = playerIn.getBoundingBox();
                List<LivingEntity> allInRange = worldIn
                        .getEntitiesWithinAABB(LivingEntity.class, playerBox.grow(r, 0.25D, r));
                //Gets player's look vector and turn it to v2d.
                Vector3d pLook = playerIn.getLookVec();
                Vector2D pLook2D = new Vector2D(pLook.x, pLook.z);
                Point pp = V3DHelper.to2DPoint(playerIn.getPositionVec());
                for (LivingEntity e : allInRange) {
                    if (e != playerIn &&//Without player self
                            (!e.isOnSameTeam(playerIn) &&//The side entity is not on the same team with attacker
                                    e.getDistanceSq(playerIn) < r * r)) {
                        Point sp = V3DHelper.to2DPoint(e.getPositionVec());
                        Point spNew = sp.minus(pp);
                        Vector2D sv = spNew.toV2D();
                        if (MathTool.belongToCC(0, MathTool.HalfPI, sv.angle(pLook2D))) {
                            e.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), skillDamage);
                            e.applyKnockback(0.5f, knockBackAngleToX, knockBackAngleToY);
                        }
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
                result = ActionResultType.SUCCESS;
            }
            //The OFF HAND MODE
            else {
                //nothing
            }
        }
        return new ActionResult(result, held);
    }

    /**
     * Gets the cool down time of weapon.
     *
     * @return The cool down time of weapon(by tick)
     */
    @Override
    public int getCoolDown() {
        return coolDown;
    }

    /**
     * Sets the cool down time of weapon
     *
     * @param tick cool down time
     */
    @Override
    public void setCoolDown(int tick) {
        coolDown = tick;
    }
}
