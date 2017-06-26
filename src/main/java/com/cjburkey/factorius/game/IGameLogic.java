package com.cjburkey.factorius.game;

import com.cjburkey.factorius.window.Window;

public interface IGameLogic {
	
	void gameInit();
	void gameTick();
	void gameCleanup();
	
	void renderInit(Window window);
	void renderUpdate(Window window);
	void renderCleanup(Window window);
	
}