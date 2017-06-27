package com.cjburkey.factorius.block;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import com.cjburkey.factorius.input.InputHandler;
import com.cjburkey.factorius.render.Camera;
import com.cjburkey.factorius.window.Window;

public class CameraMovement {

	private final Vector2d cursorCurrent;
	private final Vector2d cursorPast;
	private final Vector2f cursorDelta;
	private final Vector3f camChange;
	private final Camera camera;
	
	private final Vector2d center;
	
	public CameraMovement(Camera camera) {
		cursorCurrent = new Vector2d();
		cursorPast = new Vector2d();
		cursorDelta = new Vector2f();
		camChange = new Vector3f();
		center = new Vector2d();
		this.camera = camera;
	}
	
	public void init(Window window) {
		center.set(new Vector2d((double) window.getWidth() / 2.0d, (double) window.getHeight() / 2.0d));
		
		GLFW.glfwSetInputMode(window.getIdentity(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
	}
	
	// TODO: FIX FPS CAMERA, IT JITTERS
	public void render(float rotateSpeed, float moveSpeed, Window window, InputHandler input) {
		double[] x = new double[1];
		double[] y = new double[1];
		GLFW.glfwGetCursorPos(window.getIdentity(), x, y);
		cursorCurrent.set(x[0], y[0]);
		
		double deltaX = cursorCurrent.x - center.x;
		double deltaY = cursorCurrent.y - center.y;
		
		boolean rotX = cursorCurrent.x != cursorPast.x;
		boolean rotY = cursorCurrent.y != cursorPast.y;
		
		if(rotX) {
			camera.rotate(0.0f, (float) deltaX * rotateSpeed, 0.0f);
		}
		
		if(rotY) {
			camera.rotate((float) deltaY * rotateSpeed, 0.0f, 0.0f);
		}
		
		cursorPast.set(cursorCurrent);
		
		GLFW.glfwSetCursorPos(window.getIdentity(), center.x, center.y);
		
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