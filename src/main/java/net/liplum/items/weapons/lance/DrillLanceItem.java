package net.liplum.items.weapons.lance;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class DrillLanceItem extends WeaponBaseItem {
    private final LanceCore core;

    public DrillLanceItem(@Nonnull LanceCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);

        return new ActionResult<>(result, held);
    }

    @Nonnull
    @Override
    public LanceCore getConcreteCore() {
        return core;
    }
}
