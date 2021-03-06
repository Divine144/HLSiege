package com.divinity.hlsiege;

import com.divinity.hlsiege.init.HlSiegeBlocks;
import com.divinity.hlsiege.init.HlSiegeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HLSiege.MODID)
public class HLSiege
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "hlsiege";

    public HLSiege() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        HlSiegeBlocks.BLOCKS.register(modEventBus);
        HlSiegeBlocks.ITEMS.register(modEventBus);
        HlSiegeTileEntity.TILE_ENTITIES.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
