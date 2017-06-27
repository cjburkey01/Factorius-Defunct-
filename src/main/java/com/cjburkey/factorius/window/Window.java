package com.cjburkey.factorius.window;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import com.cjburkey.factorius.Factorius;
import com.cjburkey.factorius.Logger;
import com.cjburkey.factorius.Static;

public final class Window {
	
	private long window;
	private int width, height;

	public void initGlfw() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("GLFW could not be initialized.");
		}
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
		Logger.info("GLFW initialized.");
	}
	
	public void createWindow(boolean vsync) {
		width = 300;
		height = 300;
		window = GLFW.glfwCreateWindow(width, height, "Factorius " + Static.FACTORIUS_VERSION, MemoryUtil.NULL, MemoryUtil.NULL);
		if(window == MemoryUtil.NULL) {
			throw new RuntimeException("GLFW window could not be created.");
		}
		
		GLFW.glfwSetWindowSizeCallback(window, (win, width, height) -> {
			updateSize(width, height);
		});

		halfScreen();
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval((vsync) ? 1 : 0);
		GLFW.glfwFocusWindow(window);
		Logger.info("Window created.");
	}
	
	public void openWindow() {
		GLFW.glfwShowWindow(window);
		Logger.info("Window shown.");
	}
	
	public void cleanup() {
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
		Logger.info("Window cleaned up.");
	}
	
	public void setSize(int width, int height) {
		GLFW.glfwSetWindowSize(window, width, height);
		updateSize(width, height);
		centerOnScreen();
	}
	
	public void centerOnScreen() {
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
	}
	
	public void perLoop() {
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}
	
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
	}
	
	public long getIdentity() {
		return window;
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void halfScreen() {
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		setSize(vidmode.width() / 2, vidmode.height() / 2);
	}
	
	private void updateSize(int width, int height) {
		this.width = width;
		this.height = height;
		if(Factorius.self.getGameLoops() != null && Factorius.self.getGameLoops().hasCaps()) {
			GL11.glViewport(0, 0, width, height);
		}
	}
	
}