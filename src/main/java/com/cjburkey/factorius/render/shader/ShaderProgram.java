package com.cjburkey.factorius.render.shader;

import org.lwjgl.opengl.GL20;
import com.cjburkey.factorius.Logger;

public class ShaderProgram {
	
	private final int progId;
	
	private int vertex;
	private int fragment;
	
	public ShaderProgram() {
		progId = GL20.glCreateProgram();
		if(progId == 0) {
			Logger.info("Shader program could not be created.");
		}
	}
	
}