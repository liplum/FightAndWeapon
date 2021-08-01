package net.liplum.registeies;

import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.entities.GemswordBeam;
import net.liplum.entities.StraightDamageEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class EntityRegister {
    public static final LinkedList<EntityEntry> EntityEntries = new LinkedList<>();
    private static int ID = 0;
    public static final EntityEntry STRAIGHT_DAMAGE_ENTITY = build(
            with(EntityEntryBuilder.create().entity(StraightDamageEntity.class), Names.Entity.StraightDamageEntity, ID++)
                    .tracker(64, 10, true));

    public static final EntityEntry GemswordBeam = build(
            with(EntityEntryBuilder.create().entity(GemswordBeam.class), Names.Entity.GemswordBeam, ID++)
                    .tracker(64, 10, true)
    );

    public static EntityEntry build(EntityEntryBuilder<?> b) {
        EntityEntry e = b.build();
        EntityEntries.addLast(e);
        return e;
    }

    public static EntityEntryBuilder<?> with(EntityEntryBuilder<?> b, String name, int id) {
        return b.id(MetaData.MOD_ID + ":" + name, id).
                name(MetaData.MOD_ID + "." + name);
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<EntityEntry> event) {
        IForgeRegistry<EntityEntry> entityEntries = event.getRegistry();
        for (EntityEntry i : EntityEntries) {
            entityEntries.register(i);
        }
    }
}