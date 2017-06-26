package com.cjburkey.factorius.render.shader;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;
import com.cjburkey.factorius.Logger;

public final class ShaderProgram {
	
	private final int progId;
	private final int dumpLength = 1024;
	private final Map<String, Integer> uniforms;
	
	private int vertex;
	private int fragment;
	
	public ShaderProgram() {
		progId = GL20.glCreateProgram();
		if(progId == 0) {
			Logger.info("Shader program could not be created.");
		}
		uniforms = new HashMap<>();
	}
	
	public void createVertex(String code) throws Exception {
		vertex = createShader(GL20.GL_VERTEX_SHADER, code);
	}
	
	public void createFragment(String code) throws Exception {
		fragment = createShader(GL20.GL_FRAGMENT_SHADER, code);
	}
	
	public void link() throws Exception {
		GL20.glLinkProgram(progId);
		if(GL20.glGetProgrami(progId, GL20.GL_LINK_STATUS) == 0) {
			throw new RuntimeException("Could not link shader: " + GL20.glGetProgramInfoLog(progId, dumpLength));
		}
		
		if(vertex != 0) {
			GL20.glDetachShader(progId, vertex);
		}
		if(fragment != 0) {
			GL20.glDetachShader(progId, fragment);
		}
		
		GL20.glValidateProgram(progId);
		if(GL20.glGetProgrami(progId, GL20.GL_VALIDATE_STATUS) == 0) {
			Logger.warn("Shader validation warning: " + GL20.glGetProgramInfoLog(progId, dumpLength));
		}
	}
	
	public void createUniform(String name) throws Exception {
		int location = GL20.glGetUniformLocation(progId, name);
		if(location < 0) {
			throw new RuntimeException("Couldn't create uniform: " + name);
		}
		uniforms.put(name, location);
	}
	
	public void setUniform(String name, Matrix4f value) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			value.get(fb);
			GL20.glUniformMatrix4fv(uniforms.get(name), false, fb);
		}
	}
	
	public void bind() {
		GL20.glUseProgram(progId);
	}
	
	public void cleanup() {
		unbind();
		if(progId != 0) {
			GL20.glDeleteProgram(progId);
			Logger.info("Cleaned up program: " + progId);
		}
	}
	
	private int createShader(int type, String code) throws Exception {
		int shaderId = GL20.glCreateShader(type);
		if(shaderId == 0) {
			throw new RuntimeException("Could not create shader");
		}
		
		GL20.glShaderSource(shaderId, code);
		GL20.glCompileShader(shaderId);
		
		if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
			throw new RuntimeException("Could not compile shader: " + GL20.glGetShaderInfoLog(shaderId, dumpLength));
		}
		
		GL20.glAttachShader(progId, shaderId);
		return shaderId;
	}
	
	public static void unbind() {
		GL20.glUseProgram(0);
	}
	
}