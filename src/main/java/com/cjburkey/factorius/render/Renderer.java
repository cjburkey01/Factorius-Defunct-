package com.cjburkey.factorius.render;

import com.cjburkey.factorius.io.Resources;
import com.cjburkey.factorius.render.object.Mesh;
import com.cjburkey.factorius.render.shader.ShaderProgram;

public class Renderer {
	
	private ShaderProgram basicShader;
	
	public void init() {
		try {
			basicShader = new ShaderProgram();
			basicShader.createVertex(Resources.getResourceAsString("factorius:shader/basic/basic.vs"));
			basicShader.createFragment(Resources.getResourceAsString("factorius:shader/basic/basic.fs"));
			basicShader.link();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(Mesh mesh) {
		basicShader.bind();
		mesh.render();
		ShaderProgram.unbind();
	}
	
	public void cleanup() {
		basicShader.cleanup();
	}
	
}