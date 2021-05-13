package net.liplum.lib.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public abstract class WeaponBaseItem extends FawItem {
    public WeaponBaseItem() {
        super();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return super.showDurabilityBar(stack);
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    /**
     * Gets the basic attack speed and it can be changed by modifier.
     * Range : (0,no limit]. A greater value stands for a slower speed.(Normal items are 4)
     * @return the basic attack speed
     */
    public double getBaseAttackSpeed(){
        return 4;
    }

    /**
     * Gets the knock back.the basic is 1.0F.
     * @return the strength of knock back
     */
    public float getKnockBack() {
        return 1.0F;
    }

    /**
     * Deals damage to a target form the attacker.
     * @param stack
     * @param attacker
     * @param target
     * @param damage
     * @return true if the target was hit.
     */
    public boolean dealDamage(ItemStack stack, EntityLivingBase attacker, Entity target, float damage) {
        DamageSource source = attacker instanceof EntityPlayer?
                DamageSource.causePlayerDamage((EntityPlayer) attacker):
                DamageSource.causeMobDamage(attacker);
        return target.attackEntityFrom(source,damage);

    }

    public void reduceDurabilityOnHit(ItemStack stack, EntityPlayer player, float damage) {
        damage = Math.max(1f, damage / 10f);
        //TODO:This
        //ToolHelper.damageTool(stack, (int) damage, player);
    }

    /**
     * Whether player mines the block effectively
     * @param state the block player is mining
     * @return false but you can override it
     */
    public boolean isEffective(IBlockState state) {
        return false;
    }


}
