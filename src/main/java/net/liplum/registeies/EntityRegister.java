package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.ObjectNames;
import net.liplum.entities.StraightDamageEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class EntityRegister {
    private static int ID = 0;

    public static EntityEntryBuilder<?> with(EntityEntryBuilder<?> b, String name, int id) {
        return b.id(MetaData.MOD_ID + ":" + name, id).
                name(MetaData.MOD_ID + "." + name);
    }

    public static final EntityEntry STRAIGHT_DAMAGE_ENTITY =
            with(EntityEntryBuilder.create().entity(StraightDamageEntity.class), ObjectNames.StraightDamageEntity, ID++)
                    .tracker(64, 10, true).build();

    public static final EntityEntry[] EntityEntries = new EntityEntry[]{
            STRAIGHT_DAMAGE_ENTITY
    };

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<EntityEntry> event) {
        IForgeRegistry<EntityEntry> registry = event.getRegistry();
        registry.registerAll(EntityEntries);
    }
}