package com.divinity.hlsiege.init;

import com.divinity.hlsiege.HLSiege;
import com.divinity.hlsiege.common.GoldIdolTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HlSiegeTileEntity {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, HLSiege.MODID);

	public static final RegistryObject<TileEntityType<GoldIdolTileEntity>> GOLD_IDOL = TILE_ENTITIES.register("gold_idol",
			() -> TileEntityType.Builder.of(GoldIdolTileEntity::new, HlSiegeBlocks.GOLD_IDOL_BLOCK.get()).build(null));
}
