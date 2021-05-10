package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.enchantments.MusicEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class EnchantmentRegistry {
    public static final LinkedList<Enchantment> Enchantments = new LinkedList<>();
    public static Enchantment with(Enchantment enchantment, String name) {
        Enchantments.addLast(enchantment);
        return enchantment.setRegistryName(Names.prefixRegister(name)).
                setName(Names.prefixUnloc(name));
    }

    public static final Enchantment MUSIC = with(new MusicEnchantment(
            Enchantment.Rarity.COMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}), "music");

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Enchantment> event) {
        IForgeRegistry<Enchantment> items = event.getRegistry();
        for(Enchantment e :Enchantments){
            items.register(e);
        }
    }
}
