package com.cjburkey.factorius.block;

public class Blocks {
	
	private static boolean init = false;
	
	public static Block blockStone;
	
	public static void init() {
		if(!init) {
			init = true;
			
			blockStone = new BlockStone();
		}
	}
	
}