package com.cjburkey.factorius.util;

public final class SemVer {
	
	private static final SemVer EMPTY = new SemVer(-1, -1, -1);
	
	private final int major, minor, patch;
	
	public SemVer(int major, int minor, int patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	public int getMajor() {
		return major;
	}
	
	public int getMinor() {
		return minor;
	}
	
	public int getPatch() {
		return patch;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		result = prime * result + patch;
		return result;
	}

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
	
	public boolean isEmpty() {
		return equals(EMPTY);
	}

	public static SemVer build(String version) {
		String[] split = version.split("\\.");
		if(split.length == 3) {
			try {
				int major = Integer.parseInt(split[0]);
				int minor = Integer.parseInt(split[1]);
				int patch = Integer.parseInt(split[2]);
				return new SemVer(major, minor, patch);
			} catch(Exception e) {  }
		}
		return EMPTY;
	}
	
}