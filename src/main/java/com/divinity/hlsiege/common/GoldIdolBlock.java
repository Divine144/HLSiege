package com.divinity.hlsiege.common;

import com.divinity.hlsiege.init.HlSiegeBlockStates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.ComparatorTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class GoldIdolBlock extends ContainerBlock {
	public static final DirectionProperty FACING = DirectionalBlock.FACING;
	public static final EnumProperty<GoldIdolStates> IDOL_STATE = HlSiegeBlockStates.GOLD_IDOL_STATES;

	public GoldIdolBlock() {
		super(Properties.of(Material.STONE));
	}
	// ======================================= BLOCKSTATE MANAGEMENT ===================================================
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, IDOL_STATE);
	}

	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection().getOpposite());
	}

	public @Nonnull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	public @Nonnull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	// ====================================== TILE ENTITY MANAGEMENT ===================================================
	public TileEntity newBlockEntity(@Nonnull IBlockReader p_196283_1_) {
		return new GoldIdolTileEntity();
	}

	// ============================================== EVENTS ===========================================================
	@Override public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray_trace) {
		final int SETBLOCK_TYPE = 3;
		ItemStack stack = player.getItemInHand(hand);
		TileEntity tile_entity = world.getBlockEntity(pos);
		if (tile_entity instanceof GoldIdolTileEntity) {
			GoldIdolTileEntity idol_tile_entity = (GoldIdolTileEntity)tile_entity;
			if ((state.getValue(IDOL_STATE) == GoldIdolStates.INACTIVE || state.getValue(IDOL_STATE) == GoldIdolStates.NEAR_EXPIRE) && stack.getItem() == Items.NETHER_STAR) {
				stack.grow(-1);
				world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundCategory.BLOCKS, .3f, .5f);
				world.setBlock(pos, state.setValue(IDOL_STATE, GoldIdolStates.ACTIVE), SETBLOCK_TYPE);
				idol_tile_entity.activate(player.getUUID());
				return ActionResultType.sidedSuccess(world.isClientSide);
			} else if (state.getValue(IDOL_STATE) == GoldIdolStates.ACTIVE && idol_tile_entity.owners.contains(player.getUUID())) {
				world.setBlock(pos, state.setValue(IDOL_STATE, GoldIdolStates.SET_TRUST), SETBLOCK_TYPE);
				return ActionResultType.sidedSuccess(world.isClientSide);
			} else if (state.getValue(IDOL_STATE) == GoldIdolStates.SET_TRUST) {
				world.setBlock(pos, state.setValue(IDOL_STATE, GoldIdolStates.ACTIVE), SETBLOCK_TYPE);
				idol_tile_entity.owners.add(player.getUUID());
				return ActionResultType.sidedSuccess(world.isClientSide);
			}
		}
		return ActionResultType.FAIL;
	}

}
