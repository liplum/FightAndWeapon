package net.liplum.registeies;

import net.liplum.ObjectNames;
import net.liplum.MetaData;
import net.liplum.items.gemstones.RubyItem;
import net.liplum.items.weapons.BattleAxeItem;
import net.liplum.items.weapons.QuartzAxeItem;
import net.liplum.items.weapons.QuartzSwordItem;
import net.liplum.items.weapons.TestSwordItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS_LIST = DeferredRegister.create(ForgeRegistries.ITEMS, MetaData.MOD_ID);
    public static Item.Properties toFawItemGroup(){
        return new Item.Properties().group(ItemGroupRegistries.FawItemGroup);
    }

    public static final RegistryObject<Item> RUBY_ITEM = ITEMS_LIST.register(ObjectNames.RubyItem, () -> new RubyItem(toFawItemGroup()));
    public static final RegistryObject<Item> QUARTZ_SWORD_ITEM = ITEMS_LIST.register(ObjectNames.QuartzSwordItem, () -> new QuartzSwordItem(toFawItemGroup()));
    public static final RegistryObject<Item> QUARTZ_AXE_ITEM = ITEMS_LIST.register(ObjectNames.QuartzAxeItem, () -> new QuartzAxeItem(toFawItemGroup()));
    public static final RegistryObject<Item> TEST_SWORD_ITEM = ITEMS_LIST.register(ObjectNames.TestSwordItem, () -> new TestSwordItem(toFawItemGroup()));
    public static final RegistryObject<Item> BATTLE_AXE_ITEM = ITEMS_LIST.register(ObjectNames.BattleAxeItem, () -> new BattleAxeItem(toFawItemGroup()));
}
