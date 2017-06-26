package com.cjburkey.factorius.game;

import com.cjburkey.factorius.Factorius;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.Numbers;
import com.cjburkey.factorius.Static;
import com.cjburkey.factorius.window.Window;

public class GameLogicCore implements IGameLogic {
	
	// -- LOGIC -- //
	
	public void gameInit() {
		Logger.info("Game init");
	}

	public void gameTick() {
		
	}

	public void gameCleanup() {
		Logger.info("Game cleanup");
	}
	
	// -- RENDER -- //

	public void renderInit(Window window) {
		
	}

	public void renderUpdate(Window window) {
		window.setTitle(buildWindowTitle(window));
	}

	public void renderCleanup(Window window) {
		
	}
	
	private String buildWindowTitle(Window window) {
		StringBuilder out = new StringBuilder();
		out.append(Static.WINDOW_TITLE);
		out.append(Static.SPACE);
		out.append(Static.PIPE);
		out.append(Static.SPACE);
		out.append(Static.UPS);
		out.append(Static.COLON);
		out.append(Static.SPACE);
		out.append(Static.SPACE);
		out.append(Numbers.format(Factorius.self.getGameLoops().getUps()));
		out.append(Static.SPACE);
		out.append(Static.PIPE);
		out.append(Static.SPACE);
		out.append(Static.FPS);
		out.append(Static.COLON);
		out.append(Static.SPACE);
		out.append(Numbers.format(Factorius.self.getGameLoops().getFps()));
		out.append(Static.SPACE);
		out.append(Static.PIPE);
		out.append(Static.SPACE);
		out.append(window.getWidth());
		out.append('x');
		out.append(window.getHeight());
		return out.toString();
	}
	
}