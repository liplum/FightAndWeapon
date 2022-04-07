package net.liplum.registries;


import net.liplum.Names;
import net.liplum.api.weapon.GemQuality;
import net.liplum.api.weapon.IGemstoneItem;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.lib.ItemGroup;
import net.liplum.lib.utils.FawItemUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ItemGroupRegistry {
    public static ItemGroup FawGemstones = new ItemGroup(Names.ItemGroup.FawGemstones) {
        @Override
        protected void sortDisplayedItems(@NotNull NonNullList<ItemStack> currentItemGroupItems) {
            Map<GemQuality, List<ItemStack>> grouped = currentItemGroupItems.stream()
                    .filter(FawItemUtil::isGemstone)
                    .collect(Collectors.groupingBy(itemStack -> {
                        IGemstoneItem gemstone = (IGemstoneItem) itemStack.getItem();
                        return gemstone.getGemstone().getQuality();
                    }));
            currentItemGroupItems.clear();
            ArrayList<Map.Entry<GemQuality, List<ItemStack>>> entries = new ArrayList<>(grouped.entrySet());
            entries.sort(Map.Entry.comparingByKey());
            for (Map.Entry<GemQuality, List<ItemStack>> entry : entries) {
                List<ItemStack> gemstones = entry.getValue();
                gemstones.sort((o1, o2) -> {
                    int g1 = ((IGemstoneItem) o1.getItem()).getGemstone().getDisplayedOrderID();
                    int g2 = ((IGemstoneItem) o2.getItem()).getGemstone().getDisplayedOrderID();
                    if (g1 == g2) {
                        return 0;
                    }
                    return g1 < g2 ? -1 : 1;
                });
                currentItemGroupItems.addAll(gemstones);
            }
        }
    };
    public static ItemGroup FawWeapons = new ItemGroup(Names.ItemGroup.FawWeapons) {
        @Override
        @SideOnly(Side.CLIENT)
        protected void sortDisplayedItems(@NotNull NonNullList<ItemStack> currentItemGroupItems) {
            Map<Object, List<ItemStack>> grouped = currentItemGroupItems.stream()
                    .collect(Collectors.groupingBy(itemStack -> {
                        Item item = itemStack.getItem();
                        if (item instanceof WeaponBaseItem) {
                            WeaponBaseItem weapon = (WeaponBaseItem) item;
                            return weapon.getWeaponType();
                        }
                        return false;
                    }));
            currentItemGroupItems.clear();
            grouped.forEach((key, value) -> currentItemGroupItems.addAll(value));
        }
    };
    public static ItemGroup FawForges = new ItemGroup(Names.ItemGroup.FawForges);
}
