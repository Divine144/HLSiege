package com.divinity.hlsiege.init;

import com.divinity.hlsiege.HLSiege;
import com.divinity.hlsiege.common.GoldIdolBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class HlSiegeBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HLSiege.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HLSiege.MODID);

	public static final RegistryObject<Block> GOLD_IDOL_BLOCK = registerBlockWithItem("gold_idol", GoldIdolBlock::new);

	// =============================================== REG ================================================
	protected static RegistryObject<Block> registerBlock(String name, Supplier<Block> block) {
		return BLOCKS.register(name, block);
	}
	protected static RegistryObject<Block> registerBlockWithItem(String name, Supplier<Block> _block) {
		RegistryObject<Block> block = registerBlock(name,_block);
		ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ItemGroup.TAB_MISC)));
		return block;
	}
}
