package net.liplum.registries;

import net.liplum.MetaData;
import net.liplum.Names;
import net.liplum.lib.FawLocation;
import net.liplum.tileentities.forge.ForgeTE;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class TileEntityRegistry {
    private static final LinkedList<Tuple<Class<? extends TileEntity>, ResourceLocation>>
            TileEntities = new LinkedList<>();

    static {
        with(ForgeTE.class, Names.Block.Forge);
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        for (Tuple<Class<? extends TileEntity>, ResourceLocation> pair : TileEntities) {
            Class<? extends TileEntity> tileEntityClz = pair.getFirst();
            ResourceLocation resourceLocation = pair.getSecond();
            GameRegistry.registerTileEntity(tileEntityClz, resourceLocation);
        }
    }

    private static void with(@NotNull Class<? extends TileEntity> tileEntity, @NotNull String name) {
        TileEntities.addLast(new Tuple<>(tileEntity, new FawLocation(name)));
    }
}
