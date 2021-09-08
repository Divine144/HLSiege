package com.divinity.hlsiege.common;

import com.divinity.hlsiege.init.HlSiegeTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class GoldIdolTileEntity extends TileEntity implements ITickableTileEntity {
	public static final int TIME_UNTIL_EXPIERY = 80;
	public static final int TIME_UNTIL_NEAR_EXPIERY = 40;

	public Set<UUID> owners = new HashSet<>();
	public int ticks_active = 0;
	public boolean active = false;
	public boolean warned_near_inactive = false;

	public GoldIdolTileEntity() {
		super(HlSiegeTileEntity.GOLD_IDOL.get());
	}

	public void activate(UUID id) {
		active = true;
		ticks_active = 0;
		owners.add(id);
	}
	@Override public void tick() {
		if (active) ticks_active++;
		if (!warned_near_inactive && ticks_active >= TIME_UNTIL_NEAR_EXPIERY) {
			warned_near_inactive = true;
			setBlock(GoldIdolStates.NEAR_EXPIRE);
		}
		if (ticks_active >= TIME_UNTIL_EXPIERY) {
			ticks_active = 0;
			active = false;
			warned_near_inactive = false;
			owners.clear();
			setBlock(GoldIdolStates.INACTIVE);
		}
	}
	public void setBlock(GoldIdolStates state) {
		if (level==null) return;
		level.setBlock(getBlockPos(),level.getBlockState(getBlockPos()).setValue(GoldIdolBlock.IDOL_STATE,state),3);
	}

	// ====================================== SERIALISATION ===================================================
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		ticks_active = nbt.getInt("active_time");
		active = nbt.getBoolean("active");
		warned_near_inactive = nbt.getBoolean("warned");

		ListNBT listnbt = nbt.getList("owners", 10);
		for(int i = 0; i < listnbt.size(); ++i) {
			UUID player = listnbt.getCompound(i).getUUID("player");
			owners.add(player);
		}
	}

	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		nbt.putInt("active_time",ticks_active);
		nbt.putBoolean("active",active);
		nbt.putBoolean("warned",warned_near_inactive);

		ListNBT listnbt = new ListNBT();
		for(UUID player : owners) {
			CompoundNBT player_nbt = new CompoundNBT();
			player_nbt.putUUID("player",player);
			listnbt.add(player_nbt);
		}
		nbt.put("owners", listnbt);
		return save(nbt);
	}
}
