package com.cjburkey.factorius.block;

/**
 * Class to store information about default blocks.
 * @author cjburkey
 */
public final class Blocks {
	
	private static boolean init = false;
	
	public static Block blockStone;
	
	/**
	 * Initialize the blocks.
	 */
	public static void init() {
		if(!init) {
			init = true;
			
			blockStone = new BlockStone();
		}
	}
	
}