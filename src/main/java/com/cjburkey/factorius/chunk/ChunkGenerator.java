package com.cjburkey.factorius.chunk;

import com.cjburkey.factorius.block.Blocks;

public class ChunkGenerator {
	
	public static void generate(ChunkData chunk) {
		/*for(int x = 0; x < ChunkData.CHUNK_SIZE; x ++) {
			for(int y = 0; y < ChunkData.CHUNK_SIZE; y ++) {
				for(int z = 0; z < ChunkData.CHUNK_SIZE; z ++) {
					chunk.setBlockAt(x, y, z, (ThreadLocalRandom.current().nextInt(0, 2) == 1) ? Blocks.blockStone : null);
				}
			}
		}*/
		
		chunk.setBlockAt(1, 1, 1, Blocks.blockStone);			// Test block flower
		chunk.setBlockAt(0, 1, 1, Blocks.blockStone);
		chunk.setBlockAt(1, 1, 0, Blocks.blockStone);
		chunk.setBlockAt(2, 1, 1, Blocks.blockStone);
		chunk.setBlockAt(1, 1, 2, Blocks.blockStone);
		chunk.setBlockAt(1, 0, 1, Blocks.blockStone);
		chunk.setBlockAt(1, 2, 1, Blocks.blockStone);
		
		/*for(int x = 0; x < ChunkData.CHUNK_SIZE; x ++) {		// Axes test
			chunk.setBlockAt(x, 0, 0, Blocks.blockStone);
		}
		for(int y = 0; y < ChunkData.CHUNK_SIZE; y ++) {
			chunk.setBlockAt(0, y, 0, Blocks.blockStone);
		}
		for(int z = 0; z < ChunkData.CHUNK_SIZE; z ++) {
			chunk.setBlockAt(0, 0, z, Blocks.blockStone);
		}*/
	}
	
}