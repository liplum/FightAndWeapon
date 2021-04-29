package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.ObjectNames;
import net.liplum.items.gemstones.RubyItem;
import net.liplum.items.weapons.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class ItemRegistry {

    public static Item with(Item item, String name) {
        return item.setRegistryName(MetaData.MOD_ID + ":" + name).
                setUnlocalizedName(MetaData.MOD_ID + "." + name).
                setCreativeTab(CreativeTabsRegistry.FawItemGroup);
    }

    public static final Item.ToolMaterial Material_Quartz =
            EnumHelper.addToolMaterial(ObjectNames.MaterialQuartz, 3, 80, 10.0F, 5.0F, 18);

    public static final Item Ruby_Item = with(new RubyItem(), ObjectNames.RubyItem);
    public static final Item QUARTZ_SWORD_ITEM = with(new QuartzSwordItem(Item.ToolMaterial.IRON), ObjectNames.QuartzSwordItem);
    public static final Item Quartz_Axe_Item = with(new QuartzAxeItem(Item.ToolMaterial.IRON), ObjectNames.QuartzAxeItem);
    public static final Item Test_Sword_Item = with(new TestSwordItem(), ObjectNames.TestSwordItem);
    public static final Item Battle_Axe_Item = with(new BattleAxeItem(), ObjectNames.BattleAxeItem);
    public static final Item Lance_Item = with(new LanceItem(), ObjectNames.LanceItem);

    public static final Item[] Items = new Item[]{
            Ruby_Item, QUARTZ_SWORD_ITEM, Quartz_Axe_Item, Test_Sword_Item,
            Battle_Axe_Item, Lance_Item
    };

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> items = event.getRegistry();
        items.registerAll(Items);
    }

    private static void registerModel(Item item) {
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        for (Item item : Items) {
            registerModel(item);
        }
    }

}
