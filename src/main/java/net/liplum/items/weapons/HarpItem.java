package net.liplum.items.weapons;

import net.liplum.lib.weapons.IHarp;
import net.liplum.lib.weapons.IHarpSkill;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class HarpItem extends WeaponBaseItem implements IHarp {
    private int coolDown = 20 * 20;//Unit:tick
    private double radius = 8;
    private List<IHarpSkill> allSkills =new ArrayList<>();

    public HarpItem() {
        super(ToolMaterial.WOOD);
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        boolean isCreative = playerIn.isCreative();
        int coolDownTime = getCoolDown();
        double r = getRadius();
        AxisAlignedBB playerBox = playerIn.getEntityBoundingBox();
        List<EntityLivingBase> allInRange = worldIn
                .getEntitiesWithinAABB(EntityLivingBase.class, playerBox.grow(r, 0.25D, r));
        for (EntityLivingBase e : allInRange) {
            //If friend
            if (e == playerIn ||
                    playerIn.isOnSameTeam(e) ||//Player's team member
                    e instanceof EntityVillager
            ) {
                e.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60, 1));
            }
            //If enemy
            else {
                //Detect whether the entity is a killer of zombies
                if (e instanceof EntityZombie || e instanceof EntityZombieVillager || e instanceof EntityPigZombie || e instanceof EntityGiantZombie || e instanceof EntityZombieHorse) {
                    e.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 0));
                } else {
                    e.addPotionEffect(new PotionEffect(MobEffects.POISON, 40, 2));
                }
            }
        }
        double px = playerIn.posX, py = playerIn.posY, pz = playerIn.posZ;

        int rangeInt = MathHelper.ceil(r);
        for (int i = -rangeInt; i <= rangeInt; i++) {
            for (int j = -rangeInt; j <= rangeInt; j++) {
                worldIn.spawnParticle(EnumParticleTypes.NOTE, px + i, py, pz + j, 1, 1, 1);
            }
        }
        result = EnumActionResult.SUCCESS;
        return ActionResult.newResult(result, held);
    }

    /**
     * @return the range of the skill.
     */
    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setRadius(double newRadius) {
        radius = newRadius;
    }

    /**
     * Gets all harp skills of this harp
     *
     * @return all harp skills
     */
    @Override
    public List<IHarpSkill> getHarpSkills() {
        return allSkills;
    }
}
