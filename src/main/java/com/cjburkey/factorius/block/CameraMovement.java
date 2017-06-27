package com.cjburkey.factorius.block;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.Numbers;
import com.cjburkey.factorius.input.InputHandler;
import com.cjburkey.factorius.render.Camera;
import com.cjburkey.factorius.window.Window;

public class CameraMovement {

	private final Vector2d cursorCurrent;
	private final Vector2d cursorPrev;
	private final Vector2f cursorDelta;
	private final Vector3f camChange;
	private final Vector2d center;
	
	private final Camera camera;
	
	public CameraMovement(Camera camera) {
		cursorCurrent = new Vector2d();
		cursorPrev = new Vector2d();
		cursorDelta = new Vector2f();
		camChange = new Vector3f();
		center = new Vector2d();
		this.camera = camera;
	}
	
	public void init(Window window) {
		center.set(new Vector2d((double) window.getWidth() / 2.0d, (double) window.getHeight() / 2.0d));
		
		GLFW.glfwSetCursorPosCallback(window.getIdentity(), (win, x, y) -> {
			cursorCurrent.set(x, y);
		});
		
		GLFW.glfwSetInputMode(window.getIdentity(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	public void render(float rotateSpeed, float moveSpeed, Window window, InputHandler input) {
		double deltaX = cursorCurrent.x - cursorPrev.x;
		double deltaY = cursorCurrent.y - cursorPrev.y;
		
		if(cursorCurrent.x != cursorPrev.x) {
			cursorPrev.x = cursorCurrent.x;
			camera.rotate(0.0f, (float) deltaX * rotateSpeed, 0.0f);
		}
		
		if(cursorCurrent.y != cursorPrev.y) {
			cursorPrev.y = cursorCurrent.y;
			camera.rotate((float) deltaY * rotateSpeed, 0.0f, 0.0f);
		}

		Logger.info(deltaX + " - " + deltaY);
		Logger.info(camera.getRotation().toString(Numbers.getFormat()));
		//GLFW.glfwSetCursorPos(window.getIdentity(), center.x, center.y);
		doMovement(input);
	}
	
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
	
	public Vector2f getCursorDelta() {
		return cursorDelta;
	}
	
	public Vector3f getCameraChange() {
		return camChange;
	}
	
}