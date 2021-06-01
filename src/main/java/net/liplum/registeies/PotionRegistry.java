package net.liplum.registeies;

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


    public static Potion with(Potion potion, String name) {
        Potions.add(potion);
        return potion.setRegistryName(Names.prefixRegister(name)).
                setPotionName(Names.prefixPotionUnloc(name));
    }

    /**
     * Unstoppable:Who has this potion effect won't be attack by other mobs and players.
     */
    public static final Potion Unstoppable_Potion = with(new UnstoppablePotion(), Names.Potion.UnstoppablePotion);

    @SubscribeEvent
    public static void onPotionRegistry(RegistryEvent.Register<Potion> event) {
        IForgeRegistry<Potion> potions = event.getRegistry();
        for (Potion p : Potions) {
            potions.register(p);
        }
    }
}
