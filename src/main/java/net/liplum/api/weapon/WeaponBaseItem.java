package net.liplum.api.weapon;

import com.google.common.collect.Multimap;
import net.liplum.api.registeies.WeaponRegistry;
import net.liplum.attributes.AttrCalculator;
import net.liplum.entities.FawWeaponItemEntity;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.Utils;
import net.liplum.tooltips.IWeaponTooltipBuilder;
import net.liplum.tooltips.TooltipContext;
import net.liplum.tooltips.WeaponTooltipBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.liplum.Attributes.Generic.Durability;

public abstract class WeaponBaseItem extends FawItem {
    @Nonnull
    private final WeaponCore weaponCore;
    @Nonnull
    private final WeaponType weaponType;

    public WeaponBaseItem(@Nonnull WeaponCore weaponCore) {
        super();
        this.weaponCore = weaponCore;
        this.weaponType = weaponCore.getWeaponType();
        AttrCalculator calculator = new AttrCalculator(weaponCore);
        setMaxDamage(calculator.calcu(Durability).getInt());
        //Player can only hold ONE Weapon in an item stack.
        setMaxStackSize(1);
        //Player can't repair the weapon in common way(an anvil)
        setNoRepair();
        WeaponRegistry.register(this);
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return super.showDurabilityBar(stack);
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) {
            return;
        }
        boolean vanillaAdvanced = flagIn.isAdvanced();
        boolean shiftPressed = Utils.isShiftDown();
        boolean altPressed = Utils.isAltDown();
        TooltipOption tooltipOption = new TooltipOption(shiftPressed, altPressed, vanillaAdvanced);
        IGemstone gemstone = GemUtil.getGemstoneFrom(stack);
        Modifier modifier = null;
        if (gemstone != null) {
            modifier = gemstone.getModifierOf(weaponCore);
        }
        AttrCalculator calculator = new AttrCalculator()
                .setWeaponCore(weaponCore)
                .setModifier(modifier)
                .setPlayer(player)
                .setItemStack(stack)
                .setUseSpecialValueWhenWeaponBroken(false);
        TooltipContext context = new TooltipContext(stack, this, calculator, tooltipOption);
        IWeaponTooltipBuilder builder = new WeaponTooltipBuilder(context);
        tooltip.addAll(builder.build().getTooltip());
    }

    /**
     * Deals damage to a target form the attacker.
     * It's called by {@link FawItemUtil#attackEntity(ItemStack, WeaponBaseItem, EntityLivingBase, Entity)}
     *
     * @param stack
     * @param attacker
     * @param target
     * @param damage
     * @return true if the target was hit.
     */
    public boolean dealDamage(@Nonnull ItemStack stack, @Nonnull EntityLivingBase attacker, @Nonnull Entity target, @Nonnull DamageSource damageSource, float damage) {
        return target.attackEntityFrom(damageSource, damage);
    }

    public boolean attackEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return FawItemUtil.attackEntity(stack, this, player, entity);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return weaponCore.getLeftClickEntityBehavior().onLeftClickEntity(this, stack, player, entity);
    }

    public void reduceDurabilityOnHit(ItemStack stack, EntityPlayer player, float attackDamage) {
        float weaponDamage = MathUtil.fixMin(1f, attackDamage / 10f);
        FawItemUtil.damageWeapon(this, stack, (int) weaponDamage, player);
    }

    /**
     * Whether player mines the block effectively
     *
     * @param state the block player is mining
     * @return false but you can override it
     */
    public boolean isEffective(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entityLiving) {
        if ((int) state.getBlockHardness(worldIn, pos) != 0) {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, @Nonnull ItemStack stack) {
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        Modifier modifier = GemUtil.getModifierFrom(stack);
        WeaponAttrModifierContext context = new WeaponAttrModifierContext(stack, new AttrCalculator()
                .setWeaponCore(getCore())
                .setModifier(modifier)
                .setItemStack(stack), slot, map);
        FawItemUtil.applyAttrModifier(weaponCore, modifier, context);
        return map;
    }

    /**
     * Gets the core.
     *
     * @return A core of this weapon.
     */
    @Nonnull
    public WeaponCore getCore() {
        return weaponCore;
    }

    @Nonnull
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Override
    public boolean hasCustomEntity(@Nonnull ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public Entity createEntity(@Nonnull World world, Entity location, @Nonnull ItemStack itemstack) {
        EntityItem entity = new FawWeaponItemEntity(world, location.posX, location.posY, location.posZ, itemstack);
        if (location instanceof EntityItem) {
            EntityItem item = (EntityItem) location;
            entity.setPickupDelay(item.pickupDelay);
        }
        entity.motionX = location.motionX;
        entity.motionY = location.motionY;
        entity.motionZ = location.motionZ;
        return entity;
    }

}
