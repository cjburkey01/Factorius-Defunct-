package com.cjburkey.factorius;

import org.lwjgl.Version;
import com.cjburkey.factorius.game.GameLogicHandler;
import com.cjburkey.factorius.io.Resources;
import com.cjburkey.factorius.window.Window;

public final class Factorius {
	
	public static Factorius self;

	private boolean doVsync = true;
	private Window window;
	private Loops loops;
	private GameLogicHandler logic;
	
	public static void main(String[] args) {
		Logger.info("Factorius Version:\t" + Static.FACTORIUS_VERSION);
		Logger.info("LWJGL Version:\t" + Version.getVersion());
		Logger.info("Starting game.");
		self = new Factorius();
		self.start();
		self.end();
		Logger.info("Game closed.");
	}
	
	private void start() {
		init();
		startLoops();
	}
	
	private void init() {
		logic = new GameLogicHandler();
		logic.loadLogic();
		window = new Window();
		window.initGlfw();
		window.createWindow(doVsync);
		window.openWindow();
	}
	
	private void startLoops() {
		loops = new Loops();
		loops.startGame(window);
	}
	
	private void end() {
		window.cleanup();
	}
	
	public GameLogicHandler getLogicHandler() {
		return logic;
	}
	
	public Loops getGameLoops() {
		return loops;
	}
	
}