package com.cjburkey.factorius.game;

import com.cjburkey.factorius.window.Window;

/**
 * Interface to outline game logic.
 * @author cjburkey
 */
public interface IGameLogic {
	
	/**
	 * Called when the game loop begins.
	 */
	void gameInit();
	
	/**
	 * Called every game tick.
	 */
	void gameTick();
	
	/**
	 * Called when the game loop ends.
	 */
	void gameCleanup();
	
	/**
	 * Called when the render loop beings.
	 * @param window
	 */
	void renderInit(Window window);
	
	/**
	 * Called every render update.
	 * @param window
	 */
	void renderUpdate(Window window);
	
	/**
	 * Called when the render loop ends.
	 * @param window
	 */
	void renderCleanup(Window window);
	
}