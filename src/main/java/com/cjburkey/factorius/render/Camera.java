package com.cjburkey.factorius.render;

import org.joml.Vector3f;

public class Camera {
	
	private final Vector3f position;
	private final Vector3f rotation;
	
	public Camera() {
		position = new Vector3f();
		rotation = new Vector3f();
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public void move(float x, float y, float z) {
		if(z != 0) {
			position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
			position.z += (float)Math.cos(Math.toRadians(rotation.y)) * z;
		}
		if(x != 0) {
			position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
			position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * x;
		}
		position.y += y;
	}
	
	public void rotate(float x, float y, float z) {
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
}