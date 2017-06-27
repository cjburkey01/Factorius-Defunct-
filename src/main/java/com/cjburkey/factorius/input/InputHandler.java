package com.cjburkey.factorius.input;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.factorius.window.Window;

public class InputHandler {

	private final Queue<Integer> downKeys;
	private final Queue<Integer> upKeys;
	private final Queue<Integer> pressedKeys;
	private boolean init = false;
	
	public InputHandler() {
		downKeys = new ConcurrentLinkedQueue<>();
		upKeys = new ConcurrentLinkedQueue<>();
		pressedKeys = new ConcurrentLinkedQueue<>();
	}
	
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
	
	public void gameTick() {
		downKeys.clear();
		upKeys.clear();
	}
	
	public boolean keyDown(Integer key) {
		if(init) {
			return downKeys.contains(key);
		}
		return false;
	}
	
	public boolean keyUp(Integer key) {
		if(init) {
			return upKeys.contains(key);
		}
		return false;
	}
	
	public boolean keyPressed(Integer key) {
		if(init) {
			return pressedKeys.contains(key);
		}
		return false;
	}
	
}