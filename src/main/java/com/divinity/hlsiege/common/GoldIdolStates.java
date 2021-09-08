package com.divinity.hlsiege.common;

import net.minecraft.util.IStringSerializable;

public enum GoldIdolStates implements IStringSerializable {
	INACTIVE("inactive"),
	ACTIVE("active"),
	SET_TRUST("trust"),
	NEAR_EXPIRE("expire");

	final String name;
	GoldIdolStates(String _name) {
		name=_name;
	}
	@Override public String getSerializedName() {
		return name;
	}
}
