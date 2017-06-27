package com.cjburkey.factorius.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import com.cjburkey.factorius.io.Resources;
import com.cjburkey.factorius.object.GameObject;
import com.cjburkey.factorius.render.light.PointLight;
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

			basicShader.createUniform("specularPower");
			basicShader.createUniform("ambientLight");
			basicShader.createPointLightUniform("pointLight");
			basicShader.createMaterialUniform("material");
			
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(Window window, Camera camera, GameObject object, Vector3f ambientLight, PointLight pointLight) {
		if(object != null && object.getMesh() != null) {
			if(object.getMesh().hasShader()) {
				object.getMesh().getShader().bind();
			} else {
				basicShader.bind();
			}
			
			projectionMatrix = transformation.getProjectionMatrix(FOV, window, NEAR, FAR);
			basicShader.setUniform("projectionMatrix", projectionMatrix);
			
			viewMatrix = transformation.getViewMatrix(camera);
			
			basicShader.setUniform("ambientLight", ambientLight);
			basicShader.setUniform("specularPower", 10.0f);
			PointLight currentLight = new PointLight(pointLight);
			Vector3f lightPos = currentLight.getPosition();
			Vector4f aux = new Vector4f(lightPos, 1.0f);
			aux.mul(viewMatrix);
			lightPos.x = aux.x;
			lightPos.x = aux.y;
			lightPos.z = aux.z;
			basicShader.setUniform("pointLight", currentLight);
			basicShader.setUniform("texture_sampler", 0);

			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(object, viewMatrix);
			basicShader.setUniform("modelViewMatrix", modelViewMatrix);
			basicShader.setUniform("material", object.getMesh().getMaterial());
			
			object.render();
			
			ShaderProgram.unbind();
		}
	}
	
	public void cleanup() {
		basicShader.cleanup();
	}
	
}