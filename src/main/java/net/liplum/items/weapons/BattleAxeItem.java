package net.liplum.items.weapons;

import net.liplum.lib.weapons.IMeleeWeapon;
import net.liplum.lib.weapons.WeaponBaseItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BattleAxeItem extends WeaponBaseItem implements IMeleeWeapon {
    public BattleAxeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        double range = 3;
        //P:Gets all living entities in this range centered on this target entity.
        //P:And iterates them.
        for (LivingEntity sideEntity : playerIn.getEntityWorld().
                getEntitiesWithinAABB(LivingEntity.class, playerIn.getBoundingBox().grow(range, 0.25D, range))) {
            //P:Without the attacker
            if (sideEntity != playerIn &&
                    //P:The side entity is not on the same team with attacker
                    !playerIn.isOnSameTeam(sideEntity) &&
                    //P:It can attack the armor stand which is no marker.
                    (!(sideEntity instanceof ArmorStandEntity) || !((ArmorStandEntity) sideEntity).hasMarker()) &&
                    //P:The side entity is in the max range
                    sideEntity.getDistanceSq(sideEntity) < 8.0D + range)
            {
                sideEntity.attackEntityFrom(new EntityDamageSource("aoe",playerIn),10);
                //P:Computes the length of knock back.
                sideEntity.applyKnockback(0.4F, MathHelper.sin(playerIn.rotationYaw * ((float) Math.PI / 180F)), -MathHelper.cos(playerIn.rotationYaw * ((float) Math.PI / 180F)));
            }
        }

        playerIn.world.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, playerIn.getSoundCategory(), 1.0F, 1.0F);
        playerIn.spawnSweepParticles();
        return new ActionResult<>(ActionResultType.SUCCESS,playerIn.getHeldItem(handIn));
    }
}
