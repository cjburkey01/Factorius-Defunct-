package com.cjburkey.factorius.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
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
	private Vector3f ambient = new Vector3f(0.2f, 0.2f, 0.2f);
	
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
			basicShader.createUniform("ambientLight");
			basicShader.createUniform("blockLight");
			
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(Window window, Camera camera, GameObject object, float light) {
		if(object != null && object.getMesh() != null) {
			if(object.getMesh().hasShader()) {
				object.getMesh().getShader().bind();
			} else {
				basicShader.bind();
			}
			
			projectionMatrix = transformation.getProjectionMatrix(FOV, window, NEAR, FAR);
			basicShader.setUniform("projectionMatrix", projectionMatrix);
			basicShader.setUniform("blockLight", new Vector3f(light, light, light));
			basicShader.setUniform("ambientLight", ambient);
			
			viewMatrix = transformation.getViewMatrix(camera);
			basicShader.setUniform("texture_sampler", 0);

			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(object, viewMatrix);
			basicShader.setUniform("modelViewMatrix", modelViewMatrix);
			
			object.render();
			
			ShaderProgram.unbind();
		}
	}
	
	public void cleanup() {
		basicShader.cleanup();
	}
	
}