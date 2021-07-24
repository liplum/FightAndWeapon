package net.liplum.items.weapons.bow;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.AttrCalculator;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

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
        ItemStack held = playerIn.getHeldItem(handIn);
        if (FawItemUtil.isWeaponBroken(held)) {
            return ActionResult.newResult(EnumActionResult.FAIL, held);
        }
        if (FawItemUtil.hasAmmo(playerIn)) {
            playerIn.setActiveHand(handIn);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        if (core.isCheckPulling()) {
            EntityPlayer p = (EntityPlayer) player;
            EnumHand hand = ItemTool.inWhichHand(p, stack);
            if (hand == null) {
                return;
            }
            Modifier modifier = GemUtil.getModifierFrom(stack);
            AttrCalculator calculator = new AttrCalculator()
                    .setWeaponCore(core)
                    .setModifier(modifier)
                    .setPlayer(p);
            PullingBowArgs args = new PullingBowArgs();
            args.setPullingTick(count)
                    .setPlayer(p)
                    .setHand(hand)
                    .setWorld(p.world)
                    .setWeapon(this)
                    .setItemStack(stack)
                    .setModifier(modifier)
                    .setCalculator(calculator);
            core.onPulling(args);
        }
    }
}
