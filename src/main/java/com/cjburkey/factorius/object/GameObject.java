package com.cjburkey.factorius.object;

import org.joml.Vector3f;
import com.cjburkey.factorius.render.object.Mesh;

/**
 * Stores information about individual objects in the world.
 * @author cjburkey
 */
public final class GameObject {
	
	private final Vector3f position = new Vector3f();
	private final Vector3f rotation = new Vector3f();
	private float scale;
	private Mesh mesh;
	
	/**
	 * Instiantiate a game object.
	 * @param position The position of the object.
	 * @param rotation The rotation of the object.
	 * @param scale The scale of the object.
	 * @param mesh The mesh for the object.
	 */
	public GameObject(Vector3f position, Vector3f rotation, float scale, Mesh mesh) {
		if(position != null) setPosition(position);
		if(rotation != null) setRotation(rotation);
		setScale(scale);
		setMesh(mesh);
	}
	
	/**
	 * Instiantiate a game object.
	 * @param position The position of the object.
	 * @param rotation The rotation of the object.
	 * @param mesh The mesh for the object.
	 */
	public GameObject(Vector3f position, Vector3f rotation, Mesh mesh) {
		this(position, rotation, 1.0f, mesh);
	}
	
	/**
	 * Instiantiate a game object.
	 * @param position The position of the object.
	 * @param mesh The mesh for the object.
	 */
	public GameObject(Vector3f position, Mesh mesh) {
		this(position, null, 1.0f, mesh);
	}
	
	/**
	 * Instiantiate a game object.
	 * @param mesh The mesh for the object.
	 */
	public GameObject(Mesh mesh) {
		this(null, null, 1.0f, mesh);
	}
	
	/**
	 * Called each render update.
	 */
	public void render() {
		if(mesh != null) {
			if(!mesh.isBuilt()) {
				mesh.build();
			}
			mesh.render();
		}
	}
	
	/**
	 * Called when the render thread ends.
	 */
	public void renderCleanup() {
		if(mesh != null) {
			mesh.cleanup();
		}
	}
	
	/**
	 * Change the mesh
	 * @param mesh The new mesh.
	 */
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
	/**
	 * Change the position
	 * @param position The new position.
	 */
	public void setPosition(Vector3f position) {
		setPosition(position.x, position.y, position.z);
	}
	
	/**
	 * Change the rotation.
	 * @param rotation The new rotation.
	 */
	public void setRotation(Vector3f rotation) {
		setRotation(rotation.x, rotation.y, rotation.z);
	}
	
	/**
	 * Change the position.
	 * @param x The new x.
	 * @param y The new y.
	 * @param z The new z.
	 */
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	/**
	 * Change the rotation.
	 * @param x The new x.
	 * @param y The new y.
	 * @param z The new z.
	 */
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	/**
	 * Change the scale.
	 * @param scale The new scale.
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	/**
	 * Gets the current mesh.
	 * @return Mesh.
	 */
	public Mesh getMesh() {
		return mesh;
	}
	
	/**
	 * Gets the current position.
	 * @return Position.
	 */
	public Vector3f getPosition() {
		return position;
	}
	
	/**
	 * Gets the current rotation.
	 * @return Rotation.
	 */
	public Vector3f getRotation() {
		return rotation;
	}
	
	/**
	 * Gets the current scale.
	 * @return Scale.
	 */
	public float getScale() {
		return scale;
	}
	
}