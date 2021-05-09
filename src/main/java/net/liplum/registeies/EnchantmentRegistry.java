package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.enchantments.MusicEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class EnchantmentRegistry {
    public static Enchantment with(Enchantment item, String name) {
        return item.setRegistryName(MetaData.MOD_ID + ":" + name).
                setName(MetaData.MOD_ID + "." + name);
    }

    public static final Enchantment MUSIC = with(new MusicEnchantment(
            Enchantment.Rarity.COMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}), "music");

    public static final Enchantment[] Enchantments = new Enchantment[]{
            MUSIC
    };

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Enchantment> event) {
        IForgeRegistry<Enchantment> items = event.getRegistry();
        items.registerAll(Enchantments);
    }
}
