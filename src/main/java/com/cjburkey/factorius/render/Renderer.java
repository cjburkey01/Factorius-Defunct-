package com.cjburkey.factorius.render;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import com.cjburkey.factorius.io.Resources;
import com.cjburkey.factorius.object.GameObject;
import com.cjburkey.factorius.render.shader.ShaderProgram;
import com.cjburkey.factorius.window.Window;

public class Renderer {
	
	private static final float FOV = (float) Math.toRadians(90.0f);
	private static final float NEAR = 0.01f;
	private static final float FAR = 1000.0f;
	
	private ShaderProgram basicShader;
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Transformation transformation;
	
	public void init() {
		try {
			transformation = new Transformation();
			
			basicShader = new ShaderProgram();
			basicShader.createVertex(Resources.getResourceAsString("factorius:shader/basic/basic.vs"));
			basicShader.createFragment(Resources.getResourceAsString("factorius:shader/basic/basic.fs"));
			basicShader.link();
			
			basicShader.createUniform("projectionMatrix");
			basicShader.createUniform("modelViewMatrix");
			basicShader.createUniform("texture_sampler");
			
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(Window window, Camera camera, GameObject object) {
		if(object != null && object.getMesh() != null) {
			projectionMatrix = transformation.getProjectionMatrix(FOV, window, NEAR, FAR);
			viewMatrix = transformation.getViewMatrix(camera);
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(object, viewMatrix);
			
			if(object.getMesh().hasShader()) {
				object.getMesh().getShader().bind();
			} else {
				basicShader.bind();
			}
			basicShader.setUniform("projectionMatrix", projectionMatrix);
			basicShader.setUniform("modelViewMatrix", modelViewMatrix);
			basicShader.setUniform("texture_sampler", 0);
			
			object.render();
			
			ShaderProgram.unbind();
		}
	}
	
	public void cleanup() {
		basicShader.cleanup();
	}
	
}