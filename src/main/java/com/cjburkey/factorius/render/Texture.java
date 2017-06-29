package com.cjburkey.factorius.render;

import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import com.cjburkey.factorius.io.Resources;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
 * Stores and loads texture information.
 * @author cjburkey
 */
public final class Texture {
	
	private String location;
	private int id;
	private boolean loaded = false;
	
	/**
	 * Create a texture object.
	 * @param location
	 */
	public Texture(String location) {
		this.location = location;
	}
	
	/**
	 * Load the texture into memory.
	 */
	public void loadTexture() {
		if(!loaded) {
			loaded = true;
			try {
				InputStream stream = Resources.getResource(location);
				if(stream != null) {
					PNGDecoder decoder = new PNGDecoder(stream);
					
					ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
					decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
					buffer.flip();
					
					id = GL11.glGenTextures();
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
					GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
					GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Gets whether or not the texture has been loaded.
	 * @return Loaded.
	 */
	public boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Gets the OpenGL-assigned ID of the texture.
	 * @return Texture id.
	 */
	public int getId() {
		return id;
	}
	
}