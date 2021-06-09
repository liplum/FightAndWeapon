package net.liplum.gui.server;

import net.liplum.items.gemstones.GemstoneItem;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class InlayTableContainer extends Container {
    private static final double MaxReachDistanceSq = 64;
    private final World world;
    private final BlockPos pos;
   private final ItemStackHandler gemstoneItemHandler = new ItemStackHandler(1);
    private final ItemStackHandler weaponItemHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputItemHandler = new ItemStackHandler(1);
    private final Slot gemstoneSlot =
            new SlotItemHandler(gemstoneItemHandler, 0, 56, 17){
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return FawItemUtil.isGemstone(stack);
                }
            };
    private final Slot weaponSlot =
            new SlotItemHandler(weaponItemHandler, 0, 56, 55){
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return FawItemUtil.isGemstone(stack);
                }
            };
    private final Slot outputSlot =
            new SlotItemHandler(outputItemHandler, 0, 133, 26){
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return false;
                }
            };

    private final InventoryPlayer playerInventory;

    public InlayTableContainer(EntityPlayer player, World world, int x, int y, int z) {
        this.world = world;
        this.pos = new BlockPos(x, y, z);
        playerInventory = player.inventory;
        addAllSlots();
    }


    public void addAllSlots() {
        addSlotToContainer(gemstoneSlot);
        addSlotToContainer(weaponSlot);
        addSlotToContainer(outputSlot);

        addPlayerInventorySlots();
    }


    public void addPlayerInventorySlots() {
        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

   @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (playerIn.isServerWorld()) {
            tryDropItem(playerIn, gemstoneSlot.getStack());
            tryDropItem(playerIn, weaponSlot.getStack());
        }
    }

    public void tryDropItem(@Nonnull EntityPlayer player, @Nonnull ItemStack itemStack) {
        if (itemStack != ItemStack.EMPTY) {
            player.dropItem(itemStack, false);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.world.equals(world) &&
                playerIn.getDistanceSq(pos) <= MaxReachDistanceSq;
    }
}
