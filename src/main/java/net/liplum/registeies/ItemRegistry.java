package net.liplum.registeies;

import net.liplum.Gemstones;
import net.liplum.I18ns;
import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.items.gemstones.GemstoneItem;
import net.liplum.items.weapons.QuartzAxeItem;
import net.liplum.items.weapons.QuartzSwordItem;
import net.liplum.items.weapons.TestSwordItem;
import net.liplum.items.weapons.battleaxe.BattleAxeCoreTypes;
import net.liplum.items.weapons.battleaxe.BattleAxeItem;
import net.liplum.items.weapons.harp.HarpCoreTypes;
import net.liplum.items.weapons.harp.HarpItem;
import net.liplum.items.weapons.lance.LanceCoreTypes;
import net.liplum.items.weapons.lance.LanceItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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

    public static final Item.ToolMaterial Material_Quartz =
            EnumHelper.addToolMaterial(Names.Item.MaterialQuartz, 3, 80, 10.0F, 5.0F, 18);

    //------------------------------------------------------------------------------------------------------------------------------
    //  Registers all items
    //
    //Gemstone
    public static final Item Ruby_Item = gemstone(new GemstoneItem(Gemstones.Ruby_Gemstone));

    //Some test items
    public static final Item QUARTZ_SWORD_ITEM = weapon(new QuartzSwordItem(), Names.Item.QuartzSwordItem);
    public static final Item Quartz_Axe_Item = weapon(new QuartzAxeItem(Item.ToolMaterial.IRON), Names.Item.QuartzAxeItem);
    public static final Item Test_Sword_Item = weapon(new TestSwordItem(), Names.Item.TestSwordItem);

    //Battle Axe
    public static final Item Battle_Axe_Item = weapon(new BattleAxeItem(BattleAxeCoreTypes.Normal), Names.Item.BattleAxeItem);

    //Lance
    public static final Item Training_Lance_Item = weapon(new LanceItem(LanceCoreTypes.TrainingLance), Names.Item.Lance.TrainingLanceItem);
    public static final Item Light_Lance_Item = weapon(new LanceItem(LanceCoreTypes.LightLance), Names.Item.Lance.LightLanceItem);
    public static final Item Knight_Lance_Item = weapon(new LanceItem(LanceCoreTypes.KnightLance), Names.Item.Lance.KnightLanceItem);
    public static final Item Arena_Lance_Item = weapon(new LanceItem(LanceCoreTypes.ArenaLance), Names.Item.Lance.ArenaLanceItem);

    //Harp
    public static final Item Harp_Item = weapon(new HarpItem(HarpCoreTypes.Normal), Names.Item.Harp.HarpItem);

    //Block
    public static final Item Test_Block_Item = forge(new ItemBlock(BlockRegistry.TestBlock), "test_block");
    public static final Item Test_Func_Block_Item = forge(new ItemBlock(BlockRegistry.TestFuncBlock), "test_func_block");

    //public static final Item Test_Lance_Item = weapon(new LanceItem(LanceCoreTypes.TestLance), "test_lance");
    //------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------
    //  Sets the icon of FAW item group.
    //
    static {
        ItemGroupsRegistry.FawWeapons.setIcon(new ItemStack(Light_Lance_Item));
        ItemGroupsRegistry.FawGemstones.setIcon(new ItemStack(Ruby_Item));
        //ItemGroupsRegistry.FawForges.setIcon(new ItemStack(Ruby_Item));
    }

    //------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------
    //  Registries
    //
    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> items = event.getRegistry();
        for (Item i : Items) {
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

    //------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------
    //  Provides a easy way to register items
    //
    private static Item with(Item item, String name, CreativeTabs itemGroup) {
        Items.addLast(item);
        return Names.setRegisterName(item, name).
                setUnlocalizedName(I18ns.prefixUnloc(name)).
                setCreativeTab(itemGroup);
    }

    public static Item gemstone(GemstoneItem item) {
        return with(item, item.getGemstone().getRegisterName(), ItemGroupsRegistry.FawGemstones);
    }

    public static Item weapon(Item item, String name) {
        return with(item, name, ItemGroupsRegistry.FawWeapons);
    }

    public static Item forge(Item item, String name) {
        return with(item, name, ItemGroupsRegistry.FawForges);
    }
    //------------------------------------------------------------------------------------------------------------------------------
}
