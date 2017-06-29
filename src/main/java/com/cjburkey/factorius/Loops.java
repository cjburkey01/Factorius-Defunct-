package com.cjburkey.factorius;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import com.cjburkey.factorius.window.Window;

/**
 * Controls game loops.
 * @author cjburkey
 */
public final class Loops {
	
	private final int targetUps = 60;
	private final double deltaTiming = (double) Static.NANOS_PER_SECOND / (double) targetUps;
	
	private GLCapabilities caps;
	
	private Thread gameLoop;
	private boolean running = true;
	
	private long lastFrameCheck = 0;
	private long lastUpdateCheck = 0;
	private long updateTimer;
	private long frames = 0;
	private long updates = 0;
	
	private long fps = 0;
	private long ups = 0;
	
	/**
	 * Starts the game loops.
	 * @param window The main GLFW window.
	 */
	public void startGame(Window window) {
		gameLoop = new Thread(() -> gameLoop());
		running = true;
		
		gameLoop.start();
		renderLoop(window);
	}
	
	/**
	 * Starts the OpenGL render loop.
	 * @param window The main GLFW window.
	 */
	private void renderLoop(Window window) {
		caps = GL.createCapabilities();
		GL11.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
		Logger.info("Capabilities created and clear color set.");
		Factorius.self.getLogicHandler().foreach((e) -> e.renderInit(window));
		
		Logger.info("Starting render loop.");
		while(running) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			if(window.shouldClose()) {
				stopGame();
			}
			Factorius.self.getLogicHandler().foreach((e) -> e.renderUpdate(window));

			window.perLoop();
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
	
	/**
	 * Starts the game logic loop.
	 */
	private void gameLoop() {
		Factorius.self.getLogicHandler().foreach((e) -> e.gameInit());
		Logger.info("Starting game loop.");
		while(running) {
			long now = System.nanoTime();
			if(now - updateTimer >= deltaTiming) {
				updateTimer = now;
				tick();
			}
		}
		Factorius.self.getLogicHandler().foreach((e) -> e.gameCleanup());
		Logger.info("Finished game loop.");
	}
	
	/**
	 * Called each game tick.
	 */
	private void tick() {
		Factorius.self.getLogicHandler().foreach((e) -> e.gameTick());
		
		updates ++;
		long now = System.nanoTime();
		if(now - lastUpdateCheck >= Static.NANOS_PER_SECOND) {
			lastUpdateCheck = now;
			ups = updates;
			updates = 0;
		}
	}
	
	/**
	 * Stops the loops.
	 */
	public void stopGame() {
		running = false;
	}
	
	/**
	 * Gets the latest frames-per-second record.
	 * @return fps
	 */
	public long getFps() {
		return fps;
	}
	
	/**
	 * Gets the latest updates-per-second record.
	 * @return ups
	 */
	public long getUps() {
		return ups;
	}
	
	/**
	 * Gets whether or not GLCapabilities have been created yet.
	 * @return created
	 */
	public boolean hasCaps() {
		return caps != null;
	}
	
}