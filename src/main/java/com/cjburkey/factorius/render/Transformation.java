package com.cjburkey.factorius.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.window.Window;

public final class Transformation {
	
	private final Matrix4f projectionMatrix;
	private final Matrix4f worldMatrix;
	
	public Transformation() {
		projectionMatrix = new Matrix4f();
		worldMatrix = new Matrix4f();
		Logger.info("Initialized transformations.");
	}
	
	public Matrix4f getProjectionMatrix(float fov, Window window, float near, float far) {
		return projectionMatrix.identity().perspective(fov, (float) window.getWidth() / (float) window.getHeight(), near, far);
	}
	
	public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		return worldMatrix.identity().translate(offset).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
	}
	
}