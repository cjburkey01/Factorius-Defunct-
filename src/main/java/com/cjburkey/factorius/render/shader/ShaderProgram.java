package com.cjburkey.factorius.render.shader;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;
import com.cjburkey.factorius.Logger;

/**
 * Controls shaders.
 * @author cjburkey
 */
public final class ShaderProgram {
	
	private final int progId;
	private final int dumpLength = 1024;
	private final Map<String, Integer> uniforms;
	
	private int vertex;
	private int fragment;
	
	/**
	 * Created the shader program.
	 */
	public ShaderProgram() {
		progId = GL20.glCreateProgram();
		if(progId == 0) {
			Logger.info("Shader program could not be created.");
		}
		uniforms = new HashMap<>();
	}
	
	/**
	 * Create a vertex shader.
	 * @param code The source of the shader.
	 * @throws Exception
	 */
	public void createVertex(String code) throws Exception {
		vertex = createShader(GL20.GL_VERTEX_SHADER, code);
	}
	
	/**
	 * Create a fragment shader.
	 * @param code The source of the shader.
	 * @throws Exception
	 */
	public void createFragment(String code) throws Exception {
		fragment = createShader(GL20.GL_FRAGMENT_SHADER, code);
	}
	
	/**
	 * Links the shader.
	 * @throws Exception
	 */
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
	
	/**
	 * Creates a uniform.
	 * @param name The name of the uniform.
	 * @throws Exception
	 */
	public void createUniform(String name) throws Exception {
		int location = GL20.glGetUniformLocation(progId, name);
		if(location < 0) {
			throw new RuntimeException("Couldn't create uniform: " + name);
		}
		uniforms.put(name, location);
	}
	
	/**
	 * Sets the value of a uniform.
	 * @param name Name.
	 * @param value Matrix4f value.
	 */
	public void setUniform(String name, Matrix4f value) {
		try(MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			value.get(fb);
			GL20.glUniformMatrix4fv(uniforms.get(name), false, fb);
		}
	}
	
	/**
	 * Sets the value of a uniform.
	 * @param name Name.
	 * @param value Integer value.
	 */
	public void setUniform(String name, int value) {
		GL20.glUniform1i(uniforms.get(name), value);
	}
	
	/**
	 * Sets the value of a uniform.
	 * @param name Name.
	 * @param value Float value.
	 */
	public void setUniform(String name, float value) {
		GL20.glUniform1f(uniforms.get(name), value);
	}
	
	/**
	 * Sets the value of a uniform.
	 * @param name Name.
	 * @param value Vector3f value.
	 */
	public void setUniform(String name, Vector3f value) {
		GL20.glUniform3f(uniforms.get(name), value.x, value.y, value.z);
	}
	
	/**
	 * Sets the value of a uniform.
	 * @param name Name.
	 * @param value Vector4f value.
	 */
	public void setUniform(String name, Vector4f value) {
		GL20.glUniform4f(uniforms.get(name), value.x, value.y, value.z, value.w);
	}
	
	/**
	 * Activates this shader program.
	 */
	public void bind() {
		GL20.glUseProgram(progId);
	}
	
	/**
	 * Destroys the shader program.
	 */
	public void cleanup() {
		unbind();
		if(progId != 0) {
			GL20.glDeleteProgram(progId);
			Logger.info("Cleaned up program: " + progId);
		}
	}
	
	/**
	 * Creates a shader.
	 * @param type The type of shader.
	 * @param code The shader source code.
	 * @return The ID of the shader.
	 * @throws Exception
	 */
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
	
	/**
	 * Unbinds the current shader.
	 */
	public static void unbind() {
		GL20.glUseProgram(0);
	}
	
}