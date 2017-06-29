package com.cjburkey.factorius.block;

/**
 * Contains information about each block.
 * @author cjburkey
 */
public class Block {
	
	private String unlocalizedName;
	
	/**
	 * Sets the unlocalized name.
	 * @param name The unlocalized name.
	 */
	public void setUnlocalizedName(String name) {
		unlocalizedName = name;
	}
	
	/**
	 * Gets the unlocalized name.
	 * @return The unlocalized name.
	 */
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	/**
	 * Gets whether or not the block is a full cube or not.
	 * @return Full block.
	 */
	public boolean isFullBlock() {
		return true;
	}
	
}