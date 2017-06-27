package com.cjburkey.factorius.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.object.GameObject;
import com.cjburkey.factorius.window.Window;

public final class Transformation {
	
	private final Matrix4f projectionMatrix;
	private final Matrix4f modelViewMatrix;
	private final Matrix4f viewMatrix;
	
	public Transformation() {
		projectionMatrix = new Matrix4f();
		modelViewMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
		Logger.info("Initialized transformations.");
	}
	
	public Matrix4f getProjectionMatrix(float fov, Window window, float near, float far) {
		return projectionMatrix.identity().perspective(fov, (float) window.getWidth() / (float) window.getHeight(), near, far);
	}
	
	public Matrix4f getModelViewMatrix(GameObject obj, Matrix4f viewMatrix) {
		Vector3f rot = obj.getRotation();
		modelViewMatrix.identity().translate(obj.getPosition()).rotateX((float) Math.toRadians(rot.x)).rotateY((float) Math.toRadians(rot.y));
		modelViewMatrix.rotateZ((float) Math.toRadians(rot.z)).scale(obj.getScale());
		Matrix4f viewCurr = new Matrix4f(viewMatrix);
		return viewCurr.mul(modelViewMatrix);
	}
	
	public Matrix4f getViewMatrix(Camera camera) {
		Vector3f camPos = camera.getPosition();
		Vector3f camRot = camera.getRotation();
		
		viewMatrix.identity().rotate((float) Math.toRadians(camRot.x), new Vector3f(1, 0, 0)).rotate((float) Math.toRadians(camRot.y), new Vector3f(0, 1, 0));
		return viewMatrix.translate(-camPos.x, -camPos.y, -camPos.z);
	}
	
}