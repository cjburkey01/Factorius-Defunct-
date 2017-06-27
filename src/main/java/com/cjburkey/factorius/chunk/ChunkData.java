package com.cjburkey.factorius.chunk;

import org.joml.Vector3i;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.block.Block;
import com.cjburkey.factorius.math.MathUtils;

public class ChunkData {
	
	public static final int CHUNK_SIZE = 16;
	
	private final Vector3i chunkPos;
	private final Block[] blocks;
	
	public ChunkData(Vector3i pos) {
		chunkPos = new Vector3i(pos);
		blocks = new Block[CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE];
	}
	
	public void setBlockAt(int x, int y, int z, Block block) {
		if(inChunk(x, y, z)) {
			blocks[MathUtils.coords3dToIndex(CHUNK_SIZE, x, y, z)] = block;
			return;
		}
		Logger.warn("Can't set block: outside of chunk: " + x + ", " + y + ", " + z);
	}
	
	public Block getBlockAt(int x, int y, int z) {
		if(inChunk(x, y, z)) {
			return blocks[MathUtils.coords3dToIndex(CHUNK_SIZE, x, y, z)];
		}
		Logger.warn("Can't retrieve block: outside of chunk: " + x + ", " + y + ", " + z);
		return null;
	}
	
	public boolean inChunk(int x, int y, int z) {
		int i = MathUtils.coords3dToIndex(CHUNK_SIZE, x, y, z);
		if(i >= 0 && i < blocks.length) {
			return true;
		}
		return false;
	}
	
	public Vector3i getPosition() {
		return new Vector3i(chunkPos);
	}
	
}