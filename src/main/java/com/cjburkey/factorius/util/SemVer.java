package com.cjburkey.factorius.util;

import java.io.Serializable;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.Static;

/**
 * Holds semantic versioning information.
 * @author cjburkey
 */
public final class SemVer implements Serializable {
	
	private static final long serialVersionUID = 7840730486471413385L;
	public static final SemVer EMPTY = new SemVer(-1, -1, -1);
	
	private final int major, minor, patch;
	
	/**
	 * Instantiate the version.
	 * @param major Major version.
	 * @param minor Minor version.
	 * @param patch Patch version.
	 */
	public SemVer(int major, int minor, int patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	/**
	 * Gets the major version.
	 * @return Major.
	 */
	public int getMajor() {
		return major;
	}
	
	/**
	 * Gets the minor version.
	 * @return Minor.
	 */
	public int getMinor() {
		return minor;
	}
	
	/**
	 * Gets the patch version.
	 * @return Patch.
	 */
	public int getPatch() {
		return patch;
	}
	
	/**
	 * Gets the hashcode of the object.
	 * @return Hashcode.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		result = prime * result + patch;
		return result;
	}
	
	/**
	 * Checks if this object is the same as supplied object.
	 * @return Same or not.
	 */
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		SemVer other = (SemVer) obj;
		if(major != other.major) return false;
		if(minor != other.minor) return false;
		if(patch != other.patch) return false;
		return true;
	}
	
	/**
	 * Check if the version is unset.
	 * @return Unset.
	 */
	public boolean isEmpty() {
		return equals(EMPTY);
	}
	
	/**
	 * Returns a formatted string of the version.
	 * @return Formatted string.
	 */
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(major);
		out.append(Static.DOT);
		out.append(minor);
		out.append(Static.DOT);
		out.append(patch);
		return out.toString();
	}
	
	/**
	 * Builds a SemVer object from a formatted String.
	 * @param version Formatted string.
	 * @return Built SemVer object.
	 */
	public static SemVer build(String version) {
		String[] split = version.split("\\.");
		if(split.length == 3) {
			try {
				int major = Integer.parseInt(split[0]);
				int minor = Integer.parseInt(split[1]);
				int patch = Integer.parseInt(split[2]);
				return new SemVer(major, minor, patch);
			} catch(Exception e) {
				Logger.info("Couldn't build SemVer object: " + version);
			}
		}
		return EMPTY;
	}
	
}