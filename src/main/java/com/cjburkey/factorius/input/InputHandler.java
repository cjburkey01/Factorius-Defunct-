package com.cjburkey.factorius.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.factorius.window.Window;

/**
 * Handles input for the game.
 * @author cjburkey
 */
public final class InputHandler {

	private final Queue<Integer> downKeys;
	private final Queue<Integer> upKeys;
	private final Queue<Integer> pressedKeys;
	private boolean init = false;
	
	/**
	 * Instantiates the handler.
	 */
	public InputHandler() {
		downKeys = new ConcurrentLinkedQueue<>();
		upKeys = new ConcurrentLinkedQueue<>();
		pressedKeys = new ConcurrentLinkedQueue<>();
	}
	
	/**
	 * Called when the game is initialized.
	 * @param window The main GLFW window.
	 */
	public void renderInit(Window window) {
		if(!init) {
			init = true;
			GLFW.glfwSetKeyCallback(window.getIdentity(), (winId, key, code, action, mods) -> {
				if(action == GLFW.GLFW_PRESS) {
					downKeys.add(key);
					upKeys.remove(key);
					pressedKeys.add(key);
				} else if(action == GLFW.GLFW_RELEASE) {
					downKeys.remove(key);
					upKeys.add(key);
					pressedKeys.remove(key);
				}
			});
		}
	}
	
	/**
	 * Called every game tick.
	 */
	public void gameTick() {
		downKeys.clear();
		upKeys.clear();
	}
	
	/**
	 * Gets whether or not a key was pressed down this frame.
	 * @param key The GLFW key code.
	 * @return Pressed.
	 */
	public boolean keyDown(Integer key) {
		if(init) {
			return downKeys.contains(key);
		}
		return false;
	}
	
	/**
	 * Gets whether or not a key was released down this frame.
	 * @param key The GLFW key code.
	 * @return Released.
	 */
	public boolean keyUp(Integer key) {
		if(init) {
			return upKeys.contains(key);
		}
		return false;
	}
	
	/**
	 * Gets whether or not a key is currently down.
	 * @param key The GLFW key code.
	 * @return Held.
	 */
	public boolean keyPressed(Integer key) {
		if(init) {
			return pressedKeys.contains(key);
		}
		return false;
	}
	
}