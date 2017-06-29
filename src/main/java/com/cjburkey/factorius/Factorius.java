package com.cjburkey.factorius;

import com.cjburkey.factorius.game.GameLogicHandler;
import com.cjburkey.factorius.lang.LocalizationManager;
import com.cjburkey.factorius.store.GameSettings;
import com.cjburkey.factorius.util.SemVer;
import com.cjburkey.factorius.window.Window;

/**
 * The main game class, controlls game starting.
 * @author cjburkey
 */
public final class Factorius {
	
	public static Factorius self;

	private GameSettings settings;
	private LocalizationManager localManage;
	private boolean doVsync = true;
	private Window window;
	private Loops loops;
	private GameLogicHandler logic;
	
	/**
	 * Main initialization method, called to launch game.
	 * @param args Commandline arguments passed.
	 */
	public static void main(String[] args) {
		Logger.info("Starting game.");
		self = new Factorius();
		self.start();
		self.end();
		Logger.info("Game closed.");
	}
	
	/**
	 * Stops the game loops, game should shut down after another game tick and render update.
	 */
	public void stopGame() {
		loops.stopGame();
	}
	
	/**
	 * Initializes GLFW and starts the game loops.
	 */
	private void start() {
		init();
		startLoops();
	}
	
	/**
	 * Initializes GLFW.
	 */
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
	
	/**
	 * Checks current game version and previously run version, checks if game updated.
	 */
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
	
	/**
	 * Starts game loops.
	 */
	private void startLoops() {
		loops = new Loops();
		loops.startGame(window);
	}
	
	/**
	 * Cleans up GLFW.
	 */
	private void end() {
		window.cleanup();
	}
	
	/**
	 * Gets the game logic manager.
	 * @return The core game logic handler.
	 */
	public GameLogicHandler getLogicHandler() {
		return logic;
	}
	
	/**
	 * Gets the Loops class, which stores information about the running game loops.
	 * @return The loops instance.
	 */
	public Loops getGameLoops() {
		return loops;
	}
	
}