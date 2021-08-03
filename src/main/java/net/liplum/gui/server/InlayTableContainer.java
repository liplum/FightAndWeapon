package net.liplum.gui.server;

import net.liplum.Vanilla;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.items.GemstoneItem;
import net.liplum.items.tools.InlayingToolItem;
import net.liplum.lib.gui.Property;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.GuiUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class InlayTableContainer extends ContainerBase {
    private static final int InlayTableSlotCount = 3;
    private static final int PlayerInventoryEndIndex = InlayTableSlotCount + Vanilla.PlayerMainInventorySlotCount;
    @Nonnull
    public final Property<Boolean> canDo = new Property<>(true);
    private final InventoryPlayer playerInventory;
    private final World world;
    private final BlockPos pos;
    private final ItemStackHandler gemstoneItemHandler = new ItemStackHandler(1);
    private final ItemStackHandler weaponItemHandler = new ItemStackHandler(1);
    private final ItemStackHandler outputItemHandler = new ItemStackHandler(1);
    private final Slot outputSlot =
            new SlotItemHandler(outputItemHandler, 0, 134, 27) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return false;
                }

                @Override
                public ItemStack onTake(@Nonnull EntityPlayer thePlayer, @Nonnull ItemStack stack) {
                    ItemStack newWeapon = super.onTake(thePlayer, stack);
                    onTookOutput();
                    return newWeapon;
                }
            };
    private final Slot weaponSlot =
            new SlotItemHandler(weaponItemHandler, 0, 56, 55) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    return FawItemUtil.isFawWeapon(stack);
                }

                public void onSlotChanged() {
                    super.onSlotChanged();
                    onInputChanged();
                }
            };
    private final Slot gemstoneSlot =
            new SlotItemHandler(gemstoneItemHandler, 0, 56, 17) {
                @Override
                public boolean isItemValid(@Nonnull ItemStack stack) {
                    Item item = stack.getItem();
                    return FawItemUtil.isGemstone(item) || item instanceof InlayingToolItem;
                }

                @Override
                public void onSlotChanged() {
                    super.onSlotChanged();
                    onInputChanged();
                }
            };

    public InlayTableContainer(EntityPlayer player, World world, int x, int y, int z) {
        this.playerInventory = player.inventory;
        this.world = world;
        this.pos = new BlockPos(x, y, z);
        addAllSlots();
    }


    private void addAllSlots() {
        addSlotToContainer(gemstoneSlot);
        addSlotToContainer(weaponSlot);
        addSlotToContainer(outputSlot);

        addPlayerInventorySlots(playerInventory, 8, 84);
    }

    private void onInputChanged() {
        ItemStack weaponStack = weaponSlot.getStack();
        ItemStack gemstoneStack = gemstoneSlot.getStack();
        if (weaponStack.isEmpty() || gemstoneStack.isEmpty()) {
            setCanDo(true);
            outputSlot.putStack(ItemStack.EMPTY);
            return;
        }
        //Now we have a weapon and a "gemstone(maybe)"
        WeaponBaseItem weaponType = (WeaponBaseItem) weaponStack.getItem();
        WeaponCore core = weaponType.getCore();
        Item maybeGemstoneItem = gemstoneStack.getItem();

        if (FawItemUtil.isGemstone(maybeGemstoneItem)) {
            //It's a gemstoneItem
            GemstoneItem gemstoneItem = (GemstoneItem) maybeGemstoneItem;
            //Now we have a weapon and a gemstoneItem
            if (GemUtil.hasGemstone(weaponStack)) {
                setCanDo(false);
            } else {
                setCanDo(true);
                //Now we have a weapon without gemstoneItem and a gemstoneItem waited for inlaying
                ItemStack newWeapon = weaponStack.copy();

                IGemstone gemstone = gemstoneItem.getGemstone();
                if (GemUtil.canInlayGemstone(core, gemstone)) {
                    GemUtil.inlayGemstone(newWeapon, gemstone);
                    outputSlot.putStack(newWeapon);
                }
            }
        } else {
            //It's an inlaying tool
            //Now we have a weapon and an inlaying tool
            if (GemUtil.hasGemstone(weaponStack)) {
                setCanDo(true);
                //Now we have a weapon with gemstone and an inlaying tool
                ItemStack newWeapon = weaponStack.copy();
                GemUtil.removeGemstone(newWeapon);
                outputSlot.putStack(newWeapon);
            } else {
                setCanDo(false);
            }
        }
    }

    private void onTookOutput() {
        ItemStack weaponStack = weaponSlot.getStack();
        ItemStack gemstoneStack = gemstoneSlot.getStack();
        if (weaponStack.isEmpty() || gemstoneStack.isEmpty()) {
            return;
        }
        GuiUtil.setEmpty(weaponSlot);
        gemstoneStack.shrink(1);
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        Slot originalSlot = inventorySlots.get(index);
        if (originalSlot == null || !originalSlot.getHasStack()) {
            return ItemStack.EMPTY;
        }
        ItemStack selection = originalSlot.getStack();

        if (originalSlot == gemstoneSlot || originalSlot == weaponSlot) {
            //It comes from the inlay table
            if (mergeItemStack(selection, InlayTableSlotCount, PlayerInventoryEndIndex, true)) {
                originalSlot.onSlotChanged();
            }
        } else if (originalSlot == outputSlot) {
            //It comes from the inlay table
            if (mergeItemStack(selection, InlayTableSlotCount, PlayerInventoryEndIndex, true)) {
                onTookOutput();
            }
        } else {
            //A slot of player's inventory
            if (gemstoneSlot.isItemValid(selection)) {
                if (GuiUtil.ifEmptyThenPut(gemstoneSlot, selection)) {
                    originalSlot.putStack(ItemStack.EMPTY);
                }
            } else if (weaponSlot.isItemValid(selection)) {
                if (GuiUtil.ifEmptyThenPut(weaponSlot, selection)) {
                    originalSlot.putStack(ItemStack.EMPTY);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onContainerClosed(@Nonnull EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!world.isRemote) {
            ItemStack gemstone = gemstoneSlot.getStack();
            ItemStack weapon = weaponSlot.getStack();
            playerIn.inventory.placeItemBackInInventory(world, gemstone);
            playerIn.inventory.placeItemBackInInventory(world, weapon);
            canDo.getPropertyChangedEvent().clear();
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.world.equals(world);
    }

    @Nonnull
    public Property<Boolean> getCanDo() {
        return canDo;
    }

    public void setCanDo(boolean canDo) {
        this.canDo.set(canDo);
    }
}
