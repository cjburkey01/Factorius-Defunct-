package com.cjburkey.factorius;

import com.cjburkey.factorius.game.GameLogicHandler;
import com.cjburkey.factorius.lang.LocalizationManager;
import com.cjburkey.factorius.store.GameSettings;
import com.cjburkey.factorius.util.SemVer;
import com.cjburkey.factorius.window.Window;

public final class Factorius {
	
	public static Factorius self;

	private GameSettings settings;
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
	
	public void stopGame() {
		loops.stopGame();
	}
	
	private void start() {
		init();
		startLoops();
	}
	
	private void init() {
		settings = new GameSettings();
		versionCheck();
		localManage = new LocalizationManager();
		localManage.loadLocals();
		logic = new GameLogicHandler();
		logic.loadLogic();
		window = new Window();
		window.initGlfw();
		window.createWindow(doVsync);
		window.openWindow();
	}
	
	private void versionCheck() {
		Logger.info("Version change check");
		try {
			settings.load();
			Logger.info("  Current version: " + Static.FACTORIUS_VERSION);
			SemVer loaded = settings.getSemVer("LastLoadedVersion");
			if(loaded != null && !loaded.isEmpty()) {
				Logger.info("  Last version: " + loaded);
				Logger.info("  Same version? " + (loaded.equals(Static.FACTORIUS_VERSION) ? "Yes!" : "No! Version changed!"));
			} else {
				Logger.info("  Previous launch version not found.");
			}
			settings.set("LastLoadedVersion", Static.FACTORIUS_VERSION);
			settings.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
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