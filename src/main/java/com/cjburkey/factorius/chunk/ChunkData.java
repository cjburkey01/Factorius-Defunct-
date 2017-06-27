package com.cjburkey.factorius.chunk;

import org.joml.Vector3i;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.block.Block;

public class ChunkData {
	
	public static final int CHUNK_SIZE = 16;
	
	private final Vector3i chunkPos;
	private final Block[][][] blocks;
	
	public ChunkData(Vector3i pos) {
		chunkPos = new Vector3i(pos);
		blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
	}
	
	public void setBlockAt(int x, int y, int z, Block block) {
		if(inChunk(x, y, z)) {
			blocks[x][y][z] = block;
			return;
		}
		Logger.warn("Can't set block: outside of chunk: " + x + ", " + y + ", " + z);
	}
	
	public Block getBlockAt(int x, int y, int z) {
		if(inChunk(x, y, z)) {
			return blocks[x][y][z];
		}
		return null;
	}
	
	public boolean inChunk(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE && z < CHUNK_SIZE;
	}
	
	public Vector3i getPosition() {
		return new Vector3i(chunkPos);
	}
	
}