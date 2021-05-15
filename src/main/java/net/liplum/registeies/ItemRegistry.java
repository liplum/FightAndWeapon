package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.items.gemstones.RubyItem;
import net.liplum.items.weapons.QuartzAxeItem;
import net.liplum.items.weapons.QuartzSwordItem;
import net.liplum.items.weapons.TestSwordItem;
import net.liplum.items.weapons.battleaxe.BattleAxeItem;
import net.liplum.items.weapons.harp.HarpCoreType;
import net.liplum.items.weapons.harp.HarpItem;
import net.liplum.items.weapons.lance.LanceCoreType;
import net.liplum.items.weapons.lance.LanceItem;
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

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class ItemRegistry {
    public static final LinkedList<Item> Items = new LinkedList<>();

    public static Item with(Item item, String name) {
        Items.addLast(item);
        return item.setRegistryName(Names.prefixRegister(name)).
                setUnlocalizedName(Names.prefixUnloc(name)).
                setCreativeTab(CreativeTabsRegistry.FawItemGroup);
    }

    public static final Item.ToolMaterial Material_Quartz =
            EnumHelper.addToolMaterial(Names.Item.MaterialQuartz, 3, 80, 10.0F, 5.0F, 18);

    public static final Item Ruby_Item = with(new RubyItem(), Names.Item.RubyItem);
    public static final Item QUARTZ_SWORD_ITEM = with(new QuartzSwordItem(), Names.Item.QuartzSwordItem);
    public static final Item Quartz_Axe_Item = with(new QuartzAxeItem(Item.ToolMaterial.IRON), Names.Item.QuartzAxeItem);
    public static final Item Test_Sword_Item = with(new TestSwordItem(), Names.Item.TestSwordItem);
    public static final Item Battle_Axe_Item = with(new BattleAxeItem(), Names.Item.BattleAxeItem);
    public static final Item Lance_Item = with(new LanceItem(LanceCoreType.Normal), Names.Item.Lance.LanceItem);
    public static final Item Knight_Lance_Item = with(new LanceItem(LanceCoreType.KnightLance), Names.Item.Lance.KnightLanceItem);
    public static final Item Harp_Item = with(new HarpItem(HarpCoreType.Normal), Names.Item.Harp.HarpItem);

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> items = event.getRegistry();
        for(Item i :Items){
            items.register(i);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item) {
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        for (Item item : Items) {
            registerModel(item);
        }
    }

}
