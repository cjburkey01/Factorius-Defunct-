package com.cjburkey.factorius.render.light;

import org.joml.Vector3i;

/**
 * Stores information about point lights(like torches and lamps) for usage later in lighting calculations.
 * (First ever JavaDoc'd class by CJ :D)
 * @author cjburkey
 */
public final class PointLight {
	
	private final Vector3i position;
	private int intensity;
	
	/**
	 * Create a point light at a position.
	 * @param pos The BLOCK POSITION of the light.
	 * @param intensity Range of light, in blocks.
	 */
	public PointLight(Vector3i pos, int intensity) {
		position = new Vector3i();
		if(pos != null) {
			setPosition(pos);
		}
		setIntensity(intensity);
	}
	
	/**
	 * Create a point light at a position with an intensity of 8.
	 * @param pos The BLOCK POSITION of the light.
	 */
	public PointLight(Vector3i pos) {
		this(pos, 8);
	}
	
	/**
	 * Create a point light at (0, 0, 0) with an intensity of 8.
	 * @param intensity Range of light, in blocks.
	 */
	public PointLight() {
		this(null, 8);
	}
	
	/**
	 * Create a point light at (0, 0, 0) with an intensity.
	 * @param intensity Range of light, in blocks.
	 */
	public PointLight(int intensity) {
		this(null, intensity);
	}
	
	/**
	 * Move the point light to this position.
	 * @param pos The new BLOCK POSITION of the light.
	 */
	public void setPosition(Vector3i pos) {
		position.set(pos);
	}
	
	/**
	 * Sets the new intensity of the light.
	 * @param intensity Range of light, in blocks.
	 */
	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
	
	/**
	 * Get the current position of the light.
	 * @return position BLOCK POSITION of the light.
	 */
	public Vector3i getPosition() {
		return new Vector3i(position);
	}
	
	/**
	 * Get the current intensity(range) of the light.
	 * @return intensity The range of the light, in blocks.
	 */
	public int getIntensity() {
		return intensity;
	}
	
}