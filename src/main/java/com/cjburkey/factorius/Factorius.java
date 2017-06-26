package com.cjburkey.factorius;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import com.cjburkey.factorius.window.Window;

public final class Factorius {
	
	private static Factorius self;
	
	private Window window;
	
	public static void main(String[] args) {
		Logger.info("Factorius Version:\t" + Static.FACTORIUS_VERSION);
		Logger.info("LWJGL Version:\t" + Version.getVersion());
		Logger.info("Starting game");
		self = new Factorius();
		self.start();
		self.end();
	}
	
	private void start() {
		init();
		loop();
	}
	
	private void init() {
		window = new Window();
		window.initGlfw();
		window.createWindow();
		window.openWindow();
	}
	
	private void loop() {
		GL.createCapabilities();
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		while(!window.shouldClose()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			window.perLoop();
		}
	}
	
	private void end() {
		window.cleanup();
	}
	
}