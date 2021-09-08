package com.divinity.hlsiege.init;

import com.divinity.hlsiege.common.GoldIdolStates;
import net.minecraft.state.EnumProperty;

public class HlSiegeBlockStates {
	public static final EnumProperty<GoldIdolStates> GOLD_IDOL_STATES = EnumProperty.create("gold_idol_states", GoldIdolStates.class);
}
