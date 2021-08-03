package net.liplum.api.weapon;

import com.google.common.collect.Multimap;
import net.liplum.FawBehaviors;
import net.liplum.FawMod;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.registeies.WeaponRegistry;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.FixedAttrCalculator;
import net.liplum.entities.FawWeaponItemEntity;
import net.liplum.lib.FawDamage;
import net.liplum.lib.ItemProperty;
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
import net.minecraft.item.EnumAction;
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
import static net.liplum.Attributes.Generic.MaxUseDuration;

@LongSupport
public abstract class WeaponBaseItem extends FawItem {
    @Nonnull
    protected final FixedAttrCalculator onlyCoreCalculator;
    @Nonnull
    private final WeaponCore weaponCore;
    @Nonnull
    private final WeaponType weaponType;

    @LongSupport
    public WeaponBaseItem(@Nonnull WeaponCore weaponCore) {
        super();
        this.weaponCore = weaponCore;
        this.weaponType = weaponCore.getWeaponType();
        //Player can only hold ONE Weapon in an item stack.
        setMaxStackSize(1);
        //Player can't repair the weapon in common way(like an anvil)
        setNoRepair();
        this.onlyCoreCalculator = new FixedAttrCalculator(this, true, true);
        setMaxDamage(onlyCoreCalculator.calcu(Durability).getInt());
        this.applyPropertyOverride();
        WeaponRegistry.register(this);
    }

    @LongSupport
    public void applyPropertyOverride() {
        for (ItemProperty property : weaponCore.getItemProperties()) {
            this.addPropertyOverride(property.getPropertyName(), property);
        }
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
        try {
            boolean vanillaAdvanced = flagIn.isAdvanced();
            boolean shiftPressed = Utils.isShiftDown();
            boolean ctrlPressed = Utils.isCtrlDown();
            boolean altPressed = Utils.isAltDown();
            TooltipOption tooltipOption = new TooltipOption(shiftPressed, altPressed, ctrlPressed, vanillaAdvanced);
            IGemstone gemstone = GemUtil.getGemstoneFrom(stack);
            Modifier modifier = null;
            if (gemstone != null) {
                modifier = gemstone.getModifierOf(weaponCore);
            }
            AttrCalculator calculator = new AttrCalculator()
                    .weapon(this)
                    .modifier(modifier)
                    .entity(player)
                    .itemStack(stack)
                    .setUseSpecialValueWhenWeaponBroken(false);
            TooltipContext context = new TooltipContext(stack, this, calculator, tooltipOption, player);
            IWeaponTooltipBuilder builder = new WeaponTooltipBuilder(context);
            tooltip.addAll(builder.build().getTooltip());
        } catch (Exception e) {
            FawMod.Logger.error(e.getClass().getName() + e.getMessage());
        }
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return onlyCoreCalculator.calcu(MaxUseDuration).getInt();
    }

    /**
     * Deals damage to a target form the attacker.
     * It's called by {@link FawItemUtil#attackEntity(ItemStack, WeaponBaseItem, EntityLivingBase, Entity)}
     *
     * @param fawDamage
     * @param target
     * @param damage
     * @return true if the target was hit.
     */
    public boolean dealDamage(@Nonnull FawDamage fawDamage, @Nonnull Entity target, float damage) {
        FawBehaviors.onCauseDamageWithWeapon(fawDamage.attacker(), this, fawDamage.itemStack(), target, damage);
        return target.attackEntityFrom(fawDamage, damage);
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
        FawBehaviors.onCauseDamageWithWeapon(attacker, this, stack, target, damage);
        return target.attackEntityFrom(damageSource, damage);
    }

    public boolean attackEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return FawItemUtil.attackEntity(stack, this, player, entity);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return weaponCore.getLeftClickEntityBehavior().onLeftClickEntity(this, stack, player, entity);
    }

    @LongSupport
    public void reduceDurabilityOnHit(ItemStack stack, EntityLivingBase attacker, float attackDamage) {
        float weaponDamage = MathUtil.fixMin(1f, attackDamage / 10f);
        FawItemUtil.damageWeapon(this, stack, (int) weaponDamage, attacker);
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
                .weapon(this)
                .modifier(modifier)
                .itemStack(stack), slot, map);
        FawItemUtil.applyAttrModifier(weaponCore, modifier, context);
        return map;
    }

    /**
     * Gets the core.
     *
     * @return A core of this weapon.
     */
    @Nonnull
    @LongSupport
    public final WeaponCore getCore() {
        return weaponCore;
    }

    @Nonnull
    @LongSupport
    public abstract WeaponCore getConcreteCore();

    @Nonnull
    @LongSupport
    public WeaponType getWeaponType() {
        return weaponType;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return weaponCore.getRightClickUseAction();
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

    @Override
    public int getMaxDamage(@Nonnull ItemStack stack) {
        AttrCalculator calculator = new AttrCalculator(this).modifier(GemUtil.getModifierFrom(stack));
        return calculator.calcu(Durability).getInt();
    }
}
