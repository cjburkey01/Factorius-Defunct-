package com.cjburkey.factorius.object;

import org.joml.Vector3f;
import com.cjburkey.factorius.render.object.Mesh;

public class GameObject {
	
	private final Vector3f position = new Vector3f();
	private final Vector3f rotation = new Vector3f();
	private float scale;
	private Mesh mesh;
	
	public GameObject(Vector3f position, Vector3f rotation, float scale, Mesh mesh) {
		if(position != null) setPosition(position);
		if(rotation != null) setRotation(rotation);
		setScale(scale);
		setMesh(mesh);
	}
	
	public GameObject(Vector3f position, Vector3f rotation, Mesh mesh) {
		this(position, rotation, 1.0f, mesh);
	}
	
	public GameObject(Vector3f position, Mesh mesh) {
		this(position, null, 1.0f, mesh);
	}
	
	public GameObject(Mesh mesh) {
		this(null, null, 1.0f, mesh);
	}
	
	public void render() {
		if(mesh != null) {
			if(!mesh.isBuilt()) {
				mesh.build();
			}
			mesh.render();
		}
	}
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
	
	public void setPosition(Vector3f position) {
		setPosition(position.x, position.y, position.z);
	}
	
	public void setRotation(Vector3f rotation) {
		setRotation(rotation.x, rotation.y, rotation.z);
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
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public float getScale() {
		return scale;
	}
	
}