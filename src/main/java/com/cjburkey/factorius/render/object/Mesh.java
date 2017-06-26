package com.cjburkey.factorius.render.object;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import com.cjburkey.factorius.render.shader.ShaderProgram;

public class Mesh {
	
	private final float[] verts;
	private final int[] tris;

	private ShaderProgram shader;
	private int coordVao;
	private int coordVbo;
	private int triVbo;
	private int vertexCount;
	private boolean built = false;
	
	public Mesh(float[] verts, int[] tris) {
		this(null, verts, tris);
	}
	
	public Mesh(ShaderProgram shader, float[] verts, int[] tris) {
		this.shader = shader;
		this.verts = verts;
		this.tris = tris;
		vertexCount = tris.length;
	}
	
	public void build() {
		if(!built) {
			built = true;
			FloatBuffer vertBuf = MemoryUtil.memAllocFloat(verts.length);
			vertBuf.put(verts).flip();
			
			IntBuffer triBuf = MemoryUtil.memAllocInt(tris.length);
			triBuf.put(tris).flip();
			
			coordVao = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(coordVao);
			
			coordVbo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, coordVbo);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuf, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
	
			triVbo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, triVbo);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, triBuf, GL15.GL_STATIC_DRAW);
			
			MemoryUtil.memFree(vertBuf);
			MemoryUtil.memFree(triBuf);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			GL30.glBindVertexArray(0);
		}
	}
	
	public void render() {
		GL30.glBindVertexArray(coordVao);
		GL20.glEnableVertexAttribArray(0);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanup() {
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(coordVbo);
		GL15.glDeleteBuffers(triVbo);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(0);
	}
	
	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public boolean hasShader() {
		return shader != null;
	}
	
	public boolean isBuilt() {
		return built;
	}
	
	public ShaderProgram getShader() {
		return shader;
	}
	
}