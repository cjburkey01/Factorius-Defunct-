package com.cjburkey.factorius.chunk;

import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector3f;
import com.cjburkey.factorius.block.Block;
import com.cjburkey.factorius.render.Texture;
import com.cjburkey.factorius.render.object.Mesh;

/**
 * Controls OpenGL representations of chunks.
 * @author cjburkey
 */
public final class MeshChunk {
	
	private static final Vector3f up = new Vector3f(0, 1, 0);
	private static final Vector3f right = new Vector3f(1, 0, 0);
	private static final Vector3f forward = new Vector3f(0, 0, 1);
	
	/**
	 * Returns a mesh for a chunk.
	 * @param chunk The chunk to render.
	 * @return The mesh created from the chunk.
	 */
	public static Mesh buildChunkMesh(ChunkData chunk) {
		List<Vector3f> verts = new ArrayList<>();
		List<Integer> tris = new ArrayList<>();
		List<Vector2f> uvs = new ArrayList<>();
		
		for(int x = 0; x < ChunkData.CHUNK_SIZE; x ++) {
			for(int y = 0; y < ChunkData.CHUNK_SIZE; y ++) {
				for(int z = 0; z < ChunkData.CHUNK_SIZE; z ++) {
					Block at = chunk.getBlockAt(x, y, z);
					if(at == null) {
						continue;
					}
					addBlock(at, x, y, z, chunk, verts, tris, uvs);
				}
			}
		}
		
		float[] outVerts = new float[verts.size() * 3];
		int[] outTris = new int[tris.size()];
		float[] outUvs = new float[uvs.size() * 2];
		
		int i = 0;
		for(Vector3f f : verts) {
			outVerts[i * 3] = f.x;
			outVerts[i * 3 + 1] = f.y;
			outVerts[i * 3 + 2] = f.z;
			i ++;
		}
		
		i = 0;
		for(Integer f : tris) {
			outTris[i] = f;
			i ++;
		}
		
		i = 0;
		for(Vector2f f : uvs) {
			outUvs[i * 2] = f.x;
			outUvs[i * 2 + 1] = f.y;
			i ++;
		}
		
		return new Mesh(outVerts, outTris, outUvs, new Texture("factorius:texture/block/block_stone.png"));
	}
	
	/**
	 * Adds a single block to the mesh.
	 * @param block The block to add.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 * @param chunk The current chunk.
	 * @param verts The list of vertices.
	 * @param tris The list of triangles.
	 * @param uvs The list of texture-coordinates.
	 */
	private static void addBlock(Block block, int x, int y, int z, ChunkData chunk, List<Vector3f> verts, List<Integer> tris, List<Vector2f> uvs) {
		if(!fullBlockAt(chunk, x - 1, y, z)) {
			addFace(block, new Vector3f(x, y, z), up, forward, false, verts, tris, uvs);		// Left
		}
		if(!fullBlockAt(chunk, x + 1, y, z)) {
			addFace(block, new Vector3f(x + 1, y, z), up, forward, true, verts, tris, uvs);		// Right
		}
		
		if(!fullBlockAt(chunk, x, y - 1, z)) {
			addFace(block, new Vector3f(x, y, z), forward, right, false, verts, tris, uvs);		// Bottom
		}
		if(!fullBlockAt(chunk, x, y + 1, z)) {
			addFace(block, new Vector3f(x, y + 1, z), forward, right, true, verts, tris, uvs);	// Top
		}
		
		if(!fullBlockAt(chunk, x, y, z - 1)) {
			addFace(block, new Vector3f(x, y, z), up, right, true, verts, tris, uvs);			// Front
		}
		if(!fullBlockAt(chunk, x, y, z + 1)) {
			addFace(block, new Vector3f(x, y, z + 1), up, right, false, verts, tris, uvs);		// Back
		}
	}
	
	/**
	 * Adds a single face to the mesh.
	 * @param block The block to add.
	 * @param corner The corner of the face.
	 * @param up The upwards facing face.
	 * @param right The right facing face.
	 * @param rev Whether or not to reverse the drawing order.
	 * @param verts The list of vertices.
	 * @param tris The list of triangles.
	 * @param uvs The list of texture-coordinates.
	 */
	private static void addFace(Block block, Vector3f corner, Vector3f up, Vector3f right, boolean rev, List<Vector3f> verts, List<Integer> tris, List<Vector2f> uvs) {
		int index = verts.size();
		
		Vector3f v0 = new Vector3f(corner);
		Vector3f v1 = new Vector3f(corner).add(up);
		Vector3f v2 = new Vector3f(corner).add(up).add(right);
		Vector3f v3 = new Vector3f(corner).add(right);
		
		verts.add(v0);
		verts.add(v1);
		verts.add(v2);
		verts.add(v3);
		
		if(rev) {
			tris.add(index + 0);
			tris.add(index + 1);
			tris.add(index + 2);
			tris.add(index + 2);
			tris.add(index + 3);
			tris.add(index + 0);
		} else {
			tris.add(index + 1);
			tris.add(index + 0);
			tris.add(index + 2);
			tris.add(index + 3);
			tris.add(index + 2);
			tris.add(index + 0);
		}
		
		uvs.add(new Vector2f(0.0f, 0.0f));
		uvs.add(new Vector2f(0.0f, 1.0f));
		uvs.add(new Vector2f(1.0f, 1.0f));
		uvs.add(new Vector2f(1.0f, 0.0f));
	}
	
	/**
	 * Gets whether or not there is a full block at LOCAL BLOCK POSITION (x, y, z)
	 * @param chunk The chunk to check.
	 * @param x The local x coordinate.
	 * @param y The local y coordinate.
	 * @param z The local z coordinate.
	 * @return Whether or not the block is whole.
	 */
	private static boolean fullBlockAt(ChunkData chunk, int x, int y, int z) {
		Block b = chunk.getBlockAt(x, y, z);
		if(b == null) return false;
		return b.isFullBlock();
	}
	
}