package com.cjburkey.factorius.block;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.factorius.input.InputHandler;
import com.cjburkey.factorius.render.Camera;
import com.cjburkey.factorius.window.Window;

public class CameraMovement {
	
	private final Vector2d previousPos;
	private final Vector2d currentPos;
	private final Vector2f displVec;
	private final Vector3f camChange;
	private final Camera camera;
	
	public CameraMovement(Camera camera) {
		previousPos = new Vector2d(-1, -1);
		currentPos = new Vector2d();
		displVec = new Vector2f();
		camChange = new Vector3f();
		this.camera = camera;
	}
	
	public void init(Window window) {
		GLFW.glfwSetCursorPosCallback(window.getIdentity(), (win, x, y) -> {
			currentPos.x = x;
			currentPos.y = y;
		});
	}
	
	public void render(InputHandler input) {
		displVec.x = 0;
		displVec.y = 0;
		
		double deltaX = currentPos.x - previousPos.x;
		double deltaY = currentPos.y - previousPos.y;
		boolean rotateX = deltaX != 0;
		boolean rotateY = deltaY != 0;
		if(rotateX) {
			displVec.y = (float) deltaX;
		}
		if(rotateY) {
			displVec.x = (float) deltaY;
		}
		
		previousPos.x = currentPos.x;
		previousPos.y = currentPos.y;
		
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
	
	public Vector2f getDisplayVector() {
		return displVec;
	}
	
	public Vector3f getCameraChange() {
		return camChange;
	}
	
}