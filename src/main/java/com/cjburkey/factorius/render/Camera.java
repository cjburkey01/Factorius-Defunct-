package com.cjburkey.factorius.render;

import org.joml.Vector3f;

/**
 * Stores information about the camera object.
 * @author cjburkey
 */
public final class Camera {
	
	private final Vector3f position;
	private final Vector3f rotation;
	
	/**
	 * Instantiate the camera.
	 */
	public Camera() {
		position = new Vector3f();
		rotation = new Vector3f();
	}
	
	/**
	 * Set the position of the camera.
	 * @param x The world x coordinate.
	 * @param y The world y coordinate.
	 * @param z The world z coordinate.
	 */
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	/**
	 * Set the rotation of the camera.
	 * @param x The world x rotation.
	 * @param y The world y rotation.
	 * @param z The world z rotation.
	 */
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	/**
	 * Changes the position of the camera.
	 * @param x X change.
	 * @param y Y change.
	 * @param z Z change.
	 */
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
	
	/**
	 * Changes the rotation of the camera.
	 * @param x X change.
	 * @param y Y change.
	 * @param z Z change.
	 */
	public void rotate(float x, float y, float z) {
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
		
		if(rotation.x > 360) {
			rotation.x -= 360;
		}
		if(rotation.y > 360) {
			rotation.y -= 360;
		}
		if(rotation.z > 360) {
			rotation.z -= 360;
		}
	}
	
	/**
	 * Gets the position of the camera.
	 * @return Camera position.
	 */
	public Vector3f getPosition() {
		return position;
	}
	
	/**
	 * Gets the rotation of the camera.
	 * @return Camera rotation.
	 */
	public Vector3f getRotation() {
		return rotation;
	}
	
}