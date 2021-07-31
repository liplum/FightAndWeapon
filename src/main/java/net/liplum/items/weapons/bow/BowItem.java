package net.liplum.items.weapons.bow;

import com.google.common.base.Supplier;
import net.liplum.FawBehaviors;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.utils.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BowItem extends WeaponBaseItem {
    private final BowCore core;

    public BowItem(@Nonnull BowCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
        this.addProperty();
    }

    protected void addProperty() {

    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        if (!FawItemUtil.isWeaponBroken(held)) {
            boolean hasAmmo = FawItemUtil.hasAmmo(playerIn, core);
            ActionResult<ItemStack> newResult = ForgeEventFactory.onArrowNock(held, worldIn, playerIn, handIn, hasAmmo);
            if (newResult != null) {
                return newResult;
            }
            if (hasAmmo || playerIn.isCreative()) {
                playerIn.setActiveHand(handIn);
                result = EnumActionResult.SUCCESS;
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving, int useTime) {
        @Nullable EntityPlayer player = null;
        if (entityLiving instanceof EntityPlayer) {
            player = (EntityPlayer) entityLiving;
            useTime = ForgeEventFactory.onArrowLoose(stack, worldIn, player, useTime, true);
            if (useTime < 5) {
                return;
            }
        }
        Modifier modifier = GemUtil.getModifierFrom(stack);
        ItemStack ammo = FawItemUtil.findAmmo(entityLiving, this, stack, modifier);
        Item item = ammo.getItem();
        if (!(item instanceof ItemArrow)) {
            return;
        }
        ItemArrow arrowItem = (ItemArrow) item;
        AttrCalculator calculator = new AttrCalculator(this)
                .entity(entityLiving)
                .itemStack(stack)
                .modifier(modifier);
        //float f = getArrowVelocity(i);
        if (!worldIn.isRemote) {
            EntityArrow arrowEntity = arrowItem.createArrow(worldIn, stack, entityLiving);
            if (EntityUtil.isCreative(player)) {
                arrowEntity.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
            }
            arrowEntity.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0F, 3F, 1F);
            /*if (f == 1.0F) {
                arrowEntity.setIsCritical(true);
            }*/
            arrowEntity.setDamage(10);
            arrowEntity.setKnockbackStrength(1);
            worldIn.spawnEntity(arrowEntity);
        }
        entityLiving.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1F, 1F);
        if (!EntityUtil.isCreative(player)) {
            ammo.shrink(1);
        }

        FawBehaviors.onWeaponUse(entityLiving, this, stack);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        if (core.isCheckPulling()) {
            EntityPlayer p = (EntityPlayer) player;
            EnumHand hand = ItemUtil.inWhichHand(p, stack);
            if (hand == null) {
                return;
            }
            Modifier modifier = GemUtil.getModifierFrom(stack);
            PullingBowArgs args = new PullingBowArgs().setPullingTick(count);
            args.entity(p)
                    .hand(hand)
                    .world(p.world)
                    .weapon(this)
                    .itemStack(stack)
                    .modifier(modifier)
                    .calculator(FawUtil.toCalculator(args));
            core.onPulling(args);
        }
    }

    @Nonnull
    @Override
    public BowCore getConcreteCore() {
        return core;
    }
}
