package com.cjburkey.factorius.chunk;

import com.cjburkey.factorius.block.Blocks;

public class ChunkGenerator {
	
	public static void generate(ChunkData chunk) {
		for(int x = 0; x < ChunkData.CHUNK_SIZE; x ++) {
			for(int y = 0; y < ChunkData.CHUNK_SIZE; y ++) {
				for(int z = 0; z < ChunkData.CHUNK_SIZE; z ++) {
					chunk.setBlockAt(x, y, z, Blocks.blockStone);
				}
			}
		}
	}
	
}