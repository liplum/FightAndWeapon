package net.liplum.items.weapons.bow;

import net.liplum.FawBehaviors;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.FawUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

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
            if (playerIn.isCreative()) {
                boolean hasAmmo = FawItemUtil.hasAmmo(playerIn, core);
                ActionResult<ItemStack> newResult = ForgeEventFactory.onArrowNock(held, worldIn, playerIn, handIn, hasAmmo);
                if (newResult != null) {
                    return newResult;
                }
                if (hasAmmo) {
                    playerIn.setActiveHand(handIn);
                    result = EnumActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving, int useTime) {
        EntityPlayer player = (EntityPlayer) entityLiving;
        useTime = ForgeEventFactory.onArrowLoose(stack, worldIn, player, useTime, true);
        if (useTime < 5) {
            return;
        }
        FawBehaviors.onWeaponUse(player, this, stack);
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, useTime);
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
}
