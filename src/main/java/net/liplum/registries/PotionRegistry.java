package net.liplum.registries;

import net.liplum.I18ns;
import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.potions.UnstoppablePotion;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class PotionRegistry {
    public static final LinkedList<Potion> Potions = new LinkedList<>();
    /**
     * Unstoppable:Who has this potion effect won't be attack by other mobs and players.
     */
    public static final Potion Unstoppable_Potion = with(new UnstoppablePotion(), Names.Potion.UnstoppablePotion);

    public static Potion with(Potion potion, String name) {
        Potions.add(potion);
        return Names.setRegisterName(potion, name).
                setPotionName(I18ns.prefixPotionUnloc(name));
    }

    @SubscribeEvent
    public static void onPotionRegistry(RegistryEvent.Register<Potion> event) {
        IForgeRegistry<Potion> potions = event.getRegistry();
        for (Potion p : Potions) {
            potions.register(p);
        }
    }
}
