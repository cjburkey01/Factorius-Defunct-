package com.cjburkey.factorius.render.object;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import com.cjburkey.factorius.render.Texture;
import com.cjburkey.factorius.render.shader.ShaderProgram;

/**
 * Creates and renders every game mesh.
 * @author cjburkey
 */
public final class Mesh {
	
	private final float[] verts;
	private final int[] tris;
	private final float[] uv;
	private final Texture texture;

	private ShaderProgram shader;
	private int coordVao;
	private int coordVbo;
	private int triVbo;
	private int uvVbo;
	private int vertexCount;
	private boolean built = false;
	
	/**
	 * Create a mesh.
	 * @param verts Array of vertices.
	 * @param tris Array of triangles.
	 * @param uv Array of texture coordinates.
	 * @param texture The mesh texture.
	 */
	public Mesh(float[] verts, int[] tris, float[] uv, Texture texture) {
		this(null, verts, tris, uv, texture);
	}
	
	/**
	 * Create a mesh.
	 * @param shader The shader to use.
	 * @param verts Array of vertices.
	 * @param tris Array of triangles.
	 * @param uv Array of texture coordinates.
	 * @param texture The mesh texture.
	 */
	public Mesh(ShaderProgram shader, float[] verts, int[] tris, float[] uv, Texture texture) {
		this.shader = shader;
		this.verts = verts;
		this.tris = tris;
		this.uv = uv;
		this.texture = texture;
		vertexCount = tris.length;
	}
	
	/**
	 * Builds the mesh on the GPU.
	 */
	public void build() {
		if(!built) {
			built = true;
			FloatBuffer vertBuf = MemoryUtil.memAllocFloat(verts.length);
			vertBuf.put(verts).flip();
			
			IntBuffer triBuf = MemoryUtil.memAllocInt(tris.length);
			triBuf.put(tris).flip();
			
			FloatBuffer uvBuf = MemoryUtil.memAllocFloat(uv.length);
			uvBuf.put(uv).flip();
			
			coordVao = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(coordVao);
			
			coordVbo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, coordVbo);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuf, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
	
			triVbo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, triVbo);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, triBuf, GL15.GL_STATIC_DRAW);
	
			uvVbo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvVbo);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvBuf, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
			
			MemoryUtil.memFree(vertBuf);
			MemoryUtil.memFree(triBuf);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			GL30.glBindVertexArray(0);
		}
	}
	
	/**
	 * Renders the mesh.
	 */
	public void render() {
		if(texture != null) {
			if(!texture.isLoaded()) {
				texture.loadTexture();
			}
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		}
		
		GL30.glBindVertexArray(coordVao);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Destroys the mesh.
	 */
	public void cleanup() {
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(coordVbo);
		GL15.glDeleteBuffers(triVbo);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(0);
	}
	
	/**
	 * Sets the mesh shader.
	 * @param shader Shader.
	 */
	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}
	
	/**
	 * Gets the vertex count.
	 * @return Vertex count.
	 */
	public int getVertexCount() {
		return vertexCount;
	}
	
	/**
	 * Gets whether or not the mesh has its own shader.
	 * @return Shader.
	 */
	public boolean hasShader() {
		return shader != null;
	}
	
	/**
	 * Gets whether or not the mesh has been built.
	 * @return Built.
	 */
	public boolean isBuilt() {
		return built;
	}
	
	/**
	 * Gets the mesh's custom shader.
	 * @return Shader, null if nonexistent.
	 */
	public ShaderProgram getShader() {
		return shader;
	}
	
	/**
	 * Gets the mesh's texture.
	 * @return Mesh's texture.
	 */
	public Texture getTexture() {
		return texture;
	}
	
}