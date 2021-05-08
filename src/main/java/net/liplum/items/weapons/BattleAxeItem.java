package net.liplum.items.weapons;

import net.liplum.lib.math.MathTool;
import net.liplum.lib.math.Point;
import net.liplum.lib.math.Vector2D;
import net.liplum.lib.tools.ItemTool;
import net.liplum.lib.items.IMeleeWeapon;
import net.liplum.lib.items.ISkillableWeapon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BattleAxeItem extends ItemAxe implements IMeleeWeapon, ISkillableWeapon {
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
        int coolDownTime = getCoolDown();
        float knockBackAngleToX = MathHelper.sin(playerIn.rotationYaw * ((float) Math.PI / 180F));
        float knockBackAngleToY = -MathHelper.cos(playerIn.rotationYaw * ((float) Math.PI / 180F));
        //Default is PASS
        EnumActionResult result = EnumActionResult.PASS;
        //If play were not sneaking, didn't detect.
        if (!playerIn.isSneaking()) {
            //The MAIN HAND MODE:
            if (handIn == EnumHand.MAIN_HAND) {
                double r = getSweepRange();
                AxisAlignedBB playerBox = playerIn.getEntityBoundingBox();
                List<EntityLivingBase> allInRange = worldIn
                        .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(r, 0.25D, r));
                //Gets player's look vector and turn it to v2d.
                Vec3d pLook = playerIn.getLookVec();
                Vector2D pLook2D = new Vector2D(pLook.x, pLook.z);
                Point pp = new Point(playerIn.posX, playerIn.posZ);
                for (EntityLivingBase e : allInRange) {
                    if (e != playerIn &&//Without player self
                            (!e.isOnSameTeam(playerIn) &&//The side entity is not on the same team with attacker
                                    e.getDistanceSq(playerIn) < r * r)) {
                        Point sp = new Point(e.posX, e.posZ);
                        Point spNew = sp.minus(pp);
                        Vector2D sv = spNew.toV2D();
                        if (MathTool.belongToCO(0, 1, sv.cosAngle(pLook2D))) {
                            e.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), skillDamage);
                            e.knockBack(playerIn, 0.5f, knockBackAngleToX, knockBackAngleToY);
                        }
                    }
                }
                //Some effects of attack
                playerIn.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0f, 1.0f);
                playerIn.spawnSweepParticles();
                if (ItemTool.HeatWeaponIfSurvival(playerIn,held.getItem(),coolDownTime)) {
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
        return ActionResult.newResult(result, held);
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
}
