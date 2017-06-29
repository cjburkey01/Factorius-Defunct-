package com.cjburkey.factorius.chunk;

import org.joml.Vector3i;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.block.Block;

/**
 * Stores information about each chunk in the world.
 * @author cjburkey
 */
public final class ChunkData {
	
	public static final int CHUNK_SIZE = 16;
	
	private final Vector3i chunkPos;
	private final Block[][][] blocks;
	
	/**
	 * Create chunk data for a chunk at the position
	 * @param pos The CHUNK POSITION of the chunk.
	 */
	public ChunkData(Vector3i pos) {
		chunkPos = new Vector3i(pos);
		blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
	}
	
	/**
	 * Set a block at LOCAL BLOCK POSITION (x, y, z) to be block.
	 * @param x The local x coordinate.
	 * @param y The local y coordinate.
	 * @param z The local z coordinate.
	 * @param block The block to be set.
	 */
	public void setBlockAt(int x, int y, int z, Block block) {
		if(inChunk(x, y, z)) {
			blocks[x][y][z] = block;
			return;
		}
		Logger.warn("Can't set block: outside of chunk: " + x + ", " + y + ", " + z);
	}
	
	/**
	 * Get the block at the LOCAL BLOCK POSITION.
	 * @param x The local x coordinate.
	 * @param y The local y coordinate.
	 * @param z The local z coordinate.
	 * @return Block at the position.
	 */
	public Block getBlockAt(int x, int y, int z) {
		if(inChunk(x, y, z)) {
			return blocks[x][y][z];
		}
		return null;
	}
	
	/**
	 * Gets the CHUNK POSITION of this chunk.
	 * @return The position.
	 */
	public Vector3i getPosition() {
		return new Vector3i(chunkPos);
	}
	
	/**
	 * Gets whether or not the coordinates are in the chunk.
	 * @param x The local x coordinate.
	 * @param y The local y coordinate.
	 * @param z The local z coordinate.
	 * @return Whether or not in chunk.
	 */
	public static boolean inChunk(int x, int y, int z) {
		return x >= 0 && y >= 0 && z >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE && z < CHUNK_SIZE;
	}
	
}