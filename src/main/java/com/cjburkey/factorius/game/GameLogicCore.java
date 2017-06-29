package com.cjburkey.factorius.game;

import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import com.cjburkey.factorius.Factorius;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.Numbers;
import com.cjburkey.factorius.Static;
import com.cjburkey.factorius.block.Blocks;
import com.cjburkey.factorius.chunk.ChunkData;
import com.cjburkey.factorius.chunk.ChunkGenerator;
import com.cjburkey.factorius.chunk.MeshChunk;
import com.cjburkey.factorius.input.InputHandler;
import com.cjburkey.factorius.math.MathUtils;
import com.cjburkey.factorius.object.GameObject;
import com.cjburkey.factorius.render.Camera;
import com.cjburkey.factorius.render.Renderer;
import com.cjburkey.factorius.render.object.CameraMovement;
import com.cjburkey.factorius.window.Window;
import com.cjburkey.factorius.world.World;

/**
 * Controlls information about the base game.
 * @author cjburkey
 */
public final class GameLogicCore implements IGameLogic {
	
	private final float cameraSpeed = 0.1f;
	private final float cameraRotateSpeed = 0.5f;
	private float light = 0.2f;
	
	private Renderer renderer;
	private InputHandler input;
	private World world;
	private Camera camera;
	private CameraMovement camMove;
	
	/**
	 * Create an instance of the core game.
	 */
	public GameLogicCore() {
		world = new World();
	}
	
	// -- LOGIC -- //
	
	/**
	 * Called when the game loop begins.
	 */
	public void gameInit() {
		Blocks.init();
		camera = new Camera();
		camera.setPosition(0.0f, 0.0f, -5.0f);
		ChunkData test = new ChunkData(new Vector3i());
		ChunkGenerator.generate(test);
		world.addObjectToWorld(new GameObject(new Vector3f(0.0f, 0.0f, 0.0f), MeshChunk.buildChunkMesh(test)));
	}
	
	/**
	 * Called every game tick.
	 */
	public void gameTick() {
		if(camMove != null) {
			Vector3f move = new Vector3f(camMove.getCameraChange());
			camera.move(move.x * cameraSpeed, move.y * cameraSpeed, move.z * cameraSpeed);
		}
		
		if(input != null) {
			input.gameTick();
			if(input.keyPressed(GLFW.GLFW_KEY_UP)) {
				light += 0.01f;
			} else if(input.keyPressed(GLFW.GLFW_KEY_DOWN)) {
				light -= 0.01f;
			}
			light = MathUtils.clamp(light, 0.0f, 1.0f);
		}
	}
	
	/**
	 * Called when the game loop ends.
	 */
	public void gameCleanup() {
		
	}
	
	// -- RENDER -- //
	
	/**
	 * Called when the render loop beings.
	 * @param window
	 */
	public void renderInit(Window window) {
		Logger.blank();
		Logger.info("Factorius Version:\t" + Static.FACTORIUS_VERSION);
		Logger.info("LWJGL Version:\t" + Version.getVersion());
		Logger.info("OpenGL:\t\t" + GL11.glGetString(GL11.GL_VERSION));
		Logger.blank();
		
		renderer = new Renderer();
		renderer.init();
		input = new InputHandler();
		input.renderInit(window);
		camMove = new CameraMovement(camera, cameraSpeed, cameraRotateSpeed);
		camMove.init(window);
	}
	
	/**
	 * Called every render update.
	 * @param window
	 */
	public void renderUpdate(Window window) {
		if(input.keyUp(GLFW.GLFW_KEY_ESCAPE)) {
			Factorius.self.stopGame();
		}
		window.setTitle(buildWindowTitle(window));
		GameObject[] objs = world.getObjectsInWorld();
		for(GameObject obj : objs) {
			renderer.render(window, camera, obj, light);
		}
		camMove.render(window, input);
	}
	
	/**
	 * Called when the render loop ends.
	 * @param window
	 */
	public void renderCleanup(Window window) {
		renderer.cleanup();
		GameObject[] objs = world.getObjectsInWorld();
		for(GameObject obj : objs) {
			obj.renderCleanup();
		}
	}
	
	/**
	 * Builds a window title from FPS, UPS, and window size.
	 * @param window The window
	 * @return String for window title.
	 */
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