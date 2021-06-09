package net.liplum.registeies;

import net.liplum.I18ns;
import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.enchantments.MusicEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class EnchantmentRegistry {
    public static final LinkedList<Enchantment> Enchantments = new LinkedList<>();
    public static final Enchantment MUSIC = with(new MusicEnchantment(
            Enchantment.Rarity.COMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND}), "music");

    public static Enchantment with(Enchantment enchantment, String name) {
        Enchantments.addLast(enchantment);
        return Names.setRegisterName(enchantment, name).
                setName(I18ns.prefixUnloc(name));
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Enchantment> event) {
        IForgeRegistry<Enchantment> items = event.getRegistry();
        for (Enchantment e : Enchantments) {
            items.register(e);
        }
    }
}
