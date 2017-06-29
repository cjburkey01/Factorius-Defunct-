package com.cjburkey.factorius.block;

/**
 * The default stone block
 * @author cjburkey
 */
public final class BlockStone extends Block {
	
	/**
	 * Gets the unlocalized name.
	 * Overridden to return "block_stone".
	 * @return The unlocalized name.
	 */
	public String getUnlocalizedName() {
		return "block_stone";
	}
	
	/**
	 * Gets whether or not the block is a full cube or not.
	 * Overridden to return true.
	 * @return Full block.
	 */
	public boolean isFullBlock() {
		return true;
	}
	
}