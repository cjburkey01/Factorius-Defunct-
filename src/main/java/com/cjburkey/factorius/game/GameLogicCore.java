package com.cjburkey.factorius.game;

import org.lwjgl.Version;
import org.lwjgl.opengl.GL11;
import com.cjburkey.factorius.Factorius;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.Numbers;
import com.cjburkey.factorius.Static;
import com.cjburkey.factorius.render.Renderer;
import com.cjburkey.factorius.render.object.Mesh;
import com.cjburkey.factorius.window.Window;

public class GameLogicCore implements IGameLogic {
	
	private Renderer renderer;
	
	private float[] verts = {
			-0.5f, 0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			0.5f, 0.5f, 0.0f,
			0.5f, 0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			0.5f, -0.5f, 0.0f
	};
	
	private Mesh triangleTest;
	
	// -- LOGIC -- //
	
	public void gameInit() {
		
	}

	public void gameTick() {
		
	}

	public void gameCleanup() {
		
	}
	
	// -- RENDER -- //

	public void renderInit(Window window) {
		Logger.blank();
		Logger.info("Factorius Version:\t" + Static.FACTORIUS_VERSION);
		Logger.info("LWJGL Version:\t" + Version.getVersion());
		Logger.info("OpenGL:\t\t" + GL11.glGetString(GL11.GL_VERSION));
		Logger.blank();
		
		triangleTest = new Mesh(verts);
		triangleTest.build();
		
		renderer = new Renderer();
		renderer.init();
	}

	public void renderUpdate(Window window) {
		window.setTitle(buildWindowTitle(window));
		renderer.render(triangleTest);
	}

	public void renderCleanup(Window window) {
		renderer.cleanup();
		triangleTest.cleanup();
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