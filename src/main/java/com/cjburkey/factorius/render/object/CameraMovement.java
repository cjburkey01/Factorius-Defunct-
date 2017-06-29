package com.cjburkey.factorius.render.object;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.factorius.input.InputHandler;
import com.cjburkey.factorius.math.MathUtils;
import com.cjburkey.factorius.render.Camera;
import com.cjburkey.factorius.window.Window;

/**
 * Controls movement of the camera.
 * Will be removed when player is added.
 * @author cjburkey
 */
public final class CameraMovement {

	private final Vector2d cursorCurrent;
	private final Vector2d cursorPrev;
	private final Vector2f cursorDelta;
	private final Vector3f camChange;
	private final Vector2d center;
	
	private final Camera camera;
	
	private final float rotationSpeed;
	
	/**
	 * Instantiate camera movement.
	 * @param camera The camera.
	 * @param movementSpeed The movement speed.
	 * @param rotationSpeed The rotation speed.
	 */
	public CameraMovement(Camera camera, float movementSpeed, float rotationSpeed) {
		cursorCurrent = new Vector2d();
		cursorPrev = new Vector2d();
		cursorDelta = new Vector2f();
		camChange = new Vector3f();
		center = new Vector2d();
		this.camera = camera;
		this.rotationSpeed = rotationSpeed;
	}
	
	/**
	 * Called to initialize the camera movement.
	 * @param window
	 */
	public void init(Window window) {
		center.set(new Vector2d((double) window.getWidth() / 2.0d, (double) window.getHeight() / 2.0d));
		
		GLFW.glfwSetCursorPosCallback(window.getIdentity(), (win, x, y) -> {
			cursorCurrent.set(x, y);
		});
		
		GLFW.glfwSetInputMode(window.getIdentity(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	/**
	 * Called every frame-render.
	 * @param window The main GLFW window.
	 * @param input The main input handler.
	 */
	public void render(Window window, InputHandler input) {
		double deltaX = cursorCurrent.x - cursorPrev.x;
		double deltaY = cursorCurrent.y - cursorPrev.y;
		if(cursorCurrent.x != cursorPrev.x) {
			cursorPrev.x = cursorCurrent.x;
			camera.rotate(0.0f, (float) deltaX * rotationSpeed, 0.0f);
		}
		if(cursorCurrent.y != cursorPrev.y) {
			cursorPrev.y = cursorCurrent.y;
			camera.rotate((float) deltaY * rotationSpeed, 0.0f, 0.0f);
		}
		camera.getRotation().x = MathUtils.clamp(camera.getRotation().x, -90, 90);
		doMovement(input);
	}
	
	/**
	 * Called to do WASD movement.
	 * @param input The main input handler.
	 */
	private void doMovement(InputHandler input) {
		camChange.set(0, 0, 0);
		if(input.keyPressed(GLFW.GLFW_KEY_W)) {
			camChange.z -= 1;
		}
		if(input.keyPressed(GLFW.GLFW_KEY_S)) {
			camChange.z += 1;
		}
		
		if(input.keyPressed(GLFW.GLFW_KEY_A)) {
			camChange.x -= 1;
		}
		if(input.keyPressed(GLFW.GLFW_KEY_D)) {
			camChange.x += 1;
		}

		if(input.keyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			camChange.y -= 1;
		}
		if(input.keyPressed(GLFW.GLFW_KEY_SPACE)) {
			camChange.y += 1;
		}
	}
	
	/**
	 * Change of cursor in a frame.
	 * @return Delta.
	 */
	public Vector2f getCursorDelta() {
		return cursorDelta;
	}
	
	/**
	 * Camera change.
	 * @return Change.
	 */
	public Vector3f getCameraChange() {
		return camChange;
	}
	
}