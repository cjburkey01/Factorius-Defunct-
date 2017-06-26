package com.cjburkey.factorius;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import com.cjburkey.factorius.window.Window;

public final class Loops {
	
	private GLCapabilities caps;
	
	private Thread gameLoop;
	private boolean running = true;
	
	private long lastFrameCheck = 0;
	private long frames = 0;
	private long lastUpdateCheck = 0;
	private long updates = 0;
	
	private long fps = 0;
	private long ups = 0;
	
	public void startGame(Window window) {
		gameLoop = new Thread(() -> gameLoop());
		running = true;
		
		gameLoop.start();
		renderLoop(window);
	}
	
	private void renderLoop(Window window) {
		caps = GL.createCapabilities();
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Logger.info("Capabilities created and clear color set.");
		Factorius.self.getLogicHandler().foreach((e) -> e.renderInit(window));
		
		Logger.info("Starting render loop.");
		while(running) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			window.perLoop();
			if(window.shouldClose()) {
				stopGame();
			}
			Factorius.self.getLogicHandler().foreach((e) -> e.renderUpdate(window));
			
			frames ++;
			long now = System.nanoTime();
			if(now - lastFrameCheck >= Static.NANOS_PER_SECOND) {
				lastFrameCheck = now;
				fps = frames;
				frames = 0;
			}
		}
		Factorius.self.getLogicHandler().foreach((e) -> e.renderCleanup(window));
		Logger.info("Finished render loop.");
	}
	
	private void gameLoop() {
		Factorius.self.getLogicHandler().foreach((e) -> e.gameInit());
		Logger.info("Starting game loop.");
		while(running) {
			Factorius.self.getLogicHandler().foreach((e) -> e.gameTick());
			
			updates ++;
			long now = System.nanoTime();
			if(now - lastUpdateCheck >= Static.NANOS_PER_SECOND) {
				lastUpdateCheck = now;
				ups = updates;
				updates = 0;
			}
		}
		Factorius.self.getLogicHandler().foreach((e) -> e.gameCleanup());
		Logger.info("Finished game loop.");
	}
	
	private void stopGame() {
		running = false;
	}
	
	public long getFps() {
		return fps;
	}
	
	public long getUps() {
		return ups;
	}
	
	public boolean hasCaps() {
		return caps != null;
	}
	
}