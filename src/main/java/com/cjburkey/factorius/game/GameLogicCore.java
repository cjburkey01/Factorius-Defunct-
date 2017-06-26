package com.cjburkey.factorius.game;

import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL11;
import com.cjburkey.factorius.Factorius;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.Numbers;
import com.cjburkey.factorius.Static;
import com.cjburkey.factorius.object.GameObject;
import com.cjburkey.factorius.render.Renderer;
import com.cjburkey.factorius.render.object.Mesh;
import com.cjburkey.factorius.window.Window;
import com.cjburkey.factorius.world.World;

public class GameLogicCore implements IGameLogic {
	
	private Renderer renderer;
	private World world;
	
	private float[] verts = {
			-0.5f, 0.5f, 0.5f,		// V0
			-0.5f, -0.5f, 0.5f,		// V1
			0.5f, -0.5f, 0.5f,		// V2
			0.5f, 0.5f, 0.5f,		// V3
			-0.5f, 0.5f, -0.5f,		// V4
			0.5f, 0.5f, -0.5f,		// V5
			-0.5f, -0.5f, -0.5f,	// V6
			0.5f, -0.5f, -0.5f		// V7
	};
	
	private int[] tris = {
			0, 1, 3, 3, 1, 2,
			4, 0, 3, 5, 4, 3,
			3, 2, 7, 5, 3, 7,
			6, 1, 0, 6, 0, 4,
			2, 1, 6, 2, 6, 7,
			7, 6, 4, 7, 4, 5
	};
	
	private Mesh triangleTest;
	
	public GameLogicCore() {
		world = new World();
	}
	
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
		
		triangleTest = new Mesh(verts, tris);
		triangleTest.build();
		world.addObjectToWorld(new GameObject(new Vector3f(0.0f, 0.0f, -1.35f), triangleTest));
		
		renderer = new Renderer();
		renderer.init();
	}

	public void renderUpdate(Window window) {
		window.setTitle(buildWindowTitle(window));
		GameObject[] objs = world.getObjectsInWorld();
		for(GameObject obj : objs) {
			renderer.render(window, obj);
		}
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
		out.append(Numbers.format(window.getWidth()));
		out.append('x');
		out.append(Numbers.format(window.getHeight()));
		return out.toString();
	}
	
}