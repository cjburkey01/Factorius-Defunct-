package com.cjburkey.factorius.block;

public class Block {
	
	private String unlocalizedName;
	
	public void setUnlocalizedName(String name) {
		unlocalizedName = name;
	}
	
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public boolean isFullBlock() {
		return true;
	}
	
}