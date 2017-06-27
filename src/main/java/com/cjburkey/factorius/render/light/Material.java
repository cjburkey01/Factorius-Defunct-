package com.cjburkey.factorius.render.light;

import org.joml.Vector4f;
import com.cjburkey.factorius.render.Texture;

public class Material {
	
	private static final Vector4f DEFAULT_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	
	private Vector4f ambientColor;
	private Vector4f diffuseColor;
	private Vector4f specularColor;
	private float reflectance;
	private Texture texture;
	
	public Material() {
		ambientColor = DEFAULT_COLOR;
		diffuseColor = DEFAULT_COLOR;
		specularColor = DEFAULT_COLOR;
		reflectance = 0.0f;
		texture = null;
	}
	
	public Material(Vector4f color, float reflectance) {
		this(color, color, color, reflectance, null);
	}
	
	public Material(Texture texture) {
		this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, 0.0f, texture);
	}
	
	public Material(Texture texture, float reflectance) {
		this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, reflectance, texture);
	}

	public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, float reflectance, Texture texture) {
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseColor;
		this.specularColor = specularColor;
		this.reflectance = reflectance;
		this.texture = texture;
	}
	
	public void setAmbientColor(Vector4f ambientColor) {
		this.ambientColor = ambientColor;
	}
	
	public void setDiffuseColor(Vector4f diffuseColor) {
		this.diffuseColor = diffuseColor;
	}
	
	public void setSpecularColor(Vector4f specularColor) {
		this.specularColor = specularColor;
	}
	
	public void setReflectance(float reflectance) {
		this.reflectance = reflectance;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	public Vector4f getAmbientColor() {
		return ambientColor;
	}
	
	public Vector4f getDiffuseColor() {
		return diffuseColor;
	}
	
	public Vector4f getSpecularColor() {
		return specularColor;
	}
	
	public float getReflectance() {
		return reflectance;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public boolean isTextured() {
		return texture != null;
	}
	
}