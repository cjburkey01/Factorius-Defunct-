package com.cjburkey.factorius.render.light;

import org.joml.Vector3f;

public class PointLight {
	
	private final Vector3f color;
	private final Vector3f position;
	private float intensity;
	private final Attenuation attenuation;
	
	public PointLight(Vector3f color, Vector3f position, float intensity) {
		this.color = color;
		this.position = position;
		this.intensity = intensity;
		attenuation = new Attenuation(1.0f, 0.0f, 0.0f);
	}

	public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation attenuation) {
		this.color = color;
		this.position = position;
		this.intensity = intensity;
		this.attenuation = attenuation;
	}
	
	public PointLight(PointLight old) {
		color = old.color;
		position = old.position;
		intensity = old.intensity;
		attenuation = old.attenuation;
	}
	
	public void setColor(Vector3f c) {
		color.set(c);
	}
	
	public void setPosition(Vector3f pos) {
		position.set(pos);
	}
	
	public void setIntensity(float inten) {
		intensity = inten;
	}
	
	public void setAttenuation(Attenuation att) {
		attenuation.constant = att.constant;
		attenuation.linear = att.linear;
		attenuation.exponent = att.exponent;
	}

	public Vector3f getColor() {
		return color;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getIntensity() {
		return intensity;
	}

	public Attenuation getAttenuation() {
		return attenuation;
	}

	public static class Attenuation {
		
		private float constant;
		private float linear;
		private float exponent;
		
		public Attenuation(float constant, float linear, float exponent) {
			this.constant = constant;
			this.linear = linear;
			this.exponent = exponent;
		}

		public void setConstant(float constant) {
			this.constant = constant;
		}

		public void setLinear(float linear) {
			this.linear = linear;
		}

		public void setExponent(float exponent) {
			this.exponent = exponent;
		}

		public float getConstant() {
			return constant;
		}

		public float getLinear() {
			return linear;
		}

		public float getExponent() {
			return exponent;
		}
		
	}
	
}