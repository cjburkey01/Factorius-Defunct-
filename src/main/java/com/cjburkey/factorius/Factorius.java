package com.cjburkey.factorius;

import com.cjburkey.factorius.game.GameLogicHandler;
import com.cjburkey.factorius.lang.LocalizationManager;
import com.cjburkey.factorius.window.Window;

public final class Factorius {
	
	public static Factorius self;

	private LocalizationManager localManage;
	private boolean doVsync = true;
	private Window window;
	private Loops loops;
	private GameLogicHandler logic;
	
	public static void main(String[] args) {
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
		localManage = new LocalizationManager();
		localManage.loadLocals();
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