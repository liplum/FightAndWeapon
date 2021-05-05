package net.liplum.registeies;

import net.liplum.ObjectNames;
import net.liplum.MetaData;
import net.liplum.items.gemstones.RubyItem;
import net.liplum.items.weapons.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS_LIST = DeferredRegister.create(ForgeRegistries.ITEMS, MetaData.MOD_ID);
    public static Item.Properties defaultBuilder(){
        return new Item.Properties().group(ItemGroupRegistry.FawItemGroup);
    }

    public static final RegistryObject<Item> RUBY_ITEM = ITEMS_LIST.register(ObjectNames.RubyItem, () -> new RubyItem(defaultBuilder()));
    public static final RegistryObject<Item> QUARTZ_SWORD_ITEM = ITEMS_LIST.register(ObjectNames.QuartzSwordItem, () -> new QuartzSwordItem(defaultBuilder()));
    public static final RegistryObject<Item> QUARTZ_AXE_ITEM = ITEMS_LIST.register(ObjectNames.QuartzAxeItem, () -> new QuartzAxeItem(defaultBuilder()));
    public static final RegistryObject<Item> TEST_SWORD_ITEM = ITEMS_LIST.register(ObjectNames.TestSwordItem, () -> new TestSwordItem(defaultBuilder()));
    public static final RegistryObject<Item> BATTLE_AXE_ITEM = ITEMS_LIST.register(ObjectNames.BattleAxeItem, () -> new BattleAxeItem(defaultBuilder()));
    public static final RegistryObject<Item> LANCE_ITEM = ITEMS_LIST.register(ObjectNames.LanceItem, () -> new LanceItem(defaultBuilder()));
}
