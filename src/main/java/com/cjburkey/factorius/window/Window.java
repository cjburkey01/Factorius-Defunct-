package com.cjburkey.factorius.window;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;
import com.cjburkey.factorius.Static;

public class Window {
	
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
	}
	
	public void createWindow() {
		width = 300;
		height = 300;
		window = GLFW.glfwCreateWindow(width, height, "Factorius " + Static.FACTORIUS_VERSION, MemoryUtil.NULL, MemoryUtil.NULL);
		if(window == MemoryUtil.NULL) {
			throw new RuntimeException("GLFW window could not be created.");
		}
		
		GLFW.glfwSetKeyCallback(window, (window, key, code, action, mods) -> {
			if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
				GLFW.glfwSetWindowShouldClose(window, true);
			}
		});
		
		centerOnScreen();
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
	}
	
	public void openWindow() {
		GLFW.glfwShowWindow(window);
	}
	
	public void cleanup() {
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
	public void setSize(int width, int height) {
		
	}
	
	public void centerOnScreen() {
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
	}
	
	public void perLoop() {
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}
	
	public long getIdentity() {
		return window;
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
}