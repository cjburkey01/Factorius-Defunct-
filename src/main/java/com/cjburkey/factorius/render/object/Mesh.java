package com.cjburkey.factorius.render.object;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import com.cjburkey.factorius.render.shader.ShaderProgram;

public class Mesh {
	
	private final float[] verts;

	private ShaderProgram shader;
	private int vao;
	private int vbo;
	private int vertexCount;
	
	public Mesh(float[] verts) {
		this.shader = null;
		this.verts = verts;
	}
	
	public Mesh(ShaderProgram shader, float[] verts) {
		this.shader = shader;
		this.verts = verts;
	}
	
	public void build() {
		FloatBuffer vertBuf = MemoryUtil.memAllocFloat(verts.length);
		vertexCount = verts.length / 3;
		vertBuf.put(verts).flip();
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuf, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		MemoryUtil.memFree(vertBuf);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void render() {
		GL30.glBindVertexArray(vao);
		GL20.glEnableVertexAttribArray(0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanup() {
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vbo);
		
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
	
	public ShaderProgram getShader() {
		return shader;
	}
	
}