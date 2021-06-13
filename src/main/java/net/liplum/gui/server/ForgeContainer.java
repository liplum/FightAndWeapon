package net.liplum.gui.server;

import net.liplum.Vanilla;
import net.liplum.lib.gui.Property;
import net.liplum.tileentities.weapon.ForgeTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ForgeContainer extends ContainerBase {
    public static final int AdditionIconXOffset = 108;
    public static final int AdditionIconYOffset = 42;
    public static final int MaterialIconXOffset = 108;
    public static final int MaterialIconYOffset = 96;
    private static final int InlayTableSlotCount = 2;
    private static final int PlayerInventoryEndIndex = InlayTableSlotCount + Vanilla.PlayerInventoryCount;
    @Nonnull
    public final Property<Boolean> materialSlotHasItem = new Property<>(false);
    @Nonnull
    public final Property<Boolean> additionSlotHasItem = new Property<>(false);
    private final ForgeTE forgeTE;
    private final InventoryPlayer playerInventory;
    private final World world;
    private final BlockPos pos;
    private final Slot additionSlot;
    private final Slot materialSlot;

    public ForgeContainer(EntityPlayer player, World world, int x, int y, int z) {
        this.playerInventory = player.inventory;
        this.world = world;
        this.pos = new BlockPos(x, y, z);
        this.forgeTE = (ForgeTE) world.getTileEntity(pos);
        this.additionSlot = new SlotItemHandler(forgeTE.getAdditionHandler(), 0,
                AdditionIconXOffset, AdditionIconYOffset) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public void onSlotChanged() {
                super.onSlotChanged();
                onAdditionSlotChanged();
            }
        };
        this.materialSlot = new SlotItemHandler(forgeTE.getMaterialHandler(), 0,
                MaterialIconXOffset, MaterialIconYOffset) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return stack.getItem() == Items.IRON_INGOT;
            }

            @Override
            public void onSlotChanged() {
                super.onSlotChanged();
                onMaterialSlotChanged();
            }
        };
        addAllSlots();
    }

    private void onAdditionSlotChanged() {
        ItemStack addition = additionSlot.getStack();
        setAdditionSlotHasItem(!addition.isEmpty());
    }

    private void onMaterialSlotChanged() {
        ItemStack material = materialSlot.getStack();
        setMaterialSlotHasItem(!material.isEmpty());
    }


    private void addAllSlots() {
        addSlotToContainer(additionSlot);
        addSlotToContainer(materialSlot);

        addPlayerInventorySlots();
    }

    public void addPlayerInventorySlots() {
        int i = 36;

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.world.equals(world);
    }

    @Nonnull
    public Property<Boolean> getMaterialSlotHasItem() {
        return materialSlotHasItem;
    }

    public void setMaterialSlotHasItem(boolean materialSlotHasItem) {
        this.materialSlotHasItem.set(materialSlotHasItem);
    }

    @Nonnull
    public Property<Boolean> getAdditionSlotHasItem() {
        return additionSlotHasItem;
    }

    public void setAdditionSlotHasItem(boolean additionSlotHasItem) {
        this.additionSlotHasItem.set(additionSlotHasItem);
    }
}
